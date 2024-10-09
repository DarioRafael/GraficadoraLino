package mainClasses;

import Plano.CoordinateSystem;
import Plano.PlanoCartesianoFiguraPer;
import formasADibujar.Linea;
import formasADibujar.Punto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class FiguraAnonimaDrawMenu extends JFrame {
    private PlanoCartesianoFiguraPer planoCartesiano;
    private JButton otrosButton;
    private JPopupMenu otrosMenu;
    private JTable infoTable;
    private DefaultTableModel tableModel;
    private int figuraAnonimaCounter = 1;
    private JComboBox<String> figurasComboBox;
    private JComboBox<CoordinateSystem.Type> coordSystemComboBox;
    private Map<String, List<Punto>> figurasMap = new HashMap<>();
    private CoordinateSystem.Type currentCoordSystem = CoordinateSystem.Type.CARTESIAN_ABSOLUTE;
    private JLabel coordSystemLabel;
    private JButton clearButton;
    private String nombreFiguraAnonima;

    JComboBox<Integer> aumentoComboBox = new JComboBox<>(new Integer[]{1, 2, 4, 8, 16});
    int aumento = 1;
    // X y Y ORIGEN
    int xInicio;
    int yInicio;

    JTextField xInicialFieldNuevo;
    JTextField yInicialFieldNuevo;
    JButton regenerarFigura;
    private JButton backButton; // New button to go back to PaginaPrincipal

    public FiguraAnonimaDrawMenu() {
        setTitle("Graficación Básica por Computadora");
        setSize(1650, 960);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createComponents();
        configureLayout();
        addActionListeners();
        setVisible(true);
    }

    private void createComponents() {
        // Create the Cartesian plane panel
        planoCartesiano = new PlanoCartesianoFiguraPer();
        planoCartesiano.setPreferredSize(new Dimension(600, 400));

        // Create the "Otros" button and popup menu
        otrosButton = new JButton("Generar");
        otrosMenu = new JPopupMenu();
        JMenuItem figuraAnonimaItem = new JMenuItem("Figura de 8 puntos");
        figuraAnonimaItem.addActionListener(e -> drawFiguraAnonima());
        otrosMenu.add(figuraAnonimaItem);

        // Coordinate system components
        coordSystemComboBox = new JComboBox<>(CoordinateSystem.Type.values());
        coordSystemLabel = new JLabel("Sistema actual: " + currentCoordSystem.toString());
        clearButton = new JButton("Clear");

        // Table setup based on coordinate system
        updateTableModel();

        // Create combo box for figures
        figurasComboBox = new JComboBox<>();
        figurasComboBox.addActionListener(e -> mostrarPuntosFiguraSeleccionada());

        // Create the back button
        backButton = new JButton("Regresar a Página Principal");
    }

    private void updateTableModel() {
        String[] columnNames;
        switch (currentCoordSystem) {
            case CARTESIAN_ABSOLUTE:
                columnNames = new String[]{"Punto", "X", "Y"};
                break;
            case CARTESIAN_RELATIVE:
                columnNames = new String[]{"Punto", "ΔX", "ΔY"};
                break;
            case POLAR_ABSOLUTE:
                columnNames = new String[]{"Punto", "Radio", "Ángulo"};
                break;
            case POLAR_RELATIVE:
                columnNames = new String[]{"Punto", "ΔRadio", "ΔÁngulo"};
                break;
            default:
                columnNames = new String[]{"Punto", "X", "Y"};
        }
        tableModel = new DefaultTableModel(columnNames, 0);
        if (infoTable != null) {
            infoTable.setModel(tableModel);
        } else {
            infoTable = new JTable(tableModel);
        }
    }

    private void configureLayout() {
        setLayout(new BorderLayout());

        // Top panel with buttons and coordinate system selector
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(otrosButton);
        topPanel.add(clearButton);
        topPanel.add(new JLabel("Sistema de coordenadas: "));
        topPanel.add(coordSystemComboBox);
        topPanel.add(coordSystemLabel);
        topPanel.add(backButton); // Add the back button to the top panel
        add(topPanel, BorderLayout.NORTH);

        // Center panel with plano cartesiano
        add(planoCartesiano, BorderLayout.CENTER);

        // Right panel for table and combo box
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(figurasComboBox, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(infoTable);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel for initial coordinates and button
        JPanel bottomPanel = new JPanel(new GridLayout(4, 2)); // Adjusted to 4 rows
        bottomPanel.add(new JLabel("X inicial:"));
        xInicialFieldNuevo = new JTextField(5);
        bottomPanel.add(xInicialFieldNuevo);
        bottomPanel.add(new JLabel("Y inicial:"));
        yInicialFieldNuevo = new JTextField(5);
        bottomPanel.add(yInicialFieldNuevo);
        bottomPanel.add(new JLabel("Aumento:")); // Label for the combo box
        bottomPanel.add(aumentoComboBox); // Add the combo box
        regenerarFigura = new JButton("Generar en nuevo origen");
        bottomPanel.add(regenerarFigura);

        rightPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(rightPanel, BorderLayout.EAST);
    }

    private void addActionListeners() {
        otrosButton.addActionListener(e -> otrosMenu.show(otrosButton, 0, otrosButton.getHeight()));
        coordSystemComboBox.addActionListener(e -> {
            currentCoordSystem = (CoordinateSystem.Type) coordSystemComboBox.getSelectedItem();
            coordSystemLabel.setText("Sistema actual: " + currentCoordSystem.toString());
            planoCartesiano.setCurrentCoordSystem(currentCoordSystem); // Actualiza el sistema de coordenadas en el plano
            updateTableModel();
            mostrarPuntosFiguraSeleccionada();
            planoCartesiano.repaint(); // Asegúrate de repintar el plano

        });
        clearButton.addActionListener(e -> {
            planoCartesiano.clear(); // Método para limpiar el plano cartesiano
            tableModel.setRowCount(0); // Limpiar la tabla de información
            String selectedItem = (String) figurasComboBox.getSelectedItem();
            if (selectedItem != null) {
                figurasComboBox.removeItem(selectedItem); // Limpiar solo la figura actual
                figurasMap.remove(selectedItem); // Remover la figura del mapa
            }
        });

        regenerarFigura.addActionListener(e -> {
            planoCartesiano.clear();
            regenerarDrawAnonima();
        });

        backButton.addActionListener(e -> {
            new PaginaPrincipal().setVisible(true);
            dispose();
        });
    }

    private void updateTableWithPoints(List<Punto> puntos) {
        tableModel.setRowCount(0);
        Punto puntoAnterior = null;

        for (int i = 0; i < puntos.size(); i++) {
            Punto puntoActual = puntos.get(i);
            Object[] rowData;

            switch (currentCoordSystem) {
                case CARTESIAN_ABSOLUTE:
                    rowData = new Object[]{
                            puntoActual.getNombrePunto(),
                            (Object) puntoActual.getX(),
                            (Object) puntoActual.getY()
                    };
                    break;

                case CARTESIAN_RELATIVE:
                    if (puntoAnterior == null) {
                        rowData = new Object[]{
                                puntoActual.getNombrePunto(),
                                (Object) puntoActual.getX(),
                                (Object) puntoActual.getY()
                        };
                    } else {
                        rowData = new Object[]{
                                puntoActual.getNombrePunto(),
                                (Object) (puntoActual.getX() - puntoAnterior.getX()),
                                (Object) (puntoActual.getY() - puntoAnterior.getY())
                        };
                    }
                    break;

                case POLAR_ABSOLUTE:
                    CoordinateSystem.PolarCoordinate polar =
                            CoordinateSystem.cartesianToPolar(puntoActual.getX(), puntoActual.getY());
                    rowData = new Object[]{
                            puntoActual.getNombrePunto(),
                            String.format("%.2f", polar.getRadius()),
                            String.format("%.2f°", polar.getAngle())
                    };
                    break;

                case POLAR_RELATIVE:
                    if (puntoAnterior == null) {
                        CoordinateSystem.PolarCoordinate polarFirst =
                                CoordinateSystem.cartesianToPolar(puntoActual.getX(), puntoActual.getY());
                        rowData = new Object[]{
                                puntoActual.getNombrePunto(),
                                String.format("%.2f", polarFirst.getRadius()),
                                String.format("%.2f°", polarFirst.getAngle())
                        };
                    } else {
                        CoordinateSystem.PolarCoordinate polarRel =
                                CoordinateSystem.cartesianToPolarRelative(
                                        puntoAnterior.getX(), puntoAnterior.getY(),
                                        puntoActual.getX(), puntoActual.getY()
                                );
                        rowData = new Object[]{
                                puntoActual.getNombrePunto(),
                                String.format("%.2f", polarRel.getRadius()),
                                String.format("%.2f°", polarRel.getAngle())
                        };
                    }
                    break;

                default:
                    rowData = new Object[]{
                            puntoActual.getNombrePunto(),
                            (Object) puntoActual.getX(),
                            (Object) puntoActual.getY()
                    };
            }

            tableModel.addRow(rowData);
            puntoAnterior = puntoActual;
        }
    }

    public void drawFiguraAnonima() {
        nombreFiguraAnonima = "Figura Anonima " + figuraAnonimaCounter++;

        // Request starting point
        JPanel panelInicio = new JPanel(new GridLayout(2, 2));
        JTextField xInicioField = new JTextField(5);
        JTextField yInicioField = new JTextField(5);
        panelInicio.add(new JLabel("X origen:"));
        panelInicio.add(xInicioField);
        panelInicio.add(new JLabel("Y origen:"));
        panelInicio.add(yInicioField);

        int resultInicio = JOptionPane.showConfirmDialog(null, panelInicio,
                "Ingrese el punto de inicio", JOptionPane.OK_CANCEL_OPTION);

        if (resultInicio == JOptionPane.OK_OPTION) {
            try {
                xInicio = Integer.parseInt(xInicioField.getText());
                yInicio = Integer.parseInt(yInicioField.getText());
                Punto puntoInicio = new Punto(xInicio, yInicio);

                // Define anonymous figure points
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

                List<Punto> puntosList = Arrays.asList(puntosArray);

                // Label points
                for (int i = 0; i < puntosList.size(); i++) {
                    puntosList.get(i).setNombrePunto("P" + (i + 1));
                }


                // Draw figure
                Punto puntoAnterior = puntoInicio;
                planoCartesiano.addPunto(puntoInicio);

                for (int i = 0; i < puntosList.size(); i++) {
                    Punto punto = puntosList.get(i);
                    planoCartesiano.addPunto(punto);
                    planoCartesiano.addLinea(new Linea(puntoAnterior, punto, true, i + 1)); // Enviar el número del punto
                    puntoAnterior = punto;
                }

                xInicialFieldNuevo.setText(xInicioField.getText());
                yInicialFieldNuevo.setText(yInicioField.getText());
                // Update table with points in current coordinate system
                updateTableWithPoints(puntosList);

                // Add figure to map and combo box
                addFigura(nombreFiguraAnonima, puntosList);
                planoCartesiano.repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese valores numéricos válidos.");
            }
        }
    }

    public void regenerarDrawAnonima() {
        try {
            xInicio = Integer.parseInt(xInicialFieldNuevo.getText());
            yInicio = Integer.parseInt(yInicialFieldNuevo.getText());
            aumento = (Integer) aumentoComboBox.getSelectedItem();
            Punto puntoInicio = new Punto(xInicio, yInicio);

            // Define anonymous figure points
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

            List<Punto> puntosList = Arrays.asList(puntosArray);

            // Label points
            for (int i = 0; i < puntosList.size(); i++) {
                puntosList.get(i).setNombrePunto("P" + (i + 1));
            }

            // Draw figure
            Punto puntoAnterior = puntoInicio;
            planoCartesiano.addPunto(puntoInicio);

            for (Punto punto : puntosList) {
                if (currentCoordSystem == CoordinateSystem.Type.POLAR_ABSOLUTE) {
                }

                planoCartesiano.addPunto(punto);
                planoCartesiano.addLinea(new Linea(puntoAnterior, punto, true));
                puntoAnterior = punto;
            }

            // Update table with points in current coordinate system
            updateTableWithPoints(puntosList);
            addFigura(nombreFiguraAnonima, puntosList);
            // Add figure to map and combo box
            planoCartesiano.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese valores numéricos válidos.");
        }
    }

    private void mostrarPuntosFiguraSeleccionada() {
        String figuraSeleccionada = (String) figurasComboBox.getSelectedItem();
        if (figuraSeleccionada != null) {
            List<Punto> puntos = figurasMap.get(figuraSeleccionada);
            if (puntos != null) {
                updateTableWithPoints(puntos);
            }
        }
    }

    private void addFigura(String nombreFigura, List<Punto> puntos) {
        if (figurasMap.containsKey(nombreFigura)) {
            figurasMap.put(nombreFigura, puntos); // Update points if figure exists
        } else {
            figurasMap.put(nombreFigura, puntos); // Add new figure
            figurasComboBox.addItem(nombreFigura); // Update combo box
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FiguraAnonimaDrawMenu());
    }
}