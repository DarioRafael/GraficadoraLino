package Plano.Transformaciones.Compuestas;

import Plano.GenericsPlano.CoordinateSystem;
import formasADibujar.Linea;
import formasADibujar.Punto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.util.List;

public class PlanoCartesianoEscalacionSuc extends JPanel {

    private double offsetX = 0, offsetY = 0;
    private int gridSize = 50;
    private double zoomFactor = 1.0;
    private Point dragStart = null;

    private static final int GRID_SIZE = 50;
    private static final int AXIS_THICKNESS = 2;
    private static final int TICK_SIZE = 5;
    private static final int LABEL_OFFSET = 20;
    private CoordinateSystem.Type currentCoordSystem = CoordinateSystem.Type.CARTESIAN_ABSOLUTE;

    private static final Color COLOR_PUNTO_ORIGINAL = Color.RED;
    private static final Color COLOR_PUNTO_ESCALADO = Color.BLUE;


    private static final Color COLOR_LINEA_ORIGINAL = Color.BLACK;
    private static final Color COLOR_LINEA_ESCALADA = new Color(255, 140, 0);





    public PlanoCartesianoEscalacionSuc() {
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

        //COLOR DEL PLANO
        g2.setColor(Color.WHITE);
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

        // Dibujar líneas polares si corresponde
        if (currentCoordSystem == CoordinateSystem.Type.POLAR_ABSOLUTE ||
                currentCoordSystem == CoordinateSystem.Type.POLAR_RELATIVE) {
        }

        drawPoints(g2);
        drawLines(g2);
    }

    private void drawGrid(Graphics2D g2) {

        g2.setColor(Color.GRAY);

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

        //COLOR DE LOS EJES
        g2.setColor(Color.BLACK);

        g2.setStroke(new BasicStroke(AXIS_THICKNESS));

        double viewportWidth = getWidth() / zoomFactor;
        double viewportHeight = getHeight() / zoomFactor;

        // Dibujar los ejes X e Y
        g2.drawLine((int) (-offsetX - viewportWidth / 2), 0, (int) (-offsetX + viewportWidth / 2), 0); // Eje X
        g2.drawLine(0, (int) (-offsetY - viewportHeight / 2), 0, (int) (-offsetY + viewportHeight / 2)); // Eje Y

        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        String prefix = (currentCoordSystem == CoordinateSystem.Type.CARTESIAN_RELATIVE ||
                currentCoordSystem == CoordinateSystem.Type.POLAR_RELATIVE) ? "d" : "";

        // Etiquetas de los ejes
        g2.drawString(prefix + "X", (int) (-offsetX + viewportWidth / 2) - LABEL_OFFSET, -LABEL_OFFSET);
        g2.drawString("-" + prefix + "X", (int) (-offsetX - viewportWidth / 2) + LABEL_OFFSET, -LABEL_OFFSET);
        g2.drawString(prefix + "Y", LABEL_OFFSET, (int) (-offsetY - viewportHeight / 2) + LABEL_OFFSET);
        g2.drawString("-" + prefix + "Y", LABEL_OFFSET, (int) (-offsetY + viewportHeight / 2) - LABEL_OFFSET);

        g2.setFont(new Font("Arial", Font.PLAIN, 10));

        // Dibujar las marcas y números en los ejes
        int startX = (int) Math.floor((-offsetX - viewportWidth / 2) / GRID_SIZE);
        int endX = (int) Math.ceil((-offsetX + viewportWidth / 2) / GRID_SIZE);
        int startY = (int) Math.floor((-offsetY - viewportHeight / 2) / GRID_SIZE);
        int endY = (int) Math.ceil((-offsetY + viewportHeight / 2) / GRID_SIZE);

        // Dibujar marcas en el eje X
        for (int i = startX; i <= endX; i++) {
            if (i != 0) {
                int x = i * GRID_SIZE;
                g2.drawLine(x, -TICK_SIZE, x, TICK_SIZE);
                g2.drawString(Integer.toString(i), x - 5, LABEL_OFFSET);
            }
        }

        // Dibujar marcas en el eje Y
        for (int i = startY; i <= endY; i++) {
            if (i != 0) {
                int y = i * GRID_SIZE;
                g2.drawLine(-TICK_SIZE, y, TICK_SIZE, y);
                g2.drawString(Integer.toString(-i), -LABEL_OFFSET, y + 5);
            }
        }

        int arrowSize = 10; // Tamaño de la punta de la flecha

        // Dibujar flechas en los ejes
        // Flechas del eje X
        drawArrow(g2, (int) (-offsetX + viewportWidth / 2 - arrowSize), 0, 0); // Flecha X positivo
        drawArrow(g2, (int) (-offsetX - viewportWidth / 2 + arrowSize), 0, 180); // Flecha X negativo

        // Flechas del eje Y
        drawArrow(g2, 0, (int) (-offsetY - viewportHeight / 2 + arrowSize), -90); // Flecha Y positivo
        drawArrow(g2, 0, (int) (-offsetY + viewportHeight / 2 - arrowSize), 90); // Flecha Y negativo

        // Dibujar punto de origen
        g2.fillOval(-3, -3, 6, 6);
    }

    // Método auxiliar para dibujar las flechas
    private void drawArrow(Graphics2D g2, int x, int y, int angle) {
        int arrowSize = 10; // Tamaño de la punta de la flecha
        AffineTransform tx = g2.getTransform();
        g2.translate(x, y);
        double radians = Math.toRadians(angle);
        g2.rotate(radians);
        g2.drawLine(0, 0, -arrowSize, -arrowSize);
        g2.drawLine(0, 0, -arrowSize, arrowSize);
        g2.setTransform(tx);
    }

    private void drawPoints(Graphics2D g2) {
        List<Punto> puntos = Punto.getPuntos();

        for (Punto punto : puntos) {
            int x = punto.getX() * GRID_SIZE;
            int y = -punto.getY() * GRID_SIZE;

            // Determinar el color basado en si es un punto escalado o no
            if (punto.getNombrePunto() != null && punto.getNombrePunto().contains("'")) {
                g2.setColor(COLOR_PUNTO_ESCALADO);
            } else {
                g2.setColor(COLOR_PUNTO_ORIGINAL);
            }

            g2.fillOval(x - 3, y - 3, 6, 6);

            // Verificar si el nombre no es null antes de dibujar
            String nombrePunto = punto.getNombrePunto();
            if (nombrePunto != null) {
                g2.drawString(nombrePunto, x + 2, y - 2);
            }
        }
    }

    private void drawLines(Graphics2D g2) {
        g2.setStroke(new BasicStroke(2));
        List<Linea> lineas = Linea.getLineas();

        for (Linea linea : lineas) {
            Punto inicio = linea.getPuntoInicio();
            Punto fin = linea.getPuntoFin();
            int x1 = inicio.getX() * GRID_SIZE;
            int y1 = -inicio.getY() * GRID_SIZE;
            int x2 = fin.getX() * GRID_SIZE;
            int y2 = -fin.getY() * GRID_SIZE;

            // Determinar el color basado en si es una línea escalada o no
            if (inicio.getNombrePunto() != null && inicio.getNombrePunto().contains("'")) {
                g2.setColor(COLOR_LINEA_ESCALADA);
            } else {
                g2.setColor(COLOR_LINEA_ORIGINAL);
            }

            g2.drawLine(x1, y1, x2, y2);

            if (linea.isEsParteDeFiguraAnonima()) {
                List<Punto> puntos = Punto.getPuntos();
                for (Punto punto : puntos) {
                    int x = punto.getX() * GRID_SIZE;
                    int y = -punto.getY() * GRID_SIZE;

                    // Usar el mismo esquema de colores para los puntos
                    if (punto.getNombrePunto() != null && punto.getNombrePunto().contains("'")) {
                        g2.setColor(COLOR_PUNTO_ESCALADO);
                    } else {
                        g2.setColor(COLOR_PUNTO_ORIGINAL);
                    }

                    g2.fillOval(x - 3, y - 3, 6, 6);
                }
            }
        }
    }


    // Método para agregar un nuevo punto y repintar
    public void addPunto(Punto punto) {
        Punto.getPuntos().add(punto);
        repaint(); // Redibujar el plano
    }




    public void addLinea(Linea linea) {
        Linea.getLineas().add(linea);
        repaint(); // Redibujar el plano para reflejar los cambios
    }


    public void clear() {
        Punto.getPuntos().clear();
        Linea.getLineas().clear();
        repaint(); // Redibujar el plano para reflejar los cambios
    }




    public CoordinateSystem.Type getCurrentCoordSystem() {
        return currentCoordSystem;
    }

    // Asegúrate de que este método esté actualizado para manejar el cambio de sistema de coordenadas
    public void setCurrentCoordSystem(CoordinateSystem.Type coordSystem) {
        if (this.currentCoordSystem != coordSystem) {
            this.currentCoordSystem = coordSystem;
            System.out.println("Cambiando sistema de coordenadas a: " + coordSystem); // Debug
            repaint();
        }
    }

}

