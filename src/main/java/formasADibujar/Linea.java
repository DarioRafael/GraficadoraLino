package formasADibujar;

import java.util.ArrayList;
import java.util.List;

public class Linea {
    private Punto puntoInicio;
    private Punto puntoFin;
    private static List<Linea> lineas = new ArrayList<>(); // Lista de líneas

    public Linea(Punto puntoInicio, Punto puntoFin) {
        this.puntoInicio = puntoInicio;
        this.puntoFin = puntoFin;
        lineas.add(this); // Agregar la línea a la lista
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

    // Método para calcular todos los puntos de la línea
    public List<Punto> calcularPuntosIntermedios() {
        List<Punto> puntos = new ArrayList<>();
        int x1 = puntoInicio.getX();
        int y1 = puntoInicio.getY();
        int x2 = puntoFin.getX();
        int y2 = puntoFin.getY();

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            puntos.add(new Punto(x1, y1));
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

        return puntos;
    }
}
