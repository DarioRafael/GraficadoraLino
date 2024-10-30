package DrawingClasses.GraficadoraBasica.Conicas;

import Plano.GraficadoraBasica.PlanoCartesianoConicas;
import formasADibujar.Arco;
import formasADibujar.Circulo;
import formasADibujar.Elipse;
import formasADibujar.Punto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConicasDrawMenu extends JFrame {
    private PlanoCartesianoConicas planoCartesiano;
    private JTable pointsTable;
    private DefaultTableModel tableModel;
    private JTextField centerXField, centerYField, radiusField, radiusXField, radiusYField, angleStartField, angleEndField;
    private JComboBox<String> conicTypeComboBox;
    private JButton drawButton, clearButton, backButton;

    public ConicasDrawMenu() {
        setTitle("Dibujador de Cónicas");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createComponents();
        configureLayout();
        addActionListeners();
        setVisible(true);
    }

    private void createComponents() {
        // Inicializar el plano cartesiano
        planoCartesiano = new PlanoCartesianoConicas();
        planoCartesiano.setPreferredSize(new Dimension(800, 600));

        // Crear campos de texto para coordenadas y radios
        centerXField = new JTextField(5);
        centerYField = new JTextField(5);
        radiusField = new JTextField(5);
        radiusXField = new JTextField(5);
        radiusYField = new JTextField(5);
        angleStartField = new JTextField(5);
        angleEndField = new JTextField(5);

        // Crear combo box para tipos de cónicas
        String[] conicTypes = {"Círculo", "Elipse", "Arco"};
        conicTypeComboBox = new JComboBox<>(conicTypes);

        // Crear botones
        drawButton = new JButton("Dibujar Cónica");
        clearButton = new JButton("Limpiar");
        backButton = new JButton("Menú Principal");

        // Crear tabla para mostrar puntos
        String[] columnNames = {"Punto X", "Punto Y"};
        tableModel = new DefaultTableModel(columnNames, 0);
        pointsTable = new JTable(tableModel);
    }

    private void configureLayout() {
        setLayout(new BorderLayout());

        // Panel superior con título y botón de regreso
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Dibujador de Cónicas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Panel central con el plano cartesiano
        add(planoCartesiano, BorderLayout.CENTER);

        // Panel derecho con controles y tabla
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Panel de controles
        JPanel controlPanel = new JPanel(new GridLayout(10, 2, 5, 5));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        controlPanel.add(new JLabel("Tipo de Cónica:"));
        controlPanel.add(conicTypeComboBox);
        controlPanel.add(new JLabel("Centro X:"));
        controlPanel.add(centerXField);
        controlPanel.add(new JLabel("Centro Y:"));
        controlPanel.add(centerYField);
        controlPanel.add(new JLabel("Radio:"));
        controlPanel.add(radiusField);
        controlPanel.add(new JLabel("Radio X:"));
        controlPanel.add(radiusXField);
        controlPanel.add(new JLabel("Radio Y:"));
        controlPanel.add(radiusYField);
        controlPanel.add(new JLabel("Ángulo Inicio:"));
        controlPanel.add(angleStartField);
        controlPanel.add(new JLabel("Ángulo Fin:"));
        controlPanel.add(angleEndField);
        controlPanel.add(new JLabel(""));
        controlPanel.add(drawButton);
        controlPanel.add(new JLabel(""));
        controlPanel.add(clearButton);

        // Panel de tabla
        JPanel tablePanel = new JPanel(new BorderLayout());
        JLabel tableTitle = new JLabel("Puntos por donde pasa la cónica", SwingConstants.CENTER);
        tableTitle.setFont(new Font("Arial", Font.BOLD, 18));
        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(new JScrollPane(pointsTable), BorderLayout.CENTER);

        rightPanel.add(controlPanel, BorderLayout.NORTH);
        rightPanel.add(tablePanel, BorderLayout.CENTER);

        add(rightPanel, BorderLayout.EAST);
    }

    private void addActionListeners() {
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int centerX = Integer.parseInt(centerXField.getText());
                    int centerY = Integer.parseInt(centerYField.getText());
                    String conicType = (String) conicTypeComboBox.getSelectedItem();

                    switch (conicType) {
                        case "Círculo":
                            int radius = Integer.parseInt(radiusField.getText());
                            drawCircle(centerX, centerY, radius);
                            break;
                        case "Elipse":
                            int radiusX = Integer.parseInt(radiusXField.getText());
                            int radiusY = Integer.parseInt(radiusYField.getText());
                            drawEllipse(centerX, centerY, radiusX, radiusY);
                            break;
                        case "Arco":
                            int arcRadius = Integer.parseInt(radiusField.getText());
                            int angleStart = Integer.parseInt(angleStartField.getText());
                            int angleEnd = Integer.parseInt(angleEndField.getText());
                            drawArc(centerX, centerY, arcRadius, angleStart, angleEnd);
                            break;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ConicasDrawMenu.this,
                            "Por favor, ingrese valores numéricos válidos",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        clearButton.addActionListener(e -> {
            planoCartesiano.clear();
            tableModel.setRowCount(0);
        });

        backButton.addActionListener(e -> {
            // Aquí puedes agregar la navegación al menú principal
            dispose();
        });

        // Listener para el combo box que actualiza los campos según el tipo de cónica
        conicTypeComboBox.addActionListener(e -> {
            String selectedType = (String) conicTypeComboBox.getSelectedItem();
            switch (selectedType) {
                case "Círculo":
                    radiusField.setEnabled(true);
                    radiusXField.setEnabled(false);
                    radiusYField.setEnabled(false);
                    angleStartField.setEnabled(false);
                    angleEndField.setEnabled(false);
                    break;
                case "Elipse":
                    radiusField.setEnabled(false);
                    radiusXField.setEnabled(true);
                    radiusYField.setEnabled(true);
                    angleStartField.setEnabled(false);
                    angleEndField.setEnabled(false);
                    break;
                case "Arco":
                    radiusField.setEnabled(true);
                    radiusXField.setEnabled(false);
                    radiusYField.setEnabled(false);
                    angleStartField.setEnabled(true);
                    angleEndField.setEnabled(true);
                    break;
            }
        });
    }

    private void drawCircle(int centerX, int centerY, int radius) {
        Punto centro = new Punto(centerX, centerY);
        Circulo circulo = new Circulo(centro, radius);
        planoCartesiano.addCirculo(circulo);

        // Limpiar la tabla antes de agregar nuevos puntos
        tableModel.setRowCount(0);

        // Agregar los puntos del círculo a la tabla
        for (Punto punto : circulo.calcularPuntos()) {
            tableModel.addRow(new Object[]{punto.getX(), punto.getY()});
        }
    }

    private void drawEllipse(int centerX, int centerY, int radiusX, int radiusY) {
        Punto centro = new Punto(centerX, centerY);
        Elipse elipse = new Elipse(centro, radiusX, radiusY);
        planoCartesiano.addElipse(elipse);

        // Limpiar la tabla antes de agregar nuevos puntos
        tableModel.setRowCount(0);

        // Agregar los puntos de la elipse a la tabla
        for (Punto punto : elipse.calcularPuntos()) {
            tableModel.addRow(new Object[]{punto.getX(), punto.getY()});
        }
    }

    private void drawArc(int centerX, int centerY, int radius, int angleStart, int angleEnd) {
        Punto centro = new Punto(centerX, centerY);
        Arco arco = new Arco(centro, radius, angleStart, angleEnd);
        planoCartesiano.addArco(arco);

        // Limpiar la tabla antes de agregar nuevos puntos
        tableModel.setRowCount(0);

        // Agregar los puntos del arco a la tabla
        for (Punto punto : arco.calcularPuntos()) {
            tableModel.addRow(new Object[]{punto.getX(), punto.getY()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConicasDrawMenu();
            }
        });
    }
}