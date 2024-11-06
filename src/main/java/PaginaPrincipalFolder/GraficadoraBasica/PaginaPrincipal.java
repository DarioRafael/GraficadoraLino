package PaginaPrincipalFolder.GraficadoraBasica;

import DrawingClasses.GraficadoraBasica.Conicas.DrawingFrameConicas;
import DrawingClasses.GraficadoraBasica.Lineas.DrawingFrameLineas;
import PaginaPrincipalFolder.Polilineas.PrincipalGraficacionPolilineas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PaginaPrincipal extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 250);
    private static final Color BUTTON_COLOR = new Color(70, 130, 180);
    private static final Color HOVER_COLOR = new Color(100, 149, 237);
    private static final Color TEXT_COLOR = new Color(25, 25, 25);

    private JLabel titleLabel;
    private JButton goToLineButton, goToConicsButton, goToPolilineasButton, creditsButton;

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

        // Título con salto de línea
        titleLabel = new JLabel("<html>Graficación Básica por Computadora:<br>Figuras Geométricas Simples</html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Panel central con botones
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        // Botones
        goToLineButton = createStyledButton("Graficadora de Líneas");
        goToConicsButton = createStyledButton("Graficadora de Cónicas");
        goToPolilineasButton = createStyledButton("Graficadora de Polilineas");
        creditsButton = createStyledButton("Créditos");

        // Agregar ActionListeners para los botones
        goToLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuDeLineas menuDeLineas = new MenuDeLineas();
                menuDeLineas.setLocationRelativeTo(null);
                menuDeLineas.setExtendedState(JFrame.MAXIMIZED_BOTH);
                menuDeLineas.setVisible(true);
                dispose();
            }
        });

        goToConicsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuDeConicas menuDeConicas = new MenuDeConicas();
                menuDeConicas.setLocationRelativeTo(null);
                menuDeConicas.setExtendedState(JFrame.MAXIMIZED_BOTH);
                menuDeConicas.setVisible(true);
                dispose();
            }
        });

        goToPolilineasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrincipalGraficacionPolilineas menuPolilineas = new PrincipalGraficacionPolilineas();
                menuPolilineas.setLocationRelativeTo(null);
                menuPolilineas.setVisible(true);
                menuPolilineas.setExtendedState(JFrame.MAXIMIZED_BOTH);
                dispose();
            }
        });

        creditsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreditosParaFG menuPolilineas = new CreditosParaFG();
                menuPolilineas.setVisible(true);
                dispose();
            }
        });


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

        // Ajustar tamaño de fuente en función del tamaño de la ventana
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustFontSizes();
            }
        });
    }

    private JButton createStyledButton(String title) {
        JButton button = new JButton(title);
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.BLACK);
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

    private void adjustFontSizes() {
        int width = getWidth();

        // Ajustar el tamaño de la fuente del título
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, width / 40));

        // Ajustar el tamaño de la fuente de los botones
        Font buttonFont = new Font("Segoe UI", Font.BOLD, width / 60);
        goToLineButton.setFont(buttonFont);
        goToConicsButton.setFont(buttonFont);
        goToPolilineasButton.setFont(buttonFont);
        creditsButton.setFont(buttonFont);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaginaPrincipal().setVisible(true));
    }
}