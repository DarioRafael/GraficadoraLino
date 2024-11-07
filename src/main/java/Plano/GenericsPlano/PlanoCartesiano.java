package Plano.GenericsPlano;

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

    private CoordinateSystem.Type currentCoordSystem = CoordinateSystem.Type.CARTESIAN_ABSOLUTE;
    private boolean isDarkMode = false; // Flag to track dark mode status
    private List<Punto> puntos = new ArrayList<>();
    private List<Linea> lineas = new ArrayList<>();

    public PlanoCartesiano() {
        setupMouseListeners();
        offsetX = -GRID_SIZE * 4; // Desplazar hacia la derecha
        offsetY = GRID_SIZE * 4; // Desplazar hacia arriba
        zoomFactor = 0.8; // Ajustar el zoom inicial a 0.8x
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
    // Métodos para eliminar puntos y líneas
    public void removePuntos(List<Punto> puntos) {
        this.puntos.removeAll(puntos);
    }

    public void removeLineasConPuntos(List<Punto> puntos) {
        lineas.removeIf(linea -> puntos.contains(linea.getPuntoInicio()) || puntos.contains(linea.getPuntoFin()));
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

        // Dibujar líneas polares si corresponde
        if (currentCoordSystem == CoordinateSystem.Type.POLAR_ABSOLUTE ||
                currentCoordSystem == CoordinateSystem.Type.POLAR_RELATIVE) {
            drawPolarLines(g2);
        }

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
            g2.drawLine(x, (int) (-offsetY - viewportHeight / 2), x, (int) (-offsetY + viewportHeight / 2));
        }

        for (int i = startY; i <= endY; i++) {
            int y = i * GRID_SIZE;
            g2.drawLine((int) (-offsetX - viewportWidth / 2), y, (int) (-offsetX + viewportWidth / 2), y);
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
        //g2.drawString("(0,0)", 5, -5);
    }
    // Helper method to draw an arrow
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
            //g2.drawString("P" + 1, x1 + 5, y1 - 5);

            // Dibujar todos los puntos intermedios
            int puntoCounter = 1; // Contador para los nombres de los puntos

            if (linea.isEsParteDeFiguraAnonima()) {
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
            } else {
                List<Punto> puntosIntermedios = linea.calcularPuntosIntermedios();
                for (Punto punto : puntosIntermedios) {

                    int x = punto.getX() * GRID_SIZE;
                    int y = -punto.getY() * GRID_SIZE;
                    g2.fillOval(x - 3, y - 3, 6, 6);

                    // Dibujar el nombre del punto
                    g2.drawString("P" + puntoCounter++, x + 5, y - 5); // Etiquetar el punto
                }
            }
        }
    }

    private void drawLinesfk(Graphics2D g2) {
        if (isDarkMode) {
            g2.setColor(Color.CYAN);
        } else {
            g2.setColor(Color.BLUE);
        }
        g2.setStroke(new BasicStroke(2));

        List<Linea> lineas = Linea.getLineas();
        int puntoCounter = 1; // Contador global para los nombres de los puntos

        for (Linea linea : lineas) {
            Punto inicio = linea.getPuntoInicio();
            Punto fin = linea.getPuntoFin();
            int x1 = inicio.getX() * GRID_SIZE;
            int y1 = -inicio.getY() * GRID_SIZE;
            int x2 = fin.getX() * GRID_SIZE;
            int y2 = -fin.getY() * GRID_SIZE;

            g2.drawLine(x1, y1, x2, y2);
// Etiquetar el punto de inicio

            if (linea.isEsParteDeFiguraAnonima()) {
                // Para la figura anónima, solo dibujamos y etiquetamos los puntos de inicio y fin
                g2.fillOval(x1 - 3, y1 - 3, 6, 6);
                g2.fillOval(x2 - 3, y2 - 3, 6, 6);
                g2.drawString("P" + puntoCounter++, x1 + 5, y1 - 5);
                g2.drawString("P" + puntoCounter++, x2 + 5, y2 - 5);
            } else {
                // Para líneas normales, dibujamos todos los puntos intermedios
                List<Punto> puntosIntermedios = linea.calcularPuntosIntermedios();
                for (Punto punto : puntosIntermedios) {
                    int x = punto.getX() * GRID_SIZE;
                    int y = -punto.getY() * GRID_SIZE;
                    g2.fillOval(x - 3, y - 3, 6, 6);
                    g2.drawString("P" + puntoCounter++, x + 5, y - 5);
                }
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
        puntos.add(new Punto((int) (xCentro + radio / Math.sqrt(2)), (int) (yCentro - radio / Math.sqrt(2)))); // P₇ (diagonal arriba derecha)
        puntos.add(new Punto(xCentro, yCentro - radio));        // P₄ (arriba)
        puntos.add(new Punto((int) (xCentro - radio / Math.sqrt(2)), (int) (yCentro - radio / Math.sqrt(2)))); // P₈ (diagonal arriba izquierda)
        puntos.add(new Punto(xCentro - radio, yCentro));        // P₂ (izquierda)
        puntos.add(new Punto((int) (xCentro - radio / Math.sqrt(2)), (int) (yCentro + radio / Math.sqrt(2)))); // P₆ (diagonal abajo izquierda)
        puntos.add(new Punto(xCentro, yCentro + radio));        // P₃ (abajo)
        puntos.add(new Punto((int) (xCentro + radio / Math.sqrt(2)), (int) (yCentro + radio / Math.sqrt(2)))); // P₅ (diagonal abajo derecha)


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

    private List<int[]> calcularPuntosElipse(int xCentro, int yCentro, int semiEjeMayor, int semiEjeMenor) {
        List<int[]> puntos = new ArrayList<>();
        puntos.add(new int[]{xCentro + semiEjeMayor, yCentro, 0}); // Punto derecho
        puntos.add(new int[]{xCentro + (int)(semiEjeMayor / Math.sqrt(2)), yCentro - (int)(semiEjeMenor / Math.sqrt(2)), 45}); // Diagonal derecha arriba
        puntos.add(new int[]{xCentro, yCentro - semiEjeMenor, 90}); // Punto superior
        puntos.add(new int[]{xCentro - (int)(semiEjeMayor / Math.sqrt(2)), yCentro - (int)(semiEjeMenor / Math.sqrt(2)), 135}); // Diagonal izquierda arriba
        puntos.add(new int[]{xCentro - semiEjeMayor, yCentro, 180}); // Punto izquierdo
        puntos.add(new int[]{xCentro - (int)(semiEjeMayor / Math.sqrt(2)), yCentro + (int)(semiEjeMenor / Math.sqrt(2)), 225}); // Diagonal izquierda abajo
        puntos.add(new int[]{xCentro, yCentro + semiEjeMenor, 270}); // Punto inferior
        puntos.add(new int[]{xCentro + (int)(semiEjeMayor / Math.sqrt(2)), yCentro + (int)(semiEjeMenor / Math.sqrt(2)), 315}); // Diagonal derecha abajo
        return puntos;
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

            List<int[]> puntos = calcularPuntosElipse(xCentro, yCentro, semiEjeMayor, semiEjeMenor);
            for (int[] punto : puntos) {
                int x = punto[0];
                int y = punto[1];
                int angulo = punto[2];
                g2.fillOval(x - 3, y - 3, 6, 6);
                //g2.drawString("P" + puntoCounter++ + " (" + angulo + "°)", x + 5, y - 5);
                g2.drawString("P" + puntoCounter++, x + 5, y - 5);
            }
        }
    }



    private void drawArcos(Graphics2D g2) {
        if (isDarkMode) {
            g2.setColor(Color.ORANGE);
        } else {
            g2.setColor(Color.RED);
        }

        for (Arco arco : Arco.getArcos()) {
            int xCentro = arco.getCentro().getX() * GRID_SIZE;
            int yCentro = -arco.getCentro().getY() * GRID_SIZE;
            int radio = arco.getRadio() * GRID_SIZE;
            int anguloInicio = (int) arco.getAnguloInicio();
            int anguloFinal = (int) arco.getAnguloFin();

            // Dibujar el arco
            g2.drawArc(xCentro - radio, yCentro - radio, radio * 2, radio * 2, anguloInicio, anguloFinal - anguloInicio);

            // Dibujar los puntos inicial y final del arco
            Point puntoInicial = calcularPuntoEnArco(xCentro, yCentro, radio, anguloInicio);
            Point puntoFinal = calcularPuntoEnArco(xCentro, yCentro, radio, anguloFinal);

            g2.fillOval(puntoInicial.x - 3, puntoInicial.y - 3, 6, 6);
            g2.fillOval(puntoFinal.x - 3, puntoFinal.y - 3, 6, 6);

            // Etiquetar los puntos
            g2.drawString("Inicio", puntoInicial.x + 5, puntoInicial.y - 5);
            g2.drawString("Fin", puntoFinal.x + 5, puntoFinal.y - 5);

            // Dibujar las líneas desde los puntos inicial y final hacia el centro (simulando la rebanada de pizza)
            g2.drawLine(xCentro, yCentro, puntoInicial.x, puntoInicial.y);  // Línea desde el centro al punto inicial
            g2.drawLine(xCentro, yCentro, puntoFinal.x, puntoFinal.y);      // Línea desde el centro al punto final
        }
    }

    // Método auxiliar para calcular un punto en el arco dado el ángulo
    private Point calcularPuntoEnArco(int xCentro, int yCentro, int radio, int angulo) {
        double anguloRadianes = Math.toRadians(angulo);
        int x = (int) (xCentro + radio * Math.cos(anguloRadianes));
        int y = (int) (yCentro - radio * Math.sin(anguloRadianes)); // Y invertido en gráficos
        return new Point(x, y);
    }

    public void addLinea(Linea linea) {
        Linea.getLineas().add(linea);
        repaint(); // Redibujar el plano para reflejar los cambios
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



    private void drawPolarLines(Graphics2D g2) {
        if (currentCoordSystem == CoordinateSystem.Type.POLAR_ABSOLUTE) {
            // Guardar el stroke original
            Stroke originalStroke = g2.getStroke();

            // Establecer línea punteada
            g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0, new float[]{5}, 0));

            // Color para las líneas polares
            Color originalColor = g2.getColor();
            g2.setColor(isDarkMode ? new Color(150, 150, 150) : new Color(100, 100, 100));

            Font originalFont = g2.getFont();
            g2.setFont(new Font("Arial", Font.PLAIN, 12));

            for (Punto punto : Punto.getPuntos()) {
                int x = punto.getX() * GRID_SIZE;
                int y = -punto.getY() * GRID_SIZE;

                // Dibujar línea punteada desde origen al punto
                g2.drawLine(0, 0, x, y);

                // Calcular el radio (distancia desde el origen)
                double radio = Math.sqrt(punto.getX() * punto.getX() + punto.getY() * punto.getY());

                // Calcular el punto medio de la línea para colocar el texto
                int xMedio = x / 2;
                int yMedio = y / 2;

                // Calcular el ángulo para rotar el texto
                double angulo = Math.atan2(-y, x);

                // Guardar la transformación actual
                AffineTransform original = g2.getTransform();

                // Trasladar al punto medio y rotar para dibujar el texto
                g2.translate(xMedio, yMedio);
                g2.rotate(angulo);

                // Dibujar el valor del radio
                String radioText = "" +radio;
                g2.drawString(radioText, -20, -5);

                // Restaurar la transformación original
                g2.setTransform(original);
            }

            // Restaurar los atributos originales
            g2.setStroke(originalStroke);
            g2.setColor(originalColor);
            g2.setFont(originalFont);

        } else if (currentCoordSystem == CoordinateSystem.Type.POLAR_RELATIVE) {
            // Código existente para coordenadas polares relativas
            g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0, new float[]{5}, 0));
            Punto puntoAnterior = null;
            for (Punto punto : Punto.getPuntos()) {
                int x = punto.getX() * GRID_SIZE;
                int y = -punto.getY() * GRID_SIZE;
                if (puntoAnterior == null) {
                    g2.drawLine(0, 0, x, y);
                } else {
                    int xAnt = puntoAnterior.getX() * GRID_SIZE;
                    int yAnt = -puntoAnterior.getY() * GRID_SIZE;
                    g2.drawLine(xAnt, yAnt, x, y);
                }
                puntoAnterior = punto;
            }
        }
        // Restaurar el trazo normal
        g2.setStroke(new BasicStroke());
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

