package DrawingClasses.GraficadoraBasica.Conicas.Separadas;

import PaginaPrincipalFolder.AjustesVentana;
import PaginaPrincipalFolder.GraficadoraBasica.CreditosParaFG;
import PaginaPrincipalFolder.GraficadoraBasica.MenuDeConicas;
import PaginaPrincipalFolder.GraficadoraBasica.PaginaPrincipal;
import Plano.GraficadoraBasica.PlanoCartesianoConicasV;
import formasADibujar.Rotacion.Arco;
import formasADibujar.Rotacion.Circulo;
import formasADibujar.Rotacion.Elipse;
import formasADibujar.Rotacion.Punto;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawingFrameCirculos extends JFrame {

    private PlanoCartesianoConicasV planoCartesiano;

    private JButton drawConicasButton;

    private JLabel metodoLabel;
    private JButton clearButton;


    JPanel optionsPanel;
    JScrollPane scrollPane;
    JPanel titlePanel;

    public JPanel infoPanel;


    private JTable infoTable;
    private DefaultTableModel tableModel;
    JLabel titleLabel;

    private JButton menuButton;


    public JComboBox<String> figurasComboBox, metodoComboBox;
    private Map<String, List<Punto>> figurasMap = new HashMap<>();

    private JButton calcularButton; // Add this new field
    private boolean modoTrigonometrico;
    private JColorChooser colorChooserPuntos;
    private JColorChooser colorChooserFigura;
    private JCheckBox rellenarCirculoCheckBox;
    AjustesVentana ajustesVentana = new AjustesVentana();

    public DrawingFrameCirculos() {
        setTitle("Graficación Básica por Computadora: Figuras Geométricas Simples - CONICAS");
        setSize(ajustesVentana.getWindowSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);

        createComponents();


        configureLayout();

        addNumericOnlyFilterToAll(this.getContentPane());

        addActionListeners();

        inicializarCirculoPredeterminado();
        setVisible(true);
    }

    private void createComponents() {
        optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));

        clearButton = new JButton("Limpiar");
        menuButton = new JButton("Menú");

        planoCartesiano = new PlanoCartesianoConicasV();
        planoCartesiano.setPreferredSize(new Dimension(600, 400));

        String[] columnNames = {"Punto", "X", "Y"};
        tableModel = new DefaultTableModel(columnNames, 0);
        infoTable = new JTable(tableModel);

        Font font = new Font("Arial", Font.BOLD, 16);
        infoTable.setFont(new Font("Arial", Font.PLAIN, 14));

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setFont(font);
        infoTable.getTableHeader().setDefaultRenderer(headerRenderer);
        infoTable.getTableHeader().setFont(font); // Bold headers

        // Adjust column width if necessary
        infoTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        infoTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        infoTable.getColumnModel().getColumn(2).setPreferredWidth(50);


        figurasMap = new HashMap<>();
        figurasComboBox = new JComboBox<>();
        figurasComboBox.addActionListener(e -> mostrarPuntosFiguraSeleccionada());

        metodoComboBox = new JComboBox<>();
        // En el método createComponents(), añade:
        metodoLabel = new JLabel();
        metodoLabel.setFont(new Font("Arial", Font.BOLD, 14));

        calcularButton = new JButton("Dibujar figura");

        figurasComboBox.addItem("Circulo");

        metodoComboBox.addItem("Polinomial");
        metodoComboBox.addItem("Trigonometrico");

        // Inicializar los JColorChoosers
        colorChooserPuntos = new JColorChooser(Color.BLACK); // Color inicial
        colorChooserFigura = new JColorChooser(Color.RED); // Color inicial

        // Crear un panel para los JColorChoosers
        JPanel colorPanel = new JPanel(new GridLayout(2, 1));
        colorPanel.add(new JLabel("Elige color para puntos:"));
        colorPanel.add(colorChooserPuntos);
        colorPanel.add(new JLabel("Elige color para figura:"));
        colorPanel.add(colorChooserFigura);

        // Inicializar el JCheckBox
        rellenarCirculoCheckBox = new JCheckBox("Rellenar círculo");

        // Agregar el panel de colores a las opciones
        optionsPanel.add(colorPanel); // Agregar el panel de colores al optionsPanel
    }

    private void configureLayout() {
        setLayout(new BorderLayout());

        // Panel superior
        JPanel topPanel = new JPanel(new BorderLayout());

        // Panel del título
        titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        // Existing code
        titleLabel = new JLabel("Graficación Básica por Computadora:");
        JLabel subtitleLabel = new JLabel("Figuras Geométricas Simples - Cónicas");


        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar el título


        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar el título


        titlePanel.add(titleLabel); // Agregar el JLabel del título al panel
        titlePanel.add(subtitleLabel);

        JLabel circleLabel = new JLabel("Círculos");
        circleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        circleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(circleLabel);

// New code for method label
        JLabel methodLabel = new JLabel("Método: Polinomial"); // Default method
        methodLabel.setFont(new Font("Arial", Font.BOLD, 20));
        methodLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(methodLabel);

// Update methodLabel when the method changes
        metodoComboBox.addActionListener(e -> {
            String selectedMethod = (String) metodoComboBox.getSelectedItem();
            methodLabel.setText("Método: " + selectedMethod);
        });

        // Panel de opciones
        optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        optionsPanel.add(menuButton);
        optionsPanel.add(clearButton);

        // Añadiendo los panels al panel superior
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(optionsPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(planoCartesiano, BorderLayout.CENTER);

        // Panel derecho
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Panel de información
        infoPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        JLabel lineaLabel = new JLabel("Tipo de cónica");
        lineaLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoPanel.add(lineaLabel);
        infoPanel.add(figurasComboBox); // Añadir el JComboBox para figuras

        JLabel metodoLabel = new JLabel("Método:");
        metodoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoPanel.add(metodoLabel);
        infoPanel.add(metodoComboBox); // Añadir el JComboBox para métodos

        // Agregar campos iniciales para círculo (predeterminado)
        updateFieldsBasedOnSelection();

        infoPanel.add(calcularButton); // Botón para calcular

        rightPanel.add(infoPanel, BorderLayout.NORTH); // Añadir panel de información al panel derecho

        // ScrollPane para la tabla
        scrollPane = new JScrollPane(infoTable);
        scrollPane.setPreferredSize(new Dimension(300, 400)); // Ajustar el tamaño preferido
        rightPanel.add(scrollPane, BorderLayout.CENTER); // Añadir ScrollPane al panel derecho

        // Agregar el panel derecho al layout
        add(rightPanel, BorderLayout.EAST);
    }

    private void addActionListeners() {
        clearButton.addActionListener(e -> handlerclear());
        menuButton.addActionListener(e -> {
            dispose();
            MenuDeConicas menuDeConicas = new MenuDeConicas();
            menuDeConicas.setSize(ajustesVentana.getWindowSize());
            menuDeConicas.setLocationRelativeTo(null); // Centra la ventana en pantalla
            //menuDeConicas.setExtendedState(JFrame.MAXIMIZED_BOTH);
            menuDeConicas.setVisible(true);
            dispose(); // Cierra la ventana actual
        });
        figurasComboBox.addActionListener(e -> updateFieldsBasedOnSelection());

        metodoComboBox.addActionListener(e -> {
            updateFieldsBasedOnSelection();
            inicializarCirculoPredeterminado();
        });

        calcularButton.addActionListener(e -> calculateAndDrawFigure());


    }
    // Método para activar el modo trigonométrico
    public void activarModoTrigonometrico() {
        planoCartesiano.isTrigonometrico = true;
    }
    public void desactivarModoTrigonometrico() {
        planoCartesiano.isTrigonometrico = false;
    }



    private void updateFieldsBasedOnSelection() {
        String selectedFigura = (String) figurasComboBox.getSelectedItem();
        String selectedMetodo = (String) metodoComboBox.getSelectedItem();

        // Clear previous fields
        infoPanel.removeAll();

        // Re-add combo boxes
        JLabel lineaLabel = new JLabel("Tipo de conica");
        lineaLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoPanel.add(lineaLabel);
        infoPanel.add(figurasComboBox);

        JLabel metodoLabel = new JLabel("Metodo:");
        metodoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoPanel.add(metodoLabel);
        infoPanel.add(metodoComboBox);

        // Add specific fields based on selection
        switch (selectedFigura) {
            case "Circulo":
                addCircleFields(selectedMetodo);
                break;
        }

        // Add calculate button
        infoPanel.add(calcularButton);

        // Add numeric filters to all text fields
        for (Component comp : infoPanel.getComponents()) {
            if (comp instanceof JTextField) {
                addNumericOnlyFilter((JTextField) comp);
            }
        }

        infoPanel.revalidate();
        infoPanel.repaint();
        handlerclear();
    }

    private void addCircleFields(String metodo) {
        if (metodo.equals("Polinomial")) {
            JLabel hLabel = new JLabel("h:");
            JTextField hField = new JTextField(10);
            hField.setName("hField");
            infoPanel.add(hLabel);
            infoPanel.add(hField);

            JLabel kLabel = new JLabel("k:");
            JTextField kField = new JTextField(10);
            kField.setName("kField");
            infoPanel.add(kLabel);
            infoPanel.add(kField);
        } else {
            JLabel xOrigenLabel = new JLabel("X origen:");
            JTextField xOrigenField = new JTextField(10);
            xOrigenField.setName("xOrigenField");
            infoPanel.add(xOrigenLabel);
            infoPanel.add(xOrigenField);

            JLabel yOrigenLabel = new JLabel("Y origen:");
            JTextField yOrigenField = new JTextField(10);
            yOrigenField.setName("yOrigenField");
            infoPanel.add(yOrigenLabel);
            infoPanel.add(yOrigenField);
        }

        JLabel radioLabel = new JLabel("Radio:");
        JTextField radioField = new JTextField(10);
        radioField.setName("radioField");
        infoPanel.add(radioLabel);
        infoPanel.add(radioField);

        if (metodo.equals("Trigonometrico")) {
//            JLabel anguloLabel = new JLabel("Ángulo:");
//            JTextField anguloField = new JTextField(10);
//            anguloField.setName("anguloField");
//            infoPanel.add(anguloLabel);
//            infoPanel.add(anguloField);
        }
    }

    private void drawCircle(String metodo, Punto puntoInicio) {
        int h, k, radio;
        if (metodo.equals("Polinomial")) {
            JTextField hField = (JTextField) getComponentByName(infoPanel, "h:");
            JTextField kField = (JTextField) getComponentByName(infoPanel, "k:");
            h = Integer.parseInt(hField.getText());
            k = Integer.parseInt(kField.getText());
        } else {
            JTextField xOrigenField = (JTextField) getComponentByName(infoPanel, "X origen:");
            JTextField yOrigenField = (JTextField) getComponentByName(infoPanel, "Y origen:");
            h = Integer.parseInt(xOrigenField.getText());
            k = Integer.parseInt(yOrigenField.getText());
        }

        JTextField radioField = (JTextField) getComponentByName(infoPanel, "Radio:");
        radio = Integer.parseInt(radioField.getText());

        if (metodo.equals("Polinomial")) {
            configurarColumnas(false);
            calcularPuntosCirculoPolinomio(h, k, radio);
        } else {
            configurarColumnas(true);
            calcularPuntosCirculoTrigonometrico(h, k, radio);
        }

        Circulo nuevoCirculo = new Circulo(new Punto(h, k), radio);
        planoCartesiano.addCirculo(nuevoCirculo);
        planoCartesiano.repaint();
    }

    public void calculateAndDrawFigure() {
        try {
            String selectedFigura = (String) figurasComboBox.getSelectedItem();
            String selectedMetodo = (String) metodoComboBox.getSelectedItem();

            handlerclear();
            JTextField centroXField = null;
            JTextField centroYField = null;

            if (selectedFigura.equals("Circulo") && selectedMetodo.equals("Polinomial")) {
                centroXField = (JTextField) getComponentByName(infoPanel, "h:");
                centroYField = (JTextField) getComponentByName(infoPanel, "k:");
                desactivarModoTrigonometrico();
            } else if (selectedFigura.equals("Circulo") && selectedMetodo.equals("Trigonometrico")) {
                centroXField = (JTextField) getComponentByName(infoPanel, "X origen:");
                centroYField = (JTextField) getComponentByName(infoPanel, "Y origen:");
                activarModoTrigonometrico();
            }

            if (centroXField == null || centroYField == null) {
                throw new NullPointerException("Centro X or Centro Y field is null");
            }

            int centerX = Integer.parseInt(centroXField.getText());
            int centerY = Integer.parseInt(centroYField.getText());
            Punto puntoInicio = new Punto(centerX, centerY);

            switch (selectedFigura) {
                case "Circulo":
                    drawCircle(selectedMetodo, puntoInicio);
                    break;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese valores numéricos válidos.");
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    public Component getComponentByName(Container container, String labelText) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JLabel && ((JLabel) comp).getText().equals(labelText)) {
                int index = Arrays.asList(container.getComponents()).indexOf(comp);
                if (index + 1 < container.getComponents().length) {
                    return container.getComponents()[index + 1];
                }
            }
        }
        return null;
    }


    public void handlerclear(){
        planoCartesiano.clear();
        tableModel.setRowCount(0);
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

    private void inicializarCirculoPredeterminado() {
        // Valores predeterminados
        int hPredeterminado = 0;
        int kPredeterminado = 0;
        int radioPredeterminado = 2;

        // Actualizar campos según el método seleccionado
        String metodoSeleccionado = (String) metodoComboBox.getSelectedItem();

        if (metodoSeleccionado.equals("Polinomial")) {
            // Obtener referencias a los campos
            JTextField hField = (JTextField) getComponentByName(infoPanel, "h:");
            JTextField kField = (JTextField) getComponentByName(infoPanel, "k:");
            JTextField radioField = (JTextField) getComponentByName(infoPanel, "Radio:");

            // Establecer valores predeterminados
            if (hField != null) hField.setText(String.valueOf(hPredeterminado));
            if (kField != null) kField.setText(String.valueOf(kPredeterminado));
            if (radioField != null) radioField.setText(String.valueOf(radioPredeterminado));

            // Dibujar el círculo predeterminado
            desactivarModoTrigonometrico();
            configurarColumnas(false);
            calcularPuntosCirculoPolinomio(hPredeterminado, kPredeterminado, radioPredeterminado);

            // Crear y añadir el círculo al plano
            Circulo circuloPredeterminado = new Circulo(new Punto(hPredeterminado, kPredeterminado), radioPredeterminado);
            planoCartesiano.addCirculo(circuloPredeterminado);

        } else if (metodoSeleccionado.equals("Trigonometrico")) {
            // Obtener referencias a los campos
            JTextField xOrigenField = (JTextField) getComponentByName(infoPanel, "X origen:");
            JTextField yOrigenField = (JTextField) getComponentByName(infoPanel, "Y origen:");
            JTextField radioField = (JTextField) getComponentByName(infoPanel, "Radio:");

            // Establecer valores predeterminados
            if (xOrigenField != null) xOrigenField.setText(String.valueOf(hPredeterminado));
            if (yOrigenField != null) yOrigenField.setText(String.valueOf(kPredeterminado));
            if (radioField != null) radioField.setText(String.valueOf(radioPredeterminado));

            // Dibujar el círculo predeterminado
            activarModoTrigonometrico();
            configurarColumnas(true);
            calcularPuntosCirculoTrigonometrico(hPredeterminado, kPredeterminado, radioPredeterminado);

            // Crear y añadir el círculo al plano
            Circulo circuloPredeterminado = new Circulo(new Punto(hPredeterminado, kPredeterminado), radioPredeterminado);
            planoCartesiano.addCirculo(circuloPredeterminado);
        }

        planoCartesiano.repaint();
    }

    private void calcularPuntosCirculoPolinomio(double centerX, double centerY, double radius) {
        desactivarModoTrigonometrico();
        int numSteps = 8;
        for (int i = 0; i < numSteps; i++) {
            double t = 2 * Math.PI * i / numSteps;
            double x = centerX + radius * Math.cos(t);
            double y = centerY + radius * Math.sin(t);

            // Redondear solo los puntos P3, P5 y P7
            if (i == 2 || i == 4 || i == 6) {
                x = Math.round(x * 100.0) / 100.0; // Redondear a dos decimales
                y = Math.round(y * 100.0) / 100.0;
            }

            tableModel.addRow(new Object[]{"P" + (i + 1), String.valueOf(x), String.valueOf(y)});
        }
    }

    private void calcularPuntosCirculoTrigonometrico(double centerX, double centerY, double radius) {
        activarModoTrigonometrico();
        int numSteps = 8;
        for (int i = 0; i < numSteps; i++) {
            double angle = 2 * Math.PI * i / numSteps;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            //puntosFigura.add(new Point2D.Double(x, y));
            tableModel.addRow(new Object[]{"P" + (i + 1), String.format(String.valueOf(radius)), String.format(String.valueOf(Math.toDegrees(angle)))});
        }
    }

    private void configurarColumnas(boolean esTrigonometria) {
        String[] columnNames;
        if (esTrigonometria) {
            columnNames = new String[]{"Punto", "r", "Ángulo (°)"};
        } else {
            columnNames = new String[]{"Punto", "X", "Y"};
        }


        tableModel.setColumnCount(0);
        for (String columnName : columnNames) {
            tableModel.addColumn(columnName);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DrawingFrameCirculos();
        });
    }
}