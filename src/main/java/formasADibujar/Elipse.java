package formasADibujar;

import java.util.ArrayList;
import java.util.List;

public class Elipse {
    private Punto centro;
    private int semiEjeMayor;
    private int semiEjeMenor;
    private static List<Elipse> elipses = new ArrayList<>();

    public Elipse(Punto centro, int semiEjeMayor, int semiEjeMenor) {
        this.centro = centro;
        this.semiEjeMayor = semiEjeMayor;
        this.semiEjeMenor = semiEjeMenor;
    }

    public Punto getCentro() { return centro; }
    public int getSemiEjeMayor() { return semiEjeMayor; }
    public int getSemiEjeMenor() { return semiEjeMenor; }

    public static List<Elipse> getElipses() { return elipses; }
}