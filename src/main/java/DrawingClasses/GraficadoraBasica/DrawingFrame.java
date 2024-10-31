package DrawingClasses.GraficadoraBasica;



import Plano.GenericsPlano.PlanoCartesiano;
import formasADibujar.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class DrawingFrame extends JFrame {

    private PlanoCartesiano planoCartesiano;
    private Punto puntoActual;
    private boolean figuraAnonima = false;

    private JButton drawPointButton;

    private JButton drawLineButton;
    private JPopupMenu lineTypeMenu;


    private JButton drawConicasButton;
    private JPopupMenu conicasTypeMenu;

    private JLabel metodoLabel;
    private JButton clearButton;

    private JButton menuButton; // Nuevo botón para el menú
    private JPopupMenu popupMenu; // Menú emergente

    JPanel optionsPanel;
    JScrollPane scrollPane;
    JPanel titlePanel;

    JPanel infoPanel;
    JPanel comboBoxPanel;


    private JTable infoTable;
    private DefaultTableModel tableModel;
    private JButton creditosButton;
    JLabel titleLabel;

    private boolean isDarkMode = false;


    private JComboBox<String> figurasComboBox;
    private Map<String, List<Punto>> figurasMap = new HashMap<>();

    private JButton otrosButton;
    private JPopupMenu otrosMenu;


    private int puntosContador = 1;
    private int figuraAnonimaCounter = 1; // Counter for anonymous figures
    private int circuloPolinomialCounter = 1; // Counter for anonymous figures
    private int circuloTrigonometricoCounter = 1; // Counter for anonymous figures
    private int elipsePolinomialCounter = 1; // Counter for anonymous figures
    private int elipseTrigonometricoCounter = 1; // Counter for anonymous figures
    private int lineaVerticalCounter = 1; // Counter for anonymous figures
    private int lineaHorizontalCounter = 1; // Counter for anonymous figures


    // Constructor de la ventana
    public DrawingFrame() {
        setTitle("Graficación Básica por Computadora");
        setSize(1650, 960);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        createComponents();


        configureLayout();

        // Apply numeric-only listener to all text fields
        addNumericOnlyFilterToAll(this.getContentPane());

        // Añadir ActionListeners
        addActionListeners();

        // Hacer visible la ventana
        setVisible(true);
    }

    private void createComponents() {
        // Initialize optionsPanel
        optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));

        // Create buttons
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

        // Create the menu button and popup menu
        menuButton = new JButton("Menú");
        popupMenu = new JPopupMenu();
        JMenuItem changeScaleItem = new JMenuItem("Cambiar escala");
        popupMenu.add(changeScaleItem);

        JMenuItem darkModeItem = new JMenuItem("Modo Oscuro");
        darkModeItem.addActionListener(e -> toggleDarkMode());
        popupMenu.add(darkModeItem);

        // Create the Cartesian plane panel
        planoCartesiano = new PlanoCartesiano();
        planoCartesiano.setPreferredSize(new Dimension(600, 400));

        // Create the table and table model
        String[] columnNames = {"Punto", "X", "Y"};
        tableModel = new DefaultTableModel(columnNames, 0);
        infoTable = new JTable(tableModel);

        // Adjust font size and apply bold to headers
        Font font = new Font("Arial", Font.BOLD, 16);
        infoTable.setFont(new Font("Arial", Font.PLAIN, 14)); // Cell font size

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setFont(font);
        infoTable.getTableHeader().setDefaultRenderer(headerRenderer);
        infoTable.getTableHeader().setFont(font); // Bold headers

        // Adjust column width if necessary
        infoTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        infoTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        infoTable.getColumnModel().getColumn(2).setPreferredWidth(50);

        creditosButton = new JButton("Créditos");

        // Create the "Otros" button and popup menu
        otrosButton = new JButton("Otros");
        otrosMenu = new JPopupMenu();
        JMenuItem figuraAnonimaItem = new JMenuItem("FiguraAnonima");
        otrosMenu.add(figuraAnonimaItem);


        // Add the "Otros" button to the options panel
        optionsPanel.add(otrosButton);



        // Inicializar el mapa de figuras
        figurasMap = new HashMap<>();
        figurasComboBox = new JComboBox<>();
        figurasComboBox.addActionListener(e -> mostrarPuntosFiguraSeleccionada());

        // En el método createComponents(), añade:
        metodoLabel = new JLabel();
        metodoLabel.setFont(new Font("Arial", Font.BOLD, 14));
    }

    private void configureLayout() {
        setLayout(new BorderLayout());

        // Panel superior para el título y las opciones
        JPanel topPanel = new JPanel(new BorderLayout());

        // Panel para el título
        titlePanel = new JPanel();
        titleLabel = new JLabel("Graficación Básica por Computadora");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);

        // Panel para las opciones
        optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        optionsPanel.add(drawPointButton);
        optionsPanel.add(drawLineButton);
        optionsPanel.add(drawConicasButton);
        optionsPanel.add(otrosButton);
        optionsPanel.add(clearButton);
        optionsPanel.add(menuButton);
        optionsPanel.add(creditosButton);

        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(optionsPanel, BorderLayout.CENTER);

        // Agregar componentes al frame
        add(topPanel, BorderLayout.NORTH);
        add(planoCartesiano, BorderLayout.CENTER);



        // Panel para la tabla y el JComboBox


        JPanel rightPanel = new JPanel(new BorderLayout());

        // Panel para el JComboBox
        // Add the infoPanel to the rightPanel
        infoPanel = new JPanel();
        rightPanel.add(infoPanel, BorderLayout.NORTH);

        comboBoxPanel = new JPanel();
        comboBoxPanel.add(figurasComboBox);
        rightPanel.add(comboBoxPanel, BorderLayout.CENTER);

        scrollPane = new JScrollPane(infoTable);
        scrollPane.setPreferredSize(new Dimension(300, 400)); // Ajustar el tamaño preferido
        rightPanel.add(scrollPane, BorderLayout.SOUTH);



        add(rightPanel, BorderLayout.EAST);
    }
    private void addActionListeners() {
        drawPointButton.addActionListener(e -> handleDrawPointAction());
        drawLineButton.addActionListener(e -> lineTypeMenu.show(drawLineButton, 0, drawLineButton.getHeight()));
        drawConicasButton.addActionListener(e -> conicasTypeMenu.show(drawConicasButton, 0, drawConicasButton.getHeight()));
        menuButton.addActionListener(e -> popupMenu.show(menuButton, 0, menuButton.getHeight()));
        creditosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Créditos:\n\nDesarrollado por: Dario Rafael García Bárcenas y Juan Carlos Torres Reyna\nVersión: 1.0", "Créditos", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        clearButton.addActionListener(e -> handlerclear());
        otrosButton.addActionListener(e -> otrosMenu.show(otrosButton, 0, otrosButton.getHeight()));

    }

    private void updateInfoPanel(String tipoFigura, Map<String, String> datos) {
        infoPanel.removeAll();
        infoPanel.setLayout(new GridLayout(0, 2, 5, 5)); // Rows, 2 columns, gaps


        // Si el tipo de figura es círculo, elipse o arco, mostrar el método usado
        if (tipoFigura.contains("circulo") || tipoFigura.contains("elipse") ||
                tipoFigura.contains("arco")) {
            // Añadir el label del método al principio del panel
            infoPanel.add(new JLabel("Método:"));
            String metodo = tipoFigura.contains("Trigonometrico") ? "Trigonométrico" : "Polinomial";
            metodoLabel.setText("<html><b>" + metodo + "</b></html>");
            infoPanel.add(metodoLabel);
        } else if(tipoFigura.contains("figuraAnonimaCartesiana")){
            // Añadir el label del método al principio del panel
            infoPanel.add(new JLabel("Método:"));
        }

        switch (tipoFigura) {
            case "linea":
                // Punto inicial
                infoPanel.add(new JLabel("X inicial:"));
                JTextField xInicioField = new JTextField(datos.get("xInicio"), 10);
                infoPanel.add(xInicioField);

                infoPanel.add(new JLabel("Y inicial:"));
                JTextField yInicioField = new JTextField(datos.get("yInicio"), 10);
                infoPanel.add(yInicioField);

                // Punto final
                infoPanel.add(new JLabel("X final:"));
                JTextField xFinField = new JTextField(datos.get("xFin"), 10);
                infoPanel.add(xFinField);

                infoPanel.add(new JLabel("Y final:"));
                JTextField yFinField = new JTextField(datos.get("yFin"), 10);
                infoPanel.add(yFinField);
                break;

            case "circuloPolinomial":
                infoPanel.add(new JLabel("Centro X:"));
                JTextField centroXField = new JTextField(datos.get("centroX"), 10);
                infoPanel.add(centroXField);

                infoPanel.add(new JLabel("Centro Y:"));
                JTextField centroYField = new JTextField(datos.get("centroY"), 10);
                infoPanel.add(centroYField);

                infoPanel.add(new JLabel("Radio:"));
                JTextField radioField = new JTextField(datos.get("radio"), 10);
                infoPanel.add(radioField);
                break;

            case "circuloTrigonometrico":
                infoPanel.add(new JLabel("Centro X:"));
                JTextField centroXTrigField = new JTextField(datos.get("centroX"), 10);
                infoPanel.add(centroXTrigField);

                infoPanel.add(new JLabel("Centro Y:"));
                JTextField centroYTrigField = new JTextField(datos.get("centroY"), 10);
                infoPanel.add(centroYTrigField);

                infoPanel.add(new JLabel("Radio:"));
                JTextField radioTrigField = new JTextField(datos.get("radio"), 10);
                infoPanel.add(radioTrigField);

                infoPanel.add(new JLabel("Ángulo:"));
                JTextField anguloField = new JTextField(datos.get("angulo"), 10);
                infoPanel.add(anguloField);
                break;

            case "elipsePolinomial":
                infoPanel.add(new JLabel("Centro X:"));
                JTextField centroXElipseField = new JTextField(datos.get("centroX"), 10);
                infoPanel.add(centroXElipseField);

                infoPanel.add(new JLabel("Centro Y:"));
                JTextField centroYElipseField = new JTextField(datos.get("centroY"), 10);
                infoPanel.add(centroYElipseField);

                infoPanel.add(new JLabel("Radio X:"));
                JTextField radioXField = new JTextField(datos.get("radioX"), 10);
                infoPanel.add(radioXField);

                infoPanel.add(new JLabel("Radio Y:"));
                JTextField radioYField = new JTextField(datos.get("radioY"), 10);
                infoPanel.add(radioYField);
                break;
            case "elipseTrigonometrico":
                infoPanel.add(new JLabel("Centro X:"));
                JTextField centroXTrigElipseField = new JTextField(datos.get("centroX"), 10);
                infoPanel.add(centroXTrigElipseField);

                infoPanel.add(new JLabel("Centro Y:"));
                JTextField centroYTrigElipseField = new JTextField(datos.get("centroY"), 10);
                infoPanel.add(centroYTrigElipseField);

                infoPanel.add(new JLabel("Radio X:"));
                JTextField radioXTrigField = new JTextField(datos.get("radioX"), 10);
                infoPanel.add(radioXTrigField);

                infoPanel.add(new JLabel("Radio Y:"));
                JTextField radioYTrigField = new JTextField(datos.get("radioY"), 10);
                infoPanel.add(radioYTrigField);
                break;
            case "arcoPolinomial":
                infoPanel.add(new JLabel("Centro X:"));
                JTextField centroXArcoField = new JTextField(datos.get("centroX"), 10);
                infoPanel.add(centroXArcoField);

                infoPanel.add(new JLabel("Centro Y:"));
                JTextField centroYArcoField = new JTextField(datos.get("centroY"), 10);
                infoPanel.add(centroYArcoField);

                infoPanel.add(new JLabel("Radio:"));
                JTextField radioArcoField = new JTextField(datos.get("radio"), 10);
                infoPanel.add(radioArcoField);

                infoPanel.add(new JLabel("X1:"));
                JTextField x1Field = new JTextField(datos.get("x1"), 10);
                infoPanel.add(x1Field);

                infoPanel.add(new JLabel("X2:"));
                JTextField x2Field = new JTextField(datos.get("x2"), 10);
                infoPanel.add(x2Field);
                break;

            case "arcoTrigonometrico":
                infoPanel.add(new JLabel("Centro X:"));
                JTextField centroXArcoTrigField = new JTextField(datos.get("centroX"), 10);
                infoPanel.add(centroXArcoTrigField);

                infoPanel.add(new JLabel("Centro Y:"));
                JTextField centroYArcoTrigField = new JTextField(datos.get("centroY"), 10);
                infoPanel.add(centroYArcoTrigField);

                infoPanel.add(new JLabel("Radio:"));
                JTextField radioArcoTrigField = new JTextField(datos.get("radio"), 10);
                infoPanel.add(radioArcoTrigField);

                infoPanel.add(new JLabel("Ángulo Inicio (°):"));
                JTextField anguloInicioField = new JTextField(datos.get("anguloInicio"), 10);
                infoPanel.add(anguloInicioField);

                infoPanel.add(new JLabel("Ángulo Fin (°):"));
                JTextField anguloFinField = new JTextField(datos.get("anguloFin"), 10);
                infoPanel.add(anguloFinField);
                break;
            case "figuraAnonima":
                //infoPanel.add(centroXArcoTrigField);

                break;

        }




        // Aplica el filtro numérico a todos los JTextField
        for (Component comp : infoPanel.getComponents()) {
            if (comp instanceof JTextField) {
                addNumericOnlyFilter((JTextField) comp);
            }
        }

        infoPanel.revalidate();
        infoPanel.repaint();
    }

    public void handlerclear(){
        planoCartesiano.clear(); // Llamar al método clear() del plano cartesiano
        tableModel.setRowCount(0); // Limpiar la tabla
        figurasComboBox.removeAllItems();
    }

    private void handleDrawPointAction() {
        // Crear un panel para solicitar las coordenadas X e Y
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Coordenada X:"));
        JTextField xField = new JTextField(5);
        panel.add(xField);
        panel.add(new JLabel("Coordenada Y:"));
        JTextField yField = new JTextField(5);
        panel.add(yField);
        addNumericOnlyFilter(xField);
        addNumericOnlyFilter(yField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Ingrese las coordenadas", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                tableModel.setRowCount(0); // Limpiar la tabla
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());
                puntoActual = new Punto(x, y);

                tableModel.addRow(new Object[]{"P1", (Object) x, (Object) y});

                // Pasar el punto al plano cartesiano
                planoCartesiano.addPunto(puntoActual); // Agregar el punto
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese valores numéricos válidos.");
            }
        }
    }

    private void mostrarPuntosFiguraSeleccionada() {
        String figuraSeleccionada = (String) figurasComboBox.getSelectedItem();
        if (figuraSeleccionada != null) {
            List<Punto> puntos = figurasMap.get(figuraSeleccionada);
            if (puntos != null) {
                DefaultTableModel tableModel = (DefaultTableModel) infoTable.getModel();
                tableModel.setRowCount(0); // Limpiar la tabla
                for (Punto punto : puntos) {
                    tableModel.addRow(new Object[]{punto.getNombrePunto(), (Object) punto.getX(), (Object) punto.getY()});
                }
            }
        }
    }

    // Ejemplo de cómo agregar una figura al mapa y al JComboBox
    public void addFigura(String nombreFigura, List<Punto> puntos) {
        figurasMap.put(nombreFigura, puntos);
        figurasComboBox.addItem(nombreFigura);
    }
    private void addNumericOnlyFilterToAll(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JTextField) {
                addNumericOnlyFilter((JTextField) component);
            } else if (component instanceof Container) {
                addNumericOnlyFilterToAll((Container) component);
            }
        }
    }

    private void addNumericOnlyFilter(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;
                if (isValid(string)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) return;
                if (isValid(text)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            private boolean isValid(String text) {
                return text.matches("^-?\\d*$");
            }
        });

        // Agregar un KeyListener para mayor seguridad
        textField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) && (c != '-')) {
                    e.consume();  // Ignora el evento si no es un número
                }
                if (c == '-' && textField.getCaretPosition() != 0) {
                    e.consume();  // Solo permite '-' al principio
                }
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
            otrosButton.setBackground(Color.GRAY);
            otrosButton.setForeground(Color.WHITE);
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

            infoPanel.setBackground(Color.DARK_GRAY);
            comboBoxPanel.setBackground(Color.DARK_GRAY);
            infoPanel.setForeground(Color.WHITE);
            comboBoxPanel.setForeground(Color.WHITE);

            // Actualizar colores de todos los componentes en infoPanel
            for (Component comp : infoPanel.getComponents()) {
                if (comp instanceof JLabel) {
                    comp.setForeground(Color.WHITE);
                } else if (comp instanceof JTextField) {
                    comp.setForeground(Color.WHITE);
                    comp.setBackground(Color.DARK_GRAY);
                }
            }


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
            otrosButton.setBackground(null);
            otrosButton.setForeground(null);

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

            infoPanel.setBackground(null);
            comboBoxPanel.setBackground(null);
            infoPanel.setForeground(null);
            comboBoxPanel.setForeground(null);

            // Restablecer colores de todos los componentes en infoPanel
            for (Component comp : infoPanel.getComponents()) {
                if (comp instanceof JLabel) {
                    comp.setForeground(null);
                } else if (comp instanceof JTextField) {
                    comp.setForeground(null);
                    comp.setBackground(null);
                }
            }


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
        addNumericOnlyFilter(xInicioField);
        addNumericOnlyFilter(yInicioField);

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
                addNumericOnlyFilter(xFinField);
                addNumericOnlyFilter(yFinField);

                switch (lineType) {
                    case "Vertical":
                        panelFin.add(new JLabel("X final (fijo):"));
                        xFinField.setText(String.valueOf(xInicio));
                        xFinField.setEditable(false);
                        panelFin.add(xFinField);
                        panelFin.add(new JLabel("Y final:"));
                        panelFin.add(yFinField);
                        break;
                    case "Horizontal":
                        panelFin.add(new JLabel("X final:"));
                        panelFin.add(xFinField);
                        panelFin.add(new JLabel("Y final (fijo):"));
                        yFinField.setText(String.valueOf(yInicio));
                        yFinField.setEditable(false);
                        panelFin.add(yFinField);
                        break;
                    case "Diagonal":
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
                    Linea nuevaLinea = new Linea(puntoInicio, puntoFin, false);
                    planoCartesiano.repaint();
                    configurarColumnas(false);
                    // Actualizar la tabla con los puntos de la nueva línea
                    updateTableWithLinePoints(nuevaLinea);

                    // Agregar esta llamada para actualizar el panel de información
                    Map<String, String> datos = new HashMap<>();
                    datos.put("xInicio", String.valueOf(puntoInicio.getX()));
                    datos.put("yInicio", String.valueOf(puntoInicio.getY()));
                    datos.put("xFin", String.valueOf(puntoFin.getX()));
                    datos.put("yFin", String.valueOf(puntoFin.getY()));
                    updateInfoPanel("linea", datos);

                    // Agregar la línea al mapa de figuras y al JComboBox
                    List<Punto> puntosLinea = calcularPuntosIntermedios(puntoInicio, puntoFin);
                    String nombreFigura = lineType + " Linea " + (lineType.equals("Vertical") ? lineaVerticalCounter++ : lineaHorizontalCounter++);
                    addFigura(nombreFigura, puntosLinea);

                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese valores numéricos válidos.");
            }
        }
    }

    // Method to calculate intermediate points of a line
    public List<Punto> calcularPuntosIntermedios(Punto inicio, Punto fin) {
        List<Punto> puntosIntermedios = new ArrayList<>();
        int x1 = inicio.getX();
        int y1 = inicio.getY();
        int x2 = fin.getX();
        int y2 = fin.getY();

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;
        int puntoNumero = 1;

        while (true) {
            Punto punto = new Punto(x1, y1);
            punto.setNombrePunto("P" + puntoNumero++);
            puntosIntermedios.add(punto);
            if (x1 == x2 && y1 == y2) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
        return puntosIntermedios;
    }

// Update the drawLineBasedOnType method

    public void updateTableWithLinePoints(Linea linea) {
        DefaultTableModel tableModel = (DefaultTableModel) infoTable.getModel();
        tableModel.setRowCount(0); // Limpiar la tabla
        int puntoNumero = 1; // Inicializar el número del punto
        for (Punto punto : linea.calcularPuntosIntermedios()) {
            tableModel.addRow(new Object[]{"P" + puntoNumero++, (Object) punto.getX(), (Object) punto.getY()});
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

        addNumericOnlyFilter(xInicioField);
        addNumericOnlyFilter(yInicioField);

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

                            // Pedir el radio para el método polinomial
                            JTextField radioField = new JTextField(5);
                            JPanel panelRadio = new JPanel(new GridLayout(1, 2));
                            panelRadio.add(new JLabel("Radio:"));
                            panelRadio.add(radioField);

                            int resultRadio = JOptionPane.showConfirmDialog(null, panelRadio,
                                    "Ingrese el radio del círculo", JOptionPane.OK_CANCEL_OPTION);

                            if (resultRadio == JOptionPane.OK_OPTION) {
                                radio = Integer.parseInt(radioField.getText());
                                configurarColumnas(false);
                                calcularPuntosCirculoPolinomio(xInicio, yInicio, radio);
                                Circulo nuevoCirculoPolinomial = new Circulo(puntoInicio, radio);
                                planoCartesiano.repaint();
                                // Agregar esta llamada
                                Map<String, String> datos = new HashMap<>();
                                datos.put("centroX", String.valueOf(xInicio));
                                datos.put("centroY", String.valueOf(yInicio));
                                datos.put("radio", String.valueOf(radio));
                                updateInfoPanel("circuloPolinomial", datos);

                            }
                        } else {
                            // Método trigonométrico

                            // Pedir radio para el método trigonométrico
                            JTextField radioField = new JTextField(5);
                            JPanel panelRadio = new JPanel(new GridLayout(1, 2));
                            panelRadio.add(new JLabel("Radio:"));
                            panelRadio.add(radioField);
                            addNumericOnlyFilter(radioField);


                            int resultRadio = JOptionPane.showConfirmDialog(null, panelRadio,
                                    "Ingrese el radio del círculo", JOptionPane.OK_CANCEL_OPTION);

                            if (resultRadio == JOptionPane.OK_OPTION) {
                                radio = Integer.parseInt(radioField.getText());
                                configurarColumnas(true); // Para trigonometría
                                calcularPuntosCirculoTrigonometrico(xInicio, yInicio, radio);
                                Circulo nuevoCirculoTrigonometrico =new Circulo(puntoInicio, radio);
                                planoCartesiano.repaint();


                                // Agregar esta llamada
                                Map<String, String> datos = new HashMap<>();
                                datos.put("centroX", String.valueOf(xInicio));
                                datos.put("centroY", String.valueOf(yInicio));
                                datos.put("radio", String.valueOf(radio));
                                datos.put("angulo", "0"); // Valor inicial del ángulo
                                updateInfoPanel("circuloTrigonometrico", datos);
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

                            // Agregar esta llamada
                            Map<String, String> datos = new HashMap<>();
                            datos.put("centroX", String.valueOf(xInicio));
                            datos.put("centroY", String.valueOf(yInicio));
                            datos.put("radioX", String.valueOf(radioX));
                            datos.put("radioY", String.valueOf(radioY));



                            if (metodoSeleccionado.equals("Polinomial")) {
                                configurarColumnas(false); // Para trigonometría
                                calcularPuntosElipsePolinomio(xInicio, yInicio, radioX, radioY);
                                updateInfoPanel("elipsePolinomial", datos);
                            } else {
                                configurarColumnas(true); // Para trigonometría
                                calcularPuntosElipseTrigonometrico(xInicio, yInicio, radioX, radioY);
                                updateInfoPanel("elipseTrigonometrico", datos);
                            }
                            Elipse nuevaElipse = new Elipse(puntoInicio, radioX, radioY);
                            planoCartesiano.addElipse(nuevaElipse);
                        }
                        break;
                    case "Arco":
                        JTextField radioField = new JTextField(5);
                        JPanel panelArco;

                        if (metodoSeleccionado.equals("Polinomial")) {
                            // Configuración del panel para el método Polinomial
                            JTextField x1Field = new JTextField(5);
                            JTextField x2Field = new JTextField(5);

                            panelArco = new JPanel(new GridLayout(3, 2));
                            panelArco.add(new JLabel("Radio:"));
                            panelArco.add(radioField);
                            panelArco.add(new JLabel("x1 (coordenada inicial en X):"));
                            panelArco.add(x1Field);
                            panelArco.add(new JLabel("x2 (coordenada final en X):"));
                            panelArco.add(x2Field);

                            int resultPolinomial = JOptionPane.showConfirmDialog(null, panelArco,
                                    "Ingrese los parámetros del arco (método Polinomial)", JOptionPane.OK_CANCEL_OPTION);

                            if (resultPolinomial == JOptionPane.OK_OPTION) {
                                int radioArco = Integer.parseInt(radioField.getText());
                                int x1 = Integer.parseInt(x1Field.getText());
                                int x2 = Integer.parseInt(x2Field.getText());

                                // Actualizar el panel de información para el arco polinomial
                                Map<String, String> datosArcoPolinomial = new HashMap<>();
                                datosArcoPolinomial.put("centroX", String.valueOf(xInicio));
                                datosArcoPolinomial.put("centroY", String.valueOf(yInicio));
                                datosArcoPolinomial.put("radio", String.valueOf(radioArco));
                                datosArcoPolinomial.put("x1", String.valueOf(x1));
                                datosArcoPolinomial.put("x2", String.valueOf(x2));
                                updateInfoPanel("arcoPolinomial", datosArcoPolinomial);

                                // Llama a la función polinomial que usa los puntos x1 y x2
                                calcularPuntosArcoPolinomio(xInicio, yInicio, radioArco, x1, x2);

                                // Se agrega un nuevo Arco (o puede ser que solo quieras los puntos calculados)
                                Arco nuevoArco = new Arco(puntoInicio, radioArco, x1, x2); // Ajusta si es necesario
                                planoCartesiano.addArco(nuevoArco);
                            }
                        } else {
                            // Configuración del panel para el método Trigonométrico
                            JTextField anguloInicioField = new JTextField(5);
                            JTextField anguloFinField = new JTextField(5);

                            panelArco = new JPanel(new GridLayout(3, 2));
                            panelArco.add(new JLabel("Radio:"));
                            panelArco.add(radioField);
                            panelArco.add(new JLabel("Ángulo inicio (grados):"));
                            panelArco.add(anguloInicioField);
                            panelArco.add(new JLabel("Ángulo fin (grados):"));
                            panelArco.add(anguloFinField);

                            int resultTrigonometrico = JOptionPane.showConfirmDialog(null, panelArco,
                                    "Ingrese los parámetros del arco (método Trigonométrico)", JOptionPane.OK_CANCEL_OPTION);

                            if (resultTrigonometrico == JOptionPane.OK_OPTION) {
                                int radioArco = Integer.parseInt(radioField.getText());
                                double anguloInicio = Double.parseDouble(anguloInicioField.getText());
                                double anguloFin = Double.parseDouble(anguloFinField.getText());

                                // Actualizar el panel de información para el arco trigonométrico
                                Map<String, String> datosArcoTrigonometrico = new HashMap<>();
                                datosArcoTrigonometrico.put("centroX", String.valueOf(xInicio));
                                datosArcoTrigonometrico.put("centroY", String.valueOf(yInicio));
                                datosArcoTrigonometrico.put("radio", String.valueOf(radioArco));
                                datosArcoTrigonometrico.put("anguloInicio", String.valueOf(anguloInicio));
                                datosArcoTrigonometrico.put("anguloFin", String.valueOf(anguloFin));
                                updateInfoPanel("arcoTrigonometrico", datosArcoTrigonometrico);

                                // Llama a la función trigonométrica
                                calcularPuntosArcoTrigonometrico(xInicio, yInicio, radioArco, anguloInicio, anguloFin);

                                // Se agrega un nuevo Arco
                                Arco nuevoArco = new Arco(puntoInicio, radioArco, anguloInicio, anguloFin);
                                planoCartesiano.addArco(nuevoArco);
                            }
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

            tableModel.addRow(new Object[]{"P" + (i + 1), String.format(String.valueOf(x)), String.format(String.valueOf(y))});
        }

    }

    private void calcularPuntosCirculoTrigonometrico(double centerX, double centerY, double radius) {
        int numSteps = 8; // Puedes ajustar este valor según necesites
        for (int i = 0; i < numSteps; i++) {
            double angle = 2 * Math.PI * i / numSteps;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            //puntosFigura.add(new Point2D.Double(x, y));
            tableModel.addRow(new Object[]{"P" + (i + 1), String.format(String.valueOf(radius)), String.format(String.valueOf(Math.toDegrees(angle)))});
        }
    }

    private void calcularPuntosElipsePolinomio(double centerX, double centerY, double radioX, double radioY) {
        int numSteps = 8; // Puedes ajustar este valor según necesites
        for (int i = 0; i < numSteps; i++) {
            double t = 2 * Math.PI * i / numSteps;
            double x = centerX + radioX * Math.cos(t);
            double y = centerY + radioY * Math.sin(t);
            tableModel.addRow(new Object[]{"P" + (i + 1), String.format(String.valueOf(x)), String.format(String.valueOf(y))});
        }
    }

    private void calcularPuntosElipseTrigonometrico(double centerX, double centerY, double radioX, double radioY) {
        int numSteps = 8; // Puedes ajustar este valor según necesites
        for (int i = 0; i < numSteps; i++) {
            double angle = 2 * Math.PI * i / numSteps;
            double x = centerX + radioX * Math.cos(angle);
            double y = centerY + radioY * Math.sin(angle);

            // Calcular el radio efectivo en este punto
            double radioEfectivo = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));

            tableModel.addRow(new Object[]{
                    "P" + (i + 1),
                    String.format(String.valueOf(radioEfectivo)),
                    String.format(String.valueOf(Math.toDegrees(angle)))
            });
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

                tableModel.addRow(new Object[]{"P" + (i + 1), (Object) xRounded, (Object) yRounded});
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

                tableModel.addRow(new Object[]{"P" + (i + 1), (Object) radio, (Object) Math.toDegrees(angle)});
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


