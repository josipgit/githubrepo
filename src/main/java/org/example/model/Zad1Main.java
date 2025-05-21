package org.example.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class Zad1Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("library");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {

            Meal meal = new Meal();
            meal.setName("Manistra");

            Ingredient ing1 = new Ingredient();
            ing1.setName("Origano");

            Ingredient ing2 = new Ingredient();
            ing2.setName("Salsa");

            Ingredient ing3 = new Ingredient();
            ing3.setName("Pome");

            meal.addIngredient(ing1);
            meal.addIngredient(ing2);
            meal.addIngredient(ing3);

            em.persist(meal); // Automatski se spremaju i sastojci zbog CascadeType.ALL

            //tx.commit();

            // Dohvaćanje svih jela i ispis
            List<Meal> meals = em.createQuery("SELECT m FROM Meal m", Meal.class).getResultList();
            for (Meal m : meals) {
                System.out.println("Jelo: " + m.getName());
                for (Ingredient i : m.getIngredients()) {
                    System.out.println("Sastojak jela: " + i.getName());
                }
            }

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            tx.commit();
            em.close();
            emf.close();
        }

    } // end main
} // end class


/*

KOMENTARI KODA:

tx.commit();     // 1. Zatvaraš transakciju – potvrđuješ sve promjene
em.close();      // 2. Zatvaraš EntityManager – resurs za rad s bazom
emf.close();     // 3. Zatvaraš EntityManagerFactory – konačno oslobađaš sve povezane resurse
Objašnjenje:
tx.commit() — mora doći prije zatvaranja EntityManagera, jer se transakcija izvršava nad aktivnim EntityManagerom.
em.close() — zatvaraš instancu EntityManagera; nakon toga se više ne može koristiti za operacije nad bazom.
emf.close() — zatvaraš tvornički objekt (EntityManagerFactory) koji inače treba živjeti koliko i
              aplikacija (npr. ako koristiš JPA u web aplikaciji, obično se zatvara prilikom gašenja aplikacije).

Ako se dogodi neka iznimka (npr. greška prilikom persist()),
svakako trebaš napraviti tx.rollback() i osigurati da se em.close()
i emf.close() pozovu u finally bloku da ne ostaviš resurse otvorenima.

EntityTransaction tx = em.getTransaction();
try {
    tx.begin();
    // ... tvoje operacije
    tx.commit();
} catch (Exception e) {
    if (tx.isActive()) {
        tx.rollback();
    }
    e.printStackTrace();
} finally {
    em.close();
    emf.close();
}

To bi bio "robustan" način za rad s JPA. Ako radiš samo testno, tvoj trenutni redoslijed je sasvim u redu.

 */