package org.example.model; // Paket u kojem se nalazi klasa Author

import jakarta.persistence.*;

import java.util.HashSet; // Implementacija Set kolekcije koja ne dopušta duplikate
import java.util.Set;     // Sučelje koje predstavlja skup (bez duplikata)

/**
 * Ova klasa predstavlja autora u bazi podataka.
 */
@Entity // Ova anotacija označava da je klasa Author JPA entitet i da se mapira u tablicu baze podataka
public class Author {

    // donje anotacije @Id i @GeneratedValue(strategy = GenerationType.IDENTITY) odnose se isključivo na liniju
    // private long id;, i ne utječu ni na koji način na liniju private String name;
    // U JPA-u, anotacije se vežu uz sljedeće polje ili metodu, ovisno o tome koristiš li field-based pristup
    // (uz field odnosno atribut) ili method-based pristup (getteri).
    // U ovom slučaju koristimo field-based mapping, jer anotacije stoje iznad atributa
    // (fielda) klase (ne iznad getter metoda).

    @Id // Ova anotacija označava da je atribut "id" primarni ključ u bazi
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Ova anotacija povise kaže da će se vrijednost ID-a automatski generirati
    // (npr. auto_increment u MySQL-u)
    private long id; // Jedinstveni identifikator autora
    private String name; // Ime autora

    //linije ispod MORAJU bit tocno ovdje, inace je error, istrazi zasto
    @ManyToMany // Ova anotacija označava da postoji veza N:N (više autora može napisati više knjiga, i obrnuto)
    @JoinTable( // Definira međutablicu (join tablicu) koja mapira relaciju između entiteta Author i Book
            name = "author_book", // Ime međutablice u bazi podataka (npr. author_book)
            joinColumns = @JoinColumn(name = "author_id"), // Stupac koji referencira trenutni entitet (Author) – FK prema Author.id
            inverseJoinColumns = @JoinColumn(name = "book_id") // Stupac koji referencira povezani entitet (Book) – FK prema Book.id
    )
    // u JPA-u anotacije kao što su @ManyToMany i @JoinTable odnose se na polje koje dolazi odmah ispod njih,
    // u ovom slučaju na HashSet objekata tipa Book
    private Set<Book> books = new HashSet<>();
    // Kolekcija knjiga koje je autor napisao
    // Koristimo Set kako bi se izbjegli duplikati, i inicijaliziramo s HashSet

    public Author() {
        // Prazan (default) konstruktor – potreban JPA-u za instanciranje objekta
    }

    // Getter i setter metode za pristup i promjenu privatnih polja

    public long getId() {
        return id; // Vraća ID autora
    }

    public void setId(long id) {
        this.id = id; // Postavlja ID autora
    }

    public String getName() {
        return name; // Vraća ime autora
    }

    public void setName(String name) {
        this.name = name; // Postavlja ime autora
    }

    public Set<Book> getBooks() {
        return books; // Vraća skup knjiga koje autor ima
    }

    public void setBooks(Set<Book> books) {
        this.books = books; // Postavlja skup knjiga za autora
    }
}

/*
Ako planiraš koristiti relaciju Author → Book kao JPA vezu (npr. OneToMany),
onda bi u klasi Author trebao dodati anotacije poput:
@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
private Set<Book> books = new HashSet<>();

A u klasi Book bi bilo:
@ManyToOne
private Author author;
 */