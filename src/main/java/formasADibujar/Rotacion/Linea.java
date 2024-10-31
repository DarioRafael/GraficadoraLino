package formasADibujar.Rotacion;

import formasADibujar.Rotacion.Punto;

import java.util.ArrayList;
import java.util.List;

public class Linea {
    private Punto puntoInicio;
    private Punto puntoFin;
    private static List<Linea> lineas = new ArrayList<>();
    private boolean esParteDeFiguraAnonima;
    private double puntoNumero; // Changed to double
    private static List<String> radioLabels = new ArrayList<>(); // Lista estática para los nombres de los radios
    private String nombreRadio; // Nuevo campo para el nombre del radio

    public Linea(Punto puntoInicio, Punto puntoFin, boolean esParteDeFiguraAnonima) {
        this.puntoInicio = puntoInicio;
        this.puntoFin = puntoFin;
        this.esParteDeFiguraAnonima = esParteDeFiguraAnonima;
        lineas.add(this);
    }

    public Linea(Punto puntoInicio, Punto puntoFin, boolean esParteDeFiguraAnonima, double puntoNumero) {
        this.puntoInicio = puntoInicio;
        this.puntoFin = puntoFin;
        this.esParteDeFiguraAnonima = esParteDeFiguraAnonima;
        this.puntoNumero = puntoNumero; // Asignar el número del punto
        lineas.add(this);
    }

    public double getPuntoNumero() {
        return puntoNumero;
    }

    // Nuevo getter y setter para el nombre del radio
    public String getNombreRadio() {
        return nombreRadio;
    }

    public void setNombreRadio(String nombreRadio) {
        this.nombreRadio = nombreRadio;
    }

    public Punto getPuntoInicio() {
        return puntoInicio;
    }

    public Punto getPuntoFin() {
        return puntoFin;
    }

    public static List<Linea> getLineas() {
        return lineas;
    }

    public List<Punto> calcularPuntosAnonimos() {
        List<Punto> puntos = new ArrayList<>();

        double x1 = puntoInicio.getX();
        double y1 = puntoInicio.getY();
        double x2 = puntoFin.getX();
        double y2 = puntoFin.getY();

        for (int i = 0; i < 8; i++) {
            double t = i / 7.0; // Distribuye los puntos entre 0 y 1
            int x = (int) (x1 + t * (x2 - x1));
            int y = (int) (y1 + t * (y2 - y1));
            puntos.add(new Punto(x, y));
        }

        return puntos;
    }

    public List<Punto> calcularPuntosIntermedios(Punto inicio, Punto fin) {
        List<Punto> puntosIntermedios = new ArrayList<>();
        int x1 = (int) inicio.getX();
        int y1 = (int) inicio.getY();
        int x2 = (int) fin.getX();
        int y2 = (int) fin.getY();

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;
        int puntoNumero = 1;

        while (true) {
            Punto punto = new Punto(x1, y1);
            punto.setNombrePunto("P" + puntoNumero++);
            puntosIntermedios.add(punto);
            if (x1 == x2 && y1 == y2) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
        return puntosIntermedios;
    }

    public boolean isEsParteDeFiguraAnonima() {
        return esParteDeFiguraAnonima;
    }

    public void setEsParteDeFiguraAnonima(boolean esParteDeFiguraAnonima) {
        this.esParteDeFiguraAnonima = esParteDeFiguraAnonima;
    }
}