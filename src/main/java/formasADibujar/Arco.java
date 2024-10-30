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

    // Getters
    public Punto getCentro() { return centro; }
    public int getRadio() { return radio; }
    public double getAnguloInicio() { return anguloInicio; }
    public double getAnguloFin() { return anguloFin; }

    // Devuelve la lista de todos los arcos
    public static List<Arco> getArcos() { return arcos; }

    // Agrega un arco a la lista
    public static void agregarArco(Arco arco) {
        arcos.add(arco);
    }

    // Elimina un arco de la lista
    public static void eliminarArco(Arco arco) {
        arcos.remove(arco);
    }

    // Limpia todos los arcos
    public static void limpiarArcos() {
        arcos.clear();
    }

    // Método para calcular los puntos del arco
    public List<Punto> calcularPuntos() {
        List<Punto> puntos = new ArrayList<>();
        for (double angulo = anguloInicio; angulo <= anguloFin; angulo += 1) {
            double radianes = Math.toRadians(angulo);
            int x = (int) Math.round(centro.getX() + radio * Math.cos(radianes));
            int y = (int) Math.round(centro.getY() + radio * Math.sin(radianes));
            puntos.add(new Punto(x, y));
        }
        return puntos;
    }
}