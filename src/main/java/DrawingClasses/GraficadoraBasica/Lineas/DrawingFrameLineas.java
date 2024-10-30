package DrawingClasses.GraficadoraBasica.Lineas;


import PaginaPrincipalFolder.GraficadoraBasica.PaginaPrincipal;
import Plano.GraficadoraBasica.PlanoCartesianoLineas;
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

    private PlanoCartesianoLineas planoCartesiano;
    private Punto puntoActual;
    private JPopupMenu lineTypeMenu;
    private JLabel metodoLabel,titleLabel;
    private JButton clearButton,menuButton,drawPointButton,drawLineButton,creditosButton;
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
        optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));

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
        titleLabel = new JLabel("Graficación Básica por Computadora");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);

        optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        optionsPanel.add(menuButton);
        optionsPanel.add(drawLineButton);
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
            // Añadir el label del método al principio del panel
            infoPanel.add(new JLabel("Método:"));
        }

        switch (tipoFigura) {
            case "linea":

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

    private void handlerclear(){
        planoCartesiano.clear();
        tableModel.setRowCount(0); // Limpiar la tabla
        figurasComboBox.removeAllItems();
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


