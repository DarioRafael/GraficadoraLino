package DrawingClasses.Transformaciones.Compuestas;


import PaginaPrincipalFolder.Transformaciones.Componentes.TransformacionesCompuestas;
import Plano.Transformaciones.Basicas.PlanoCartesianoTraslacion;
import formasADibujar.Linea;
import formasADibujar.Punto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolilineasTraslacionSuc extends JFrame {
    private PlanoCartesianoTraslacion planoCartesiano;
    private JTable originalTable;
    private JTable translatedTable1;
    private JTable translatedTable2;
    private DefaultTableModel originalTableModel;
    private DefaultTableModel translatedTableModel1;
    private DefaultTableModel translatedTableModel2;
    private JButton backButton;
    private JTextField xInicialField;
    private JTextField yInicialField;
    private JTextField tx1Field;
    private JTextField ty1Field;
    private JTextField tx2Field;
    private JTextField ty2Field;
    private JLabel tx1Label;
    private JLabel ty1Label;
    private JLabel tx2Label;
    private JLabel ty2Label;
    public JComboBox<String> aumentoComboBox;
    private JButton regenerarFigura;
    private JButton trasladar1Button;
    private JButton trasladar2Button;
    private List<Punto> puntosList;
    private List<Punto> puntosTrasladadosList1;
    private List<Punto> puntosTrasladadosList2;
    private int figuraCounter = 1;
    public int tx1 = 0;
    public int ty1 = 0;
    public int tx2 = 0;
    public int ty2 = 0;
    private JLabel translatedTable1Label;
    private JLabel translatedTable2Label;

    public PolilineasTraslacionSuc() {
        setTitle("Transformaciones Geométricas 2D Compuestas: Traslacion Sucesiva");
        setSize(1850, 960);
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
        tx1Field = new JTextField("0", 5);
        ty1Field = new JTextField("0", 5);
        tx2Field = new JTextField("0", 5);
        ty2Field = new JTextField("0", 5);

        backButton = new JButton("Menu");
        regenerarFigura = new JButton("Generar figura");
        trasladar1Button = new JButton("Primera Traslación");
        trasladar2Button = new JButton("Segunda Traslación");
        trasladar2Button.setEnabled(false);

        String[] aumentoOptions = {"x1", "x2", "x4", "x8", "x16"};
        aumentoComboBox = new JComboBox<>(aumentoOptions);
        aumentoComboBox.setSelectedIndex(0);

        String[] columnNames = {"Punto", "X", "Y"};
        originalTableModel = new DefaultTableModel(columnNames, 0);
        translatedTableModel1 = new DefaultTableModel(new String[]{"P'", "X'", "Y'"}, 0);
        translatedTableModel2 = new DefaultTableModel(new String[]{"P''", "X''", "Y''"}, 0);

        originalTable = new JTable(originalTableModel);
        translatedTable1 = new JTable(translatedTableModel1);
        translatedTable2 = new JTable(translatedTableModel2);

        tx1Label = new JLabel("Tx1: 0", SwingConstants.CENTER);
        ty1Label = new JLabel("Ty1: 0", SwingConstants.CENTER);
        tx2Label = new JLabel("Tx2: 0", SwingConstants.CENTER);
        ty2Label = new JLabel("Ty2: 0", SwingConstants.CENTER);

        tx1Label.setFont(new Font("Arial", Font.BOLD, 18));
        ty1Label.setFont(new Font("Arial", Font.BOLD, 18));
        tx2Label.setFont(new Font("Arial", Font.BOLD, 18));
        ty2Label.setFont(new Font("Arial", Font.BOLD, 18));
    }

    private void configureLayout() {
        setLayout(new BorderLayout());

        // Top Panel con título y botón de regreso
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Transformaciones Geométricas 2D Compuestas: Traslacion Sucesiva", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Panel central con el plano cartesiano
        add(planoCartesiano, BorderLayout.CENTER);

        // Panel derecho con tablas y controles
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Panel para las tres tablas
        JPanel tablesPanel = new JPanel(new GridLayout(3, 1, 5, 5));

        // Configuración de las tres tablas
        JPanel originalTablePanel = new JPanel(new BorderLayout());
        originalTablePanel.add(new JLabel("Puntos Originales", SwingConstants.CENTER), BorderLayout.NORTH);
        JScrollPane originalScrollPane = new JScrollPane(originalTable);
        originalScrollPane.setPreferredSize(new Dimension(300, 150));
        originalTablePanel.add(originalScrollPane, BorderLayout.CENTER);

        JPanel translatedTable1Panel = new JPanel(new BorderLayout());
        translatedTable1Label = new JLabel("Primera Traslación (Tx1: 0, Ty1: 0)", SwingConstants.CENTER);
        translatedTable1Panel.add(translatedTable1Label, BorderLayout.NORTH);

        JScrollPane translatedScrollPane1 = new JScrollPane(translatedTable1);
        translatedScrollPane1.setPreferredSize(new Dimension(300, 150));
        translatedTable1Panel.add(translatedScrollPane1, BorderLayout.CENTER);

        JPanel translatedTable2Panel = new JPanel(new BorderLayout());
        translatedTable2Label = new JLabel("Segunda Traslación (Tx2: 0, Ty2: 0)", SwingConstants.CENTER);
        translatedTable2Panel.add(translatedTable2Label, BorderLayout.NORTH);
        JScrollPane translatedScrollPane2 = new JScrollPane(translatedTable2);
        translatedScrollPane2.setPreferredSize(new Dimension(300, 150));
        translatedTable2Panel.add(translatedScrollPane2, BorderLayout.CENTER);

        tablesPanel.add(originalTablePanel);
        tablesPanel.add(translatedTable1Panel);
        tablesPanel.add(translatedTable2Panel);

        rightPanel.add(tablesPanel, BorderLayout.CENTER);

        // Panel de control con campos de entrada y botones
        JPanel controlPanel = new JPanel(new GridLayout(14, 2, 5, 5));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Configuración inicial
        controlPanel.add(new JLabel("X inicial:"));
        controlPanel.add(xInicialField);
        controlPanel.add(new JLabel("Y inicial:"));
        controlPanel.add(yInicialField);
        controlPanel.add(new JLabel("Aumento:"));
        controlPanel.add(aumentoComboBox);
        controlPanel.add(new JLabel(""));
        controlPanel.add(regenerarFigura);

        // Separador
        controlPanel.add(new JSeparator());
        controlPanel.add(new JSeparator());

        // Primera traslación
        controlPanel.add(new JLabel("Tx1:"));
        controlPanel.add(tx1Field);
        controlPanel.add(new JLabel("Ty1:"));
        controlPanel.add(ty1Field);
        controlPanel.add(new JLabel(""));
        controlPanel.add(trasladar1Button);
        controlPanel.add(tx1Label);
        controlPanel.add(ty1Label);

        // Segunda traslación
        controlPanel.add(new JLabel("Tx2:"));
        controlPanel.add(tx2Field);
        controlPanel.add(new JLabel("Ty2:"));
        controlPanel.add(ty2Field);
        controlPanel.add(new JLabel(""));
        controlPanel.add(trasladar2Button);
        controlPanel.add(tx2Label);
        controlPanel.add(ty2Label);

        rightPanel.add(controlPanel, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.EAST);
    }
    private void addActionListeners() {
        backButton.addActionListener(e -> {
            clearPlanoAndData();
            new TransformacionesCompuestas().setVisible(true);
            dispose();
        });

        regenerarFigura.addActionListener(e -> {
            int xInicio = Integer.parseInt(xInicialField.getText());
            int yInicio = Integer.parseInt(yInicialField.getText());
            int aumento = Integer.parseInt(aumentoComboBox.getSelectedItem().toString().substring(1));
            drawFiguraOriginal(xInicio, yInicio, aumento);
            trasladar2Button.setEnabled(false);
        });

        trasladar1Button.addActionListener(e -> {
            realizarPrimeraTraslacion();
            trasladar2Button.setEnabled(true);
        });

        trasladar2Button.addActionListener(e -> realizarSegundaTraslacion());
    }

    private void realizarPrimeraTraslacion() {
        try {
            tx1 = Integer.parseInt(tx1Field.getText());
            ty1 = Integer.parseInt(ty1Field.getText());

            if (puntosList == null || puntosList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Primero debe generar la figura original");
                return;
            }

            // Limpiar el plano pero mantener los datos originales
            planoCartesiano.clear();

            // Redibujar la figura original
            dibujarFigura(puntosList, Color.BLACK);

            // Crear y dibujar los puntos de la primera traslación
            puntosTrasladadosList1 = new ArrayList<>();
            for (int i = 0; i < puntosList.size(); i++) {
                Punto puntoOriginal = puntosList.get(i);
                Punto puntoTransladado = new Punto(
                        puntoOriginal.getX() + tx1,
                        puntoOriginal.getY() + ty1
                );
                puntoTransladado.setNombrePunto("P" + (i + 1) + "'");
                puntosTrasladadosList1.add(puntoTransladado);
            }

            clearTableAll(2);
            // Dibujar la figura trasladada
            dibujarFigura(puntosTrasladadosList1, Color.BLUE);

            // Actualizar tablas y etiquetas
            updateTranslatedTable1(puntosTrasladadosList1);
            tx1Label.setText("Tx1: " + tx1);
            ty1Label.setText("Ty1: " + ty1);

            planoCartesiano.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos para Tx1 y Ty1");
        }
    }

    private void realizarSegundaTraslacion() {
        try {
            tx2 = Integer.parseInt(tx2Field.getText());
            ty2 = Integer.parseInt(ty2Field.getText());

            if (puntosTrasladadosList1 == null || puntosTrasladadosList1.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Primero debe realizar la primera traslación");
                return;
            }

            // Limpiar el plano
            planoCartesiano.clear();

            // Redibujar la figura original y la primera traslación
            dibujarFigura(puntosList, Color.BLACK);
            dibujarFigura(puntosTrasladadosList1, Color.BLUE);

            // Crear y dibujar los puntos de la segunda traslación
            puntosTrasladadosList2 = new ArrayList<>();
            for (int i = 0; i < puntosTrasladadosList1.size(); i++) {
                Punto puntoTrasladadoPrevio = puntosTrasladadosList1.get(i);
                Punto puntoTransladado = new Punto(
                        puntoTrasladadoPrevio.getX() + tx2,
                        puntoTrasladadoPrevio.getY() + ty2
                );
                puntoTransladado.setNombrePunto("P" + (i + 1) + "''");
                puntosTrasladadosList2.add(puntoTransladado);
            }

            // Dibujar la segunda figura trasladada
            dibujarFigura(puntosTrasladadosList2, Color.RED);

            // Actualizar tablas y etiquetas
            updateTranslatedTable2(puntosTrasladadosList2);
            tx2Label.setText("Tx2: " + tx2);
            ty2Label.setText("Ty2: " + ty2);

            planoCartesiano.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos para Tx2 y Ty2");
        }
    }

    private void dibujarFigura(List<Punto> puntos, Color color) {
        Punto puntoAnterior = puntos.get(0);
        planoCartesiano.addPunto(puntoAnterior);

        for (int i = 1; i < puntos.size(); i++) {
            Punto punto = puntos.get(i);
            planoCartesiano.addPunto(punto);
            Linea linea = new Linea(puntoAnterior, punto, true, i);
            planoCartesiano.addLinea(linea);
            puntoAnterior = punto;
        }
    }
    private void updateTranslatedTable1(List<Punto> puntos) {
        translatedTableModel1.setRowCount(0);
        for (Punto punto : puntos) {
            translatedTableModel1.addRow(new Object[]{
                    punto.getNombrePunto(),
                    punto.getX(),
                    punto.getY()
            });
        }
        translatedTable1Label.setText(String.format("Primera Traslación (Tx1: %d, Ty1: %d)", tx1, ty1));
    }

    private void updateTranslatedTable2(List<Punto> puntos) {
        translatedTableModel2.setRowCount(0);
        for (Punto punto : puntos) {
            translatedTableModel2.addRow(new Object[]{
                    punto.getNombrePunto(),
                    punto.getX(),
                    punto.getY()
            });
        }
        translatedTable2Label.setText(String.format("Segunda Traslación (Tx2: %d, Ty2: %d)", tx2, ty2));
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

            clearTableAll(1);
            // Dibuja la figura original en negro
            dibujarFigura(puntosList, Color.BLACK);

            // Actualiza la tabla de puntos originales
            updateOriginalTable(puntosList);

            // Limpiar las tablas de traslación
            translatedTableModel1.setRowCount(0);
            translatedTableModel2.setRowCount(0);

            // Resetear las etiquetas
            tx1Label.setText("Tx1: 0");
            ty1Label.setText("Ty1: 0");
            tx2Label.setText("Tx2: 0");
            ty2Label.setText("Ty2: 0");

            planoCartesiano.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos.");
        }
    }

    private void clearPlanoAndData() {
        planoCartesiano.clear();
        originalTableModel.setRowCount(0);
        translatedTableModel1.setRowCount(0);
        translatedTableModel2.setRowCount(0);
        puntosTrasladadosList1 = null;
        puntosTrasladadosList2 = null;
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
            translatedTable1Label.setText("Primera Traslación (Tx1: 0, Ty1: 0)");
            translatedTable2Label.setText("Segunda Traslación (Tx2: 0, Ty2: 0)");
        } else if (index == 2){
            translatedTable2Label.setText("Segunda Traslación (Tx2: 0, Ty2: 0)");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PolilineasTraslacionSuc();
        });
    }
}
