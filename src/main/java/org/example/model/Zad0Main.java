package org.example.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

import static java.lang.System.out;

public class Zad0Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("library");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {

            tx.begin();

            Author author1 = new Author();
            author1.setName("Pero Perić");
            em.persist(author1);

            Author author2 = new Author();
            author2.setName("Iva Ivić");
            em.persist(author2);

            Book book1 = new Book();
            book1.setTitle("Java");
            book1.getAuthors().add(author1);
            book1.getAuthors().add(author2);
            em.persist(book1);

            Book book2 = new Book();
            book2.setTitle("Hibernate");
            book2.getAuthors().add(author2);
            em.persist(book2);

            //Dohvaćanje knjiga
            List<Book> books = em.createQuery("select b from Book b").getResultList();
            for (Book b : books) {
                System.out.println("Naslov: " + b.getTitle());
                for (Author a : b.getAuthors()) {
                    System.out.println("Authori: " + a.getName());
                }
            }

            Book book = em.find(Book.class, 1L); // pronalaženje knjige
            if (book != null) {
                book.setTitle("Novi naziv knjige"); // ažuriranje naslova knjige
            }

            book = em.find(Book.class, 1L);
            System.out.println("Novi naziv: " + book.getTitle());

            out.println("\n-------------------------------");

            book = em.find(Book.class, 1L); // Pronalaženje knjige
            if (book != null) {
                em.remove(book);  //Brisanje knjige
            }

            for (Book b : books) {
                System.out.println("Naslov: " + b.getTitle());
                for (Author a : b.getAuthors()) {
                    System.out.println("Authori: " + a.getName());
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