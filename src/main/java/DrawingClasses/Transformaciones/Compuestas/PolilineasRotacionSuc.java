package DrawingClasses.Transformaciones.Compuestas;

import PaginaPrincipalFolder.Transformaciones.Componentes.TransformacionesCompuestas;
import Plano.Transformaciones.Basicas.PlanoCartesianoRotacion;
import formasADibujar.Rotacion.Linea;
import formasADibujar.Rotacion.Punto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolilineasRotacionSuc extends JFrame {
    private PlanoCartesianoRotacion planoCartesiano;
    private JTable originalTable;
    private JTable firstRotationTable;
    private JTable secondRotationTable;
    private DefaultTableModel originalTableModel;
    private DefaultTableModel firstRotationTableModel;
    private DefaultTableModel secondRotationTableModel;
    private JButton backButton;
    private JTextField xInicialField;
    private JTextField yInicialField;
    private JTextField primerAnguloField;
    private JTextField segundoAnguloField;
    private JLabel primerAnguloLabel;
    private JLabel segundoAnguloLabel;
    private JButton regenerarFigura;
    private JButton rotarButton;
    private List<Punto> puntosList;
    private List<Punto> primeraRotacionList;
    private List<Punto> segundaRotacionList;
    public JComboBox<String> aumentoComboBox;
    private JButton primerRotacionButton;
    private JButton segundaRotacionButton;
    private boolean primeraRotacionCompletada = false;
    private JLabel rotationTable1Label;
    private JLabel rotationTable2Label;
    double primerAngulo;
    double segundoAngulo;

    public PolilineasRotacionSuc() {
        setTitle("Transformaciones Geométricas 2D Compuestas: Rotaciones Sucesivas");
        setSize(1850, 960);
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
        primerAnguloField = new JTextField("0", 5);
        segundoAnguloField = new JTextField("0", 5);

        backButton = new JButton("Menu");
        regenerarFigura = new JButton("Generar figura");
        primerRotacionButton = new JButton("Aplicar primera rotación");
        segundaRotacionButton = new JButton("Aplicar segunda rotación");
        segundaRotacionButton.setEnabled(false);

        String[] aumentoOptions = {"x1", "x2", "x4", "x8", "x16"};
        aumentoComboBox = new JComboBox<>(aumentoOptions);
        aumentoComboBox.setSelectedIndex(0);

        String[] columnNames = {"Punto", "X", "Y"};
        String[] columnNamesFirst = {"P'", "X'", "Y'"};
        String[] columnNamesSecond = {"P''", "X''", "Y''"};

        originalTableModel = new DefaultTableModel(columnNames, 0);
        firstRotationTableModel = new DefaultTableModel(columnNamesFirst, 0);
        secondRotationTableModel = new DefaultTableModel(columnNamesSecond, 0);

        originalTable = new JTable(originalTableModel);
        firstRotationTable = new JTable(firstRotationTableModel);
        secondRotationTable = new JTable(secondRotationTableModel);

        primerAnguloLabel = new JLabel("Primera rotación: 0°", SwingConstants.CENTER);
        segundoAnguloLabel = new JLabel("Segunda rotación: 0°", SwingConstants.CENTER);
        primerAnguloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        segundoAnguloLabel.setFont(new Font("Arial", Font.BOLD, 18));
    }

    private void configureLayout() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Transformaciones Geométricas 2D Compuestas: Rotaciones Sucesivas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        add(planoCartesiano, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        JPanel tablesPanel = new JPanel(new GridLayout(3, 1, 5, 5));

        // Panel para tabla original
        JPanel originalTablePanel = new JPanel(new BorderLayout());
        JLabel originalLabel = new JLabel("Puntos Originales", SwingConstants.CENTER);
        originalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        originalTablePanel.add(originalLabel, BorderLayout.NORTH);
        JScrollPane originalScrollPane = new JScrollPane(originalTable);
        originalScrollPane.setPreferredSize(new Dimension(300, 150));
        originalTablePanel.add(originalScrollPane, BorderLayout.CENTER);

        // Panel para primera rotación
        JPanel firstRotationPanel = new JPanel(new BorderLayout());
        rotationTable1Label = new JLabel("Primera Rotación (θ1 = 0°)", SwingConstants.CENTER);
        rotationTable1Label.setFont(new Font("Arial", Font.BOLD, 18));
        firstRotationPanel.add(rotationTable1Label, BorderLayout.NORTH);
        JScrollPane firstRotationScrollPane = new JScrollPane(firstRotationTable);
        firstRotationScrollPane.setPreferredSize(new Dimension(300, 150));
        firstRotationPanel.add(firstRotationScrollPane, BorderLayout.CENTER);

        // Panel para segunda rotación
        JPanel secondRotationPanel = new JPanel(new BorderLayout());
        rotationTable2Label = new JLabel("Segunda Rotación (θ2 = 0°)", SwingConstants.CENTER);
        rotationTable2Label.setFont(new Font("Arial", Font.BOLD, 18));
        secondRotationPanel.add(rotationTable2Label, BorderLayout.NORTH);
        JScrollPane secondRotationScrollPane = new JScrollPane(secondRotationTable);
        secondRotationScrollPane.setPreferredSize(new Dimension(300, 150));
        secondRotationPanel.add(secondRotationScrollPane, BorderLayout.CENTER);

        tablesPanel.add(originalTablePanel);
        tablesPanel.add(firstRotationPanel);
        tablesPanel.add(secondRotationPanel);

        rightPanel.add(tablesPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(13, 2, 5, 5));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        controlPanel.add(new JLabel("X inicial:"));
        controlPanel.add(xInicialField);
        controlPanel.add(new JLabel("Y inicial:"));
        controlPanel.add(yInicialField);
        controlPanel.add(new JLabel("Aumento:"));
        controlPanel.add(aumentoComboBox);
        controlPanel.add(new JLabel(""));
        controlPanel.add(regenerarFigura);
        controlPanel.add(new JSeparator());
        controlPanel.add(new JSeparator());
        controlPanel.add(new JLabel("Primer ángulo:"));
        controlPanel.add(primerAnguloField);
        controlPanel.add(new JLabel(""));
        controlPanel.add(primerRotacionButton);
        controlPanel.add(new JLabel("Segundo ángulo:"));
        controlPanel.add(segundoAnguloField);
        controlPanel.add(new JLabel(""));
        controlPanel.add(segundaRotacionButton);
        controlPanel.add(primerAnguloLabel);
        controlPanel.add(segundoAnguloLabel);

        rightPanel.add(controlPanel, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.EAST);
    }

    private void addActionListeners() {
        backButton.addActionListener(e -> {
            new TransformacionesCompuestas().setVisible(true);
            clearPlanoAndData();
            dispose();
        });

        primerRotacionButton.addActionListener(e -> realizarPrimeraRotacion());
        segundaRotacionButton.addActionListener(e -> realizarSegundaRotacion());

        regenerarFigura.addActionListener(e -> {
            primeraRotacionCompletada = false;
            segundaRotacionButton.setEnabled(false);
            clearPlanoAndData();
            int xInicio = Integer.parseInt(xInicialField.getText());
            int yInicio = Integer.parseInt(yInicialField.getText());
            int aumento = Integer.parseInt(aumentoComboBox.getSelectedItem().toString().substring(1));
            drawFiguraOriginal(xInicio, yInicio, aumento);
        });


    }


    private void realizarPrimeraRotacion() {
        try {
            if (puntosList == null || puntosList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Primero debe generar la figura original");
                return;
            }

            primerAngulo = Math.toRadians(Double.parseDouble(primerAnguloField.getText()));

            // Primera rotación - mover al segundo cuadrante
            primeraRotacionList = realizarRotacion(puntosList, primerAngulo, "'", 2);

            planoCartesiano.clear();
            clearTableAll(2);
            dibujarFigura(puntosList, false, 1);
            dibujarFigura(primeraRotacionList, true, 2);
            updateRotatedTable(primeraRotacionList, firstRotationTableModel);

            primerAnguloLabel.setText("Primera rotación: " + primerAnguloField.getText() + "°");
            primeraRotacionCompletada = true;
            segundaRotacionButton.setEnabled(true);

            planoCartesiano.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un valor numérico válido para el primer ángulo.");
        }
    }

    private void realizarSegundaRotacion() {
        try {
            if (!primeraRotacionCompletada) {
                JOptionPane.showMessageDialog(this, "Primero debe realizar la primera rotación");
                return;
            }

            segundoAngulo = Math.toRadians(Double.parseDouble(segundoAnguloField.getText()));

            // Segunda rotación - mover al tercer cuadrante
            segundaRotacionList = realizarRotacion(primeraRotacionList, segundoAngulo, "''", 3);

            planoCartesiano.clear();
            dibujarFigura(puntosList, false, 1);
            dibujarFigura(primeraRotacionList, true, 2);
            dibujarFigura(segundaRotacionList, true, 3);
            updateRotatedTable2(segundaRotacionList, secondRotationTableModel);

            segundoAnguloLabel.setText("Segunda rotación: " + segundoAnguloField.getText() + "°");

            planoCartesiano.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un valor numérico válido para el segundo ángulo.");
        }
    }

    private List<Punto> realizarRotacion(List<Punto> puntosOriginales, double angulo, String sufijo, int cuadrante) {
        List<Punto> puntosRotados = new ArrayList<>();
        Punto puntoReferencia = puntosOriginales.get(0);
        double xRef = puntoReferencia.getX();
        double yRef = puntoReferencia.getY();

        // Ajustar coordenadas según el cuadrante
        double xAjustada = xRef;
        double yAjustada = yRef;

        switch (cuadrante) {
            case 2: // Segundo cuadrante (-x, y)
                xAjustada = -Math.abs(xRef);
                yAjustada = Math.abs(yRef);
                break;
            case 3: // Tercer cuadrante (-x, -y)
                xAjustada = -Math.abs(xRef);
                yAjustada = -Math.abs(yRef);
                break;
        }

        // Realizar rotación para cada punto
        for (int i = 0; i < puntosOriginales.size(); i++) {
            Punto puntoOriginal = puntosOriginales.get(i);
            double x = puntoOriginal.getX() - xRef;
            double y = puntoOriginal.getY() - yRef;

            double newX = xAjustada + (x * Math.cos(angulo) - y * Math.sin(angulo));
            double newY = yAjustada + (x * Math.sin(angulo) + y * Math.cos(angulo));

            Punto puntoRotado = new Punto(newX, newY);
            puntoRotado.setNombrePunto("P" + (i + 1) + sufijo);
            puntosRotados.add(puntoRotado);
        }

        return puntosRotados;
    }

    private void dibujarFigura(List<Punto> puntos, boolean esRotada, int colorIndex) {
        Punto puntoAnterior = puntos.get(0);
        planoCartesiano.addPunto(puntoAnterior);

        for (int i = 1; i < puntos.size(); i++) {
            Punto punto = puntos.get(i);
            planoCartesiano.addPunto(punto);
            planoCartesiano.addLinea(new Linea(puntoAnterior, punto, true, colorIndex));
            puntoAnterior = punto;
        }
    }

    // Los métodos drawFiguraOriginal, clearPlanoAndData, updateOriginalTable y updateRotatedTable
    // permanecen igual que en el código original

    private void updateRotatedTable(List<Punto> puntos, DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        for (Punto punto : puntos) {
            tableModel.addRow(new Object[]{
                    punto.getNombrePunto(),
                    String.format("%.2f", punto.getX()),
                    String.format("%.2f", punto.getY())
            });
        }
        rotationTable1Label.setText(String.format("Primera rotación: %s°", primerAnguloField.getText()));    }

    private void updateRotatedTable2(List<Punto> puntos, DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        for (Punto punto : puntos) {
            tableModel.addRow(new Object[]{
                    punto.getNombrePunto(),
                    String.format("%.2f", punto.getX()),
                    String.format("%.2f", punto.getY())
            });
        }
        rotationTable2Label.setText(String.format("Primera rotación: %s°", segundoAnguloField.getText()));
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

            clearTableAll(1);
            dibujarFigura(puntosList, false, 1);
            updateOriginalTable(puntosList);
            planoCartesiano.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos.");
        }
    }

    private void clearPlanoAndData() {
        planoCartesiano.clear();
        originalTableModel.setRowCount(0);
        firstRotationTableModel.setRowCount(0);
        secondRotationTableModel.setRowCount(0);
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

    private void clearTableAll(int index) {
        if(index == 1){
            rotationTable1Label.setText("Primera rotación: 0°");
            rotationTable2Label.setText("Segunda rotación: 0°");


        } else if (index == 2){
            rotationTable2Label.setText("Segunda rotación: 0°");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PolilineasRotacionSuc());
    }
}