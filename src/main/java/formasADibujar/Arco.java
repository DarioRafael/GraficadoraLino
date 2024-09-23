package formasADibujar;

import java.util.ArrayList;
import java.util.List;

public class Arco {
    private Punto centro;
    private int radio;
    private double anguloInicio;
    private double anguloFin;
    private static List<Arco> arcos = new ArrayList<>();

    public Arco(Punto centro, int radio, double anguloInicio, double anguloFin) {
        this.centro = centro;
        this.radio = radio;
        this.anguloInicio = anguloInicio;
        this.anguloFin = anguloFin;
    }

    public Punto getCentro() { return centro; }
    public int getRadio() { return radio; }
    public double getAnguloInicio() { return anguloInicio; }
    public double getAnguloFin() { return anguloFin; }

    public static List<Arco> getArcos() { return arcos; }
}