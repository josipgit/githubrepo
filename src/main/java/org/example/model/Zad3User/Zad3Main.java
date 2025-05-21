package org.example.model.Zad3User;

import jakarta.persistence.*;

public class Zad3Main {

    // Stvaramo jedinstveni EntityManagerFactory za cijeli životni vijek aplikacije
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("library");

    public static void main(String[] args) {
        // Kreiramo novog korisnika
        User user = new User("ivan123", "ivan@example.com");

        // 1. Perzistiramo (spremamo) korisnika u bazu
        persistUser(user);

        // 2. Odcjepljujemo korisnika iz persistence konteksta
        detachUser(user);

        // 3. Mijenjamo podatke izvan persistence konteksta
        user.setEmail("novi.email@example.com");

        // 4. Ponovno ga spajamo s persistence kontekstom (merge)
        User mergedUser = reattachUser(user);

        // 5. Brišemo korisnika
        deleteUser(mergedUser);

        // Zatvaramo factory na kraju aplikacije
        emf.close();
    }

    // Metoda za perzistenciju (spremanje) korisnika u bazu
    private static void persistUser(User user) {
        EntityManager em = emf.createEntityManager(); // Kreiramo EntityManager
        EntityTransaction transaction = em.getTransaction(); // Dohvaćamo transakciju

        try {
            transaction.begin(); // Početak transakcije
            em.persist(user); // Spremamo entitet u bazu
            transaction.commit(); // Potvrđujemo transakciju
            System.out.println("User persisted successfully!");
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback(); // U slučaju greške, vraćamo transakciju
            e.printStackTrace();
        } finally {
            em.close(); // Zatvaramo EntityManager
        }
    }

    // Metoda za odcjepljivanje korisnika (detachment)
    private static void detachUser(User user) {
        EntityManager em = emf.createEntityManager(); // Novi EntityManager
        try {
            // Ponovno dohvaćamo korisnika iz baze kako bi bio u "managed" stanju
            user = em.find(User.class, user.getId());

            // Odcjepljujemo korisnika (više nije pod nadzorom EntityManagera)
            em.detach(user);
            System.out.println("User detached!");
        } finally {
            em.close(); // Zatvaramo EntityManager
        }
    }

    // Metoda za ponovno spajanje (reattach) korisnika koristeći merge
    private static User reattachUser(User detachedUser) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            // Merge vraća novi "managed" objekt (kopiju s ažuriranim podacima)
            User merged = em.merge(detachedUser);
            transaction.commit();
            System.out.println("User reattached via merge!");
            return merged;
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback(); // U slučaju greške, rollback
            e.printStackTrace();
            return null;
        } finally {
            em.close(); // Zatvaramo EntityManager
        }
    }

    // Metoda za brisanje korisnika iz baze
    private static void deleteUser(User user) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            // osiguravamo da je entitet u "managed" stanju prije brisanja
            User managedUser = em.merge(user);
            em.remove(managedUser); // brišemo korisnika
            transaction.commit();
            System.out.println("User deleted successfully!");
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback(); // rollback u slučaju greške
            e.printStackTrace();
        } finally {
            em.close(); // Zatvaramo EntityManager
        }
    }
}
