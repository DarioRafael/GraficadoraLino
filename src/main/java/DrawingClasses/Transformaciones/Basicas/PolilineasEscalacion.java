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

public class PolilineasEscalacion extends JFrame {
    private PlanoCartesianoFiguraPerTrans planoCartesiano;
    private JTable originalTable;
    private JTable scaledTable;
    private DefaultTableModel originalTableModel;
    private DefaultTableModel scaledTableModel;
    private JButton backButton;
    private JTextField xInicialField;
    private JTextField yInicialField;
    private JTextField sxField;
    private JTextField syField;
    private JButton regenerarFigura;
    private JButton escalarButton;
    private List<Punto> puntosList;
    private List<Punto> puntosEscaladosList;

    public PolilineasEscalacion() {
        setTitle("Escalación de Figuras");
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
        sxField = new JTextField("1", 5);
        syField = new JTextField("1", 5);

        backButton = new JButton("Menu");
        regenerarFigura = new JButton("Generar figura");
        escalarButton = new JButton("Escalar figura");

        String[] columnNames = {"P", "X", "Y"};
        String[] columnNamesEdi = {"P'", "X'", "Y'"};
        originalTableModel = new DefaultTableModel(columnNames, 0);
        scaledTableModel = new DefaultTableModel(columnNamesEdi, 0);
        originalTable = new JTable(originalTableModel);
        scaledTable = new JTable(scaledTableModel);
    }

    private void configureLayout() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Escalación de Figuras", SwingConstants.CENTER);
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

        JPanel scaledTablePanel = new JPanel(new BorderLayout());
        scaledTablePanel.add(new JLabel("Puntos Escalados", SwingConstants.CENTER), BorderLayout.NORTH);
        JScrollPane scaledScrollPane = new JScrollPane(scaledTable);
        scaledScrollPane.setPreferredSize(new Dimension(300, 200));
        scaledTablePanel.add(scaledScrollPane, BorderLayout.CENTER);

        tablesPanel.add(originalTablePanel);
        tablesPanel.add(scaledTablePanel);

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
        controlPanel.add(new JLabel("Sx:"));
        controlPanel.add(sxField);
        controlPanel.add(new JLabel("Sy:"));
        controlPanel.add(syField);
        controlPanel.add(new JLabel(""));
        controlPanel.add(escalarButton);

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

        escalarButton.addActionListener(e -> realizarEscalacion());
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

    private void realizarEscalacion() {
        try {
            int sx = Integer.parseInt(sxField.getText());
            int sy = Integer.parseInt(syField.getText());

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

            // Calcular punto de referencia (primer punto)
            Punto puntoReferencia = puntosList.get(0);
            int xRef = puntoReferencia.getX();
            int yRef = puntoReferencia.getY();

            System.out.println("X = " + xRef +"X = " + yRef) ;

            // Crear puntos escalados
            for (int i = 0; i < puntosList.size(); i++) {
                Punto puntoOriginal = puntosList.get(i);
                // Aplicar transformación de escalado respecto al punto de referencia
                double newX =(puntoOriginal.getX() ) * sx;
                double newY =(puntoOriginal.getY() ) * sy;

                Punto puntoEscalado = new Punto((int)Math.round(newX), (int)Math.round(newY));
                puntoEscalado.setNombrePunto("P" + (i + 1) + "'");
                puntosEscaladosList.add(puntoEscalado);
            }

            // Dibujar figura escalada
            Punto puntoAnterior = puntosEscaladosList.get(0);
            planoCartesiano.addPunto(puntoAnterior);

            for (int i = 1; i < puntosEscaladosList.size(); i++) {
                Punto punto = puntosEscaladosList.get(i);
                planoCartesiano.addPunto(punto);
                Linea linea = new Linea(puntoAnterior, punto, true, i);
                planoCartesiano.addLinea(linea);
                puntoAnterior = punto;
            }

            updateScaledTable(puntosEscaladosList);
            planoCartesiano.repaint();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos para Sx y Sy");
        }
    }

    private void clearPlanoAndData() {
        planoCartesiano.clear();
        originalTableModel.setRowCount(0);
        scaledTableModel.setRowCount(0);
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

    private void updateScaledTable(List<Punto> puntos) {
        scaledTableModel.setRowCount(0);
        for (Punto punto : puntos) {
            scaledTableModel.addRow(new Object[]{
                    punto.getNombrePunto(),
                    punto.getX(),
                    punto.getY()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PolilineasEscalacion frame = new PolilineasEscalacion();
        });
    }
}