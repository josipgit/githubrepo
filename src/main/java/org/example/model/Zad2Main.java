package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.model.Company;
import org.example.model.Contract;
import org.example.model.Person;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Zad2Main {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("library");

    public static void main(String[] args) {
        // Kreiranje novih entiteta i relacija
        createEntities();

        // Ažuriranje entiteta
        updatePersonName(1L, "Jane Doe");

        // Brisanje entiteta
        deleteContract(1L);

        // Zatvaranje EntityManagerFactory
        emf.close();
    }

    private static void createEntities() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            // Kreiranje novih entiteta
            Person person = new Person();
            person.setName("John Doe");

            Company company = new Company();
            company.setName("TechCorp");

            Contract contract = new Contract();
            contract.setStartDate(LocalDate.now());
            contract.setDurationInMonths(12);
            contract.setSalary(new BigDecimal(100000.00));

            // Postavljanje relacija
            person.addContract(contract);
            company.addContract(contract);

            // Spremanje entiteta
            em.persist(person);
            em.persist(company);
            em.persist(contract);

            transaction.commit();
            System.out.println("Entities saved successfully!");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private static void updatePersonName(Long personId, String newName) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            // Pronalaženje entiteta po ID-u
            Person person = em.find(Person.class, personId);
            if (person != null) {
                // Ažuriranje imena
                person.setName(newName);
                em.merge(person);
                transaction.commit();
                System.out.println("Person updated successfully!");
            } else {
                System.out.println("Person not found!");
                transaction.rollback();
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private static void deleteContract(Long contractId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            // Pronalaženje ugovora po ID-u
            Contract contract = em.find(Contract.class, contractId);
            if (contract != null) {
                // Uklanjanje referenci iz povezanih entiteta
                for (Person person : contract.getPersons()) {
                    person.removeContract(contract);
                    em.merge(person);
                }
                for (Company company : contract.getCompanies()) {
                    company.removeContract(contract);
                    em.merge(company);
                }

                // Brisanje ugovora
                em.remove(contract);
                transaction.commit();
                System.out.println("Contract deleted successfully!");
            } else {
                System.out.println("Contract not found!");
                transaction.rollback();
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
