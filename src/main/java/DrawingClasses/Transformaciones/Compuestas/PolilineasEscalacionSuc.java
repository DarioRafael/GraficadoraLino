package DrawingClasses.Transformaciones.Compuestas;

import PaginaPrincipalFolder.Transformaciones.Componentes.TransformacionesCompuestas;
import Plano.Transformaciones.Compuestas.PlanoCartesianoEscalacionSuc;
import formasADibujar.Linea;
import formasADibujar.Punto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolilineasEscalacionSuc extends JFrame {
    private PlanoCartesianoEscalacionSuc planoCartesiano;
    private JTable originalTable;
    private JTable scaledTable1;
    private JTable scaledTable2;
    private DefaultTableModel originalTableModel;
    private DefaultTableModel scaledTableModel1;
    private DefaultTableModel scaledTableModel2;
    private JButton backButton;
    private JTextField xInicialField;
    private JTextField yInicialField;
    private JTextField sx1Field;
    private JTextField sy1Field;
    private JTextField sx2Field;
    private JTextField sy2Field;
    private JLabel sx1Label;
    private JLabel sy1Label;
    private JLabel sx2Label;
    private JLabel sy2Label;
    public JComboBox<String> aumentoComboBox;
    private JButton regenerarFigura;
    private JButton escalarButton1;
    private JButton escalarButton2;
    private List<Punto> puntosList;
    private List<Punto> puntosEscalados1List;
    private List<Punto> puntosEscalados2List;
    private JLabel scaletedTable1Label;
    private JLabel scaletedTable2Label;
    private int sx1, sx2, sy1, sy2;

    public PolilineasEscalacionSuc() {
        setTitle("Transformaciones Geométricas 2D Compuestas: Escalación Sucesiva");
        setSize(1800, 960);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createComponents();
        configureLayout();
        addActionListeners();
        setVisible(true);
    }

    private void createComponents() {
        planoCartesiano = new PlanoCartesianoEscalacionSuc();
        planoCartesiano.setPreferredSize(new Dimension(600, 400));

        xInicialField = new JTextField("2", 5);
        yInicialField = new JTextField("2", 5);
        sx1Field = new JTextField("1", 5);
        sy1Field = new JTextField("1", 5);
        sx2Field = new JTextField("1", 5);
        sy2Field = new JTextField("1", 5);

        backButton = new JButton("Menu");
        regenerarFigura = new JButton("Generar figura");
        escalarButton1 = new JButton("Primera escalación");
        escalarButton2 = new JButton("Segunda escalación");

        String[] aumentoOptions = {"x1", "x2", "x4", "x8", "x16"};
        aumentoComboBox = new JComboBox<>(aumentoOptions);
        aumentoComboBox.setSelectedIndex(0);

        String[] columnNames = {"P", "X", "Y"};
        String[] columnNames1 = {"P'", "X'", "Y'"};
        String[] columnNames2 = {"P''", "X''", "Y''"};
        originalTableModel = new DefaultTableModel(columnNames, 0);
        scaledTableModel1 = new DefaultTableModel(columnNames1, 0);
        scaledTableModel2 = new DefaultTableModel(columnNames2, 0);
        originalTable = new JTable(originalTableModel);
        scaledTable1 = new JTable(scaledTableModel1);
        scaledTable2 = new JTable(scaledTableModel2);

        sx1Label = new JLabel("Sx1: 1", SwingConstants.CENTER);
        sy1Label = new JLabel("Sy1: 1", SwingConstants.CENTER);
        sx2Label = new JLabel("Sx2: 1", SwingConstants.CENTER);
        sy2Label = new JLabel("Sy2: 1", SwingConstants.CENTER);

        Font boldFont = new Font("Arial", Font.BOLD, 18);
        sx1Label.setFont(boldFont);
        sy1Label.setFont(boldFont);
        sx2Label.setFont(boldFont);
        sy2Label.setFont(boldFont);
    }

    private void configureLayout() {
        setLayout(new BorderLayout());

        // Top Panel con título y botón de regreso
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Transformaciones Geométricas 2D Compuestas: Escalación Sucesiva", SwingConstants.CENTER);
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

        JPanel scaledTable1Panel = new JPanel(new BorderLayout());
        scaletedTable1Label = new JLabel("Primera Escalación (Sx1: 1, Sy1: 1)", SwingConstants.CENTER);
        scaledTable1Panel.add(scaletedTable1Label, BorderLayout.NORTH);

        JScrollPane scaledScrollPane1 = new JScrollPane(scaledTable1);
        scaledScrollPane1.setPreferredSize(new Dimension(300, 150));
        scaledTable1Panel.add(scaledScrollPane1, BorderLayout.CENTER);

        JPanel scaledTable2Panel = new JPanel(new BorderLayout());
        scaletedTable2Label = new JLabel("Segunda Escalación (Sx2: 1, Sy2: 1)", SwingConstants.CENTER);
        scaledTable2Panel.add(scaletedTable2Label, BorderLayout.NORTH);


        JScrollPane scaledScrollPane2 = new JScrollPane(scaledTable2);
        scaledScrollPane2.setPreferredSize(new Dimension(300, 150));
        scaledTable2Panel.add(scaledScrollPane2, BorderLayout.CENTER);

        tablesPanel.add(originalTablePanel);
        tablesPanel.add(scaledTable1Panel);
        tablesPanel.add(scaledTable2Panel);

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

        // Primera escalación
        controlPanel.add(new JLabel("Sx1:"));
        controlPanel.add(sx1Field);
        controlPanel.add(new JLabel("Sy1:"));
        controlPanel.add(sy1Field);
        controlPanel.add(new JLabel(""));
        controlPanel.add(escalarButton1);
        controlPanel.add(sx1Label);
        controlPanel.add(sy1Label);

        // Segunda escalación
        controlPanel.add(new JLabel("Sx2:"));
        controlPanel.add(sx2Field);
        controlPanel.add(new JLabel("Sy2:"));
        controlPanel.add(sy2Field);
        controlPanel.add(new JLabel(""));
        controlPanel.add(escalarButton2);
        controlPanel.add(sx2Label);
        controlPanel.add(sy2Label);

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
        });

        escalarButton1.addActionListener(e -> realizarPrimeraEscalacion());
        escalarButton2.addActionListener(e -> realizarSegundaEscalacion());
    }

    public void drawFiguraOriginal(int xInicio, int yInicio, int aumento) {
        clearPlanoAndData();

        try {
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

            Punto puntoAnterior = puntosList.get(0);
            planoCartesiano.addPunto(puntoAnterior);

            for (int i = 1; i < puntosList.size(); i++) {
                Punto punto = puntosList.get(i);
                planoCartesiano.addPunto(punto);
                planoCartesiano.addLinea(new Linea(puntoAnterior, punto, true, i));
                puntoAnterior = punto;
            }

            clearTableAll(1);
            updateOriginalTable(puntosList);
            planoCartesiano.repaint();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos.");
        }
    }

    private void realizarPrimeraEscalacion() {
        scaledTableModel2.setRowCount(0);
        try {
            sx1 = Integer.parseInt(sx1Field.getText());
            sy1 = Integer.parseInt(sy1Field.getText());

            if (puntosList == null || puntosList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Primero debe generar la figura original");
                return;
            }

            puntosEscalados1List = new ArrayList<>();
            for (Punto puntoOriginal : puntosList) {
                int nuevoX = puntoOriginal.getX() * sx1;
                int nuevoY = puntoOriginal.getY() * sy1;
                Punto puntoEscalado = new Punto(nuevoX, nuevoY);
                puntoEscalado.setNombrePunto(puntoOriginal.getNombrePunto() + "'");
                puntosEscalados1List.add(puntoEscalado);
            }

            clearTableAll(2);
            dibujarFiguras();
            updateScaledTable1(puntosEscalados1List);
            sx1Label.setText("Sx1: " + sx1);
            sy1Label.setText("Sy1: " + sy1);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos.");
        }
    }

    private void realizarSegundaEscalacion() {
        try {
            sx2 = Integer.parseInt(sx2Field.getText());
            sy2 = Integer.parseInt(sy2Field.getText());

            if (puntosEscalados1List == null || puntosEscalados1List.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Primero debe realizar la primera escalación");
                return;
            }

            puntosEscalados2List = new ArrayList<>();
            for (Punto puntoEscalado1 : puntosEscalados1List) {
                int nuevoX = puntoEscalado1.getX() * sx2;
                int nuevoY = puntoEscalado1.getY() * sy2;
                Punto puntoEscalado2 = new Punto(nuevoX, nuevoY);
                puntoEscalado2.setNombrePunto(puntoEscalado1.getNombrePunto().replace("'", "") + "''");
                puntosEscalados2List.add(puntoEscalado2);
            }

            dibujarFiguras();
            updateScaledTable2(puntosEscalados2List);
            sx2Label.setText("Sx2: " + sx2);
            sy2Label.setText("Sy2: " + sy2);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos.");
        }
    }

    private void dibujarFiguras() {
        planoCartesiano.clear();

        // Dibujar figura original
        dibujarFigura(puntosList, true);

        // Dibujar primera escalación si existe
        if (puntosEscalados1List != null && !puntosEscalados1List.isEmpty()) {
            dibujarFigura(puntosEscalados1List, false);
        }

        // Dibujar segunda escalación si existe
        if (puntosEscalados2List != null && !puntosEscalados2List.isEmpty()) {
            dibujarFigura(puntosEscalados2List, false);
        }

        planoCartesiano.repaint();
    }

    private void dibujarFigura(List<Punto> puntos, boolean esOriginal) {
        Punto puntoAnterior = puntos.get(0);
        planoCartesiano.addPunto(puntoAnterior);

        for (int i = 1; i < puntos.size(); i++) {
            Punto punto = puntos.get(i);
            planoCartesiano.addPunto(punto);
            planoCartesiano.addLinea(new Linea(puntoAnterior, punto, esOriginal, i));
            puntoAnterior = punto;
        }
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

    private void updateScaledTable1(List<Punto> puntos) {
        scaledTableModel1.setRowCount(0);
        for (Punto punto : puntos) {
            scaledTableModel1.addRow(new Object[]{
                    punto.getNombrePunto(),
                    punto.getX(),
                    punto.getY()
            });
        }
        scaletedTable1Label.setText(String.format("Primera Escalación (Sx1: %d, Sy1: %d)", sx1, sy1));

    }

    private void updateScaledTable2(List<Punto> puntos) {
        scaledTableModel2.setRowCount(0);
        for (Punto punto : puntos) {
            scaledTableModel2.addRow(new Object[]{
                    punto.getNombrePunto(),
                    punto.getX(),
                    punto.getY()
            });
        }
        scaletedTable2Label.setText(String.format("Segunda Escalación (Sx2: %d, Sy2: %d)", sx2, sy2));
    }

    private void clearTableAll(int index) {
        if(index == 1){
            scaletedTable1Label.setText("Primera Escalación (Sx1: 1, Sy1: 1)");
            scaletedTable2Label.setText("Segunda Escalación (Sx2: 1, Sy2: 1)");
        } else if (index == 2){
            scaletedTable2Label.setText("Segunda Escalación (Sx2: 1, Sy2: 1)");
        }
    }

    private void clearPlanoAndData() {
        planoCartesiano.clear();
        originalTableModel.setRowCount(0);
        scaledTableModel1.setRowCount(0);
        scaledTableModel2.setRowCount(0);
        puntosList = null;
        puntosEscalados1List = null;
        puntosEscalados2List = null;
        sx1Label.setText("Sx1: 1");
        sy1Label.setText("Sy1: 1");
        sx2Label.setText("Sx2: 1");
        sy2Label.setText("Sy2: 1");
        planoCartesiano.repaint();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PolilineasEscalacionSuc());
    }
}