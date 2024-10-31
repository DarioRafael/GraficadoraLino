package formasADibujar.Rotacion;


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

    // Method to calculate the points of the ellipse
    public List<Punto> calcularPuntos() {
        List<Punto> puntos = new ArrayList<>();
        for (int angulo = 0; angulo < 360; angulo++) {
            double radianes = Math.toRadians(angulo);
            int x = (int) Math.round(centro.getX() + semiEjeMayor * Math.cos(radianes));
            int y = (int) Math.round(centro.getY() + semiEjeMenor * Math.sin(radianes));
            puntos.add(new Punto(x, y));
        }
        return puntos;
    }
}