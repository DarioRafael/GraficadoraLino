    package DrawingClasses.Polilineas;

    import PaginaPrincipalFolder.AjustesVentana;
    import PaginaPrincipalFolder.Polilineas.MenuDePolilineas;
    import Plano.GenericsPlano.CoordinateSystem;
    import Plano.Polilineas.PlanoCartesianoFiguraPer;
    import formasADibujar.Linea;
    import formasADibujar.Punto;

    import javax.swing.*;
    import javax.swing.table.DefaultTableModel;
    import java.awt.*;
    import java.util.*;
    import java.util.List;

    public class PolilineasDrawMenu extends JFrame {
        private PlanoCartesianoFiguraPer planoCartesiano;
        private JButton otrosButton;
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
        JLabel titleLabel;


        public JComboBox<Integer> aumentoComboBox;

        int aumento = 1;
        public static int xInicio = 2;
        public static int yInicio= 2;

        public static JTextField xInicialFieldNuevo;
        public static JTextField yInicialFieldNuevo;
        JButton regenerarFigura;

        private JButton backButton; // New button to go back to PaginaPrincipal

        List<Punto> puntosList;
        AjustesVentana ajustesVentana = new AjustesVentana();
        public PolilineasDrawMenu() {
            setTitle("Graficación Geométrica 2D Básica: Polilineas");
            setSize(ajustesVentana.getWindowSize());            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

            xInicialFieldNuevo = new JTextField();
            yInicialFieldNuevo = new JTextField();

            // Create the "Generar" button and its menu

            titleLabel = new JLabel("Graficación Geométrica 2D Básica: Polilíneas");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set larger font size

            JMenuItem figuraAnonimaItem = new JMenuItem("Graficación Geométrica 2D Básica: Polilíneas");
            figuraAnonimaItem.addActionListener(e -> regenerarDrawAnonima());

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
            backButton = new JButton("Menu");

            coordSystemComboBox.setFont(new Font("Arial", Font.BOLD, 14));

            coordSystemComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    label.setFont(new Font("Arial", Font.BOLD, 14)); // Set the larger font
                    if (value instanceof CoordinateSystem.Type) {
                        CoordinateSystem.Type type = (CoordinateSystem.Type) value;
                        switch (type) {
                            case CARTESIAN_ABSOLUTE:
                                label.setText("Cartesianas absolutas");
                                break;
                            case CARTESIAN_RELATIVE:
                                label.setText("Cartesianas relativas");
                                break;
                            case POLAR_ABSOLUTE:
                                label.setText("Polares absolutas");
                                break;
                            case POLAR_RELATIVE:
                                label.setText("Polares relativas");
                                break;
                        }
                    }
                    return label;
                }
            });

            aumentoComboBox = new JComboBox<>(new Integer[]{(Integer) 1, (Integer) 2, (Integer) 4, (Integer) 8, (Integer) 16});
            aumentoComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (c instanceof JLabel && value instanceof Integer) {
                        ((JLabel) c).setText("x" + value);
                    }
                    return c;
                }
            });
        }

        private void updateTableModel() {
            String[] columnNames;
            switch (currentCoordSystem) {
                case CARTESIAN_ABSOLUTE:
                    columnNames = new String[]{"Punto", "X", "Y"};
                    break;
                case CARTESIAN_RELATIVE:
                    columnNames = new String[]{"Punto", "dX", "dY"};
                    break;
                case POLAR_ABSOLUTE:
                    columnNames = new String[]{"Punto", "r", "θ"};
                    break;
                case POLAR_RELATIVE:
                    columnNames = new String[]{"Punto", "dr", "dθ"};
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

            // Top panel with the back button and title label
            JPanel topPanel = new JPanel(new BorderLayout());

            // Panel for the back button on the left
            JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            leftPanel.add(backButton);

            // Panel for the title label in the center
            JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            centerPanel.add(titleLabel);

            topPanel.add(leftPanel, BorderLayout.WEST);
            topPanel.add(centerPanel, BorderLayout.CENTER);
            add(topPanel, BorderLayout.NORTH);

            // Center panel with plano cartesiano
            add(planoCartesiano, BorderLayout.CENTER);

            // Right panel for table and combo box
            JPanel rightPanel = new JPanel(new BorderLayout());
            rightPanel.add(figurasComboBox, BorderLayout.NORTH);
            JScrollPane scrollPane = new JScrollPane(infoTable);
            scrollPane.setPreferredSize(new Dimension(300, 200)); // Adjusted height to 200
            rightPanel.add(scrollPane, BorderLayout.CENTER);

            // Panel for initial coordinates, combo box, and buttons
            JPanel bottomPanel = new JPanel(new GridLayout(6, 2)); // Adjusted for 6 rows

            JLabel coordLabel = new JLabel("Sistema de coordenadas:");
            coordLabel.setFont(new Font("Arial", Font.BOLD, 17));
            bottomPanel.add(coordLabel);
            bottomPanel.add(coordSystemComboBox);

            JLabel xInicialLabel = new JLabel("X inicial:");
            xInicialLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            bottomPanel.add(xInicialLabel);
            xInicialFieldNuevo = new JTextField(5);
            bottomPanel.add(xInicialFieldNuevo);

            JLabel yInicialLabel = new JLabel("Y inicial:");
            yInicialLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            bottomPanel.add(yInicialLabel);
            yInicialFieldNuevo = new JTextField(5);
            bottomPanel.add(yInicialFieldNuevo);

            JLabel aumentoLabel = new JLabel("Aumento:");
            aumentoLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            bottomPanel.add(aumentoLabel);
            bottomPanel.add(aumentoComboBox);
            bottomPanel.add(clearButton);
            //bottomPanel.add(otrosButton);


            regenerarFigura = new JButton("Generar figura");
            bottomPanel.add(regenerarFigura);

            rightPanel.add(bottomPanel, BorderLayout.NORTH);
            add(rightPanel, BorderLayout.EAST);
        }
        public void initializeAndDraw(int xInicioMet, int yInicioMet) {
            xInicialFieldNuevo.setText(String.valueOf(xInicioMet));
            yInicialFieldNuevo.setText(String.valueOf(yInicioMet));
            xInicio = xInicioMet;
            yInicio = yInicioMet;
            drawFiguraAnonima(xInicioMet, yInicioMet);
        }

        private void addActionListeners() {
            coordSystemComboBox.addActionListener(e -> {
                currentCoordSystem = (CoordinateSystem.Type) coordSystemComboBox.getSelectedItem();
                coordSystemLabel.setText("Sistema actual: " + currentCoordSystem.toString());
                planoCartesiano.setCurrentCoordSystem(currentCoordSystem); // Update system in plane
                updateTableModel();
                mostrarPuntosFiguraSeleccionada();
                planoCartesiano.repaint();
            });

            clearButton.addActionListener(e -> {
                planoCartesiano.clear(); // Method to clear the Cartesian plane
                tableModel.setRowCount(0); // Clear the information table
                String selectedItem = (String) figurasComboBox.getSelectedItem();
                if (selectedItem != null) {
                    figurasComboBox.removeItem(selectedItem); // Remove only current figure
                    figurasMap.remove(selectedItem); // Remove figure from map
                }
            });

            regenerarFigura.addActionListener(e -> {
                planoCartesiano.clear();
                regenerarDrawAnonima();
            });

            backButton.addActionListener(e -> {
                clearPlanoAndData(); // Limpia el plano y los datos de las figuras
                dispose();
                SwingUtilities.invokeLater(() -> {
                    MenuDePolilineas principalFrame = new MenuDePolilineas();
                    principalFrame.setSize(ajustesVentana.getWindowSize());
                    principalFrame.setLocationRelativeTo(null);
                    principalFrame.setVisible(true);
                });
            });
        }

        private void clearPlanoAndData() {
            planoCartesiano.clear(); // Limpia el plano cartesiano
            figurasMap.clear(); // Limpia el mapa de figuras
            figurasComboBox.removeAllItems(); // Limpia el combo box de figuras
            tableModel.setRowCount(0); // Limpia la tabla de información
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

        public void drawFiguraAnonimaInternal(int xInicio, int yInicio, int aumento) {
            clearPlanoAndData(); // Limpia el plano y los datos antes de dibujar la nueva figura

            nombreFiguraAnonima = "Figura Anonima " + figuraAnonimaCounter++;

            try {
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

                puntosList = Arrays.asList(puntosArray);

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
                    planoCartesiano.addLinea(new Linea(puntoAnterior, punto, true, i + 1));
                    puntoAnterior = punto;
                }

                // Update table with points in current coordinate system
                updateTableWithPoints(puntosList);

                // Add figure to map and combo box
                addFigura(nombreFiguraAnonima, puntosList);
                planoCartesiano.repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese valores numéricos válidos.");
            }
        }

        public void drawFiguraAnonima(int xInicio, int yInicio) {
            drawFiguraAnonimaInternal(xInicio, yInicio, aumento);
        }

        public void regenerarDrawAnonima() {
            try {
                int xInicio = Integer.parseInt(xInicialFieldNuevo.getText());
                int yInicio = Integer.parseInt(yInicialFieldNuevo.getText());
                int aumento = (Integer) aumentoComboBox.getSelectedItem();
                drawFiguraAnonimaInternal(xInicio, yInicio, aumento);
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

            SwingUtilities.invokeLater(() -> {
                PolilineasDrawMenu frame = new PolilineasDrawMenu();

                xInicialFieldNuevo.setText("2");
                yInicialFieldNuevo.setText("2");
                xInicio = Integer.parseInt(xInicialFieldNuevo.getText());
                yInicio = Integer.parseInt(yInicialFieldNuevo.getText());
                frame.drawFiguraAnonima(xInicio, yInicio);

                frame.setVisible(true);
            });
        }
    }