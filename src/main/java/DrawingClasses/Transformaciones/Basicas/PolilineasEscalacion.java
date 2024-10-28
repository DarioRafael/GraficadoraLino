package DrawingClasses.Transformaciones.Basicas;

import PaginaPrincipalFolder.Transformaciones.Componentes.TransformacionesBasicas;
import Plano.Transformaciones.Basicas.PlanoCartesianoEscalacion;
import formasADibujar.Linea;
import formasADibujar.Punto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolilineasEscalacion extends JFrame {
    private PlanoCartesianoEscalacion planoCartesiano;
    private JTable originalTable;
    private JTable scaledTable;
    private DefaultTableModel originalTableModel;
    private DefaultTableModel scaledTableModel;
    private JButton backButton;
    private JTextField xInicialField;
    private JTextField yInicialField;
    private JTextField sxField;
    private JTextField syField;
    private JLabel sxLabel;
    private JLabel syLabel;
    public JComboBox<String> aumentoComboBox;
    private JButton regenerarFigura;
    private JButton escalarButton;
    private List<Punto> puntosList;
    private List<Punto> puntosEscaladosList;
    public int sx = 2;
    public int sy = 2;

    public PolilineasEscalacion() {
        setTitle("Transformaciones Geométricas 2D Básica: Escalación");
        setSize(1650, 960);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createComponents();
        configureLayout();
        addActionListeners();
        setVisible(true);
    }

    private void createComponents() {
        planoCartesiano = new PlanoCartesianoEscalacion();
        planoCartesiano.setPreferredSize(new Dimension(600, 400));

        xInicialField = new JTextField("2", 5);
        yInicialField = new JTextField("2", 5);
        sxField = new JTextField("1", 5);
        syField = new JTextField("1", 5);

        backButton = new JButton("Menu");
        regenerarFigura = new JButton("Generar figura");
        escalarButton = new JButton("Escalar figura");

        // ComboBox para seleccionar el aumento
        String[] aumentoOptions = {"x1", "x2", "x4", "x8", "x16"};
        aumentoComboBox = new JComboBox<>(aumentoOptions);
        aumentoComboBox.setSelectedIndex(0); // Valor por defecto: x1

        String[] columnNames = {"P", "X", "Y"};
        String[] columnNamesEdi = {"P'", "X'", "Y'"};
        originalTableModel = new DefaultTableModel(columnNames, 0);
        scaledTableModel = new DefaultTableModel(columnNamesEdi, 0);
        originalTable = new JTable(originalTableModel);
        scaledTable = new JTable(scaledTableModel);

        // Labels para mostrar valores de Sx y Sy después de la escalación
        sxLabel = new JLabel("Sx: 1", SwingConstants.CENTER);
        sxLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Cambia "Arial" y 18 por la fuente y tamaño deseados

        syLabel = new JLabel("Sy: 1", SwingConstants.CENTER);
        syLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Cambia "Arial" y 18 por la fuente y tamaño deseados
    }

    private void configureLayout() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Transformaciones Geométricas 2D Básica: Escalación", SwingConstants.CENTER);
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

        JPanel scaledTablePanel = new JPanel(new BorderLayout());

        JLabel scaledLabel = new JLabel("Puntos Escalados: " + "Sx: " + sx + " Sy: " + sy, SwingConstants.CENTER);
        scaledLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set font to Arial, bold, size 18
        scaledTablePanel.add(scaledLabel, BorderLayout.NORTH);
        JScrollPane scaledScrollPane = new JScrollPane(scaledTable);

        scaledScrollPane.setPreferredSize(new Dimension(300, 200));
        scaledTablePanel.add(scaledScrollPane, BorderLayout.CENTER);

        tablesPanel.add(originalTablePanel);
        tablesPanel.add(scaledTablePanel);

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
        controlPanel.add(new JLabel("Sx:"));
        controlPanel.add(sxField);
        controlPanel.add(new JLabel("Sy:"));
        controlPanel.add(syField);
        controlPanel.add(new JLabel(""));
        controlPanel.add(escalarButton);
        controlPanel.add(sxLabel);  // Mostrar valor de Sx
        controlPanel.add(syLabel);  // Mostrar valor de Sy

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

        escalarButton.addActionListener(e -> realizarEscalacion());
    }

    public void drawFiguraOriginal(int xInicio, int yInicio, int aumento) {
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
            // Actualizar la etiqueta de la tabla escalada
            Component parent = scaledTable.getParent().getParent().getParent();
            if (parent instanceof JPanel) {
                JLabel label = (JLabel) ((JPanel) parent).getComponent(0);
                label.setText("Puntos Escalados: Sx: " + 1 + " Sy: " + 1);
                label.setFont(new Font("Arial", Font.BOLD, 18)); // Cambia "Arial" y 18 por la fuente y tamaño deseados
            }
            updateOriginalTable(puntosList);

            planoCartesiano.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos.");
        }
    }

    private void realizarEscalacion() {
        try {
            sx = Integer.parseInt(sxField.getText());
            sy = Integer.parseInt(syField.getText());

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

            puntosEscaladosList = new ArrayList<>();
            for (Punto puntoOriginal : puntosList) {
                int nuevoX = puntoOriginal.getX() * sx;
                int nuevoY = puntoOriginal.getY() * sy;
                Punto puntoEscalado = new Punto(nuevoX, nuevoY);
                puntosEscaladosList.add(puntoEscalado);
            }

            Punto puntoAnteriorEscalado = puntosEscaladosList.get(0);
            planoCartesiano.addPunto(puntoAnteriorEscalado);

            for (int i = 0; i < puntosEscaladosList.size(); i++) {
                Punto puntoEscalado = puntosEscaladosList.get(i);
                puntoEscalado.setNombrePunto("P" + (i+1 ) + "'"); // Establece el nombre P'1, P'2, etc.

                planoCartesiano.addPunto(puntoEscalado);
                planoCartesiano.addLinea(new Linea(puntoAnteriorEscalado, puntoEscalado, true, i));
                puntoAnteriorEscalado = puntoEscalado;
            }

            updateScaledTable(puntosEscaladosList);
            planoCartesiano.repaint();

            // Actualizar las etiquetas para mostrar los valores de Sx y Sy
            sxLabel.setText("Sx: " + sx);
            syLabel.setText("Sy: " + sy);

            // Actualizar la etiqueta de la tabla escalada
            Component parent = scaledTable.getParent().getParent().getParent();
            if (parent instanceof JPanel) {
                ((JLabel) ((JPanel) parent).getComponent(0)).setText("Puntos Escalados: Sx: " + sx + " Sy: " + sy);
            }        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos.");
        }
    }

    private void updateOriginalTable(List<Punto> puntos) {
        originalTableModel.setRowCount(0);
        for (Punto punto : puntos) {
            originalTableModel.addRow(new Object[]{punto.getNombrePunto(), punto.getX(), punto.getY()});
        }
    }

    private void updateScaledTable(List<Punto> puntosEscalados) {
        scaledTableModel.setRowCount(0);
        for (int i = 0; i < puntosEscalados.size(); i++) {
            Punto puntoEscalado = puntosEscalados.get(i);
            scaledTableModel.addRow(new Object[]{"P" + (i + 1) + "'", puntoEscalado.getX(), puntoEscalado.getY()});
        }
    }

    private void clearPlanoAndData() {
        planoCartesiano.clear();
        originalTableModel.setRowCount(0);
        scaledTableModel.setRowCount(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PolilineasEscalacion::new);
    }
}
