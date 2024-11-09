package PaginaPrincipalFolder.GraficadoraBasica;

import DrawingClasses.GraficadoraBasica.Conicas.DrawingFrameConicas;
import DrawingClasses.GraficadoraBasica.Conicas.Separadas.DrawingFrameArcos;
import DrawingClasses.GraficadoraBasica.Conicas.Separadas.DrawingFrameCirculos;
import DrawingClasses.GraficadoraBasica.Conicas.Separadas.DrawingFrameElipse;
import PaginaPrincipalFolder.AjustesVentana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MenuDeConicas extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 250);
    private static final Color BUTTON_COLOR = new Color(70, 130, 180);
    private static final Color HOVER_COLOR = new Color(100, 149, 237);
    private static final Color TEXT_COLOR = new Color(25, 25, 25);
    AjustesVentana ajustesVentana = new AjustesVentana();

    //
    public MenuDeConicas() {
        setTitle("Graficación Básica por Computadora: Figuras Geométricas Simples - CÓNICAS");
        setSize(ajustesVentana.getWindowSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Panel superior con título
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("<html>Graficación Básica por Computadora:<br>Figuras Geométricas Simples - Cónicas</html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Panel central con botones
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 5, 5, 5));

        JButton generalConicsButton = createStyledButton("Graficadora General de Cónicas", false);
        generalConicsButton.addActionListener(e -> {
            dispose();
            new DrawingFrameConicas().setVisible(true);
        });

        JPanel specificConicsPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        specificConicsPanel.setBackground(BACKGROUND_COLOR);

        JButton circleButton = createStyledButton("Círculos", false);
        circleButton.addActionListener(e -> {
            dispose();
            new DrawingFrameCirculos().setVisible(true);
        });

        JButton ellipseButton = createStyledButton("Elipses", false);
        ellipseButton.addActionListener(e -> {
            dispose();
            new DrawingFrameElipse().setVisible(true);
        });

        JButton arcButton = createStyledButton("Arcos", false);
        arcButton.addActionListener(e -> {
            dispose();
            DrawingFrameArcos frame = new DrawingFrameArcos();
            frame.handlerclear();
            frame.setVisible(true);
        });

        specificConicsPanel.add(circleButton);
        specificConicsPanel.add(ellipseButton);
        specificConicsPanel.add(arcButton);

        JButton backButton = createStyledButton("Menú", false);
        backButton.addActionListener(e -> {
            new PaginaPrincipal().setVisible(true);
            dispose();
        });

        buttonPanel.add(generalConicsButton);
        buttonPanel.add(specificConicsPanel);
        buttonPanel.add(backButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
        pack();
        setVisible(true);

        // Ajuste dinámico del tamaño de los componentes al cambiar el tamaño de la ventana
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
        button.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

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

    // Método para ajustar el tamaño de los componentes
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuDeConicas().setVisible(true));
    }
}
