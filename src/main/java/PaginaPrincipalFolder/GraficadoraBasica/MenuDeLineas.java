package PaginaPrincipalFolder.GraficadoraBasica;

import DrawingClasses.GraficadoraBasica.Lineas.DrawingFrameLineas;
import DrawingClasses.GraficadoraBasica.Lineas.Separadas.DrawingFrameDiagonal;
import DrawingClasses.GraficadoraBasica.Lineas.Separadas.DrawingFrameHorizontal;
import DrawingClasses.GraficadoraBasica.Lineas.Separadas.DrawingFrameVertical;
import PaginaPrincipalFolder.AjustesVentana;
import PaginaPrincipalFolder.GraficadoraBasica.PaginaPrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MenuDeLineas extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 250);
    private static final Color BUTTON_COLOR = new Color(70, 130, 180);
    private static final Color HOVER_COLOR = new Color(100, 149, 237);
    private static final Color TEXT_COLOR = new Color(25, 25, 25);

    private JLabel titleLabel;
    private JButton generalLineButton, horizontalLineButton, verticalLineButton,diagonalLineButton;
    AjustesVentana ajustesVentana = new AjustesVentana();

    public MenuDeLineas() {
        setTitle("Graficación Básica - Líneas");
        setSize(ajustesVentana.getWindowSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 5, 5, 5));

        // Panel superior con título simplificado
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);

        titleLabel = new JLabel("<html>Graficación Básica por Computadora:<br>Figuras Geométricas Simples - Lineas</html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Panel central con botones sin descripciones ni imágenes
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 20, 20));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 5, 5, 5));

        generalLineButton = createStyledButton("Graficadora General de Líneas", false);
        generalLineButton.addActionListener(e -> {
            dispose();
            DrawingFrameLineas frameVen = new DrawingFrameLineas();
            frameVen.setVisible(true);
        });

        JPanel specificLinesPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        specificLinesPanel.setBackground(BACKGROUND_COLOR);

        //<html>Líneas <br>Horizontales</html>
        horizontalLineButton = createStyledButton("<html>Horizontales</html>", false);
        horizontalLineButton.addActionListener(e -> {
            dispose();
            DrawingFrameHorizontal frameVen = new DrawingFrameHorizontal();
            frameVen.setVisible(true);
        });

        verticalLineButton = createStyledButton("<html>Verticales</html>", false);
        verticalLineButton.addActionListener(e -> {
            dispose();
            DrawingFrameVertical frameVen = new DrawingFrameVertical();
            frameVen.setVisible(true);
        });

        diagonalLineButton = createStyledButton("<html>Diagonales</html>", false);
        diagonalLineButton.addActionListener(e -> {
            dispose();
            DrawingFrameDiagonal frameVen = new DrawingFrameDiagonal();
            frameVen.handlerclear();
            frameVen.setVisible(true);
        });

        specificLinesPanel.add(horizontalLineButton);
        specificLinesPanel.add(verticalLineButton);
        specificLinesPanel.add(diagonalLineButton);

        // Botón para volver
        JButton backButton = createStyledButton("Menú", false);
        backButton.addActionListener(e -> {
            new PaginaPrincipal().setVisible(true);
            dispose();
        });

        buttonPanel.add(generalLineButton);
        buttonPanel.add(specificLinesPanel);
        buttonPanel.add(backButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
        //pack();
        setVisible(true);

        // Listener para ajustar los tamaños al cambiar el tamaño de la ventana
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustComponentSizes(mainPanel);
            }
        });
    }

    private JButton createStyledButton(String title, boolean isMainButton) {
        JButton button = new JButton(title);
        button.setFont(new Font("Segoe UI", Font.BOLD, isMainButton ? 24 : 22));
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(isMainButton ? 30 : 20, 30, isMainButton ? 30 : 20, 30));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
            }
        });

        return button;
    }

    // Método para ajustar los tamaños de los componentes según el tamaño de la ventana
    private void adjustComponentSizes(JPanel mainPanel) {
        int width = getWidth();
        int height = getHeight();

        Font titleFont = new Font("Segoe UI", Font.BOLD, Math.min(width / 20, 32));
        Font buttonFont = new Font("Segoe UI", Font.BOLD, Math.min(width / 25, 22));

        Component[] components = mainPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                for (Component subComponent : ((JPanel) component).getComponents()) {
                    if (subComponent instanceof JButton) {
                        subComponent.setFont(buttonFont);
                    } else if (subComponent instanceof JLabel) {
                        ((JLabel) subComponent).setFont(titleFont);
                    }
                }
            }
        }
    }

    private void adjustFontSizes() {
        int width = getWidth();

        // Ajustar el tamaño de la fuente del título
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, width / 40));

        // Ajustar el tamaño de la fuente de los botones
        Font buttonFont = new Font("Segoe UI", Font.BOLD, width / 60);
        generalLineButton.setFont(buttonFont);
        verticalLineButton.setFont(buttonFont);
        horizontalLineButton.setFont(buttonFont);
        diagonalLineButton.setFont(buttonFont);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuDeLineas().setVisible(true));
    }
}
