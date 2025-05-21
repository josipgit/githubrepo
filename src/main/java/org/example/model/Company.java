package org.example.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

//* Klasa „Company” mora sadržavati identifikator „Long id” i naziv „String name” i
// Set objekata klase „Contract” kojem pripada ta kompanija označen s anotacijama „@ManyToMany” i „@JoinTable”.
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "company_contract",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "contract_id")
    )
    private Set<Contract> contracts = new HashSet<>();

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

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    public void addContract(Contract contract) {
        this.contracts.add(contract);
        contract.getCompanies().add(this);
    }

    public void removeContract(Contract contract) {
        this.contracts.remove(contract);
        contract.getCompanies().remove(this);
    }
}
