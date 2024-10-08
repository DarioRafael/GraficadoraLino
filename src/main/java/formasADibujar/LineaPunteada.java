package formasADibujar;

import java.util.ArrayList;
import java.util.List;

public class LineaPunteada {
    private Punto puntoInicio;
    private Punto puntoFin;
    private static List<LineaPunteada> lineasPunteadas = new ArrayList<>();
    private static final int TAMANO_PUNTO = 2; // Tamaño de cada punto
    private static final int ESPACIO_ENTRE_PUNTOS = 5; // Espacio entre puntos

    public LineaPunteada(Punto puntoInicio, Punto puntoFin) {
        this.puntoInicio = puntoInicio;
        this.puntoFin = puntoFin;
        lineasPunteadas.add(this);
    }

    public Punto getPuntoInicio() {
        return puntoInicio;
    }

    public Punto getPuntoFin() {
        return puntoFin;
    }

    public static List<LineaPunteada> getLineasPunteadas() {
        return lineasPunteadas;
    }

    public List<Punto> calcularPuntosPunteados() {
        List<Punto> puntos = new ArrayList<>();
        int x1 = puntoInicio.getX();
        int y1 = puntoInicio.getY();
        int x2 = puntoFin.getX();
        int y2 = puntoFin.getY();

        // Calcular la distancia total
        double distanciaTotal = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

        // Calcular cuántos puntos necesitamos
        int numPuntos = (int) (distanciaTotal / ESPACIO_ENTRE_PUNTOS);

        if (numPuntos < 2) numPuntos = 2; // Asegurar al menos inicio y fin

        // Calcular los incrementos
        double dx = (x2 - x1) / (double) (numPuntos - 1);
        double dy = (y2 - y1) / (double) (numPuntos - 1);

        // Generar los puntos
        for (int i = 0; i < numPuntos; i++) {
            int x = (int) (x1 + dx * i);
            int y = (int) (y1 + dy * i);
            puntos.add(new Punto(x, y));
        }

        return puntos;
    }

    // Método para limpiar todas las líneas punteadas
    public static void clearLineasPunteadas() {
        lineasPunteadas.clear();
    }

    public double getRadio() {
        // Calcular el radio (distancia desde el origen al punto final)
        return Math.sqrt(puntoFin.getX() * puntoFin.getX() + puntoFin.getY() * puntoFin.getY());
    }

    public double getAngulo() {
        // Calcular el ángulo en grados
        return Math.toDegrees(Math.atan2(puntoFin.getY(), puntoFin.getX()));
    }
}