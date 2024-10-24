package DrawingClasses.GraficadoraBasica.Lineas;

import Plano.GraficadoraBasica.PlanoCartesianoLineas;
import formasADibujar.Linea;
import formasADibujar.Punto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LineasDrawMenu extends JFrame {
    private PlanoCartesianoLineas planoCartesiano;
    private JTable pointsTable;
    private DefaultTableModel tableModel;
    private JTextField x1Field, y1Field, x2Field, y2Field;
    private JComboBox<String> lineTypeComboBox;
    private JButton drawButton, clearButton, backButton;

    public LineasDrawMenu() {
        setTitle("Dibujador de Líneas");
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
        planoCartesiano = new PlanoCartesianoLineas();
        planoCartesiano.setPreferredSize(new Dimension(800, 600));

        // Crear campos de texto para coordenadas
        x1Field = new JTextField(5);
        y1Field = new JTextField(5);
        x2Field = new JTextField(5);
        y2Field = new JTextField(5);

        // Crear combo box para tipos de línea
        String[] lineTypes = {"Horizontal", "Vertical", "Diagonal"};
        lineTypeComboBox = new JComboBox<>(lineTypes);

        // Crear botones
        drawButton = new JButton("Dibujar Línea");
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
        JLabel titleLabel = new JLabel("Dibujador de Líneas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Panel central con el plano cartesiano
        add(planoCartesiano, BorderLayout.CENTER);

        // Panel derecho con controles y tabla
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Panel de controles
        JPanel controlPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        controlPanel.add(new JLabel("Tipo de Línea:"));
        controlPanel.add(lineTypeComboBox);
        controlPanel.add(new JLabel("X1:"));
        controlPanel.add(x1Field);
        controlPanel.add(new JLabel("Y1:"));
        controlPanel.add(y1Field);
        controlPanel.add(new JLabel("X2:"));
        controlPanel.add(x2Field);
        controlPanel.add(new JLabel("Y2:"));
        controlPanel.add(y2Field);
        controlPanel.add(new JLabel(""));
        controlPanel.add(drawButton);
        controlPanel.add(new JLabel(""));
        controlPanel.add(clearButton);

        // Panel de tabla
        JPanel tablePanel = new JPanel(new BorderLayout());
        JLabel tableTitle = new JLabel("Puntos por donde pasa la línea", SwingConstants.CENTER);
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
                    int x1 = Integer.parseInt(x1Field.getText());
                    int y1 = Integer.parseInt(y1Field.getText());
                    int x2 = Integer.parseInt(x2Field.getText());
                    int y2 = Integer.parseInt(y2Field.getText());
                    String lineType = (String) lineTypeComboBox.getSelectedItem();

                    // Validar tipo de línea según las coordenadas
                    if (!validateLineType(x1, y1, x2, y2, lineType)) {
                        JOptionPane.showMessageDialog(LineasDrawMenu.this,
                                "Las coordenadas no corresponden al tipo de línea seleccionado",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Crear y dibujar la línea
                    Punto inicio = new Punto(x1, y1);
                    Punto fin = new Punto(x2, y2);
                    inicio.setNombrePunto("P1");
                    fin.setNombrePunto("P2");

                    Linea linea = new Linea(inicio, fin, false);
                    planoCartesiano.addPunto(inicio);
                    planoCartesiano.addPunto(fin);
                    planoCartesiano.addLinea(linea);

                    // Limpiar la tabla antes de agregar nuevos puntos
                    tableModel.setRowCount(0);

                    // Agregar los puntos intermedios a la tabla
                    addPointsToTable(x1, y1, x2, y2, lineType);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(LineasDrawMenu.this,
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

        // Listener para el combo box que actualiza los campos según el tipo de línea
        lineTypeComboBox.addActionListener(e -> {
            String selectedType = (String) lineTypeComboBox.getSelectedItem();
            switch (selectedType) {
                case "Horizontal":
                    y2Field.setText(y1Field.getText());
                    break;
                case "Vertical":
                    x2Field.setText(x1Field.getText());
                    break;
            }
        });
    }

    private boolean validateLineType(int x1, int y1, int x2, int y2, String lineType) {
        switch (lineType) {
            case "Horizontal":
                return y1 == y2;
            case "Vertical":
                return x1 == x2;
            case "Diagonal":
                return Math.abs(x2 - x1) == Math.abs(y2 - y1);
            default:
                return false;
        }
    }

    private void addPointsToTable(int x1, int y1, int x2, int y2, String lineType) {
        if (lineType.equals("Horizontal")) {
            for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                tableModel.addRow(new Object[]{x, y1});
            }
        } else if (lineType.equals("Vertical")) {
            for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                tableModel.addRow(new Object[]{x1, y});
            }
        } else if (lineType.equals("Diagonal")) {
            int xStep = (x2 > x1) ? 1 : -1;
            int yStep = (y2 > y1) ? 1 : -1;
            int x = x1;
            int y = y1;

            while (x != x2 + xStep && y != y2 + yStep) {
                tableModel.addRow(new Object[]{x, y});
                x += xStep;
                y += yStep;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LineasDrawMenu();
            }
        });
    }
}
