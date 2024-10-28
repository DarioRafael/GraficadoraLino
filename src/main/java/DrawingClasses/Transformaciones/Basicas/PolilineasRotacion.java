package DrawingClasses.Transformaciones.Basicas;

import PaginaPrincipalFolder.Transformaciones.Componentes.TransformacionesBasicas;
import Plano.Transformaciones.Basicas.PlanoCartesianoRotacion;
import formasADibujar.Rotacion.Linea;
import formasADibujar.Rotacion.Punto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolilineasRotacion extends JFrame {
    private PlanoCartesianoRotacion planoCartesiano;
    private JTable originalTable;
    private JTable rotatedTable;
    private DefaultTableModel originalTableModel;
    private DefaultTableModel rotatedTableModel;
    private JButton backButton;
    private JTextField xInicialField;
    private JTextField yInicialField;
    private JTextField anguloField;
    private JLabel anguloLabel;
    private JButton regenerarFigura;
    private JButton rotarButton;
    private List<Punto> puntosList;
    private List<Punto> puntosRotadosList;
    public JComboBox<String> aumentoComboBox;
    public int anguloText = 0;

    public PolilineasRotacion() {
        setTitle("Transformaciones Geométricas 2D Básica: Rotacion");
        setSize(1650, 960);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        createComponents();
        configureLayout();
        addActionListeners();
        setVisible(true);
    }

    private void createComponents() {
        planoCartesiano = new PlanoCartesianoRotacion();
        planoCartesiano.setPreferredSize(new Dimension(600, 400));

        xInicialField = new JTextField("2", 5);
        yInicialField = new JTextField("2", 5);
        anguloField = new JTextField("0", 5);

        backButton = new JButton("Menu");
        regenerarFigura = new JButton("Generar figura");
        rotarButton = new JButton("Rotar figura");

        // ComboBox para seleccionar el aumento
        String[] aumentoOptions = {"x1", "x2", "x4", "x8", "x16"};
        aumentoComboBox = new JComboBox<>(aumentoOptions);
        aumentoComboBox.setSelectedIndex(0); // Valor por defecto: x1

        String[] columnNames = {"Punto", "X", "Y"};
        String[] columnNamesEdi = {"P'", "X'", "Y'"};

        originalTableModel = new DefaultTableModel(columnNames, 0);
        rotatedTableModel = new DefaultTableModel(columnNamesEdi, 0);
        originalTable = new JTable(originalTableModel);
        rotatedTable = new JTable(rotatedTableModel);

        // Labels para mostrar valores de Sx y Sy después de la escalación
        anguloLabel = new JLabel("Angulo de rotación: 0 °", SwingConstants.CENTER);
        anguloLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Cambia "Arial" y 18 por la fuente y tamaño deseados

    }

    private void configureLayout() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Transformaciones Geométricas 2D Básica: Rotacion", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        add(planoCartesiano, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());

        JPanel tablesPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        JPanel originalTablePanel = new JPanel(new BorderLayout());

        JLabel originalLabel = new JLabel("Puntos Originales", SwingConstants.CENTER);
        originalLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set font to Arial, bold, size 18
        originalTablePanel.add(originalLabel, BorderLayout.NORTH);
        JScrollPane originalScrollPane = new JScrollPane(originalTable);

        originalScrollPane.setPreferredSize(new Dimension(300, 200));
        originalTablePanel.add(originalScrollPane, BorderLayout.CENTER);

        JPanel rotatedTablePanel = new JPanel(new BorderLayout());

        JLabel scaledLabel = new JLabel("Puntos Rotados: " + "Ángulo de rotación: " + anguloText + "°" , SwingConstants.CENTER);
        scaledLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set font to Arial, bold, size 18
        rotatedTablePanel.add(scaledLabel, BorderLayout.NORTH);

        JScrollPane rotatedScrollPane = new JScrollPane(rotatedTable);


        rotatedScrollPane.setPreferredSize(new Dimension(300, 200));
        rotatedTablePanel.add(rotatedScrollPane, BorderLayout.CENTER);

        tablesPanel.add(originalTablePanel);
        tablesPanel.add(rotatedTablePanel);

        rightPanel.add(tablesPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(10, 2, 5, 5));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        controlPanel.add(new JLabel("X inicial:"));
        controlPanel.add(xInicialField);
        controlPanel.add(new JLabel("Y inicial:"));
        controlPanel.add(yInicialField);
        controlPanel.add(new JLabel("Aumento:"));
        controlPanel.add(aumentoComboBox); // Añadimos el ComboBox del aumento
        controlPanel.add(new JLabel(""));
        controlPanel.add(regenerarFigura);
        controlPanel.add(new JSeparator());
        controlPanel.add(new JSeparator());
        controlPanel.add(new JLabel ("Angulo de rotacion:"));
        controlPanel.add(anguloLabel);
        controlPanel.add(anguloField);
        controlPanel.add(new JLabel(""));
        controlPanel.add(rotarButton);
        controlPanel.add(anguloLabel);

        rightPanel.add(controlPanel, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.EAST);
    }

    private void addActionListeners() {
        backButton.addActionListener(e -> {
            new TransformacionesBasicas().setVisible(true);
            clearPlanoAndData();
            dispose();
        });

        regenerarFigura.addActionListener(e -> {
            int xInicio = Integer.parseInt(xInicialField.getText());
            int yInicio = Integer.parseInt(yInicialField.getText());
            int aumento = Integer.parseInt(aumentoComboBox.getSelectedItem().toString().substring(1)); // Obtener el valor de aumento (x1, x2, etc.)
            drawFiguraOriginal(xInicio, yInicio, aumento);
        });

        rotarButton.addActionListener(e -> realizarRotacion());
    }

    public void drawFiguraOriginal(double xInicio, double yInicio, double aumento) {
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
            anguloText = (int) angulo;

            if (puntosList == null || puntosList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Primero debe generar la figura original");
                return;
            }

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

            // Ajustar coordenadas según el cuadrante
            double xAjustada = xRef;
            double yAjustada = yRef;

            // Primer cuadrante (x > 0, y > 0) -> Segundo cuadrante (-x, y)
            if (xRef > 0 && yRef > 0) {
                xAjustada = -Math.abs(xRef);
                yAjustada = Math.abs(yRef);
            }
            // Segundo cuadrante (x < 0, y > 0) -> Tercer cuadrante (-x, -y)
            else if (xRef < 0 && yRef > 0) {
                xAjustada = -Math.abs(xRef);
                yAjustada = -Math.abs(yRef);
            }
            // Tercer cuadrante (x < 0, y < 0) -> Cuarto cuadrante (x, -y)
            else if (xRef < 0 && yRef < 0) {
                xAjustada = Math.abs(xRef);
                yAjustada = -Math.abs(yRef);
            }
            // Cuarto cuadrante (x > 0, y < 0) -> Primer cuadrante (x, y)
            else if (xRef > 0 && yRef < 0) {
                xAjustada = Math.abs(xRef);
                yAjustada = Math.abs(yRef);
            }

            // Crear puntos rotados con coordenadas double
            for (int i = 0; i < puntosList.size(); i++) {
                Punto puntoOriginal = puntosList.get(i);

                double x = puntoOriginal.getX() - xRef;
                double y = puntoOriginal.getY() - yRef;

                double newX = xAjustada + (x * Math.cos(angulo) - y * Math.sin(angulo));
                double newY = yAjustada + (x * Math.sin(angulo) + y * Math.cos(angulo));

                Punto puntoRotado = new Punto(newX, newY);
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
            anguloLabel.setText("Angulo de rotación: " + anguloField.getText() + " °");

            // Actualizar la etiqueta de la tabla escalada
            Component parent = rotatedTable.getParent().getParent().getParent();
            if (parent instanceof JPanel) {
                ((JLabel) ((JPanel) parent).getComponent(0)).setText("Puntos Rotados: " + "Ángulo de rotación: " + anguloField.getText() + "°");
            }


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
                    String.format("%.2f", punto.getX()),
                    String.format("%.2f", punto.getY())
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PolilineasRotacion frame = new PolilineasRotacion();

        });
    }
}