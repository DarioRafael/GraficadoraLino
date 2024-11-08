package Plano.Polilineas;

import Plano.GenericsPlano.CoordinateSystem;
import formasADibujar.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.util.List;

public class PlanoCartesianoFiguraPer extends JPanel {

    private double offsetX = 0, offsetY = 0;
    private int gridSize = 50;
    private double zoomFactor = 1.0;
    private Point dragStart = null;

    private static final int GRID_SIZE = 50;
    private static final int AXIS_THICKNESS = 2;
    private static final int TICK_SIZE = 5;
    private static final int LABEL_OFFSET = 20;

    private CoordinateSystem.Type currentCoordSystem = CoordinateSystem.Type.CARTESIAN_ABSOLUTE;


    public PlanoCartesianoFiguraPer() {
        offsetX = -GRID_SIZE * 4; // Desplazar hacia la derecha
        offsetY = GRID_SIZE * 4; // Desplazar hacia arriba
        zoomFactor = 0.8; // Ajustar el zoom inicial a 0.8x

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
        drawPolarLines(g2);
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
            drawPolarLines(g2);
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
        g2.setColor(Color.BLACK);

        List<Punto> puntos = Punto.getPuntos();

        for (Punto punto : puntos) {
            int x = punto.getX() * GRID_SIZE;
            int y = -punto.getY() * GRID_SIZE;

            // Lógica personalizada para ajustar la posición según el nombre del punto
            String nombrePunto = punto.getNombrePunto();
            System.out.println(nombrePunto);
            if (nombrePunto != null) {
                switch (nombrePunto) {
                    case "P1":
                        x += 10; // Mover a la derecha
                        y -= 10; // Mover hacia arriba
                        break;
                    case "P2":
                        x -= 20; // Mover a la izquierda
                        y -= 10;
                        break;
                    case "P3":
                        x -= 20; // Mover hacia abajo
                        y += 20;
                        break;
                    case "P4":
                        x -= 20 ; // Mover hacia abajo
                        y += 20;
                        break;
                    case "P5":
                        x += 5 ; // Mover hacia abajo
                        y += 20;
                        break;
                    case "P6":
                        x += 5 ; // Mover hacia abajo
                        y += 20;
                        break;
                    case "P7":
                        x += 5 ; // Mover hacia abajo
                        y -= 10;
                        break;

                    case "P8":
                        x -= 15 ; // Mover hacia abajo
                        y += 20;
                        break;
                        default:
                        break; // Mantener posición original para otros puntos
                }
                g2.drawString(nombrePunto, x + 2, y - 2);
            }
        }
    }


    // Método para agregar un nuevo punto y repintar
    public void addPunto(Punto punto) {
        Punto.getPuntos().add(punto);
        repaint(); // Redibujar el plano
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


            // COLOR DE LINEAS DE LA FIGURA DE POLILINEAS
            g2.setColor(Color.BLACK);
            g2.drawLine(x1, y1, x2, y2);
            //g2.drawString("P" + 1, x1 + 5, y1 - 5);

            // Dibujar todos los puntos intermedios
            int puntoCounter = 1; // Contador para los nombres de los puntos

            if (linea.isEsParteDeFiguraAnonima()) {
                List<Punto> puntos = Punto.getPuntos();

                for (Punto punto : puntos) {
                    int x = punto.getX() * GRID_SIZE;
                    int y = -punto.getY() * GRID_SIZE;

                    //COLOR DE LOS PUNTOS
                    g2.setColor(Color.RED );
                    g2.fillOval(x - 3, y - 3, 6, 6);


                }
            }
        }
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


    private void drawPolarLines(Graphics2D g2) {

        if (currentCoordSystem == CoordinateSystem.Type.CARTESIAN_RELATIVE) {
            // Guardar el stroke original
            Stroke strokeOriginal = g2.getStroke();
            Color colorOriginal = g2.getColor();


            // COLOR DE LAS LINEAS PUNTEADAS EN RELATIVAS
            g2.setColor(Color.RED);
            float[] dash = {5.0f, 5.0f}; // Define el patrón de línea punteada
            g2.setStroke(new BasicStroke(
                    1.0f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f,
                    dash,
                    0.0f
            ));

            List<Punto> puntos = Punto.getPuntos();
            if (puntos.size() > 0) {
                Point lastPoint = new Point(0, 0);

                for (int i = 0; i < puntos.size(); i++) {
                    Punto currentPunto = puntos.get(i);
                    int currentX = currentPunto.getX() * GRID_SIZE;
                    int currentY = -currentPunto.getY() * GRID_SIZE;

                    if (i == 0) {
                        g2.drawLine(0, currentY, currentX, currentY);
                        g2.drawString("dx1", currentX / 2, currentY - 5);
                        drawArrowHead(g2, 0, currentY, currentX, currentY);

                        g2.drawLine(currentX, 0, currentX, currentY);
                        g2.drawString("dy1", currentX + 5, currentY / 2);
                        drawArrowHead(g2, currentX, 0, currentX, currentY);

                    } else if (i == 2) {
                        g2.drawLine(lastPoint.x - 10, lastPoint.y, lastPoint.x - 10, currentY);
                        g2.drawString("dy2", lastPoint.x - 30, (lastPoint.y + currentY) / 2);
                        drawArrowHead(g2, lastPoint.x - 10, lastPoint.y, lastPoint.x - 10, currentY);


                    } else if (i == 3) {
                        g2.drawLine(lastPoint.x, lastPoint.y - 10, currentX, lastPoint.y - 10);
                        g2.drawString("dx3", lastPoint.x + 30, ((lastPoint.y + currentY) / 2) - 15);
                        drawArrowHead(g2, lastPoint.x, lastPoint.y - 10, currentX, lastPoint.y - 10);


                    } else if (i == 4) {
                        g2.drawLine(lastPoint.x + 10, lastPoint.y, lastPoint.x + 10, currentY);
                        g2.drawString("dy4", lastPoint.x + 20, (lastPoint.y + currentY) / 2);
                        drawArrowHead(g2, lastPoint.x + 10, lastPoint.y, lastPoint.x + 10, currentY);


                    } else if (i == 5) {
                        g2.drawLine(lastPoint.x, lastPoint.y + 10, currentX, lastPoint.y + 10);
                        g2.drawString("dx5", lastPoint.x + 10, ((lastPoint.y + currentY) / 2) + 20);
                        drawArrowHead(g2, lastPoint.x, lastPoint.y + 10, currentX, lastPoint.y + 10);

                    } else if (i == 6) {
                        g2.drawLine(lastPoint.x - 10, lastPoint.y, lastPoint.x - 10, currentY);
                        g2.drawString("dy6", lastPoint.x - 30, (lastPoint.y + currentY) / 2);
                        drawArrowHead(g2, lastPoint.x - 10, lastPoint.y, lastPoint.x - 10, currentY);

                    } else if (i == 7) {
                        g2.drawLine(lastPoint.x, lastPoint.y - 10, currentX, lastPoint.y - 10);
                        g2.drawString("dx7", lastPoint.x + 30, ((lastPoint.y + currentY) / 2) - 15);
                        drawArrowHead(g2, lastPoint.x, lastPoint.y - 10, currentX, lastPoint.y - 10);


                    } else if (i == 8) {
                        g2.drawLine(lastPoint.x + 10, lastPoint.y, lastPoint.x + 10, currentY);
                        g2.drawString("dy8", lastPoint.x + 20, (lastPoint.y + currentY) / 2);
                        drawArrowHead(g2, lastPoint.x + 10, lastPoint.y, lastPoint.x + 10, currentY);

                    }


                    lastPoint = new Point(currentX, currentY);
                }
            }

            g2.setStroke(strokeOriginal);
            g2.setColor(colorOriginal);
        }


        if (currentCoordSystem == CoordinateSystem.Type.POLAR_ABSOLUTE ||
                currentCoordSystem == CoordinateSystem.Type.POLAR_RELATIVE) {
            if (currentCoordSystem == CoordinateSystem.Type.POLAR_RELATIVE) {
                // Guardar el stroke original
                Stroke strokeOriginal = g2.getStroke();
                Color colorOriginal = g2.getColor();

                // Configurar el estilo para las líneas punteadas
                g2.setColor(Color.BLUE);
                float[] guiones = {10.0f, 10.0f};
                g2.setStroke(new BasicStroke(
                        2.0f,
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND,
                        0,
                        guiones,
                        0
                ));

                List<Punto> puntos = Punto.getPuntos();

                // Solo proceder si hay puntos
                if (puntos.size() > 0) {
                    // Primera línea: desde origen al primer punto
                    Punto primerPunto = puntos.get(0);
                    int x1 = primerPunto.getX() * GRID_SIZE;
                    int y1 = -primerPunto.getY() * GRID_SIZE;

                    g2.drawLine(0, 0, x1, y1);
                    drawArrowHead(g2, 0, 0, x1, y1);


                    g2.drawString("dr1", x1 / 2 - 10, y1 / 2 - 5);

                    // Punto de origen actual inicia en el primer punto
                    Point origenActual = new Point(x1, y1);
                    Point origenActual0 = new Point(0, 0);

                    g2.setColor(Color.RED);
                    drawAngleRelativo(g2, origenActual0, x1, y1, "  dθ1", 50);


                    // Tercera línea
                    if (puntos.size() > 2) {
                        Punto tercerPunto = puntos.get(2);
                        int x3 = tercerPunto.getX() * GRID_SIZE;
                        int y3 = -tercerPunto.getY() * GRID_SIZE;

                        // Dibujar la segunda línea en azul
                        g2.setColor(Color.BLUE);
                        g2.drawLine(origenActual.x, origenActual.y, x3, y3);
                        drawArrowHead(g2, origenActual.x, origenActual.y, x3, y3);
                        g2.drawString("dr2", (origenActual.x + x3) / 2 - 20, (origenActual.y + y3) / 2 - 5);

                        g2.setColor(Color.RED);
                        drawAngleRelativo(g2, origenActual, x3, y3, "   dθ2", 50);


                        origenActual = new Point(x3, y3);
                        //drawAngle(g2,origenActual.x, origenActual.y, x3,y3,"02", 0);

                    }

                    // Cuarta línea
                    if (puntos.size() > 3) {
                        Punto cuartoPunto = puntos.get(3);
                        int x4 = cuartoPunto.getX() * GRID_SIZE;
                        int y4 = -cuartoPunto.getY() * GRID_SIZE;

                        g2.setColor(Color.BLUE);
                        g2.drawLine(origenActual.x, origenActual.y, x4, y4);
                        drawArrowHead(g2, origenActual.x, origenActual.y, x4, y4);
                        g2.drawString("dr3", (origenActual.x + x4) / 2 - 10, (origenActual.y + y4) / 2 - 5);

                        //g2.setColor(Color.RED);
                        //drawAngleRelativo(g2, origenActual, x4, y4, "dθ3", 30);


                        origenActual = new Point(x4, y4);
                    }

                    // Quinta línea
                    if (puntos.size() > 4) {
                        Punto quintoPunto = puntos.get(4);
                        int x5 = quintoPunto.getX() * GRID_SIZE;
                        int y5 = -quintoPunto.getY() * GRID_SIZE;

                        g2.setColor(Color.BLUE);
                        g2.drawLine(origenActual.x, origenActual.y, x5, y5);
                        drawArrowHead(g2, origenActual.x, origenActual.y, x5, y5);
                        g2.drawString("dr4", (origenActual.x + x5) / 2 + 5, (origenActual.y + y5) / 2 - 5);

                        g2.setColor(Color.RED);
                        drawAngleRelativo(g2, origenActual, x5, y5, "dθ4", 30);


                        origenActual = new Point(x5, y5);
                    }

                    // Sexta línea
                    if (puntos.size() > 5) {
                        Punto sextoPunto = puntos.get(5);
                        int x6 = sextoPunto.getX() * GRID_SIZE;
                        int y6 = -sextoPunto.getY() * GRID_SIZE;

                        g2.setColor(Color.BLUE);
                        g2.drawLine(origenActual.x, origenActual.y, x6, y6);
                        drawArrowHead(g2, origenActual.x, origenActual.y, x6, y6);
                        g2.drawString("dr5", (origenActual.x + x6) / 2 - 10, (origenActual.y + y6) / 2 - 5);

                        //g2.setColor(Color.RED);
                        //drawAngleRelativo(g2, origenActual, x6, y6, "dθ5", 30);


                        origenActual = new Point(x6, y6);
                    }

                    // Séptima línea
                    if (puntos.size() > 6) {
                        Punto septimoPunto = puntos.get(6);
                        int x7 = septimoPunto.getX() * GRID_SIZE;
                        int y7 = -septimoPunto.getY() * GRID_SIZE;

                        g2.setColor(Color.BLUE);
                        g2.drawLine(origenActual.x, origenActual.y, x7, y7);
                        drawArrowHead(g2, origenActual.x, origenActual.y, x7, y7);
                        g2.drawString("dr6", (origenActual.x + x7) / 2 - 20, (origenActual.y + y7) / 2 - 5);

                        g2.setColor(Color.RED);
                        drawAngleRelativo(g2, origenActual, x7, y7, "dθ6", 30);


                        origenActual = new Point(x7, y7);
                    }

                    // Octava línea
                    if (puntos.size() > 7) {
                        Punto octavoPunto = puntos.get(7);
                        int x8 = octavoPunto.getX() * GRID_SIZE;
                        int y8 = -octavoPunto.getY() * GRID_SIZE;

                        g2.setColor(Color.BLUE);
                        g2.drawLine(origenActual.x, origenActual.y, x8, y8);
                        drawArrowHead(g2, origenActual.x, origenActual.y, x8, y8);
                        g2.drawString("dr7", (origenActual.x + x8) / 2 - 10, (origenActual.y + y8) / 2 - 5);

                        //g2.setColor(Color.RED);
                        //drawAngleRelativo(g2, origenActual, x8, y8, "dθ7", 30);


                        origenActual = new Point(x8, y8);
                    }

                    // Octava línea
                    if (puntos.size() > 8) {
                        Punto novenoPunto = puntos.get(8);
                        int x9 = novenoPunto.getX() * GRID_SIZE;
                        int y9 = -novenoPunto.getY() * GRID_SIZE;

                        g2.setColor(Color.BLUE);
                        g2.drawLine(origenActual.x, origenActual.y, x9, y9);
                        drawArrowHead(g2, origenActual.x, origenActual.y, x9, y9);
                        g2.drawString("dr8", (origenActual.x + x9) / 2 - 20, (origenActual.y + y9) / 2 - 5);

                        g2.setColor(Color.RED);
                        drawAngleRelativo(g2, origenActual, x9, y9, "dθ8", 30);


                        origenActual = new Point(x9, y9);
                    }

                }

                // Restaurar el stroke y color original
                g2.setStroke(strokeOriginal);
                g2.setColor(colorOriginal);
            } else {
                // Guardar el stroke original
                Stroke strokeOriginal = g2.getStroke();
                Color colorOriginal = g2.getColor();

                // Configurar el estilo para las líneas punteadas
                g2.setColor(Color.BLUE);
                float[] guiones = {10.0f, 10.0f};
                g2.setStroke(new BasicStroke(
                        2.0f,
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND,
                        0,
                        guiones,
                        0
                ));

                List<Punto> puntos = Punto.getPuntos();

                // Solo proceder si hay puntos
                if (puntos.size() > 0) {
                    // Primera línea: desde el origen al primer punto
                    Punto primerPunto = puntos.get(0);
                    int x1 = primerPunto.getX() * GRID_SIZE;
                    int y1 = -primerPunto.getY() * GRID_SIZE;

                    g2.setColor(Color.BLUE);
                    g2.drawLine(0, 0, x1, y1);
                    drawArrowHead(g2, 0, 0, x1, y1);
                    g2.drawString("", x1 / 2 - 10, y1 / 2 - 5);

                    g2.setColor(Color.RED);
                    drawAngle(g2, 0, 0, x1, y1, "0");

                    // Segunda línea
                    if (puntos.size() > 1) {
                        Punto segundoPunto = puntos.get(1);
                        int x2 = segundoPunto.getX() * GRID_SIZE;
                        int y2 = -segundoPunto.getY() * GRID_SIZE;


                        g2.setColor(Color.BLUE);
                        g2.drawLine(0, 0, x2, y2);
                        drawArrowHead(g2, 0, 0, x2, y2);
                        g2.drawString("r1", x2 / 2 - 10, y2 / 2 - 5);

                        g2.setColor(Color.RED);
                        drawAngle(g2, 0, 0, x2, y2, "01");
                    }

                    // Tercera línea
                    if (puntos.size() > 2) {
                        Punto tercerPunto = puntos.get(2);
                        int x3 = tercerPunto.getX() * GRID_SIZE;
                        int y3 = -tercerPunto.getY() * GRID_SIZE;

                        g2.setColor(Color.BLUE);
                        g2.drawLine(0, 0, x3, y3);
                        drawArrowHead(g2, 0, 0, x3, y3);
                        g2.drawString("r2", x3 / 2 - 10, y3 / 2 - 5);

                        g2.setColor(Color.RED);
                        drawAngle(g2, 0, 0, x3, y3, "02");
                    }

                    // Cuarta línea
                    if (puntos.size() > 3) {
                        Punto cuartoPunto = puntos.get(3);
                        int x4 = cuartoPunto.getX() * GRID_SIZE;
                        int y4 = -cuartoPunto.getY() * GRID_SIZE;

                        g2.setColor(Color.BLUE);
                        g2.drawLine(0, 0, x4, y4);
                        drawArrowHead(g2, 0, 0, x4, y4);
                        g2.drawString("r3", x4 / 2 - 10, y4 / 2 - 5);

                        g2.setColor(Color.RED);
                        drawAngle(g2, 0, 0, x4, y4, "03");

                    }

                    // Quinta línea
                    if (puntos.size() > 4) {
                        Punto quintoPunto = puntos.get(4);
                        int x5 = quintoPunto.getX() * GRID_SIZE;
                        int y5 = -quintoPunto.getY() * GRID_SIZE;

                        g2.setColor(Color.BLUE);
                        g2.drawLine(0, 0, x5, y5);
                        drawArrowHead(g2, 0, 0, x5, y5);
                        g2.drawString("r4", x5 / 2 - 10, y5 / 2 - 5);

                        g2.setColor(Color.RED);
                        drawAngle(g2, 0, 0, x5, y5, "04");

                    }

                    // Sexta línea
                    if (puntos.size() > 5) {
                        Punto sextoPunto = puntos.get(5);
                        int x6 = sextoPunto.getX() * GRID_SIZE;
                        int y6 = -sextoPunto.getY() * GRID_SIZE;

                        g2.setColor(Color.BLUE);
                        g2.drawLine(0, 0, x6, y6);
                        drawArrowHead(g2, 0, 0, x6, y6);
                        g2.drawString("r5", x6 / 2 - 10, y6 / 2 - 5);

                        g2.setColor(Color.RED);
                        drawAngle(g2, 0, 0, x6, y6, "05");

                    }

                    // Séptima línea
                    if (puntos.size() > 6) {
                        Punto septimoPunto = puntos.get(6);
                        int x7 = septimoPunto.getX() * GRID_SIZE;
                        int y7 = -septimoPunto.getY() * GRID_SIZE;

                        g2.setColor(Color.BLUE);
                        g2.drawLine(0, 0, x7, y7);
                        drawArrowHead(g2, 0, 0, x7, y7);
                        g2.drawString("r6", x7 / 2 - 10, y7 / 2 - 5);

                        g2.setColor(Color.RED);
                        drawAngle(g2, 0, 0, x7, y7, "06");

                    }

                    // Octava línea
                    if (puntos.size() > 7) {
                        Punto octavoPunto = puntos.get(7);
                        int x8 = octavoPunto.getX() * GRID_SIZE;
                        int y8 = -octavoPunto.getY() * GRID_SIZE;

                        g2.setColor(Color.BLUE);
                        g2.drawLine(0, 0, x8, y8);
                        drawArrowHead(g2, 0, 0, x8, y8);
                        g2.drawString("r7", x8 / 2 - 10, y8 / 2 - 5);

                        g2.setColor(Color.RED);
                        drawAngle(g2, 0, 0, x8, y8, "07");

                    }


                    if (puntos.size() > 8) {
                        Punto novenoPunto = puntos.get(8);
                        int x9 = novenoPunto.getX() * GRID_SIZE;
                        int y9 = -novenoPunto.getY() * GRID_SIZE;

                        g2.setColor(Color.BLUE);
                        g2.drawLine(0, 0, x9, y9);
                        drawArrowHead(g2, 0, 0, x9, y9);
                        g2.drawString("r8", x9 / 2 - 10, y9 / 2 - 5);

                        g2.setColor(Color.RED);
                        drawAngle(g2, 0, 0, x9, y9, "08");

                    }
                }

                // Restaurar el stroke y color original
                g2.setStroke(strokeOriginal);
                g2.setColor(colorOriginal);
            }

        }
    }

    private void drawAngleRelativo(Graphics2D g2, Point origenActual, int x2, int y2, String angleLabel, int radio) {
        // Calcular el ángulo respecto al punto de origen actual
        double angle = Math.atan2(-(y2 - origenActual.y), x2 - origenActual.x);
        if (angle < 0) angle += 2 * Math.PI;

        // Radio para el arco del ángulo
        int arcRadius = radio;

        // Guardar el stroke actual
        Stroke originalStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(1.5f));

        boolean isSpecialCase = false;
        if (y2 > origenActual.y && Math.abs(x2 - origenActual.x) < 5) {  // 5 pixels de tolerancia
            angle = -Math.PI / 2;  // -90 grados
            isSpecialCase = true;
        }

        // Dibujar el arco
        int angleInDegrees = (int) Math.toDegrees(angle);
        g2.drawArc(
                origenActual.x - arcRadius,
                origenActual.y - arcRadius,
                arcRadius * 2,
                arcRadius * 2,
                0,
                angleInDegrees
        );

        // Dibujar el texto del ángulo cerca del inicio del arco
        int textOffsetX = (int) (15 * Math.cos(Math.toRadians(10)));
        int textOffsetY = -(int) (15 * Math.sin(Math.toRadians(10)));
        g2.drawString(angleLabel, (origenActual.x + textOffsetX) + 30, origenActual.y + textOffsetY);

        // Dibujar la punta de flecha al final del arco
        double endAngleRad = Math.toRadians(angleInDegrees);
        int arrowX = origenActual.x + (int) (arcRadius * Math.cos(endAngleRad));
        int arrowY = origenActual.y - (int) (arcRadius * Math.sin(endAngleRad));

        // Calculamos la dirección tangente al arco en el punto final
        double tangentAngle = endAngleRad + Math.PI / 2;

        if (isSpecialCase) {
            tangentAngle -= Math.PI; // Flip the arrowhead direction by 180 degrees
        }


        int arrowLength = 10;

        // Puntos para la punta de flecha
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];

        xPoints[0] = arrowX;
        yPoints[0] = arrowY;

        xPoints[1] = (int) (arrowX - arrowLength * Math.cos(tangentAngle - Math.PI / 6));
        yPoints[1] = (int) (arrowY + arrowLength * Math.sin(tangentAngle - Math.PI / 6));

        xPoints[2] = (int) (arrowX - arrowLength * Math.cos(tangentAngle + Math.PI / 6));
        yPoints[2] = (int) (arrowY + arrowLength * Math.sin(tangentAngle + Math.PI / 6));

        // Dibujar la punta de flecha
        g2.fillPolygon(xPoints, yPoints, 3);

        // Restaurar el stroke original
        g2.setStroke(originalStroke);
    }

    private void drawAngle(Graphics2D g2, int x1, int y1, int x2, int y2, String angleLabel) {
        // Calcular el ángulo
        double angle = Math.atan2(-(y2 - y1), x2 - x1);
        if (angle < 0) angle += 2 * Math.PI;

        // Calcular la longitud del radio (distancia desde el origen al punto)
        double radioTotal = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

        // El arco será la mitad del radio total
        int arcRadius = (int) (radioTotal / 2);

        // Guardar el stroke actual
        Stroke originalStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(1.5f));

        // Dibujar el arco
        int angleInDegrees = (int) Math.toDegrees(angle);
        g2.drawArc(
                x1 - arcRadius,
                y1 - arcRadius,
                arcRadius * 2,
                arcRadius * 2,
                0,
                angleInDegrees
        );

        g2.setColor(Color.RED);

        // Modificación para el posicionamiento del texto
        double textAngle;
        if (angleLabel.equals("08")) {
            // Ajuste específico para θ8
            textAngle = Math.PI / 180; // Reducimos el ángulo a la mitad (7.5 grados)
        } else {
            textAngle = Math.PI / 12; // 15 grados para los demás
        }

        // Colocar el texto cerca del inicio del ángulo
        // Usamos un ángulo pequeño fijo para el texto (15 grados = π/12 radianes)

        // El texto estará un poco más alejado que el arco
        int textRadius = arcRadius + 5;
        int textOffsetX = (int) (textRadius * Math.cos(textAngle));
        int textOffsetY = -(int) (textRadius * Math.sin(textAngle));

        // Dibujamos el texto
        g2.drawString("θ" + angleLabel.substring(1), x1 + textOffsetX, y1 + textOffsetY);

        // Dibujar la punta de flecha al final del arco
        double endAngleRad = Math.toRadians(angleInDegrees);
        int arrowX = x1 + (int) (arcRadius * Math.cos(endAngleRad));
        int arrowY = y1 - (int) (arcRadius * Math.sin(endAngleRad));

        // Calculamos la dirección tangente al arco en el punto final
        double tangentAngle = endAngleRad + Math.PI / 2;
        int arrowLength = 10;

        // Puntos para la punta de flecha
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];

        xPoints[0] = arrowX;
        yPoints[0] = arrowY;

        xPoints[1] = (int) (arrowX - arrowLength * Math.cos(tangentAngle - Math.PI / 6));
        yPoints[1] = (int) (arrowY + arrowLength * Math.sin(tangentAngle - Math.PI / 6));

        xPoints[2] = (int) (arrowX - arrowLength * Math.cos(tangentAngle + Math.PI / 6));
        yPoints[2] = (int) (arrowY + arrowLength * Math.sin(tangentAngle + Math.PI / 6));

        // Dibujar la punta de flecha
        g2.fillPolygon(xPoints, yPoints, 3);

        // Restaurar el stroke original
        g2.setStroke(originalStroke);
    }

    private void drawArrowHead(Graphics2D g2, int x1, int y1, int x2, int y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = 10; // Longitud de la flecha

        // Calcular los puntos de la flecha
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];

        xPoints[0] = x2;
        yPoints[0] = y2;

        xPoints[1] = (int) (x2 - len * Math.cos(angle - Math.PI / 6));
        yPoints[1] = (int) (y2 - len * Math.sin(angle - Math.PI / 6));

        xPoints[2] = (int) (x2 - len * Math.cos(angle + Math.PI / 6));
        yPoints[2] = (int) (y2 - len * Math.sin(angle + Math.PI / 6));

        // Dibujar la flecha como un triángulo relleno
        g2.fillPolygon(xPoints, yPoints, 3);
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

