package org.example.model.Zad3User;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primarni ključ, automatski generiran

    private String username; // Korisničko ime
    private String email;    // Email korisnika

    public User() {
        // Prazan konstruktor potreban JPA-u
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // Getteri i setteri
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
