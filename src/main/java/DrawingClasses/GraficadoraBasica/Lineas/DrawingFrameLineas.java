package DrawingClasses.GraficadoraBasica.Lineas;


import PaginaPrincipalFolder.GraficadoraBasica.CreditosParaFG;
import PaginaPrincipalFolder.GraficadoraBasica.PaginaPrincipal;
import Plano.GraficadoraBasica.PlanoCartesianoLineas;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawingFrameLineas extends JFrame {

    private PlanoCartesianoLineas planoCartesiano;
    private Punto puntoActual;
    private JLabel metodoLabel,titleLabel;
    private JButton clearButton,menuButton,drawPointButton,creditosButton;
    private JButton originDraw, endDraw;
    JPanel optionsPanel;
    JScrollPane scrollPane;
    private JPanel titlePanel,infoPanel;

    private JTable infoTable;
    private DefaultTableModel tableModel;


    private JTextField xInicioField, yInicioField, xFinField, yFinField;

    private JComboBox<String> figurasComboBox;
    private Map<String, List<Punto>> figurasMap = new HashMap<>();

    public DrawingFrameLineas() {
        setTitle("Graficación Básica por Computadora: Figuras Geométricas Simples - LINEAS");
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
        optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));

        drawPointButton = new JButton("Dibujar Punto");




        clearButton = new JButton("Limpiar");

        menuButton = new JButton("Menú");


        planoCartesiano = new PlanoCartesianoLineas();
        planoCartesiano.setPreferredSize(new Dimension(600, 400));

        String[] columnNames = {"Punto", "X", "Y"};
        tableModel = new DefaultTableModel(columnNames, 0);
        infoTable = new JTable(tableModel);

        Font font = new Font("Arial", Font.BOLD, 16);
        infoTable.setFont(new Font("Arial", Font.PLAIN, 14)); // Cell font size

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setFont(font);
        infoTable.getTableHeader().setDefaultRenderer(headerRenderer);
        infoTable.getTableHeader().setFont(font); // Bold headers

        infoTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        infoTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        infoTable.getColumnModel().getColumn(2).setPreferredWidth(50);

        creditosButton = new JButton("Créditos");

        figurasMap = new HashMap<>();
        figurasComboBox = new JComboBox<>();
        figurasComboBox.addActionListener(e -> mostrarPuntosFiguraSeleccionada());

        metodoLabel = new JLabel();
        metodoLabel.setFont(new Font("Arial", Font.BOLD, 14));
    }

    private void configureLayout() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        titlePanel = new JPanel();
        titleLabel = new JLabel("Graficación Básica por Computadora: Figuras Geométricas Simples - LINEAS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titlePanel.add(titleLabel);

        optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        optionsPanel.add(menuButton);
        optionsPanel.add(clearButton);
        optionsPanel.add(creditosButton);

        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(optionsPanel, BorderLayout.CENTER);

        // Agregar componentes al frame
        add(topPanel, BorderLayout.NORTH);
        add(planoCartesiano, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());

        infoPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        JLabel lineaLabel = new JLabel("Tipo de lineas");
        lineaLabel.setFont(new Font("Arial",Font.BOLD,16));
        infoPanel.add(lineaLabel);

        figurasComboBox.addItem("Vertical");
        figurasComboBox.addItem("Horizontal");
        figurasComboBox.addItem("Diagonal");

        infoPanel.add(figurasComboBox);

        JLabel xInicialLabel = new JLabel("X inicial:");
        xInicialLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font size to 16
        xInicialLabel.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        infoPanel.add(xInicialLabel);

        xInicioField = new JTextField(3);
        xInicioField.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        infoPanel.add(xInicioField);

        JLabel yInicialLabel = new JLabel("Y inicial:");
        yInicialLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font size to 16
        yInicialLabel.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        infoPanel.add(yInicialLabel);

        yInicioField = new JTextField(3);
        yInicioField.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        infoPanel.add(yInicioField);

        originDraw = new JButton("Dibujar punto origen");
        infoPanel.add(new JLabel());
        infoPanel.add(originDraw);

        JLabel xFinalLabel = new JLabel("X final:");
        xFinalLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font size to 16
        xFinalLabel.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        infoPanel.add(xFinalLabel);

        xFinField = new JTextField(3);
        xFinField.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        infoPanel.add(xFinField);

        JLabel yFinalLabel = new JLabel("Y final:");
        yFinalLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font size to 16
        yFinalLabel.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        infoPanel.add(yFinalLabel);

        yFinField = new JTextField(3);
        yFinField.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        infoPanel.add(yFinField);

        endDraw = new JButton("Dibujar punto final");
        infoPanel.add(new JLabel());
        infoPanel.add(endDraw);

        rightPanel.add(infoPanel, BorderLayout.NORTH);



        scrollPane = new JScrollPane(infoTable);
        scrollPane.setPreferredSize(new Dimension(300, 400)); // Ajustar el tamaño preferido
        rightPanel.add(scrollPane, BorderLayout.SOUTH);


        add(rightPanel, BorderLayout.EAST);

        figurasComboBox.addActionListener(e -> {
            String selectedType = (String) figurasComboBox.getSelectedItem();
            resetFields();
            enableFields(selectedType);
        });

        // Agregar listeners a los botones de dibujo
        originDraw.addActionListener(e -> handleOriginDraw());
        endDraw.addActionListener(e -> handleEndDraw());

        // Deshabilitar campos finales inicialmente
        xFinField.setEnabled(false);
        yFinField.setEnabled(false);
        endDraw.setEnabled(false);
    }
    private void addActionListeners() {
        drawPointButton.addActionListener(e -> handleDrawPointAction());
        menuButton.addActionListener(e -> {
            dispose();
            new PaginaPrincipal().setVisible(true);
        });
        creditosButton.addActionListener(e -> CreditosParaFG.mostrarCreditos(this));
        clearButton.addActionListener(e -> handlerclear());

    }

    private void resetFields() {
        xInicioField.setText("");
        yInicioField.setText("");
        xFinField.setText("");
        yFinField.setText("");
        xFinField.setEnabled(false);
        yFinField.setEnabled(false);
        endDraw.setEnabled(false);
    }

    private void enableFields(String lineType) {
        xInicioField.setEnabled(true);
        yInicioField.setEnabled(true);
    }

    private void handleOriginDraw() {
        handlerclear();
        try {
            int xInicio = Integer.parseInt(xInicioField.getText());
            int yInicio = Integer.parseInt(yInicioField.getText());

            String lineType = (String) figurasComboBox.getSelectedItem();

            // Habilitar campos finales según el tipo de línea
            switch (lineType) {
                case "Horizontal":
                    xFinField.setEnabled(true);
                    yFinField.setEnabled(false);
                    yFinField.setText(String.valueOf(yInicio)); // Mantener mismo valor Y
                    break;
                case "Vertical":
                    xFinField.setEnabled(false);
                    yFinField.setEnabled(true);
                    xFinField.setText(String.valueOf(xInicio)); // Mantener mismo valor X
                    break;
                case "Diagonal":
                    xFinField.setEnabled(true);
                    yFinField.setEnabled(true);
                    break;
            }

            endDraw.setEnabled(true);

            // Dibujar punto inicial
            puntoActual = new Punto(xInicio, yInicio);
            planoCartesiano.addPunto(puntoActual);
            planoCartesiano.repaint();

            // Actualizar tabla
            tableModel.setRowCount(0);
            tableModel.addRow(new Object[]{"P1", xInicio, yInicio});

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, ingrese valores numéricos válidos para el punto inicial.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleEndDraw() {
        handlerclear();
        try {
            int xInicio = Integer.parseInt(xInicioField.getText());
            int yInicio = Integer.parseInt(yInicioField.getText());
            int xFin = Integer.parseInt(xFinField.getText());
            int yFin = Integer.parseInt(yFinField.getText());

            // Crear puntos
            Punto puntoInicio = new Punto(xInicio, yInicio);
            Punto puntoFin = new Punto(xFin, yFin);

            // Crear y dibujar la línea
            Linea nuevaLinea = new Linea(puntoInicio, puntoFin, false);
            planoCartesiano.repaint();

            // Actualizar la tabla con los puntos intermedios
            List<Punto> puntosIntermedios = calcularPuntosIntermedios(puntoInicio, puntoFin);
            updateTableWithPoints(puntosIntermedios);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, ingrese valores numéricos válidos para el punto final.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void handlerclear(){
        planoCartesiano.clear();
        tableModel.setRowCount(0); // Limpiar la tabla
    }

    private void handleDrawPointAction() {
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






    private void updateTableWithPoints(List<Punto> puntos) {
        tableModel.setRowCount(0);
        int puntoNumero = 1;
        for (Punto punto : puntos) {
            tableModel.addRow(new Object[]{"P" + puntoNumero++, punto.getX(), punto.getY()});
        }
    }

    // Method to calculate intermediate points of a line
// Método para calcular puntos intermedios de una línea
    public List<Punto> calcularPuntosIntermedios(Punto inicio, Punto fin) {
        List<Punto> puntosIntermedios = new ArrayList<>();

        int x1 = (int) inicio.getX();
        int y1 = (int) inicio.getY();
        int x2 = (int) fin.getX();
        int y2 = (int) fin.getY();

        // Determinar si la pendiente es mayor a 1 o menor a -1
        boolean pendientePronunciada = Math.abs(y2 - y1) > Math.abs(x2 - x1);

        // Si la pendiente es pronunciada, intercambiamos x y y
        if (pendientePronunciada) {
            int temp = x1;
            x1 = y1;
            y1 = temp;

            temp = x2;
            x2 = y2;
            y2 = temp;
        }

        // Si el punto inicial está después del final, intercambiarlos
        if (x1 > x2) {
            int temp = x1;
            x1 = x2;
            x2 = temp;

            temp = y1;
            y1 = y2;
            y2 = temp;
        }

        int dx = x2 - x1;
        int dy = Math.abs(y2 - y1);
        int error = dx / 2;

        int y = y1;
        int ystep = (y1 < y2) ? 1 : -1;

        // Iterar sobre cada punto en x
        for (int x = x1; x <= x2; x++) {
            Punto punto;
            if (pendientePronunciada) {
                punto = new Punto(y, x);
            } else {
                punto = new Punto(x, y);
            }
            punto.setNombrePunto("P" + (puntosIntermedios.size() + 1));
            puntosIntermedios.add(punto);

            error -= dy;
            if (error < 0) {
                y += ystep;
                error += dx;
            }
        }

        return puntosIntermedios;
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
            new DrawingFrameLineas();
        });
    }
}

