package Plano.GraficadoraBasica;

import Plano.GenericsPlano.CoordinateSystem;
import formasADibujar.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class PlanoCartesianoConicas extends JPanel {

    private double offsetX = 0, offsetY = 0;
    private double zoomFactor = 1.0;
    private Point dragStart = null;

    private static final int GRID_SIZE = 50;
    private static final int AXIS_THICKNESS = 2;
    private static final int TICK_SIZE = 5;
    private static final int LABEL_OFFSET = 20;

    private CoordinateSystem.Type currentCoordSystem = CoordinateSystem.Type.CARTESIAN_ABSOLUTE;
    private List<Punto> puntos = new ArrayList<>();
    private List<Circulo> circulos = new ArrayList<>();
    private List<Elipse> elipses = new ArrayList<>();
    private List<Arco> arcos = new ArrayList<>();

    public PlanoCartesianoConicas() {
        setupMouseListeners();

        zoomFactor = 0.9; // Ajustar el zoom inicial a 0.8x
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

        AffineTransform originalTransform = g2.getTransform();
        applyTransformation(g2);

        drawComponents(g2);

        g2.setTransform(originalTransform);
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
        drawCircles(g2);
        drawEllipses(g2);
        drawArcs(g2);
    }

    private void drawGrid(Graphics2D g2) {
        g2.setStroke(new BasicStroke(1));

        double viewportWidth = getWidth() / zoomFactor;
        double viewportHeight = getHeight() / zoomFactor;

        int startX = (int) Math.floor((-offsetX - viewportWidth / 2) / GRID_SIZE);
        int endX = (int) Math.ceil((-offsetX + viewportWidth / 2) / GRID_SIZE);
        int startY = (int) Math.floor((-offsetY - viewportHeight / 2) / GRID_SIZE);
        int endY = (int) Math.ceil((-offsetY + viewportHeight / 2) / GRID_SIZE);

        for (int i = startX; i <= endX; i++) {
            int x = i * GRID_SIZE;
            g2.drawLine(x, (int) (-offsetY - viewportHeight / 2), x, (int) (-offsetY + viewportHeight / 2));
        }

        for (int i = startY; i <= endY; i++) {
            int y = i * GRID_SIZE;
            g2.drawLine((int) (-offsetX - viewportWidth / 2), y, (int) (-offsetX + viewportWidth / 2), y);
        }
    }

    private void drawAxes(Graphics2D g2) {
        g2.setStroke(new BasicStroke(AXIS_THICKNESS));

        double viewportWidth = getWidth() / zoomFactor;
        double viewportHeight = getHeight() / zoomFactor;

        g2.drawLine((int) (-offsetX - viewportWidth / 2), 0, (int) (-offsetX + viewportWidth / 2), 0);
        g2.drawLine(0, (int) (-offsetY - viewportHeight / 2), 0, (int) (-offsetY + viewportHeight / 2));

        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        g2.drawString("X", (int) (-offsetX + viewportWidth / 2) - LABEL_OFFSET, -LABEL_OFFSET);
        g2.drawString("-X", (int) (-offsetX - viewportWidth / 2) + LABEL_OFFSET, -LABEL_OFFSET);
        g2.drawString("Y", LABEL_OFFSET, (int) (-offsetY - viewportHeight / 2) + LABEL_OFFSET);
        g2.drawString("-Y", LABEL_OFFSET, (int) (-offsetY + viewportHeight / 2) - LABEL_OFFSET);

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
        int arrowSize = 10; // Tamaño de la punta de la flecha

        // Add arrows to the axes (modificado para que no crucen el origen)
        drawArrow(g2, (int) (-offsetX + viewportWidth / 2 - arrowSize), 0, 0); // X-axis arrow
        drawArrow(g2, (int) (-offsetX - viewportWidth / 2 + arrowSize), 0, 180); // -X-axis arrow
        drawArrow(g2, 0, (int) (-offsetY - viewportHeight / 2 + arrowSize), 270); // Y-axis arrow (corregido)
        drawArrow(g2, 0, (int) (-offsetY + viewportHeight / 2 - arrowSize), 90); // -Y-axis arrow (corregido)

        g2.fillOval(-3, -3, 6, 6);
    }

    private void drawArrow(Graphics2D g2, int x, int y, int angle) {
        int arrowSize = 10; // Size of the arrowhead
        AffineTransform tx = g2.getTransform();
        g2.translate(x, y);
        g2.rotate(Math.toRadians(angle));
        g2.drawLine(0, 0, -arrowSize, -arrowSize);
        g2.drawLine(0, 0, -arrowSize, arrowSize);
        g2.setTransform(tx);
    }

    private void drawPoints(Graphics2D g2) {
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

    private void drawCircles(Graphics2D g2) {
        g2.setStroke(new BasicStroke(2));

        List<Circulo> circulos = Circulo.getCirculos();

        for (Circulo circulo : circulos) {
            Punto centro = circulo.getCentro();
            int x = centro.getX() * GRID_SIZE;
            int y = -centro.getY() * GRID_SIZE;
            int radius = circulo.getRadio() * GRID_SIZE;

            g2.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
        }
    }

    private void drawEllipses(Graphics2D g2) {
        g2.setStroke(new BasicStroke(2));

        List<Elipse> elipses = Elipse.getElipses();

        for (Elipse elipse : elipses) {
            Punto centro = elipse.getCentro();
            int x = centro.getX() * GRID_SIZE;
            int y = -centro.getY() * GRID_SIZE;
            int radiusX = elipse.getSemiEjeMayor() * GRID_SIZE;
            int radiusY = elipse.getSemiEjeMenor() * GRID_SIZE;

            g2.drawOval(x - radiusX, y - radiusY, 2 * radiusX, 2 * radiusY);
        }
    }

    private void drawArcs(Graphics2D g2) {
        g2.setStroke(new BasicStroke(2));

        List<Arco> arcos = Arco.getArcos();

        for (Arco arco : arcos) {
            Punto centro = arco.getCentro();
            int x = centro.getX() * GRID_SIZE;
            int y = -centro.getY() * GRID_SIZE;
            int radius = arco.getRadio() * GRID_SIZE;
            int startAngle = (int) arco.getAnguloInicio();
            int endAngle = (int) arco.getAnguloFin();

            g2.drawArc(x - radius, y - radius, 2 * radius, 2 * radius, startAngle, endAngle - startAngle);
        }
    }

    public void addPunto(Punto punto) {
        Punto.getPuntos().add(punto);
        repaint(); // Redibujar el plano
    }

    public void addCirculo(Circulo circulo) {
        Circulo.getCirculos().add(circulo);
        repaint(); // Redibujar el plano para reflejar los cambios
    }

    public void addElipse(Elipse elipse) {
        Elipse.getElipses().add(elipse);
        repaint(); // Redibujar el plano para reflejar los cambios
    }

    public void addArco(Arco arco) {
        Arco.getArcos().add(arco);
        repaint(); // Redibujar el plano para reflejar los cambios
    }

    public void clear() {
        Punto.getPuntos().clear();
        Circulo.getCirculos().clear();
        Elipse.getElipses().clear();
        Arco.getArcos().clear();
        repaint(); // Redibujar el plano para reflejar los cambios
    }

    public CoordinateSystem.Type getCurrentCoordSystem() {
        return currentCoordSystem;
    }

    public void setCurrentCoordSystem(CoordinateSystem.Type coordSystem) {
        if (this.currentCoordSystem != coordSystem) {
            this.currentCoordSystem = coordSystem;
            System.out.println("Cambiando sistema de coordenadas a: " + coordSystem); // Debug
            repaint();
        }
    }
}