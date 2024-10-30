package DrawingClasses.GraficadoraBasica.Lineas;


import PaginaPrincipalFolder.GraficadoraBasica.PaginaPrincipal;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawingFrameLineas extends JFrame {

    private PlanoCartesiano planoCartesiano;
    private Punto puntoActual;

    private JButton drawPointButton;

    private JButton drawLineButton;
    private JPopupMenu lineTypeMenu;


    private JLabel metodoLabel;
    private JButton clearButton;

    private JButton menuButton; // Nuevo botón para el menú
    private JPopupMenu popupMenu; // Menú emergente

    JPanel optionsPanel;
    JScrollPane scrollPane;
    JPanel titlePanel;

    JPanel infoPanel;


    private JTable infoTable;
    private DefaultTableModel tableModel;
    private JButton creditosButton;
    JLabel titleLabel;



    private JComboBox<String> figurasComboBox;
    private Map<String, List<Punto>> figurasMap = new HashMap<>();

    private JButton otrosButton;
    private JPopupMenu otrosMenu;



    // Constructor de la ventana
    public DrawingFrameLineas() {
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


        clearButton = new JButton("Limpiar");

        // Create the menu button and popup menu
        menuButton = new JButton("Menú");


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
        optionsPanel.add(menuButton);
        optionsPanel.add(drawLineButton);
        optionsPanel.add(otrosButton);
        optionsPanel.add(clearButton);
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



        scrollPane = new JScrollPane(infoTable);
        scrollPane.setPreferredSize(new Dimension(300, 400)); // Ajustar el tamaño preferido
        rightPanel.add(scrollPane, BorderLayout.SOUTH);



        add(rightPanel, BorderLayout.EAST);
    }
    private void addActionListeners() {
        drawPointButton.addActionListener(e -> handleDrawPointAction());
        drawLineButton.addActionListener(e -> lineTypeMenu.show(drawLineButton, 0, drawLineButton.getHeight()));
        menuButton.addActionListener(e -> {
            dispose();
            new PaginaPrincipal().setVisible(true);
        });
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

    private void handlerclear(){
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

                    List<Punto> puntosLinea = calcularPuntosIntermedios(puntoInicio, puntoFin);


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


    private void configurarColumnas(boolean esTrigonometria) {
        String[] columnNames;

            columnNames = new String[]{"Punto", "X", "Y"}; // Nombres por defecto



        tableModel.setColumnCount(0); // Limpiar columnas anteriores
        for (String columnName : columnNames) {
            tableModel.addColumn(columnName); // Agregar nuevas columnas
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DrawingFrameLineas();
        });
    }
}


