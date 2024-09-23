package mainClasses;



import Plano.PlanoCartesiano;
import formasADibujar.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawingFrame extends JFrame {

    private PlanoCartesiano planoCartesiano;
    private Punto puntoActual;


    private JButton drawPointButton;

    private JButton drawLineButton;
    private JPopupMenu lineTypeMenu;


    private JButton drawConicasButton;
    private JPopupMenu conicasTypeMenu;


    private JButton clearButton;

    private JButton menuButton; // Nuevo botón para el menú
    private JPopupMenu popupMenu; // Menú emergente

    JPanel optionsPanel;
    JScrollPane scrollPane;
    JPanel titlePanel;


    private JTable infoTable;
    private DefaultTableModel tableModel;
    private JButton creditosButton;
    JLabel titleLabel;

    private boolean isDarkMode = false;

    // Constructor de la ventana
    public DrawingFrame() {
        setTitle("Graficación Básica Por Computadora");
        setSize(1650, 960);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        createComponents();


        configureLayout();

        // Añadir ActionListeners
        addActionListeners();

        // Hacer visible la ventana
        setVisible(true);
    }

    private void createComponents() {
        // Crear botones
        drawPointButton = new JButton("Dibujar Punto");

        drawLineButton = new JButton("Tipos de Lineas");
        lineTypeMenu = new JPopupMenu();
        String[] lineTypes = {"Vertical", "Horizontal", "Diagonal"};
        for (String type : lineTypes) {
            JMenuItem item = new JMenuItem(type);
            item.addActionListener(e -> drawLineBasedOnType(type));
            lineTypeMenu.add(item);
        }

        drawConicasButton = new JButton("Cónicas");
        conicasTypeMenu = new JPopupMenu();
        String[] conicasTypes = {"Circulo", "Elipse", "Arco"};
        for (String type : conicasTypes) {
            JMenuItem itemConicas = new JMenuItem(type);
            itemConicas.addActionListener(e -> drawConicaBasedOnType(type));
            conicasTypeMenu.add(itemConicas);
        }

        clearButton = new JButton("Limpiar");

        // Crear el botón del menú y el menú emergente
        menuButton = new JButton("Menú");
        popupMenu = new JPopupMenu();
        JMenuItem changeScaleItem = new JMenuItem("Cambiar escala");
        popupMenu.add(changeScaleItem);

        JMenuItem darkModeItem = new JMenuItem("Modo Oscuro");
        darkModeItem.addActionListener(e -> toggleDarkMode());
        popupMenu.add(darkModeItem);

        // Crear el panel del plano cartesiano
        planoCartesiano = new PlanoCartesiano();
        planoCartesiano.setPreferredSize(new Dimension(600, 400));

        // Crear la tabla y el modelo de tabla
        String[] columnNames = {"Punto","X", "Y"};
        tableModel = new DefaultTableModel(columnNames, 0);
        infoTable = new JTable(tableModel);

        // Ajustar el tamaño de la fuente y aplicar negritas a las cabeceras
        Font font = new Font("Arial", Font.BOLD, 16);
        infoTable.setFont(new Font("Arial", Font.PLAIN, 14)); // Tamaño de la fuente de las celdas

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setFont(font);
        infoTable.getTableHeader().setDefaultRenderer(headerRenderer);
        infoTable.getTableHeader().setFont(font); // Negritas en las cabeceras

        // Ajustar el tamaño de la columna si es necesario
        infoTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        infoTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        infoTable.getColumnModel().getColumn(2).setPreferredWidth(50);

        creditosButton = new JButton("Créditos");
    }

    private void configureLayout() {
        setLayout(new BorderLayout());

        // Panel superior para el título y las opciones
        JPanel topPanel = new JPanel(new BorderLayout());

        // Panel para el título
        titlePanel = new JPanel();
        titleLabel = new JLabel("Graficación Básica Por Computadora");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);

        // Panel para las opciones
        optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        optionsPanel.add(drawPointButton);
        optionsPanel.add(drawLineButton);
        optionsPanel.add(drawConicasButton);
        optionsPanel.add(clearButton);
        optionsPanel.add(menuButton);
        optionsPanel.add(creditosButton);

        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(optionsPanel, BorderLayout.CENTER);

        // Agregar componentes al frame
        add(topPanel, BorderLayout.NORTH);
        add(planoCartesiano, BorderLayout.CENTER);

        scrollPane = new JScrollPane(infoTable);
        scrollPane.setPreferredSize(new Dimension(200, 400)); // Ajustar el tamaño preferido
        add(scrollPane, BorderLayout.EAST);

    }

    private void addActionListeners() {
        drawPointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear un panel para solicitar las coordenadas X e Y
                JPanel panel = new JPanel(new GridLayout(2, 2));
                panel.add(new JLabel("Coordenada X:"));
                JTextField xField = new JTextField(5);
                panel.add(xField);
                panel.add(new JLabel("Coordenada Y:"));
                JTextField yField = new JTextField(5);
                panel.add(yField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Ingrese las coordenadas", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int x = Integer.parseInt(xField.getText());
                        int y = Integer.parseInt(yField.getText());
                        puntoActual = new Punto(x, y);



                        // Pasar el punto al plano cartesiano
                        planoCartesiano.addPunto(puntoActual); // Agregar el punto
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Por favor, ingrese valores numéricos válidos.");
                    }
                }
            }
        });
        drawLineButton.addActionListener(e -> lineTypeMenu.show(drawLineButton, 0, drawLineButton.getHeight()));
        drawConicasButton.addActionListener(e -> conicasTypeMenu.show(drawConicasButton, 0, drawConicasButton.getHeight()));
        menuButton.addActionListener(e -> popupMenu.show(menuButton, 0, menuButton.getHeight()));
        creditosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Créditos:\n\nDesarrollado por: Dario Rafael García Bárcenas y Juan Carlos Torres Reyna\nVersión: 1.0", "Créditos", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                planoCartesiano.clear(); // Llamar al método clear() del plano cartesiano
                tableModel.setRowCount(0); // Limpiar la tabla

            }
        });
    }


    private void toggleDarkMode() {
        isDarkMode = !isDarkMode;
        updateColors();
    }

    private void updateColors() {
        if (isDarkMode) {
            // Dark mode colors
            getContentPane().setBackground(Color.DARK_GRAY);
            drawPointButton.setBackground(Color.GRAY);
            drawPointButton.setForeground(Color.WHITE);
            drawLineButton.setBackground(Color.GRAY);
            drawLineButton.setForeground(Color.WHITE);
            drawConicasButton.setBackground(Color.GRAY);
            drawConicasButton.setForeground(Color.WHITE);
            clearButton.setBackground(Color.GRAY);
            clearButton.setForeground(Color.WHITE);
            menuButton.setBackground(Color.GRAY);
            menuButton.setForeground(Color.WHITE);
            creditosButton.setBackground(Color.GRAY);
            creditosButton.setForeground(Color.WHITE);
            // Tabla en modo oscuro
            infoTable.setBackground(Color.DARK_GRAY); // Fondo oscuro
            infoTable.setForeground(Color.WHITE);     // Texto blanco

            // Cabeceras de la tabla en modo oscuro
            DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) infoTable.getTableHeader().getDefaultRenderer();
            headerRenderer.setBackground(Color.GRAY);   // Fondo gris para las cabeceras
            headerRenderer.setForeground(Color.WHITE);  // Texto blanco en las cabeceras

            scrollPane.getViewport().setBackground(Color.BLACK); // Cambiar el color de fondo del scrollpane
            optionsPanel.setBackground(Color.DARK_GRAY);
            titlePanel.setBackground(Color.DARK_GRAY);

            titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Ejemplo de cambio de fuente
            titleLabel.setForeground(Color.WHITE); // Establecer el color del texto a blanco


        } else {
            // Light mode colors (reset to defaults)
            getContentPane().setBackground(null); // Reset to default
            drawPointButton.setBackground(null);
            drawPointButton.setForeground(null);
            drawLineButton.setBackground(null);
            drawLineButton.setForeground(null);
            drawConicasButton.setBackground(null);
            drawConicasButton.setForeground(null);
            clearButton.setBackground(null);
            clearButton.setForeground(null);
            menuButton.setBackground(null);
            menuButton.setForeground(null);
            creditosButton.setBackground(null);
            creditosButton.setForeground(null);
            // Tabla en modo claro (restablecer a valores predeterminados)
            infoTable.setBackground(null);
            infoTable.setForeground(null);

            // Cabeceras de la tabla en modo claro (restablecer a valores predeterminados)
            DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) infoTable.getTableHeader().getDefaultRenderer();
            headerRenderer.setBackground(null);
            headerRenderer.setForeground(null);
            // Reset colors for other components

            optionsPanel.setBackground(null);
            scrollPane.getViewport().setBackground(null); // Restablecer el color de fondo del scrollpane a su valor predeterminado
            titlePanel.setBackground(null);

            titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Ejemplo de cambio de fuente
            titleLabel.setForeground(Color.BLACK); // Establecer el color del texto a blanco



        }

        // Update the PlanoCartesiano colors (you'll need to implement this in PlanoCartesiano)
        planoCartesiano.setDarkMode(isDarkMode);

        repaint(); // Repaint the entire frame to apply the new colors
    }
    private void drawLineBasedOnType(String lineType) {
        Punto puntoInicio = null;
        Punto puntoFin = null;

        // Solicitar el punto de inicio
        JPanel panelInicio = new JPanel(new GridLayout(2, 2));
        JTextField xInicioField = new JTextField(5);
        JTextField yInicioField = new JTextField(5);
        panelInicio.add(new JLabel("X inicial:"));
        panelInicio.add(xInicioField);
        panelInicio.add(new JLabel("Y inicial:"));
        panelInicio.add(yInicioField);

        int resultInicio = JOptionPane.showConfirmDialog(null, panelInicio,
                "Ingrese el punto de inicio", JOptionPane.OK_CANCEL_OPTION);

        if (resultInicio == JOptionPane.OK_OPTION) {
            try {
                int xInicio = Integer.parseInt(xInicioField.getText());
                int yInicio = Integer.parseInt(yInicioField.getText());
                puntoInicio = new Punto(xInicio, yInicio);

                // Solicitar el punto final basado en el tipo de línea
                JPanel panelFin = new JPanel(new GridLayout(2, 2));
                JTextField xFinField = new JTextField(5);
                JTextField yFinField = new JTextField(5);

                switch (lineType) {
                    case "Vertical":
                        configurarColumnas(false);
                        panelFin.add(new JLabel("X final (fijo):"));
                        xFinField.setText(String.valueOf(xInicio));
                        xFinField.setEditable(false);
                        panelFin.add(xFinField);
                        panelFin.add(new JLabel("Y final:"));
                        panelFin.add(yFinField);
                        break;
                    case "Horizontal":
                        configurarColumnas(false);
                        panelFin.add(new JLabel("X final:"));
                        panelFin.add(xFinField);
                        panelFin.add(new JLabel("Y final (fijo):"));
                        yFinField.setText(String.valueOf(yInicio));
                        yFinField.setEditable(false);
                        panelFin.add(yFinField);
                        break;
                    case "Diagonal":
                        configurarColumnas(false);
                        panelFin.add(new JLabel("X final:"));
                        panelFin.add(xFinField);
                        panelFin.add(new JLabel("Y final:"));
                        panelFin.add(yFinField);
                        break;
                }

                int resultFin = JOptionPane.showConfirmDialog(null, panelFin,
                        "Ingrese el punto final", JOptionPane.OK_CANCEL_OPTION);

                if (resultFin == JOptionPane.OK_OPTION) {
                    int xFin = Integer.parseInt(xFinField.getText());
                    int yFin = Integer.parseInt(yFinField.getText());
                    puntoFin = new Punto(xFin, yFin);

                    // Crear y dibujar la línea
                    Linea nuevaLinea = new Linea(puntoInicio, puntoFin);
                    planoCartesiano.repaint();

                    // Actualizar la tabla con los puntos de la nueva línea
                    updateTableWithLinePoints(nuevaLinea);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese valores numéricos válidos.");
            }
        }
    }

    public void updateTableWithLinePoints(Linea linea) {
        DefaultTableModel tableModel = (DefaultTableModel) infoTable.getModel();
        tableModel.setRowCount(0); // Limpiar la tabla
        int puntoNumero = 1; // Inicializar el número del punto
        for (Punto punto : linea.calcularPuntosIntermedios()) {
            tableModel.addRow(new Object[]{"P" + puntoNumero++, punto.getX(), punto.getY()});
        }
    }



    private void drawConicaBasedOnType(String option) {
        Punto puntoInicio = null;
        int radio = 0;

        // Primero, preguntar por el método
        String[] metodos = {"Polinomial", "Trigonométrico"};
        String metodoSeleccionado = (String) JOptionPane.showInputDialog(null,
                "Seleccione el método de cálculo:",
                "Método de Cálculo",
                JOptionPane.QUESTION_MESSAGE,
                null,
                metodos,
                metodos[0]);

        if (metodoSeleccionado == null) {
            return; // El usuario canceló la selección
        }

        // Solicitar el punto de inicio
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
                tableModel.setRowCount(0);
                int xInicio = Integer.parseInt(xInicioField.getText());
                int yInicio = Integer.parseInt(yInicioField.getText());
                puntoInicio = new Punto(xInicio, yInicio);

                switch (option) {
                    case "Circulo":
                        if (metodoSeleccionado.equals("Polinomial")) {
                            configurarColumnas(false);
                            // Pedir el radio para el método polinomial
                            JTextField radioField = new JTextField(5);
                            JPanel panelRadio = new JPanel(new GridLayout(1, 2));
                            panelRadio.add(new JLabel("Radio:"));
                            panelRadio.add(radioField);

                            int resultRadio = JOptionPane.showConfirmDialog(null, panelRadio,
                                    "Ingrese el radio del círculo", JOptionPane.OK_CANCEL_OPTION);

                            if (resultRadio == JOptionPane.OK_OPTION) {
                                radio = Integer.parseInt(radioField.getText());
                                calcularPuntosCirculoPolinomio(xInicio, yInicio, radio);
                                Circulo nuevoCirculoPolinomial = new Circulo(puntoInicio, radio);
                                planoCartesiano.repaint();

                            }
                        } else { // Método trigonométrico
                            configurarColumnas(true); // Para trigonometría
                            // Pedir radio para el método trigonométrico
                            JTextField radioField = new JTextField(5);
                            JPanel panelRadio = new JPanel(new GridLayout(1, 2));
                            panelRadio.add(new JLabel("Radio:"));
                            panelRadio.add(radioField);

                            int resultRadio = JOptionPane.showConfirmDialog(null, panelRadio,
                                    "Ingrese el radio del círculo", JOptionPane.OK_CANCEL_OPTION);

                            if (resultRadio == JOptionPane.OK_OPTION) {
                                radio = Integer.parseInt(radioField.getText());
                                calcularPuntosCirculoTrigonometrico(xInicio, yInicio, radio);
                                Circulo nuevoCirculoTrigonometrico =new Circulo(puntoInicio, radio);
                                planoCartesiano.repaint();
                            }
                        }
                        break;
                    case "Elipse":
                        JTextField radioXField = new JTextField(5);
                        JTextField radioYField = new JTextField(5);
                        JPanel panelElipse = new JPanel(new GridLayout(2, 2));
                        panelElipse.add(new JLabel("Radio X:"));
                        panelElipse.add(radioXField);
                        panelElipse.add(new JLabel("Radio Y:"));
                        panelElipse.add(radioYField);

                        int resultElipse = JOptionPane.showConfirmDialog(null, panelElipse,
                                "Ingrese los radios de la elipse", JOptionPane.OK_CANCEL_OPTION);

                        if (resultElipse == JOptionPane.OK_OPTION) {
                            int radioX = Integer.parseInt(radioXField.getText());
                            int radioY = Integer.parseInt(radioYField.getText());
                            if (metodoSeleccionado.equals("Polinomial")) {
                                calcularPuntosElipsePolinomio(xInicio, yInicio, radioX, radioY);
                            } else {
                                calcularPuntosElipseTrigonometrico(xInicio, yInicio, radioX, radioY);
                            }
                            Elipse nuevaElipse = new Elipse(puntoInicio, radioX, radioY);
                            planoCartesiano.addElipse(nuevaElipse);
                        }
                        break;
                    case "Arco":
                        JTextField radioField = new JTextField(5);
                        JTextField anguloInicioField = new JTextField(5);
                        JTextField anguloFinField = new JTextField(5);
                        JPanel panelArco = new JPanel(new GridLayout(3, 2));
                        panelArco.add(new JLabel("Radio:"));
                        panelArco.add(radioField);
                        panelArco.add(new JLabel("Ángulo inicio (grados):"));
                        panelArco.add(anguloInicioField);
                        panelArco.add(new JLabel("Ángulo fin (grados):"));
                        panelArco.add(anguloFinField);

                        int resultArco = JOptionPane.showConfirmDialog(null, panelArco,
                                "Ingrese los parámetros del arco", JOptionPane.OK_CANCEL_OPTION);

                        if (resultArco == JOptionPane.OK_OPTION) {
                            int radioArco = Integer.parseInt(radioField.getText());
                            double anguloInicio = Double.parseDouble(anguloInicioField.getText());
                            double anguloFin = Double.parseDouble(anguloFinField.getText());
                            if (metodoSeleccionado.equals("Polinomial")) {
                                calcularPuntosArcoPolinomio(xInicio, yInicio, radioArco, anguloInicio, anguloFin);
                            } else {
                                calcularPuntosArcoTrigonometrico(xInicio, yInicio, radioArco, anguloInicio, anguloFin);
                            }
                            Arco nuevoArco = new Arco(puntoInicio, radioArco, anguloInicio, anguloFin);
                            planoCartesiano.addArco(nuevoArco);
                        }
                        break;
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese valores numéricos válidos.");
            }
        }
    }


    private void calcularPuntosCirculoPolinomio(double centerX, double centerY, double radius) {
        int numSteps = 8; // Puedes ajustar este valor según necesites
        for (int i = 0; i < numSteps; i++) {
            double t = 2 * Math.PI * i / numSteps;
            double x = centerX + radius * Math.cos(t);
            double y = centerY + radius * Math.sin(t);
            //puntosFigura.add(new Point2D.Double(x, y));
            tableModel.addRow(new Object[]{"P" + (i + 1), String.format("%.2f", x), String.format("%.2f", y)});
        }
    }

    private void calcularPuntosCirculoTrigonometrico(double centerX, double centerY, double radius) {
        int numSteps = 8; // Puedes ajustar este valor según necesites
        for (int i = 0; i < numSteps; i++) {
            double angle = 2 * Math.PI * i / numSteps;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            //puntosFigura.add(new Point2D.Double(x, y));
            tableModel.addRow(new Object[]{"P" + (i + 1), String.format("%.2f", radius), String.format("%.2f", Math.toDegrees(angle))});
        }
    }

    private void calcularPuntosElipsePolinomio(double centerX, double centerY, double radioX, double radioY) {
        int numSteps = 8; // Puedes ajustar este valor según necesites
        for (int i = 0; i < numSteps; i++) {
            double t = 2 * Math.PI * i / numSteps;
            double x = centerX + radioX * Math.cos(t);
            double y = centerY + radioY * Math.sin(t);
            tableModel.addRow(new Object[]{"P" + (i + 1), String.format("%.2f", x), String.format("%.2f", y)});
        }
    }

    private void calcularPuntosElipseTrigonometrico(double centerX, double centerY, double radioX, double radioY) {
        int numSteps = 8; // Puedes ajustar este valor según necesites
        for (int i = 0; i < numSteps; i++) {
            double angle = 2 * Math.PI * i / numSteps;
            double x = centerX + radioX * Math.cos(angle);
            double y = centerY + radioY * Math.sin(angle);
            tableModel.addRow(new Object[]{"P" + (i + 1), String.format("%.2f", radioX), String.format("%.2f", radioY), String.format("%.2f", Math.toDegrees(angle))});
        }
    }

    private void calcularPuntosArcoPolinomio(double centerX, double centerY, double radio, double anguloInicio, double anguloFin) {
        int numSteps = 8;
        double startRad = Math.toRadians(anguloInicio);
        double endRad = Math.toRadians(anguloFin);

        // Verificar si es un círculo completo (0 a 360 grados)
        boolean esCirculoCompleto = (anguloInicio == 0 && anguloFin == 360);

        for (int i = 0; i < numSteps; i++) {
            // Calcular el ángulo en radianes para este paso
            double t = startRad + (endRad - startRad) * i / (numSteps - 1);

            // Si es un círculo completo, usar todos los ángulos; si no, verificar si el ángulo está dentro del rango
            if (esCirculoCompleto || (t >= startRad && t <= endRad)) {
                double x = centerX + radio * Math.cos(t);
                double y = centerY + radio * Math.sin(t);

                int xRounded = (int) Math.round(x);
                int yRounded = (int) Math.round(y);

                tableModel.addRow(new Object[]{"P" + (i + 1), xRounded, yRounded});
            }
        }
    }

    private void calcularPuntosArcoTrigonometrico(double centerX, double centerY, double radio, double anguloInicio, double anguloFin) {
        int numSteps = 8;
        double startRad = Math.toRadians(anguloInicio);
        double endRad = Math.toRadians(anguloFin);

        // Verificar si es un círculo completo (0 a 360 grados)
        boolean esCirculoCompleto = (anguloInicio == 0 && anguloFin == 360);

        for (int i = 0; i < numSteps; i++) {
            // Calcular el ángulo en radianes para este paso
            double angle = startRad + (endRad - startRad) * i / (numSteps - 1);

            // Si es un círculo completo, usar todos los ángulos; si no, verificar si el ángulo está dentro del rango
            if (esCirculoCompleto || (angle >= startRad && angle <= endRad)) {
                double x = centerX + radio * Math.cos(angle);
                double y = centerY + radio * Math.sin(angle);

                int xRounded = (int) Math.round(x);
                int yRounded = (int) Math.round(y);

                tableModel.addRow(new Object[]{"P" + (i + 1), radio, Math.toDegrees(angle)});
            }
        }
    }


    // Método para configurar las columnas según el contexto
    private void configurarColumnas(boolean esTrigonometria) {
        String[] columnNames;
        if (esTrigonometria) {
            columnNames = new String[]{"Punto", "r", "Ángulo (°)"}; // Nombres para trigonometría
        } else {
            columnNames = new String[]{"Punto", "X", "Y"}; // Nombres por defecto
        }


        tableModel.setColumnCount(0); // Limpiar columnas anteriores
        for (String columnName : columnNames) {
            tableModel.addColumn(columnName); // Agregar nuevas columnas
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DrawingFrame();
        });
    }
}


