package org.example.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity // Ova klasa predstavlja JPA entitet koji se mapira u tablicu baze
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatsko generiranje primarnog ključa
    private Long id;

    private String name; // Naziv jela

    //jednom jelu moze pripadati vise sastojaka
    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    // mappedBy = "meal" znači da je veza definirana u klasi Ingredient preko polja 'meal'
    // CascadeType.ALL: sve operacije (persist, merge, remove...) se prenose na sastojke
    // orphanRemoval = true: ako uklonimo sastojak iz jela, bit će obrisan iz baze
    private Set<Ingredient> ingredients = new HashSet<>();

    public Meal() {
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

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    // Pomoćna metoda za dodavanje sastojka
    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        ingredient.setMeal(this); // Postavi vezu s trenutnim jelom
    }

    // Pomoćna metoda za uklanjanje sastojka
    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
        ingredient.setMeal(null);
    }
}
