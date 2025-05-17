package J13PrFuncUcenik;

import static java.lang.System.out;

public class TestUcenik {
    public static void main(String[] args) {

        Ucenik ucenik1 = new Ucenik("Ante", 20, 3.0, 4.0, 4.3, 4.5);
        Ucenik ucenik2 = new Ucenik("Ivan", 31, 5.8, 4.0, 4.6);

        out.println(ucenik1.IzracunajProsjekOcjena());
        out.println(ucenik1.IspisiUcenika());

        out.println(ucenik2.IzracunajProsjekOcjena());
        out.println(ucenik2.IspisiUcenika());

    }
}
