package org.example.model; // Paket u kojem se nalazi klasa Book

// JPA anotacije za označavanje entiteta, primarnog ključa i generiranja ID-a
import jakarta.persistence.*;

import java.util.HashSet; // Implementacija skupa koja ne dozvoljava duplikate
import java.util.Set;     // Sučelje za skup objekata (bez duplikata)

/**
 * Klasa Book predstavlja entitet knjige u aplikaciji i bazi podataka.
 */
@Entity // Ova anotacija označava da je klasa Book JPA entitet koji se mapira u tablicu baze
public class Book {

    @Id // Označava da je ovo primarni ključ (jedinstveni identifikator knjige)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Strategija generiranja ID-a (IDENTITY znači da će baza automatski dodjeljivati ID, npr. AUTO_INCREMENT)
    private long id; // Primarni ključ – jedinstveni ID knjige

    private String title; // Naslov knjige

    @ManyToMany(mappedBy = "books") //ova linija MORA bit tocno ovdje, inace je error, istrazi zasto
    private Set<Author> authors = new HashSet<>();
    // Skup autora koji su pisali ovu knjigu
    // HashSet se koristi jer Set ne dopušta duplikate, a HashSet omogućava brze operacije dodavanja i traženja

    public Book() {
        // Prazan konstruktor – potreban za JPA i instanciranje objekta iz baze
    }

    // Getter i setter metode – omogućuju dohvat i postavljanje privatnih polja

    public long getId() {
        return id; // Vraća ID knjige
    }

    public void setId(long id) {
        this.id = id; // Postavlja ID knjige
    }

    public String getTitle() {
        return title; // Vraća naslov knjige
    }

    public void setTitle(String title) {
        this.title = title; // Postavlja naslov knjige
    }

    public Set<Author> getAuthors() {
        return authors; // Vraća skup autora povezanih s ovom knjigom
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors; // Postavlja autore knjige
    }
}

/*
Ako želiš povezati knjige i autore putem Many-to-Many relacije
(što je najčešće za ovakve primjere), moraš dodati JPA anotacije za tu vezu, npr.:
U Book klasi:
@ManyToMany
@JoinTable(
    name = "book_author", // naziv pomoćne tablice
    joinColumns = @JoinColumn(name = "book_id"), // veza s ovom klasom
    inverseJoinColumns = @JoinColumn(name = "author_id") // veza s Author klasom
)
private Set<Author> authors = new HashSet<>();

U Author klasi:
@ManyToMany(mappedBy = "authors")
private Set<Book> books = new HashSet<>();

To automatski stvara međutablicu book_author u bazi koja povezuje knjige i autore.
 */