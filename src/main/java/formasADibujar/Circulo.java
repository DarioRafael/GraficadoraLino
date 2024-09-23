package formasADibujar;

import Plano.PlanoCartesiano;

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

    private List<Punto> calcularPuntosCirculo(int xCentro, int yCentro, int radio) {
        List<Punto> puntos = new ArrayList<>();
        puntos.add(new Punto(xCentro + radio, yCentro));        // P₁ (derecha)
        puntos.add(new Punto(xCentro - radio, yCentro));        // P₂ (izquierda)
        puntos.add(new Punto(xCentro, yCentro + radio));        // P₃ (abajo)
        puntos.add(new Punto(xCentro, yCentro - radio));        // P₄ (arriba)
        puntos.add(new Punto((int)(xCentro + radio / Math.sqrt(2)), (int)(yCentro + radio / Math.sqrt(2)))); // P₅ (diagonal abajo derecha)
        puntos.add(new Punto((int)(xCentro - radio / Math.sqrt(2)), (int)(yCentro + radio / Math.sqrt(2)))); // P₆ (diagonal abajo izquierda)
        puntos.add(new Punto((int)(xCentro + radio / Math.sqrt(2)), (int)(yCentro - radio / Math.sqrt(2)))); // P₇ (diagonal arriba derecha)
        puntos.add(new Punto((int)(xCentro - radio / Math.sqrt(2)), (int)(yCentro - radio / Math.sqrt(2)))); // P₈ (diagonal arriba izquierda)
        return puntos;
    }



}
