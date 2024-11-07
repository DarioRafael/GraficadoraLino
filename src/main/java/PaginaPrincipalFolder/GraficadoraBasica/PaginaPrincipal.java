package PaginaPrincipalFolder.GraficadoraBasica;

import PaginaPrincipalFolder.Polilineas.MenuDePolilineas;

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
    private static final int letter = 20;


    private JLabel titleLabel;
    private JButton goToLineButton, goToConicsButton, goToPolilineasButton, creditsButton;

    public PaginaPrincipal() {
        setTitle("Página Principal");
        setSize(1280, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Panel superior con banner que ocupa toda la franja superior
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);

        // Cargar la imagen del banner y hacer que ocupe todo el ancho
        JLabel bannerLabel = new JLabel(new ImageIcon(getClass().getResource("/images/bannerTec.jpeg")));
        bannerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(bannerLabel, BorderLayout.NORTH); // Añadir el banner al norte del panel

        // Título con salto de línea debajo del banner
        // Crear las etiquetas separadas
        JLabel institutoLabel = new JLabel("Instituto Tecnológico de Cd. Victoria", SwingConstants.CENTER);
        institutoLabel.setFont(new Font("Segoe UI", Font.BOLD, letter));
        institutoLabel.setForeground(TEXT_COLOR);

        JLabel carreraLabel = new JLabel("Ing. en Sistemas Computacionales", SwingConstants.CENTER);
        carreraLabel.setFont(new Font("Segoe UI", Font.BOLD, letter));
        carreraLabel.setForeground(TEXT_COLOR);

        JLabel materiaLabel = new JLabel("Graficación", SwingConstants.CENTER);
        materiaLabel.setFont(new Font("Segoe UI", Font.BOLD, letter));
        materiaLabel.setForeground(TEXT_COLOR);

        JLabel temaLabel = new JLabel("Graficación Básica por Computadora:", SwingConstants.CENTER);
        temaLabel.setFont(new Font("Segoe UI", Font.BOLD, letter));
        temaLabel.setForeground(TEXT_COLOR);

        JLabel figurasLabel = new JLabel("Figuras Geométricas Simples", SwingConstants.CENTER);
        figurasLabel.setFont(new Font("Segoe UI", Font.BOLD, letter));
        figurasLabel.setForeground(TEXT_COLOR);

// Panel para alinear las etiquetas verticalmente
        // Panel para alinear las etiquetas verticalmente con GridBagLayout
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(BACKGROUND_COLOR);

// Configurar restricciones de GridBag
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE; // Añadir las etiquetas una debajo de otra
        gbc.anchor = GridBagConstraints.CENTER;  // Centrar las etiquetas
        gbc.insets = new Insets(5, 0, 5, 0);   // Espacio entre las etiquetas

// Añadir las etiquetas al panel con las restricciones
        titlePanel.add(institutoLabel, gbc);
        titlePanel.add(carreraLabel, gbc);
        titlePanel.add(materiaLabel, gbc);
        titlePanel.add(temaLabel, gbc);
        titlePanel.add(figurasLabel, gbc);

        headerPanel.add(titlePanel, BorderLayout.CENTER);


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
                menuDeLineas.setSize(1280, 768);
                menuDeLineas.setLocationRelativeTo(null);
                menuDeLineas.setVisible(true);
                dispose();
            }
        });

        goToConicsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuDeConicas menuDeConicas = new MenuDeConicas();
                menuDeConicas.setSize(1280, 768);
                menuDeConicas.setLocationRelativeTo(null);
                menuDeConicas.setVisible(true);
                dispose();
            }
        });

        goToPolilineasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuDePolilineas menuPolilineas = new MenuDePolilineas();
                menuPolilineas.setSize(1280, 768);
                menuPolilineas.setLocationRelativeTo(null);
                menuPolilineas.setVisible(true);
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


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaginaPrincipal().setVisible(true));
    }
}
