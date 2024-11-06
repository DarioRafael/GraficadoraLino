package PaginaPrincipalFolder.GraficadoraBasica;

import DrawingClasses.GraficadoraBasica.Conicas.DrawingFrameConicas;
import DrawingClasses.GraficadoraBasica.Lineas.DrawingFrameLineas;
import PaginaPrincipalFolder.Polilineas.PrincipalGraficacionPolilineas;

import javax.swing.*;
import java.awt.*;

public class PaginaPrincipal extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 250);
    private static final Color BUTTON_COLOR = new Color(70, 130, 180);
    private static final Color HOVER_COLOR = new Color(100, 149, 237);
    private static final Color TEXT_COLOR = new Color(25, 25, 25);

    public PaginaPrincipal() {
        setTitle("Página Principal");
        setSize(1280, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Panel superior con banner y título
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);

        // Cargar la imagen del banner
        JLabel bannerLabel = new JLabel(new ImageIcon(getClass().getResource("/images/bannerTec.jpeg")));
        headerPanel.add(bannerLabel, BorderLayout.NORTH);

        // Título
        JLabel titleLabel = new JLabel("Graficación Básica por Computadora: Figuras Geométricas Simples", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Panel central con botones
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        // Botones
        JButton goToLineButton = createStyledButton("Graficadora de Líneas", "Acceso a la graficadora de líneas");
        goToLineButton.addActionListener(e -> {
            MenuDeLineas menuDeLineas = new MenuDeLineas();
            menuDeLineas.setLocationRelativeTo(null);
            menuDeLineas.setExtendedState(JFrame.MAXIMIZED_BOTH);
            menuDeLineas.setVisible(true);
            dispose();
        });

        JButton goToConicsButton = createStyledButton("Graficadora de Cónicas", "Acceso a la graficadora de cónicas");
        goToConicsButton.addActionListener(e -> {
            MenuDeConicas menuDeConicas = new MenuDeConicas();
            menuDeConicas.setLocationRelativeTo(null);
            menuDeConicas.setExtendedState(JFrame.MAXIMIZED_BOTH);
            menuDeConicas.setVisible(true);
            dispose();
        });

        JButton goToPolilineasButton = createStyledButton("Graficadora de Polilineas", "Acceso a la graficadora de polilineas");
        goToPolilineasButton.addActionListener(e -> {
            PrincipalGraficacionPolilineas menuPolilineas = new PrincipalGraficacionPolilineas();
            menuPolilineas.setLocationRelativeTo(null);
            menuPolilineas.setVisible(true);
            menuPolilineas.setExtendedState(JFrame.MAXIMIZED_BOTH);
            dispose();
        });

        JButton creditsButton = createStyledButton("Créditos", "Ver créditos del proyecto");
        creditsButton.addActionListener(e -> CreditosParaFG.mostrarCreditos(this));

        buttonPanel.add(goToLineButton);
        buttonPanel.add(goToConicsButton);
        buttonPanel.add(goToPolilineasButton);
        buttonPanel.add(creditsButton);

        // Panel inferior con créditos
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(BACKGROUND_COLOR);

        JLabel creditosLabel = new JLabel("<html><center>Asesor: ING. José Lino Hernández Omaña<br>Cd. Victoria, Tamaulipas - Septiembre 2024</center></html>", SwingConstants.CENTER);
        creditosLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        creditosLabel.setForeground(new Color(100, 100, 100));
        footerPanel.add(creditosLabel, BorderLayout.CENTER);

        // Agregar panels al panel principal
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String title, String description) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());

        JPanel buttonContent = new JPanel(new GridLayout(2, 1));
        buttonContent.setOpaque(false);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel descLabel = new JLabel(description, SwingConstants.CENTER);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        buttonContent.add(titleLabel);
        buttonContent.add(descLabel);

        button.add(buttonContent, BorderLayout.CENTER);

        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaginaPrincipal().setVisible(true));
    }
}
