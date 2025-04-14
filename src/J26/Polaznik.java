package J26;

public class Polaznik {
    private String ime;
    private String prezime;
    private String email;

    // Konstruktor
    public Polaznik(String ime, String prezime, String email) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
    }

    // Getteri i setteri
    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Ime: " + ime + ", Prezime: " + prezime + ", E-mail: " + email;
    }
}
