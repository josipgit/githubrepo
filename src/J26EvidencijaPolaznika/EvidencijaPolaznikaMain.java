package J26EvidencijaPolaznika;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.out;

class EvidencijaPolaznikaMain {
    public static void main(String[] args) {
        ArrayList<Polaznik> polaznici = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            out.println("\n--- Evidencija polaznika ---");
            out.println("1. Unos novog polaznika");
            out.println("2. Ispis svih polaznika");
            out.println("3. Pretraži polaznika po e-mailu");
            out.println("4. Izlaz");
            out.print("Odaberite opciju: ");

            int opcija = scanner.nextInt();
            scanner.nextLine();  // Očisti newline nakon nextInt()
            // je potrebna zato što metoda nextInt() ne konzumira newline znak (\n)
            // koji korisnik unese kada pritisne Enter.
            // Dakle, kada korisnik unese broj i pritisne Enter,
            // nextInt() pročita samo broj, ali ostavi newline znak u bufferu.
            //	nextInt() pročita 5, ali newline ↵ ostane.
            //	Ako odmah nakon toga pozoveš nextLine(),
            //	ona će pročitati samo taj preostali newline i vratiti prazan string,
            //	što može izazvati zbunjujuće ponašanje ako želiš kasnije učitavati tekstualni unos.
            //	Zato se često odmah nakon nextInt() (ili nextDouble(), nextLong(), itd.) stavi:
            //	scanner.nextLine();
            //	da se taj "zaostali" newline počisti iz input buffera.


            switch (opcija) {
                case 1:
                    // Unos novog polaznika
                    out.print("Unesite ime: ");
                    String ime = scanner.nextLine();
                    out.print("Unesite prezime: ");
                    String prezime = scanner.nextLine();
                    out.print("Unesite e-mail: ");
                    String email = scanner.nextLine();

                    Polaznik noviPolaznik = new Polaznik(ime, prezime, email);
                    polaznici.add(noviPolaznik);
                    out.println("Polaznik je uspješno dodan!");
                    break;

                case 2:
                    // Ispis svih polaznika
                    if (polaznici.isEmpty()) {
                        out.println("Nema unesenih polaznika.");
                    } else {
                        out.println("\n--- Popis polaznika ---");
                        for (Polaznik p : polaznici) {
                            out.println(p);
                        }
                    }
                    break;

                case 3:
                    // Pretraživanje po e-mailu
                    out.print("Unesite e-mail za pretragu: ");
                    String trazeniEmail = scanner.nextLine();
                    boolean pronaden = false;

                    for (Polaznik p : polaznici) {
                        if (p.getEmail().equalsIgnoreCase(trazeniEmail)) {
                            out.println("Pronađen polaznik: " + p);
                            pronaden = true;
                            break;
                        }
                    }

                    if (!pronaden) {
                        out.println("Polaznik s e-mailom '" + trazeniEmail + "' nije pronađen.");
                    }
                    break;

                case 4:
                    // Izlaz iz programa
                    out.println("Izlazim iz programa...");
                    scanner.close();
                    System.exit(0);
                    // Linija System.exit(0); služi za prekid rada Java programa
                    // i zatvaranje svih njegovih procesa

                default:
                    out.println("Nevažeća opcija! Pokušajte ponovno.");
            }
        }
    } // end main
} // end class

