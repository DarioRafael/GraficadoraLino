package DrawingClasses.GraficadoraBasica.Conicas;

import PaginaPrincipalFolder.GraficadoraBasica.CreditosParaFG;
import PaginaPrincipalFolder.GraficadoraBasica.MenuDeConicas;
import PaginaPrincipalFolder.GraficadoraBasica.PaginaPrincipal;

import Plano.GraficadoraBasica.PlanoCartesianoConicasV;
import formasADibujar.Rotacion.*;

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

public class DrawingFrameConicas extends JFrame {

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


    public DrawingFrameConicas() {
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

        figurasComboBox.addItem("Circulo");
        figurasComboBox.addItem("Elipse");
        figurasComboBox.addItem("Arco");

        metodoComboBox.addItem("Polinomial");
        metodoComboBox.addItem("Trigonometrico");
    }

    private void configureLayout() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        titlePanel = new JPanel();
        titleLabel = new JLabel("Graficación Básica por Computadora: Figuras Geométricas Simples - CONICAS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titlePanel.add(titleLabel);

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
            case "Circulo":
                addCircleFields(selectedMetodo);
                break;
            case "Elipse":
                addElipseFields(selectedMetodo);
                break;
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
    private void addElipseFields(String metodo) {
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

        JLabel radioXLabel = new JLabel("a (longitud del eje mayor):");
        JTextField radioXField = new JTextField(10);
        radioXField.setName("radioXField");
        infoPanel.add(radioXLabel);
        infoPanel.add(radioXField);

        JLabel radioYLabel = new JLabel("b (longitud del eje menor):");
        JTextField radioYField = new JTextField(10);
        radioYField.setName("radioYField");
        infoPanel.add(radioYLabel);
        infoPanel.add(radioYField);

        if (metodo.equals("Trigonometrico")) {
//            JLabel anguloLabel = new JLabel("Ángulo:");
//            JTextField anguloField = new JTextField(10);
//            anguloField.setName("anguloField");
//            infoPanel.add(anguloLabel);
//            infoPanel.add(anguloField);
        }
    }

    private void addArcoFields(String metodo) {


        if (metodo.equals("Polinomial")) {
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

            if (selectedFigura.equals("Circulo") && selectedMetodo.equals("Polinomial")) {
                centroXField = (JTextField) getComponentByName(infoPanel, "h:");
                centroYField = (JTextField) getComponentByName(infoPanel, "k:");
                desactivarModoTrigonometrico();
            } else if (selectedFigura.equals("Circulo") && selectedMetodo.equals("Trigonometrico")) {
                centroXField = (JTextField) getComponentByName(infoPanel, "X origen:");
                centroYField = (JTextField) getComponentByName(infoPanel, "Y origen:");
                activarModoTrigonometrico();
            } else if (selectedFigura.equals("Elipse")) {
                centroXField = (JTextField) getComponentByName(infoPanel, "h:");
                centroYField = (JTextField) getComponentByName(infoPanel, "k:");
                if (selectedMetodo.equals("Polinomial")) {
                    desactivarModoTrigonometrico();
                } else {
                    activarModoTrigonometrico();
                }
            } else if (selectedFigura.equals("Arco")) {
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
                case "Circulo":
                    drawCircle(selectedMetodo, puntoInicio);
                    break;
                case "Elipse":
                    drawElipse(selectedMetodo, puntoInicio);
                    break;
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
            JTextField anguloField = (JTextField) getComponentByName(infoPanel, "Ángulo:");
            double angulo = Double.parseDouble(anguloField.getText());
            configurarColumnas(true);
            calcularPuntosElipseTrigonometrico(h, k, radioX, radioY, angulo);
        }

        Elipse nuevaElipse = new Elipse(new Punto(h, k), radioX, radioY);
        planoCartesiano.addElipse(nuevaElipse);
        planoCartesiano.repaint();
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



    private void updateInfoPanel(String tipoFigura, Map<String, String> datos) {
        infoPanel.removeAll();
        infoPanel.setLayout(new GridLayout(0, 2, 5, 5)); // Rows, 2 columns, gaps


        if (tipoFigura.contains("circulo") || tipoFigura.contains("elipse") ||
                tipoFigura.contains("arco")) {
            infoPanel.add(new JLabel("Método:"));
            String metodo = tipoFigura.contains("Trigonometrico") ? "Trigonométrico" : "Polinomial";
            metodoLabel.setText("<html><b>" + metodo + "</b></html>");
            infoPanel.add(metodoLabel);
        } else if(tipoFigura.contains("figuraAnonimaCartesiana")){
            infoPanel.add(new JLabel("Método:"));
        }

        switch (tipoFigura) {
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
                infoPanel.add(new JLabel("X origen:"));
                JTextField centroXTrigField = new JTextField(datos.get("centroX"), 10);
                infoPanel.add(centroXTrigField);

                infoPanel.add(new JLabel("Y origen:"));
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

        }
        for (Component comp : infoPanel.getComponents()) {
            if (comp instanceof JTextField) {
                addNumericOnlyFilter((JTextField) comp);
            }
        }

        infoPanel.revalidate();
        infoPanel.repaint();
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


    private void calcularPuntosCirculoPolinomio(double centerX, double centerY, double radius) {
        desactivarModoTrigonometrico();
        int numSteps = 8;
        for (int i = 0; i < numSteps; i++) {
            double t = 2 * Math.PI * i / numSteps;
            double x = centerX + radius * Math.cos(t);
            double y = centerY + radius * Math.sin(t);

            tableModel.addRow(new Object[]{"P" + (i + 1), String.format(String.valueOf(x)), String.format(String.valueOf(y))});
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
    }    private void calcularPuntosArcoPolinomio(double centerX, double centerY, double radio, double anguloInicio, double anguloFin) {
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


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DrawingFrameConicas();
        });
    }
}