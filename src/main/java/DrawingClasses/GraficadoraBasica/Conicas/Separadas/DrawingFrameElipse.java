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

public class DrawingFrameElipse extends JFrame {

    private PlanoCartesianoConicasV planoCartesiano;

    private JButton drawConicasButton;

    private JLabel metodoLabel;
    private JButton clearButton;


    JPanel optionsPanel;
    JScrollPane scrollPane;
    JPanel titlePanel;

    JPanel infoPanel;


    private JTable infoTable;
    private DefaultTableModel tableModel;
    JLabel titleLabel;

    private JButton menuButton;


    private JComboBox<String> figurasComboBox, metodoComboBox;
    private Map<String, List<Punto>> figurasMap = new HashMap<>();

    private JButton calcularButton; // Add this new field
    private boolean modoTrigonometrico;
    AjustesVentana ajustesVentana = new AjustesVentana();


    public DrawingFrameElipse() {
        setTitle("Graficación Básica por Computadora: Figuras Geométricas Simples - CONICAS");
        setSize(ajustesVentana.getWindowSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);

        createComponents();


        configureLayout();

        addNumericOnlyFilterToAll(this.getContentPane());

        addActionListeners();
        calculateAndDrawFigure();


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


        figurasComboBox.addItem("Elipse");


        metodoComboBox.addItem("Polinomial");
        metodoComboBox.addItem("Trigonometrico");
    }

    private void configureLayout() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        titleLabel = new JLabel("Graficación Básica por Computadora:");
        JLabel subtitleLabel = new JLabel("Figuras Geométricas Simples - Cónicas");


        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar el título
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar el título


        titlePanel.add(titleLabel); // Agregar el JLabel del título al panel
        titlePanel.add(subtitleLabel);

        JLabel circleLabel = new JLabel("Elipse");
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

        optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        optionsPanel.add(menuButton);
        optionsPanel.add(clearButton);

        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(optionsPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(planoCartesiano, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());

        infoPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        JLabel lineaLabel = new JLabel("Tipo de conica");
        lineaLabel.setFont(new Font("Arial",Font.BOLD,16));
        infoPanel.add(lineaLabel);


        infoPanel.add(figurasComboBox);

        JLabel metodoLabel = new JLabel("Metodo:");
        metodoLabel.setFont(new Font("Arial",Font.BOLD,16));
        infoPanel.add(metodoLabel);


//

        infoPanel.add(metodoComboBox);


// Add initial fields for circle (default)
        updateFieldsBasedOnSelection();

        infoPanel.add(calcularButton);


        rightPanel.add(infoPanel, BorderLayout.NORTH);



        scrollPane = new JScrollPane(infoTable);
        scrollPane.setPreferredSize(new Dimension(300, 400)); // Ajustar el tamaño preferido
        rightPanel.add(scrollPane, BorderLayout.CENTER);



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

        metodoComboBox.addActionListener(e -> updateFieldsBasedOnSelection());

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
            case "Elipse":
                addElipseFields(selectedMetodo);
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

        // Draw default figure
        calculateAndDrawFigure();
    }

    private void addElipseFields(String metodo) {
        JLabel hLabel = new JLabel("h:");
        JTextField hField = new JTextField("0", 10); // Valor predeterminado
        hField.setName("hField");
        infoPanel.add(hLabel);
        infoPanel.add(hField);

        JLabel kLabel = new JLabel("k:");
        JTextField kField = new JTextField("0", 10); // Valor predeterminado
        kField.setName("kField");
        infoPanel.add(kLabel);
        infoPanel.add(kField);

        JLabel radioXLabel = new JLabel("a (longitud del eje mayor):");
        JTextField radioXField = new JTextField("3", 10); // Valor predeterminado
        radioXField.setName("radioXField");
        infoPanel.add(radioXLabel);
        infoPanel.add(radioXField);

        JLabel radioYLabel = new JLabel("b (longitud del eje menor):");
        JTextField radioYField = new JTextField("2", 10); // Valor predeterminado
        radioYField.setName("radioYField");
        infoPanel.add(radioYLabel);
        infoPanel.add(radioYField);

        if (metodo.equals("Trigonometrico")) {


        }
    }
    private void calculateAndDrawFigure() {
        try {
            String selectedFigura = (String) figurasComboBox.getSelectedItem();
            String selectedMetodo = (String) metodoComboBox.getSelectedItem();

            handlerclear();
            JTextField centroXField = null;
            JTextField centroYField = null;


            if (selectedFigura.equals("Elipse")) {
                centroXField = (JTextField) getComponentByName(infoPanel, "h:");
                centroYField = (JTextField) getComponentByName(infoPanel, "k:");
                if (selectedMetodo.equals("Polinomial")) {
                    desactivarModoTrigonometrico();
                } else {
                    activarModoTrigonometrico();
                }
            }

            if (centroXField == null || centroYField == null) {
                throw new NullPointerException("Centro X or Centro Y field is null");
            }

            int centerX = Integer.parseInt(centroXField.getText());
            int centerY = Integer.parseInt(centroYField.getText());
            Punto puntoInicio = new Punto(centerX, centerY);

            switch (selectedFigura) {

                case "Elipse":
                    drawElipse(selectedMetodo, puntoInicio);
                    break;

            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese valores numéricos válidos.");
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    private Component getComponentByName(Container container, String labelText) {
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

    private void drawElipse(String metodo, Punto puntoInicio) {
        JTextField hField = (JTextField) getComponentByName(infoPanel, "h:");
        JTextField kField = (JTextField) getComponentByName(infoPanel, "k:");
        JTextField radioXField = (JTextField) getComponentByName(infoPanel, "a (longitud del eje mayor):");
        JTextField radioYField = (JTextField) getComponentByName(infoPanel, "b (longitud del eje menor):");

        int h = Integer.parseInt(hField.getText());
        int k = Integer.parseInt(kField.getText());
        int radioX = Integer.parseInt(radioXField.getText());
        int radioY = Integer.parseInt(radioYField.getText());

        if (metodo.equals("Polinomial")) {
            configurarColumnas(false);
            calcularPuntosElipsePolinomio(h, k, radioX, radioY);
        } else {

            configurarColumnas(true);
            calcularPuntosElipseTrigonometrico(h, k, radioX, radioY, 0);
        }

        Elipse nuevaElipse = new Elipse(new Punto(h, k), radioX, radioY);
        planoCartesiano.addElipse(nuevaElipse);
        planoCartesiano.repaint();
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



    private void calcularPuntosElipsePolinomio(double centerX, double centerY, double radioX, double radioY) {
        int numSteps = 8;
        for (int i = 0; i < numSteps; i++) {
            double t = 2 * Math.PI * i / numSteps;
            double x = centerX + radioX * Math.cos(t);
            double y = centerY + radioY * Math.sin(t);
            tableModel.addRow(new Object[]{"P" + (i + 1), String.format(String.valueOf(x)), String.format(String.valueOf(y))});
        }
    }
    private void calcularPuntosElipseTrigonometrico(double centerX, double centerY, double radioX, double radioY, double angulo) {
        int numSteps = 8;
        double anguloRad = Math.toRadians(angulo);
        for (int i = 0; i < numSteps; i++) {
            double angle = 2 * Math.PI * i / numSteps;
            double x = centerX + radioX * Math.cos(angle) * Math.cos(anguloRad) - radioY * Math.sin(angle) * Math.sin(anguloRad);
            double y = centerY + radioX * Math.cos(angle) * Math.sin(anguloRad) + radioY * Math.sin(angle) * Math.cos(anguloRad);

            double radioEfectivo = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));

            tableModel.addRow(new Object[]{
                    "P" + (i + 1),
                    String.format(String.valueOf(radioEfectivo)),
                    String.format(String.valueOf(Math.toDegrees(angle)))
            });
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
            new DrawingFrameElipse();
        });
    }
}