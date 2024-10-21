package DrawingClasses.Transformaciones.Basicas;

import PaginaPrincipalFolder.Transformaciones.TransformacionesBasicas;
import Plano.Transformaciones.PlanoCartesianoFiguraPerTrans;
import formasADibujar.Linea;
import formasADibujar.Punto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolilineasTraslacion extends JFrame {
    private PlanoCartesianoFiguraPerTrans planoCartesiano;
    private JTable originalTable;
    private JTable translatedTable;
    private DefaultTableModel originalTableModel;
    private DefaultTableModel translatedTableModel;
    private JButton backButton;
    private JTextField xInicialField;
    private JTextField yInicialField;
    private JTextField txField;
    private JTextField tyField;
    private JButton regenerarFigura;
    private JButton trasladarButton;
    private List<Punto> puntosList;
    private List<Punto> puntosTrasladadosList;
    private int figuraCounter = 1;

    public PolilineasTraslacion() {
        setTitle("Traslación de Figuras");
        setSize(1650, 960);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createComponents();
        configureLayout();
        addActionListeners();
        setVisible(true);
    }

    private void createComponents() {
        planoCartesiano = new PlanoCartesianoFiguraPerTrans();
        planoCartesiano.setPreferredSize(new Dimension(600, 400));

        xInicialField = new JTextField("2", 5);
        yInicialField = new JTextField("2", 5);
        txField = new JTextField("0", 5);
        tyField = new JTextField("0", 5);

        backButton = new JButton("Menu");
        regenerarFigura = new JButton("Generar figura");
        trasladarButton = new JButton("Trasladar figura");

        String[] columnNames = {"Punto", "X", "Y"};
        String[] columnNamesEdi = {"P'", "X'", "Y'"};
        originalTableModel = new DefaultTableModel(columnNames, 0);
        translatedTableModel = new DefaultTableModel(columnNamesEdi, 0);
        originalTable = new JTable(originalTableModel);
        translatedTable = new JTable(translatedTableModel);
    }

    private void configureLayout() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Traslación de Figuras", SwingConstants.CENTER);
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
        originalTablePanel.add(new JLabel("Puntos Originales", SwingConstants.CENTER), BorderLayout.NORTH);
        JScrollPane originalScrollPane = new JScrollPane(originalTable);
        originalScrollPane.setPreferredSize(new Dimension(300, 200));
        originalTablePanel.add(originalScrollPane, BorderLayout.CENTER);

        // Segunda tabla (Trasladada)
        JPanel translatedTablePanel = new JPanel(new BorderLayout());
        translatedTablePanel.add(new JLabel("Puntos Trasladados", SwingConstants.CENTER), BorderLayout.NORTH);
        JScrollPane translatedScrollPane = new JScrollPane(translatedTable);
        translatedScrollPane.setPreferredSize(new Dimension(300, 200));
        translatedTablePanel.add(translatedScrollPane, BorderLayout.CENTER);

        tablesPanel.add(originalTablePanel);
        tablesPanel.add(translatedTablePanel);

        rightPanel.add(tablesPanel, BorderLayout.CENTER);

        // Panel de controles
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
        controlPanel.add(new JLabel("Tx:"));
        controlPanel.add(txField);
        controlPanel.add(new JLabel("Ty:"));
        controlPanel.add(tyField);
        controlPanel.add(new JLabel(""));
        controlPanel.add(trasladarButton);

        rightPanel.add(controlPanel, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.EAST);
    }

    private void addActionListeners() {
        backButton.addActionListener(e -> {
            new TransformacionesBasicas().setVisible(true);
            dispose();
        });

        regenerarFigura.addActionListener(e -> {
            int xInicio = Integer.parseInt(xInicialField.getText());
            int yInicio = Integer.parseInt(yInicialField.getText());
            drawFiguraOriginal(xInicio, yInicio, 1);
        });

        trasladarButton.addActionListener(e -> realizarTraslacion());
    }

    private void drawFiguraOriginal(int xInicio, int yInicio, int aumento) {
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