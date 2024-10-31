package DrawingClasses.GraficadoraBasica.Conicas.Separadas;

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

public class DrawingFrameAngulos extends JFrame {

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
    private JButton creditosButton;
    JLabel titleLabel;

    private JButton menuButton;


    private JComboBox<String> figurasComboBox, metodoComboBox;
    private Map<String, List<Punto>> figurasMap = new HashMap<>();

    private JButton calcularButton; // Add this new field
    private boolean modoTrigonometrico;


    public DrawingFrameAngulos() {
        setTitle("Graficación Básica por Computadora: Figuras Geométricas Simples - CONICAS");
        setSize(1650, 960);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createComponents();


        configureLayout();

        addNumericOnlyFilterToAll(this.getContentPane());

        addActionListeners();

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

        creditosButton = new JButton("Créditos");


        figurasMap = new HashMap<>();
        figurasComboBox = new JComboBox<>();
        figurasComboBox.addActionListener(e -> mostrarPuntosFiguraSeleccionada());


        metodoComboBox = new JComboBox<>();
        // En el método createComponents(), añade:
        metodoLabel = new JLabel();
        metodoLabel.setFont(new Font("Arial", Font.BOLD, 14));


        calcularButton = new JButton("Dibujar figura");

        figurasComboBox.addItem("Arco");

        metodoComboBox.addItem("Polinomial");
        metodoComboBox.addItem("Trigonometrico");
    }

    private void configureLayout() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        titleLabel = new JLabel("Graficación Básica por Computadora: Figuras Geométricas Simples - CONICAS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar el título
        titlePanel.add(titleLabel); // Agregar el JLabel del título al panel

        JLabel circleLabel = new JLabel("Arco");
        circleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        circleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(circleLabel);


        optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        optionsPanel.add(menuButton);
        optionsPanel.add(clearButton);
        optionsPanel.add(creditosButton);

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
        rightPanel.add(scrollPane, BorderLayout.SOUTH);



        add(rightPanel, BorderLayout.EAST);
    }
    private void addActionListeners() {
        creditosButton.addActionListener(e -> CreditosParaFG.mostrarCreditos(this));
        clearButton.addActionListener(e -> handlerclear());
        menuButton.addActionListener(e -> {
            dispose();
            new MenuDeConicas().setVisible(true);
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
            case "Arco":
                addArcoFields(selectedMetodo);
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



    private void addArcoFields(String metodo) {


        if (metodo.equals("Polinomial")) {
            JLabel xOrigenLabel = new JLabel("X origen:");
            JTextField xOrigenField = new JTextField(10);
            infoPanel.add(xOrigenLabel);
            infoPanel.add(xOrigenField);

            JLabel yOrigenLabel = new JLabel("Y origen:");
            JTextField yOrigenField = new JTextField(10);
            infoPanel.add(yOrigenLabel);
            infoPanel.add(yOrigenField);


            JLabel x1Label = new JLabel("X1:");
            JTextField x1Field = new JTextField(10);
            infoPanel.add(x1Label);
            infoPanel.add(x1Field);

            JLabel x2Label = new JLabel("X2:");
            JTextField x2Field = new JTextField(10);
            infoPanel.add(x2Label);
            infoPanel.add(x2Field);

            JLabel radioLabel = new JLabel("Radio:");
            JTextField radioField = new JTextField(10);
            infoPanel.add(radioLabel);
            infoPanel.add(radioField);

            JLabel anguloInicioLabel = new JLabel("Ángulo Inicio (°):");
            JTextField anguloInicioField = new JTextField(10);
            infoPanel.add(anguloInicioLabel);
            infoPanel.add(anguloInicioField);

            JLabel anguloFinLabel = new JLabel("Ángulo Fin (°):");
            JTextField anguloFinField = new JTextField(10);
            infoPanel.add(anguloFinLabel);
            infoPanel.add(anguloFinField);
        } else {
            JLabel xOrigenLabel = new JLabel("X origen:");
            JTextField xOrigenField = new JTextField(10);
            infoPanel.add(xOrigenLabel);
            infoPanel.add(xOrigenField);

            JLabel yOrigenLabel = new JLabel("Y origen:");
            JTextField yOrigenField = new JTextField(10);
            infoPanel.add(yOrigenLabel);
            infoPanel.add(yOrigenField);

            JLabel anguloInicioLabel = new JLabel("Ángulo Inicio (°):");
            JTextField anguloInicioField = new JTextField(10);
            infoPanel.add(anguloInicioLabel);
            infoPanel.add(anguloInicioField);

            JLabel anguloFinLabel = new JLabel("Ángulo Fin (°):");
            JTextField anguloFinField = new JTextField(10);
            infoPanel.add(anguloFinLabel);
            infoPanel.add(anguloFinField);

            JLabel radioLabel = new JLabel("Radio:");
            JTextField radioField = new JTextField(10);
            infoPanel.add(radioLabel);
            infoPanel.add(radioField);
        }
    }
    private void calculateAndDrawFigure() {
        try {
            String selectedFigura = (String) figurasComboBox.getSelectedItem();
            String selectedMetodo = (String) metodoComboBox.getSelectedItem();

            handlerclear();
            JTextField centroXField = null;
            JTextField centroYField = null;

            if (selectedFigura.equals("Arco")) {
                centroXField = (JTextField) getComponentByName(infoPanel, "X origen:");
                centroYField = (JTextField) getComponentByName(infoPanel, "Y origen:");
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
                case "Arco":
                    drawArco(selectedMetodo, puntoInicio);
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

    private void drawArco(String metodo, Punto puntoInicio) {
        JTextField radioField = (JTextField) getComponentByName(infoPanel, "Radio:");
        int radio = Integer.parseInt(radioField.getText());

        if (metodo.equals("Polinomial")) {
            JTextField x1Field = (JTextField) getComponentByName(infoPanel, "X1:");
            JTextField x2Field = (JTextField) getComponentByName(infoPanel, "X2:");

            int x1 = Integer.parseInt(x1Field.getText());
            int x2 = Integer.parseInt(x2Field.getText());

            configurarColumnas(false);
            calcularPuntosArcoPolinomio(puntoInicio.getX(), puntoInicio.getY(), radio, x1, x2);

            Arco nuevoArco = new Arco(puntoInicio, radio, x1, x2);
            planoCartesiano.addArco(nuevoArco);
        } else {
            JTextField xOrigenField = (JTextField) getComponentByName(infoPanel, "X origen:");
            JTextField yOrigenField = (JTextField) getComponentByName(infoPanel, "Y origen:");
            JTextField anguloInicioField = (JTextField) getComponentByName(infoPanel, "Ángulo Inicio (°):");
            JTextField anguloFinField = (JTextField) getComponentByName(infoPanel, "Ángulo Fin (°):");

            int xOrigen = Integer.parseInt(xOrigenField.getText());
            int yOrigen = Integer.parseInt(yOrigenField.getText());
            double anguloInicio = Double.parseDouble(anguloInicioField.getText());
            double anguloFin = Double.parseDouble(anguloFinField.getText());

            configurarColumnas(true);
            calcularPuntosArcoTrigonometrico(xOrigen, yOrigen, radio, anguloInicio, anguloFin);

            Arco nuevoArco = new Arco(new Punto(xOrigen, yOrigen), radio, anguloInicio, anguloFin);
            planoCartesiano.addArco(nuevoArco);
        }

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





    private void calcularPuntosArcoPolinomio(double centerX, double centerY, double radio, double anguloInicio, double anguloFin) {
        int numSteps = 8;
        double startRad = Math.toRadians(anguloInicio);
        double endRad = Math.toRadians(anguloFin);

        boolean esCirculoCompleto = (anguloInicio == 0 && anguloFin == 360);

        for (int i = 0; i < numSteps; i++) {
            double t = startRad + (endRad - startRad) * i / (numSteps - 1);

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

        boolean esCirculoCompleto = (anguloInicio == 0 && anguloFin == 360);

        for (int i = 0; i < numSteps; i++) {
            double angle = startRad + (endRad - startRad) * i / (numSteps - 1);

            if (esCirculoCompleto || (angle >= startRad && angle <= endRad)) {
                double x = centerX + radio * Math.cos(angle);
                double y = centerY + radio * Math.sin(angle);

                int xRounded = (int) Math.round(x);
                int yRounded = (int) Math.round(y);

                tableModel.addRow(new Object[]{"P" + (i + 1), (Object) radio, (Object) Math.toDegrees(angle)});
            }
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
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DrawingFrameAngulos();
        });
    }
}