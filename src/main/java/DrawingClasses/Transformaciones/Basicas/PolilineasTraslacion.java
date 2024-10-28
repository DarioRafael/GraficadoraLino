package DrawingClasses.Transformaciones.Basicas;

import PaginaPrincipalFolder.Transformaciones.Componentes.TransformacionesBasicas;
import Plano.Transformaciones.Basicas.PlanoCartesianoTraslacion;
import formasADibujar.Linea;
import formasADibujar.Punto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolilineasTraslacion extends JFrame {
    private PlanoCartesianoTraslacion planoCartesiano;
    private JTable originalTable;
    private JTable translatedTable;
    private DefaultTableModel originalTableModel;
    private DefaultTableModel translatedTableModel;
    private JButton backButton;
    private JTextField xInicialField;
    private JTextField yInicialField;
    private JTextField txField;
    private JTextField tyField;
    private JLabel txLabel;
    private JLabel tyLabel;
    public JComboBox<String> aumentoComboBox;
    private JButton regenerarFigura;
    private JButton trasladarButton;
    private List<Punto> puntosList;
    private List<Punto> puntosTrasladadosList;
    private int figuraCounter = 1;
    public int tx = 0;
    public int ty = 0;

    public PolilineasTraslacion() {
        setTitle("Transformaciones Geométricas 2D Básica: Traslación");
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
        txField = new JTextField("0", 5);
        tyField = new JTextField("0", 5);

        backButton = new JButton("Menu");
        regenerarFigura = new JButton("Generar figura");
        trasladarButton = new JButton("Trasladar figura");

        // ComboBox para seleccionar el aumento
        String[] aumentoOptions = {"x1", "x2", "x4", "x8", "x16"};
        aumentoComboBox = new JComboBox<>(aumentoOptions);
        aumentoComboBox.setSelectedIndex(0); // Valor por defecto: x1

        String[] columnNames = {"Punto", "X", "Y"};
        String[] columnNamesEdi = {"P'", "X'", "Y'"};
        originalTableModel = new DefaultTableModel(columnNames, 0);
        translatedTableModel = new DefaultTableModel(columnNamesEdi, 0);
        originalTable = new JTable(originalTableModel);
        translatedTable = new JTable(translatedTableModel);

        // Labels para mostrar valores de Sx y Sy después de la escalación
        txLabel = new JLabel("Tx: 0", SwingConstants.CENTER);
        txLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Cambia "Arial" y 18 por la fuente y tamaño deseados

        tyLabel = new JLabel("Ty: 0", SwingConstants.CENTER);
        tyLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Cambia "Arial" y 18 por la fuente y tamaño deseados

    }

    private void configureLayout() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Transformaciones Geométricas 2D Básica: Traslacion", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        add(planoCartesiano, BorderLayout.CENTER);

        // Panel derecho con las dos tablas
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Panel para las tablas
        JPanel tablesPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        // Primera tabla (Original)
        JPanel originalTablePanel = new JPanel(new BorderLayout());

        JLabel originalLabel = new JLabel("Puntos Originales", SwingConstants.CENTER);
        originalLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set font to Arial, bold, size 18
        originalTablePanel.add(originalLabel, BorderLayout.NORTH);
        JScrollPane originalScrollPane = new JScrollPane(originalTable);


        originalScrollPane.setPreferredSize(new Dimension(300, 200));
        originalTablePanel.add(originalScrollPane, BorderLayout.CENTER);

        // Segunda tabla (Trasladada)
        JPanel translatedTablePanel = new JPanel(new BorderLayout());

        JLabel scaledLabel = new JLabel("Puntos Trasladados: " + "Tx: " + tx + " Ty: " + ty, SwingConstants.CENTER);
        scaledLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set font to Arial, bold, size 18
        translatedTablePanel.add(scaledLabel, BorderLayout.NORTH);



        JScrollPane translatedScrollPane = new JScrollPane(translatedTable);
        translatedScrollPane.setPreferredSize(new Dimension(300, 200));
        translatedTablePanel.add(translatedScrollPane, BorderLayout.CENTER);

        tablesPanel.add(originalTablePanel);
        tablesPanel.add(translatedTablePanel);

        rightPanel.add(tablesPanel, BorderLayout.CENTER);

        // Panel de controles
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
        controlPanel.add(new JLabel("Tx:"));
        controlPanel.add(txField);
        controlPanel.add(new JLabel("Ty:"));
        controlPanel.add(tyField);
        controlPanel.add(new JLabel(""));
        controlPanel.add(trasladarButton);
        controlPanel.add(txLabel);  // Mostrar valor de Sx
        controlPanel.add(tyLabel);  // Mostrar valor de Sy

        rightPanel.add(controlPanel, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.EAST);
    }

    private void addActionListeners() {
        backButton.addActionListener(e -> {
            clearPlanoAndData();
            new TransformacionesBasicas().setVisible(true);
            dispose();
        });

        regenerarFigura.addActionListener(e -> {
            int xInicio = Integer.parseInt(xInicialField.getText());
            int yInicio = Integer.parseInt(yInicialField.getText());
            int aumento = Integer.parseInt(aumentoComboBox.getSelectedItem().toString().substring(1)); // Obtener el valor de aumento (x1, x2, etc.)
            drawFiguraOriginal(xInicio, yInicio, aumento);
        });

        trasladarButton.addActionListener(e -> realizarTraslacion());
    }

    public void drawFiguraOriginal(int xInicio, int yInicio, int aumento) {
        clearPlanoAndData();

        try {
            Punto puntoInicio = new Punto(xInicio, yInicio);

            // Define los puntos de la figura
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

            // Asignar nombres a los puntos
            for (int i = 0; i < puntosList.size(); i++) {
                puntosList.get(i).setNombrePunto("P" + (i + 1));
            }

            // Dibuja la figura
            Punto puntoAnterior = puntoInicio;
            planoCartesiano.addPunto(puntoInicio);

            for (int i = 0; i < puntosList.size(); i++) {
                Punto punto = puntosList.get(i);
                planoCartesiano.addPunto(punto);
                planoCartesiano.addLinea(new Linea(puntoAnterior, punto, true, i + 1));
                puntoAnterior = punto;
            }

            // Actualiza la tabla de puntos originales
            updateOriginalTable(puntosList);

            planoCartesiano.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos.");
        }
    }

    private void realizarTraslacion() {
        try {
            int tx = Integer.parseInt(txField.getText());
            int ty = Integer.parseInt(tyField.getText());

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

            // Crear y dibujar los puntos trasladados
            puntosTrasladadosList = new ArrayList<>();

            for (int i = 0; i < puntosList.size(); i++) {
                Punto puntoOriginal = puntosList.get(i);
                Punto puntoTransladado = new Punto(
                        puntoOriginal.getX() + tx,
                        puntoOriginal.getY() + ty
                );
                puntoTransladado.setNombrePunto("P" + (i + 1) + "'");
                puntosTrasladadosList.add(puntoTransladado);
            }

            // Dibuja la figura trasladada
            Punto puntoAnterior = puntosTrasladadosList.get(0);
            planoCartesiano.addPunto(puntoAnterior);

            for (int i = 1; i < puntosTrasladadosList.size(); i++) {
                Punto punto = puntosTrasladadosList.get(i);
                planoCartesiano.addPunto(punto);
                Linea linea = new Linea(puntoAnterior, punto, true, i);
                planoCartesiano.addLinea(linea);
                puntoAnterior = punto;
            }

            // Actualiza la tabla de puntos trasladados
            updateTranslatedTable(puntosTrasladadosList);
            planoCartesiano.repaint();

            // Actualizar las etiquetas para mostrar los valores de Sx y Sy
            txLabel.setText("Tx: " + tx);
            tyLabel.setText("Ty: " + ty);

            // Actualizar la etiqueta de la tabla escalada
            Component parent = translatedTable.getParent().getParent().getParent();
            if (parent instanceof JPanel) {
                ((JLabel) ((JPanel) parent).getComponent(0)).setText("Puntos Escalados: Tx: " + tx + " Ty: " + ty);
            }


        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos para Tx y Ty");
        }
    }


    private void clearPlanoAndData() {
        planoCartesiano.clear();
        originalTableModel.setRowCount(0);
        translatedTableModel.setRowCount(0);
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

    private void updateTranslatedTable(List<Punto> puntos) {
        translatedTableModel.setRowCount(0);
        for (Punto punto : puntos) {
            translatedTableModel.addRow(new Object[]{
                    punto.getNombrePunto(),
                    punto.getX(),
                    punto.getY()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PolilineasTraslacion frame = new PolilineasTraslacion();
        });
    }
}