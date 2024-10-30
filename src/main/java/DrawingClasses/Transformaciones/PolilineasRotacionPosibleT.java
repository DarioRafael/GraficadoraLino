package DrawingClasses.Transformaciones;

import PaginaPrincipalFolder.GraficadoraBasica.PaginaPrincipal;
import Plano.Transformaciones.Basicas.PlanoCartesianoTraslacion;
import formasADibujar.Linea;
import formasADibujar.Punto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolilineasRotacionPosibleT extends JFrame {
    private PlanoCartesianoTraslacion planoCartesiano;
    private JTable originalTable;
    private JTable rotatedTable;
    private DefaultTableModel originalTableModel;
    private DefaultTableModel rotatedTableModel;
    private JButton backButton;
    private JTextField xInicialField;
    private JTextField yInicialField;
    private JTextField anguloField;
    private JButton regenerarFigura;
    private JButton rotarButton;
    private List<Punto> puntosList;
    private List<Punto> puntosRotadosList;

    public PolilineasRotacionPosibleT() {
        setTitle("Rotación de Figuras");
        setSize(1650, 960);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        createComponents();
        configureLayout();
        addActionListeners();
        setVisible(true);
    }

    private void createComponents() {
        planoCartesiano = new PlanoCartesianoTraslacion();
        planoCartesiano.setPreferredSize(new Dimension(600, 400));

        xInicialField = new JTextField("2", 5);
        yInicialField = new JTextField("2", 5);
        anguloField = new JTextField("0", 5);

        backButton = new JButton("Regresar a Página Principal");
        regenerarFigura = new JButton("Generar figura");
        rotarButton = new JButton("Rotar figura");

        String[] columnNames = {"Punto", "X", "Y"};
        String[] columnNamesEdi = {"P'", "X'", "Y'"};

        originalTableModel = new DefaultTableModel(columnNames, 0);
        rotatedTableModel = new DefaultTableModel(columnNamesEdi, 0);
        originalTable = new JTable(originalTableModel);
        rotatedTable = new JTable(rotatedTableModel);
    }

    private void configureLayout() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Rotación de Figuras", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        add(planoCartesiano, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());

        JPanel tablesPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        JPanel originalTablePanel = new JPanel(new BorderLayout());
        originalTablePanel.add(new JLabel("Puntos Originales", SwingConstants.CENTER), BorderLayout.NORTH);
        JScrollPane originalScrollPane = new JScrollPane(originalTable);
        originalScrollPane.setPreferredSize(new Dimension(300, 200));
        originalTablePanel.add(originalScrollPane, BorderLayout.CENTER);

        JPanel rotatedTablePanel = new JPanel(new BorderLayout());
        rotatedTablePanel.add(new JLabel("Puntos Rotados", SwingConstants.CENTER), BorderLayout.NORTH);
        JScrollPane rotatedScrollPane = new JScrollPane(rotatedTable);
        rotatedScrollPane.setPreferredSize(new Dimension(300, 200));
        rotatedTablePanel.add(rotatedScrollPane, BorderLayout.CENTER);

        tablesPanel.add(originalTablePanel);
        tablesPanel.add(rotatedTablePanel);

        rightPanel.add(tablesPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        controlPanel.add(new JLabel("X inicial:"));
        controlPanel.add(xInicialField);
        controlPanel.add(new JLabel("Y inicial:"));
        controlPanel.add(yInicialField);
        controlPanel.add(new JLabel(""));
        controlPanel.add(regenerarFigura);
        controlPanel.add(new JSeparator());
        controlPanel.add(new JSeparator());
        controlPanel.add(new JLabel("Ángulo (grados):"));
        controlPanel.add(anguloField);
        controlPanel.add(new JLabel(""));
        controlPanel.add(rotarButton);

        rightPanel.add(controlPanel, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.EAST);
    }

    private void addActionListeners() {
        backButton.addActionListener(e -> {
            new PaginaPrincipal().setVisible(true);
            dispose();
        });

        regenerarFigura.addActionListener(e -> {
            int xInicio = Integer.parseInt(xInicialField.getText());
            int yInicio = Integer.parseInt(yInicialField.getText());
            drawFiguraOriginal(xInicio, yInicio, 1);
        });

        rotarButton.addActionListener(e -> realizarRotacion());
    }

    private void drawFiguraOriginal(int xInicio, int yInicio, int aumento) {
        clearPlanoAndData();

        try {
            Punto puntoInicio = new Punto(xInicio, yInicio);

            Punto[] puntosArray = {
                    new Punto(xInicio, yInicio),
                    new Punto(xInicio, yInicio + (2 * aumento)),
                    new Punto(xInicio + (2 * aumento), yInicio + (2 * aumento)),
                    new Punto(xInicio + (2 * aumento), yInicio + (1 * aumento)),
                    new Punto(xInicio + (4 * aumento), yInicio + (1 * aumento)),
                    new Punto(xInicio + (4 * aumento), yInicio + (2 * aumento)),
                    new Punto(xInicio + (6 * aumento), yInicio + (2 * aumento)),
                    new Punto(xInicio + (6 * aumento), yInicio)
            };

            puntosList = Arrays.asList(puntosArray);

            for (int i = 0; i < puntosList.size(); i++) {
                puntosList.get(i).setNombrePunto("P" + (i + 1));
            }

            Punto puntoAnterior = puntoInicio;
            planoCartesiano.addPunto(puntoInicio);

            for (int i = 0; i < puntosList.size(); i++) {
                Punto punto = puntosList.get(i);
                planoCartesiano.addPunto(punto);
                planoCartesiano.addLinea(new Linea(puntoAnterior, punto, true, i + 1));
                puntoAnterior = punto;
            }

            updateOriginalTable(puntosList);
            planoCartesiano.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos.");
        }
    }

    private void realizarRotacion() {
        try {
            double angulo = Math.toRadians(Double.parseDouble(anguloField.getText()));

            if (puntosList == null || puntosList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Primero debe generar la figura original");
                return;
            }

            // Limpiar el plano pero mantener los datos originales
            planoCartesiano.clear();

            // Redibujar la figura original
            Punto puntoAnteriorOriginal = puntosList.get(0);
            planoCartesiano.addPunto(puntoAnteriorOriginal);

            for (int i = 1; i < puntosList.size(); i++) {
                Punto puntoOriginal = puntosList.get(i);
                planoCartesiano.addPunto(puntoOriginal);
                planoCartesiano.addLinea(new Linea(puntoAnteriorOriginal, puntoOriginal, true, i));
                puntoAnteriorOriginal = puntoOriginal;
            }

            puntosRotadosList = new ArrayList<>();

            // Calcular punto de referencia (primer punto)
            Punto puntoReferencia = puntosList.get(0);
            double xRef = puntoReferencia.getX();
            double yRef = puntoReferencia.getY();

            // Modificar el primer punto si es positivo y positivo


            // Crear puntos rotados
            for (int i = 0; i < puntosList.size(); i++) {
                Punto puntoOriginal = puntosList.get(i);

                // Aplicar transformación de rotación respecto al punto de referencia
                double x = puntoOriginal.getX() - xRef;
                double y = puntoOriginal.getY() - yRef;
                System.out.println(i + "x " + x);
                System.out.println(i + "y " + y);



                double newX = xRef + (x * Math.cos(angulo) - y * Math.sin(angulo));
                double newY = yRef + (x * Math.sin(angulo) + y * Math.cos(angulo));

                System.out.println( "Nueva X " + newX);
                System.out.println("Nueva Y " + newY);

                Punto puntoRotado = new Punto((int)Math.round(newX), (int)Math.round(newY));
                puntoRotado.setNombrePunto("P" + (i + 1) + "'");
                puntosRotadosList.add(puntoRotado);
            }

            // Dibujar figura rotada
            Punto puntoAnterior = puntosRotadosList.get(0);
            planoCartesiano.addPunto(puntoAnterior);

            for (int i = 1; i < puntosRotadosList.size(); i++) {
                Punto punto = puntosRotadosList.get(i);
                planoCartesiano.addPunto(punto);
                Linea linea = new Linea(puntoAnterior, punto, true, i);
                planoCartesiano.addLinea(linea);
                puntoAnterior = punto;
            }

            updateRotatedTable(puntosRotadosList);
            planoCartesiano.repaint();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un valor numérico válido para el ángulo.");
        }
    }


    private void clearPlanoAndData() {
        planoCartesiano.clear();
        originalTableModel.setRowCount(0);
        rotatedTableModel.setRowCount(0);
    }

    private void updateOriginalTable(List<Punto> puntos) {
        originalTableModel.setRowCount(0);
        for (Punto punto : puntos) {
            originalTableModel.addRow(new Object[]{
                    punto.getNombrePunto(),
                    punto.getX(),
                    punto.getY()
            });
        }
    }

    private void updateRotatedTable(List<Punto> puntos) {
        rotatedTableModel.setRowCount(0);
        for (Punto punto : puntos) {
            rotatedTableModel.addRow(new Object[]{
                    punto.getNombrePunto(),
                    punto.getX(),
                    punto.getY()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PolilineasRotacionPosibleT frame = new PolilineasRotacionPosibleT();
        });
    }
}