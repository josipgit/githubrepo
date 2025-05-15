package org.example.model;

import jakarta.persistence.*;

@Entity // Ova klasa predstavlja JPA entitet koji se mapira u tablicu baze
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatsko generiranje primarnog ključa
    private Long id;

    private String name; // Naziv sastojka

    @ManyToOne // Više sastojaka može pripadati jednom jelu
    @JoinColumn(name = "meal_id") // Strani ključ u tablici Ingredient koji povezuje s Meal
    private Meal meal;

    public Ingredient() {
        // Prazan konstruktor potreban za JPA
    }

    // Getteri i setteri

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}
