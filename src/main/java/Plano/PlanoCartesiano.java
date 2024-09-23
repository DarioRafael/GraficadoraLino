package Plano;

import formasADibujar.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class PlanoCartesiano extends JPanel {

    private double offsetX = 0, offsetY = 0;
    private int gridSize = 50;
    private double zoomFactor = 1.0;
    private Point dragStart = null;

    private static final int GRID_SIZE = 50;
    private static final int AXIS_THICKNESS = 2;
    private static final int TICK_SIZE = 5;
    private static final int LABEL_OFFSET = 20;


    private boolean isDarkMode = false; // Flag to track dark mode status



    public PlanoCartesiano() {
        setupMouseListeners();
    }

    private void setupMouseListeners() {
        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dragStart = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragStart = null;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseDrag(e);
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                handleMouseWheel(e);
            }
        };

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
        addMouseWheelListener(mouseHandler);
    }

    private void handleMouseDrag(MouseEvent e) {
        if (dragStart != null) {
            Point dragEnd = e.getPoint();
            offsetX += (dragEnd.x - dragStart.x) / zoomFactor;
            offsetY += (dragEnd.y - dragStart.y) / zoomFactor;
            dragStart = dragEnd;
            repaint();
        }
    }

    private void handleMouseWheel(MouseWheelEvent e) {
        double oldZoom = zoomFactor;
        if (e.getWheelRotation() < 0) {
            zoomFactor *= 1.1;
        } else {
            zoomFactor /= 1.1;
        }

        Point mousePoint = e.getPoint();
        offsetX += mousePoint.x * (1 / oldZoom - 1 / zoomFactor);
        offsetY += mousePoint.y * (1 / oldZoom - 1 / zoomFactor);

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        setupGraphics(g2);

        AffineTransform originalTransform = g2.getTransform();
        applyTransformation(g2);

        drawComponents(g2);

        g2.setTransform(originalTransform);
    }

    private void setupGraphics(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Set background color based on dark mode
        if (isDarkMode) {
            g2.setColor(Color.BLACK);
        } else {
            g2.setColor(Color.WHITE);
        }
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    private void applyTransformation(Graphics2D g2) {
        g2.translate(getWidth() / 2.0, getHeight() / 2.0);
        g2.scale(zoomFactor, zoomFactor);
        g2.translate(offsetX, offsetY);
    }

    private void drawComponents(Graphics2D g2) {
        drawGrid(g2);
        drawAxes(g2);
        drawPoints(g2);
        drawLines(g2);
        drawCircles(g2);
        drawElipses(g2);
        drawArcos(g2);
    }
    private void drawGrid(Graphics2D g2) {
        if (isDarkMode) {
            g2.setColor(Color.GRAY);
        } else {
            g2.setColor(Color.LIGHT_GRAY);
        }
        g2.setStroke(new BasicStroke(1));

        double viewportWidth = getWidth() / zoomFactor;
        double viewportHeight = getHeight() / zoomFactor;

        int startX = (int) Math.floor((-offsetX - viewportWidth / 2) / GRID_SIZE);
        int endX = (int) Math.ceil((-offsetX + viewportWidth / 2) / GRID_SIZE);
        int startY = (int) Math.floor((-offsetY - viewportHeight / 2) / GRID_SIZE);
        int endY = (int) Math.ceil((-offsetY + viewportHeight / 2) / GRID_SIZE);

        for (int i = startX; i <= endX; i++) {
            int x = i * GRID_SIZE;
            g2.drawLine(x, (int)(-offsetY - viewportHeight/2), x, (int)(-offsetY + viewportHeight/2));
        }

        for (int i = startY; i <= endY; i++) {
            int y = i * GRID_SIZE;
            g2.drawLine((int)(-offsetX - viewportWidth/2), y, (int)(-offsetX + viewportWidth/2), y);
        }
    }

    private void drawAxes(Graphics2D g2) {
        if (isDarkMode) {
            g2.setColor(Color.WHITE);
        } else {
            g2.setColor(Color.BLACK);
        }
        g2.setStroke(new BasicStroke(AXIS_THICKNESS));

        double viewportWidth = getWidth() / zoomFactor;
        double viewportHeight = getHeight() / zoomFactor;

        g2.drawLine((int)(-offsetX - viewportWidth/2), 0, (int)(-offsetX + viewportWidth/2), 0);
        g2.drawLine(0, (int)(-offsetY - viewportHeight/2), 0, (int)(-offsetY + viewportHeight/2));

        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        g2.drawString("X", (int)(-offsetX + viewportWidth/2) - LABEL_OFFSET, -LABEL_OFFSET);
        g2.drawString("-X", (int)(-offsetX - viewportWidth/2) + LABEL_OFFSET, -LABEL_OFFSET);
        g2.drawString("Y", LABEL_OFFSET, (int)(-offsetY - viewportHeight/2) + LABEL_OFFSET);
        g2.drawString("-Y", LABEL_OFFSET, (int)(-offsetY + viewportHeight/2) - LABEL_OFFSET);

        g2.setFont(new Font("Arial", Font.PLAIN, 10));

        int startX = (int) Math.floor((-offsetX - viewportWidth / 2) / GRID_SIZE);
        int endX = (int) Math.ceil((-offsetX + viewportWidth / 2) / GRID_SIZE);
        int startY = (int) Math.floor((-offsetY - viewportHeight / 2) / GRID_SIZE);
        int endY = (int) Math.ceil((-offsetY + viewportHeight / 2) / GRID_SIZE);

        for (int i = startX; i <= endX; i++) {
            if (i != 0) {
                int x = i * GRID_SIZE;
                g2.drawLine(x, -TICK_SIZE, x, TICK_SIZE);
                g2.drawString(Integer.toString(i), x - 5, LABEL_OFFSET);
            }
        }

        for (int i = startY; i <= endY; i++) {
            if (i != 0) {
                int y = i * GRID_SIZE;
                g2.drawLine(-TICK_SIZE, y, TICK_SIZE, y);
                g2.drawString(Integer.toString(-i), -LABEL_OFFSET, y + 5);
            }
        }

        g2.fillOval(-3, -3, 6, 6);
        //g2.drawString("(0,0)", 5, -5);
    }

    private void drawPoints(Graphics2D g2) {
        if (isDarkMode) {
            g2.setColor(Color.YELLOW);
        } else {
            g2.setColor(Color.RED);
        }
        List<Punto> puntos = Punto.getPuntos();

        for (Punto punto : puntos) {
            int x = punto.getX() * GRID_SIZE;
            int y = -punto.getY() * GRID_SIZE;

            g2.fillOval(x - 3, y - 3, 6, 6);

            // Verificar si el nombre no es null antes de dibujar
            String nombrePunto = punto.getNombrePunto();
            if (nombrePunto != null) {
                g2.drawString(nombrePunto, x + 5, y - 5);
            }
        }
    }




    // Método para agregar un nuevo punto y repintar
    public void addPunto(Punto punto) {
        Punto.getPuntos().add(punto);
        repaint(); // Redibujar el plano
    }

    private void drawLines(Graphics2D g2) {
        if (isDarkMode) {
            g2.setColor(Color.CYAN);
        } else {
            g2.setColor(Color.BLUE);
        }
        g2.setStroke(new BasicStroke(2));

        List<Linea> lineas = Linea.getLineas();
        for (Linea linea : lineas) {
            Punto inicio = linea.getPuntoInicio();
            Punto fin = linea.getPuntoFin();
            int x1 = inicio.getX() * GRID_SIZE;
            int y1 = -inicio.getY() * GRID_SIZE;
            int x2 = fin.getX() * GRID_SIZE;
            int y2 = -fin.getY() * GRID_SIZE;

            g2.drawLine(x1, y1, x2, y2);

            // Dibujar todos los puntos intermedios
            List<Punto> puntosIntermedios = linea.calcularPuntosIntermedios();
            int puntoCounter = 1; // Contador para los nombres de los puntos
            for (Punto punto : puntosIntermedios) {
                int x = punto.getX() * GRID_SIZE;
                int y = -punto.getY() * GRID_SIZE;
                g2.fillOval(x - 3, y - 3, 6, 6);

                // Dibujar el nombre del punto
                g2.drawString("P" + puntoCounter++, x + 5, y - 5); // Etiquetar el punto
            }
        }
    }


    private void drawCircles(Graphics2D g2) {
        if (isDarkMode) {
            g2.setColor(Color.LIGHT_GRAY);
        } else {
            g2.setColor(Color.GREEN);
        }
        for (Circulo circulo : Circulo.getCirculos()) {
            int xCentro = circulo.getCentro().getX() * GRID_SIZE;
            int yCentro = -circulo.getCentro().getY() * GRID_SIZE;
            int radio = circulo.getRadio() * GRID_SIZE;

            // Dibujar el círculo completo
            g2.drawOval(xCentro - radio, yCentro - radio, radio * 2, radio * 2);

            // Dibujar los puntos usando simetría polinomial
            drawCirclePoints(g2, xCentro, yCentro, radio);


        }
    }

    // Add this method to the PlanoCartesiano class
    public List<Punto> calcularPuntosCirculo(int xCentro, int yCentro, int radio) {
        List<Punto> puntos = new ArrayList<>();
        puntos.add(new Punto(xCentro + radio, yCentro));        // P₁ (derecha)
        puntos.add(new Punto((int)(xCentro + radio / Math.sqrt(2)), (int)(yCentro - radio / Math.sqrt(2)))); // P₇ (diagonal arriba derecha)
        puntos.add(new Punto(xCentro, yCentro - radio));        // P₄ (arriba)
        puntos.add(new Punto((int)(xCentro - radio / Math.sqrt(2)), (int)(yCentro - radio / Math.sqrt(2)))); // P₈ (diagonal arriba izquierda)
        puntos.add(new Punto(xCentro - radio, yCentro));        // P₂ (izquierda)
        puntos.add(new Punto((int)(xCentro - radio / Math.sqrt(2)), (int)(yCentro + radio / Math.sqrt(2)))); // P₆ (diagonal abajo izquierda)
        puntos.add(new Punto(xCentro, yCentro + radio));        // P₃ (abajo)
        puntos.add(new Punto((int)(xCentro + radio / Math.sqrt(2)), (int)(yCentro + radio / Math.sqrt(2)))); // P₅ (diagonal abajo derecha)



        return puntos;
    }

    // Update the drawCirclePoints method in the PlanoCartesiano class
    private void drawCirclePoints(Graphics2D g2, int xCentro, int yCentro, int radio) {
        List<Punto> puntos = calcularPuntosCirculo(xCentro, yCentro, radio);
        g2.setColor(Color.RED); // Color para los puntos
        String[] etiquetas = {"P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8"};

        for (int i = 0; i < puntos.size(); i++) {
            Punto punto = puntos.get(i);
            int x = punto.getX();
            int y = punto.getY();
            g2.fillOval(x - 2, y - 2, 8, 8); // Ajuste para centrar el punto

            // Dibujar la etiqueta del punto
            g2.drawString(etiquetas[i], x + 5, y - 5); // Etiquetar el punto
        }
    }

    private void drawElipses(Graphics2D g2) {
        if (isDarkMode) {
            g2.setColor(Color.PINK);
        } else {
            g2.setColor(Color.MAGENTA);
        }
        for (Elipse elipse : Elipse.getElipses()) {
            int xCentro = elipse.getCentro().getX() * GRID_SIZE;
            int yCentro = -elipse.getCentro().getY() * GRID_SIZE;
            int semiEjeMayor = elipse.getSemiEjeMayor() * GRID_SIZE;
            int semiEjeMenor = elipse.getSemiEjeMenor() * GRID_SIZE;

            g2.drawOval(xCentro - semiEjeMayor, yCentro - semiEjeMenor, semiEjeMayor * 2, semiEjeMenor * 2);

            // Dibujar puntos característicos de la elipse
            g2.setColor(Color.RED);
            int puntoCounter = 1; // Contador para los nombres de los puntos


            g2.fillOval(xCentro + semiEjeMayor - 3, yCentro - 3, 6, 6); // Punto derecho
            g2.drawString("P" + puntoCounter++, xCentro + semiEjeMayor + 5, yCentro - 5); // Etiquetar punto derecho

            g2.fillOval(xCentro + (int)(semiEjeMayor / Math.sqrt(2)) - 3, yCentro - (int)(semiEjeMenor / Math.sqrt(2)), 6, 6); // Diagonal derecha arriba
            g2.drawString("P" + puntoCounter++, xCentro + (int)(semiEjeMayor / Math.sqrt(2)) + 5, yCentro - (int)(semiEjeMenor / Math.sqrt(2)) - 5); // Etiquetar

            g2.fillOval(xCentro - 3, yCentro - semiEjeMenor, 6, 6); // Punto superior
            g2.drawString("P" + puntoCounter++, xCentro + 5, yCentro - semiEjeMenor - 5); // Etiquetar punto superior

            g2.fillOval(xCentro - (int)(semiEjeMayor / Math.sqrt(2)), yCentro - (int)(semiEjeMenor / Math.sqrt(2)), 6, 6); // Diagonal izquierda arriba
            g2.drawString("P" + puntoCounter++, xCentro - (int)(semiEjeMayor / Math.sqrt(2)) + 5, yCentro - (int)(semiEjeMenor / Math.sqrt(2)) - 5); // Etiquetar

            g2.fillOval(xCentro - semiEjeMayor, yCentro - 3, 6, 6); // Punto izquierdo
            g2.drawString("P" + puntoCounter++, xCentro - semiEjeMayor + 5, yCentro - 5); // Etiquetar punto izquierdo



            g2.fillOval(xCentro - (int)(semiEjeMayor / Math.sqrt(2)), yCentro + (int)(semiEjeMenor / Math.sqrt(2)) - 3, 6, 6); // Diagonal izquierda abajo
            g2.drawString("P" + puntoCounter++, xCentro - (int)(semiEjeMayor / Math.sqrt(2)) + 5, yCentro + (int)(semiEjeMenor / Math.sqrt(2)) + 5); // Etiquetar


            g2.fillOval(xCentro - 3, yCentro + semiEjeMenor - 3, 6, 6); // Punto inferior
            g2.drawString("P" + puntoCounter++, xCentro + 5, yCentro + semiEjeMenor + 5); // Etiquetar punto inferior


            g2.fillOval(xCentro + (int)(semiEjeMayor / Math.sqrt(2)) - 3, yCentro + (int)(semiEjeMenor / Math.sqrt(2)) - 3, 6, 6); // Diagonal derecha abajo
            g2.drawString("P" + puntoCounter++, xCentro + (int)(semiEjeMayor / Math.sqrt(2)) + 5, yCentro + (int)(semiEjeMenor / Math.sqrt(2)) + 5); // Etiquetar






}
    }

    private void drawArcos(Graphics2D g2) {
        if (isDarkMode) {
            g2.setColor(Color.ORANGE); // Mantener el mismo color para arcos en modo oscuro
        } else {
            g2.setColor(Color.ORANGE);
        }
        for (Arco arco : Arco.getArcos()) {
            int xCentro = arco.getCentro().getX() * GRID_SIZE;
            int yCentro = -arco.getCentro().getY() * GRID_SIZE;
            int radio = arco.getRadio() * GRID_SIZE;
            int anguloInicio = (int) Math.toDegrees(arco.getAnguloInicio());
            int anguloFin = (int) Math.toDegrees(arco.getAnguloFin());

            // Calcular la extensión del arco (siempre positiva)
            int anguloExtendido = (anguloFin - anguloInicio + 360) % 360;

            // Dibujar solo el arco
            g2.drawArc(xCentro - radio, yCentro - radio, radio * 2, radio * 2, anguloInicio, anguloExtendido);

            // Dibujar puntos de inicio y fin del arco
            g2.setColor(Color.RED);
            int xInicio = (int) (xCentro + radio * Math.cos(arco.getAnguloInicio()));
            int yInicio = (int) (yCentro - radio * Math.sin(arco.getAnguloInicio()));
            int xFin = (int) (xCentro + radio * Math.cos(arco.getAnguloFin()));
            int yFin = (int) (yCentro - radio * Math.sin(arco.getAnguloFin()));
            g2.fillOval(xInicio - 3, yInicio - 3, 6, 6);
            g2.fillOval(xFin - 3, yFin - 3, 6, 6);

            // Imprimir ángulos en radianes y grados para verificar
            System.out.println("Arco - Ángulo Inicio (radianes): " + arco.getAnguloInicio());
            System.out.println("Arco - Ángulo Fin (radianes): " + arco.getAnguloFin());
            System.out.println("Arco - Ángulo Inicio (grados): " + anguloInicio);
            System.out.println("Arco - Ángulo Fin (grados): " + anguloFin);
            System.out.println("Arco - Ángulo Extendido (grados): " + anguloExtendido);

            // Dibujar líneas desde el centro hasta los puntos de inicio y fin del arco
            g2.setColor(Color.BLUE);
            g2.drawLine(xCentro, yCentro, xInicio, yInicio);
            g2.drawLine(xCentro, yCentro, xFin, yFin);

            g2.setColor(Color.ORANGE);
        }
    }

    public void addElipse(Elipse elipse) {
        Elipse.getElipses().add(elipse);
        repaint();
    }

    public void addArco(Arco arco) {
        Arco.getArcos().add(arco);
        repaint();
    }



    public void setDarkMode(boolean darkMode) {
        isDarkMode = darkMode;
        repaint(); // Redibujar el plano al cambiar el modo
    }



    public void addCirculo(Circulo circulo) {
        Circulo.getCirculos().add(circulo);
        repaint();
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int newSize) {
        this.gridSize = newSize;
        repaint();
    }

    public void clear() {
        Punto.getPuntos().clear();
        Linea.getLineas().clear();
        Circulo.getCirculos().clear();
        Elipse.getElipses().clear();
        Arco.getArcos().clear();
        repaint(); // Redibujar el plano para reflejar los cambios
    }
}

