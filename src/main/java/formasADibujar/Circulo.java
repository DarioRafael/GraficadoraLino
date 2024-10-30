package formasADibujar;

import Plano.GenericsPlano.PlanoCartesiano;

import java.util.ArrayList;
import java.util.List;

public class Circulo {
    private static List<Circulo> circulos = new ArrayList<>(); // Lista estática para almacenar los círculos
    private Punto centro;
    private int radio;

    // Constructor
    public Circulo(Punto centro, int radio) {
        this.centro = centro;
        this.radio = radio;
        circulos.add(this); // Agregar el círculo a la lista al crearlo
    }

    // Getters y setters
    public Punto getCentro() {
        return centro;
    }

    public void setCentro(Punto centro) {
        this.centro = centro;
    }

    public int getRadio() {
        return radio;
    }

    public void setRadio(int radio) {
        this.radio = radio;
    }

    // Método para calcular el área del círculo
    public double calcularArea() {
        return Math.PI * Math.pow(radio, 2);
    }

    // Método para calcular el perímetro del círculo (circunferencia)
    public double calcularPerimetro() {
        return 2 * Math.PI * radio;
    }

    // Método para obtener la lista de círculos
    public static List<Circulo> getCirculos() {
        return circulos;
    }

    // Método para dibujar el círculo en el plano cartesiano
    public void dibujar(PlanoCartesiano plano) {
        plano.addCirculo(this); // Método para agregar al plano

        // Calcular y agregar los puntos del círculo
        for (int i = 0; i < 360; i++) {
            double radianes = Math.toRadians(i);
            int x = (int) Math.round(centro.getX() + radio * Math.cos(radianes));
            int y = (int) Math.round(centro.getY() + radio * Math.sin(radianes));
            plano.addPunto(new Punto(x, y)); // Asegúrate de que tu método addPunto esté definido
            System.out.println("Punto " + i + ": (" + x + ", " + y + ")");
        }

        plano.repaint(); // Repaint para actualizar el plano
    }

    // Método para calcular los puntos del círculo
    public List<Punto> calcularPuntos() {
        List<Punto> puntos = new ArrayList<>();
        for (int angulo = 0; angulo < 360; angulo++) {
            double radianes = Math.toRadians(angulo);
            int x = (int) Math.round(centro.getX() + radio * Math.cos(radianes));
            int y = (int) Math.round(centro.getY() + radio * Math.sin(radianes));
            puntos.add(new Punto(x, y));
        }
        return puntos;
    }
}