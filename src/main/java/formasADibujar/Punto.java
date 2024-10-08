package formasADibujar;

import java.util.ArrayList;
import java.util.List;

public class Punto {
    private int x;
    private int y;
    private String nombrePunto; // Nombre del punto, solo si es parte de una línea
    private static List<Punto> puntos = new ArrayList<>(); // Lista de puntos
    private static int contadorPunto = 1;
    private int puntoNumero; // Nuevo campo para el número del punto



    // Constructor para un punto que no tiene nombre
    public Punto(int x, int y) {
        this.x = x;
        this.y = y;
        this.nombrePunto = null; // Sin nombre inicialmente
        puntos.add(this); // Agregar el punto a la lista
    }

    // Constructor para un punto que es parte de una línea
    public Punto(int x, int y, boolean esParteDeLinea) {
        this(x, y); // Llama al constructor anterior
        if (esParteDeLinea) {
            this.nombrePunto = "P" + contadorPunto++; // Asigna un nombre
        }
    }

    public Punto(int x, int y, int puntoNumero) {
        this.x = x;
        this.y = y;
        this.puntoNumero = puntoNumero; // Asignar el número del punto
        puntos.add(this);
    }
    // Métodos getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getNombrePunto() {
        return nombrePunto; // Devuelve null si no tiene nombre
    }

    public static List<Punto> getPuntos() {
        return puntos;
    }

    public void setNombrePunto(String nombrePunto) {
        this.nombrePunto = nombrePunto;
    }
    public int getPuntoNumero() {
        return puntoNumero;
    }

}
