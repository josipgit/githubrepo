package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Ingredient;
import org.example.model.Meal;

import java.util.List;

import static java.lang.System.out;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
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
                book.setTitle("Novi naziv knjige"); // ažuriranje naslova knjig
            }

            book = em.find(Book.class, 1L);
            System.out.println("Novi naziv: " + book.getTitle());

            out.println("----------------------------------------------------------");

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

            out.println("\n\n----------------------------------------------------------");

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

tx.commit();     // 1. Zatvaraš transakciju – potvrđuješ sve promjene
em.close();      // 2. Zatvaraš EntityManager – resurs za rad s bazom
emf.close();     // 3. Zatvaraš EntityManagerFactory – konačno oslobađaš sve povezane resurse
Objašnjenje:
tx.commit() — mora doći prije zatvaranja EntityManagera, jer se transakcija izvršava nad aktivnim EntityManagerom.
em.close() — zatvaraš instancu EntityManagera; nakon toga se više ne može koristiti za operacije nad bazom.
emf.close() — zatvaraš tvornički objekt (EntityManagerFactory) koji inače treba živjeti koliko i aplikacija (npr. ako koristiš JPA u web aplikaciji, obično se zatvara prilikom gašenja aplikacije).


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