package J26;

import java.util.ArrayList;
import java.util.Scanner;

class EvidencijaPolaznika {
    public static void main(String[] args) {
        ArrayList<Polaznik> polaznici = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Evidencija polaznika ---");
            System.out.println("1. Unos novog polaznika");
            System.out.println("2. Ispis svih polaznika");
            System.out.println("3. Pretraži polaznika po e-mailu");
            System.out.println("4. Izlaz");
            System.out.print("Odaberite opciju: ");

            int opcija = scanner.nextInt();
            scanner.nextLine();  // Očisti newline nakon nextInt()

            switch (opcija) {
                case 1:
                    // Unos novog polaznika
                    System.out.print("Unesite ime: ");
                    String ime = scanner.nextLine();
                    System.out.print("Unesite prezime: ");
                    String prezime = scanner.nextLine();
                    System.out.print("Unesite e-mail: ");
                    String email = scanner.nextLine();

                    Polaznik noviPolaznik = new Polaznik(ime, prezime, email);
                    polaznici.add(noviPolaznik);
                    System.out.println("Polaznik je uspješno dodan!");
                    break;

                case 2:
                    // Ispis svih polaznika
                    if (polaznici.isEmpty()) {
                        System.out.println("Nema unesenih polaznika.");
                    } else {
                        System.out.println("\n--- Popis polaznika ---");
                        for (Polaznik p : polaznici) {
                            System.out.println(p);
                        }
                    }
                    break;

                case 3:
                    // Pretraživanje po e-mailu
                    System.out.print("Unesite e-mail za pretragu: ");
                    String trazeniEmail = scanner.nextLine();
                    boolean pronaden = false;

                    for (Polaznik p : polaznici) {
                        if (p.getEmail().equalsIgnoreCase(trazeniEmail)) {
                            System.out.println("Pronađen polaznik: " + p);
                            pronaden = true;
                            break;
                        }
                    }

                    if (!pronaden) {
                        System.out.println("Polaznik s e-mailom '" + trazeniEmail + "' nije pronađen.");
                    }
                    break;

                case 4:
                    // Izlaz iz programa
                    System.out.println("Izlazim iz programa...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Nevažeća opcija! Pokušajte ponovno.");
            }
        }
    }
}

