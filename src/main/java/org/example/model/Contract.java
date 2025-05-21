package org.example.model;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

//* Klasa „Contract” mora sadržavati identifikator „Long id”, varijablu „LocalDate” koji označava datum početka ugovora,
// cjelobrojnu vrijednost koja označava trajanje ugovora, „BigDecimal” vrijednost koja predstavlja iznos plaće
// te Set osoba i Set kompanija označenih s „@ManyToMany” anotacijama.
@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private int durationInMonths;
    private BigDecimal salary;

    @ManyToMany(mappedBy = "contracts")
    private Set<Person> persons = new HashSet<>();

    @ManyToMany(mappedBy = "contracts")
    private Set<Company> companies = new HashSet<>();

    // Getteri i setteri
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getDurationInMonths() {
        return durationInMonths;
    }

    public void setDurationInMonths(int durationInMonths) {
        this.durationInMonths = durationInMonths;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }
}