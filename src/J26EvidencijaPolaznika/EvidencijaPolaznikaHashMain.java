//Napiši program za evidenciju polaznika na tečaju koji također
//provjerava i sprječava dodavanje duplikata polaznika na tečaj.
//Program treba omogućiti unos polaznika i njihovih podataka te
//provjeriti jesu li polaznici jedinstveni na tečaju.
//Koristi klasu Polaznik s atributima: ime, prezime i e-mail
//Koristi klasu HashSet<Polaznik> za pohranu polaznika kako bi
//se osigurala jedinstvenost
//Napravi glavnu klasu EvidencijaPolaznika koja sadrži main metodu
//Omogući korisniku unos novih polaznika (ime, prezime, e-mail)
//Pri dodavanju novog polaznika, provjeri je li polaznik već
//prisutan na tečaju (usporedba po e-mail adresi)
//Ispisuj odgovarajuće poruke o uspješnom ili neuspješnom
//dodavanju polaznika na tečaj
//Omogući ispis svih polaznika na tečaju nakon unosa
//koristiti metode Equals i HashCode
//Što bi trebalo izmijeniti u rješenju ako dodamo novi zahtjev?
//Svi polaznici moraju biti cijelo vrijeme sortirani po prezimenu uzlazno
//Iskorisitit TreeSet() i sučelje Comparable

package J26EvidencijaPolaznika;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

// Glavna klasa za evidenciju polaznika
class EvidencijaPolaznikaHashMain {
    public static void main(String[] args) {
        // Set je interface tj. skup koji nije sortiran i ne sadrzi duplikate
        // TreeSet jer klasa ciji su elementi sortirani u binarnom stablu
        // Kreiramo TreeSet koji automatski održava sortiran redoslijed
        // Koristi compareTo metodu iz Polaznik klase za sortiranje
        Set<Polaznik> polaznici = new TreeSet<>(); // HashSet nije kompatibilan sa TreeSet, treba Set

        // Scanner za čitanje korisničkog unosa
        Scanner scanner = new Scanner(System.in);

        // Beskonačna petlja za glavni izbornik
        while (true) {
            // Ispis glavnog izbornika
            System.out.println("\n--- Evidencija polaznika ---");
            System.out.println("1. Unos novog polaznika");
            System.out.println("2. Ispis svih polaznika");
            System.out.println("3. Pretraži polaznika po e-mailu");
            System.out.println("4. Izlaz");
            System.out.print("Odaberite opciju: ");

            // Čitanje korisničkog unosa
            int opcija = scanner.nextInt();
            scanner.nextLine();  // ovo treba da se ocisti newline nakon nextInt()
            /*
               Objašnjenje zašto je potrebna linija "scanner.nextLine();" :
               Kada korisnik unese broj i pritisne Enter, nextInt() pročita samo broj,
               ali newline znak (\n) ostane u bufferu. Ako odmah nakon toga pozovemo
               nextLine(), ona će pročitati samo taj preostali newline i vratiti prazan string.
               Zato se čisti buffer pozivom scanner.nextLine() nakon nextInt().
             */

            // Switch-case za obradu odabira korisnika
            switch (opcija) {
                case 1: // Unos novog polaznika
                    System.out.print("Unesite ime: ");
                    String ime = scanner.nextLine(); // Čitanje imena

                    System.out.print("Unesite prezime: ");
                    String prezime = scanner.nextLine(); // Čitanje prezimena

                    System.out.print("Unesite e-mail: ");
                    String email = scanner.nextLine(); // Čitanje emaila

                    // Kreiranje novog polaznika
                    Polaznik noviPolaznik = new Polaznik(ime, prezime, email);

                    /* TreeSet automatski provjerava duplikate pomoću compareTo metode
                       Ako polaznik već postoji (isti email), add() će vratiti false
                       Ako polaznik ne postoji, dodaje ga i vraća true */
                    if (polaznici.add(noviPolaznik)) {
                        System.out.println("Polaznik je uspješno dodan!");
                    } else {
                        System.out.println("Polaznik s tim e-mailom već postoji!");
                    }
                    break;

                case 2: // Ispis svih polaznika
                    if (polaznici.isEmpty()) {
                        System.out.println("Nema unesenih polaznika.");
                    } else {
                        System.out.println("\n--- Popis polaznika ---");
                        // Enhanced for petlja za prolazak kroz sve polaznike
                        for (Polaznik p : polaznici) {
                            // Ispis polaznika koristeći toString() metodu
                            System.out.println(p);
                        }
                    }
                    break;

                case 3: // Pretraživanje po e-mailu
                    System.out.print("Unesite e-mail za pretragu: ");
                    String trazeniEmail = scanner.nextLine(); // Čitanje emaila za pretragu
                    boolean pronaden = false; // flag za pronalazak

                    // Prolazak kroz sve polaznike
                    for (Polaznik p : polaznici) {
                        // Usporedba emailova (ignorira velika/mala slova)
                        if (p.getEmail().equalsIgnoreCase(trazeniEmail)) {
                            System.out.println("Pronađen polaznik: " + p);
                            pronaden = true; // Postavi zastavicu
                            break; // Prekini petlju nakon pronalaska
                        }
                    }

                    // Ako polaznik nije pronađen
                    if (!pronaden) {
                        System.out.println("Polaznik s e-mailom '" + trazeniEmail + "' nije pronađen.");
                    }
                    break;

                case 4: // Izlaz iz programa
                    System.out.println("Izlazim iz programa...");
                    scanner.close(); // Zatvaranje Scanner resursa
                    System.exit(0);  // Prekid programa s statusom 0 (uspješan završetak)
                    /* System.exit(0) služi za prekid rada Java programa i zatvaranje svih njegovih procesa.
                       Argument 0 označava da je program završio bez grešaka. */

                default: // Nevažeći unos
                    System.out.println("Nevažeća opcija! Pokušajte ponovno.");
            }
        }
    }
}

