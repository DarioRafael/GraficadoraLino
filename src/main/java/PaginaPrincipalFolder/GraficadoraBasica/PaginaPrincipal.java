package PaginaPrincipalFolder.GraficadoraBasica;

import PaginaPrincipalFolder.AjustesVentana;
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
    private static final int FONT_SIZE = 14;
    AjustesVentana ajustesVentana = new AjustesVentana();


    private JLabel titleLabel;
    private JButton goToLineButton, goToConicsButton, goToPolilineasButton, creditsButton;

    public PaginaPrincipal() {
        setTitle("Página Principal");
        setSize(ajustesVentana.getWindowSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
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
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);

        JLabel institutoLabel = new JLabel("Instituto Tecnológico de Cd. Victoria", SwingConstants.CENTER);
        institutoLabel.setFont(new Font("Segoe UI", Font.BOLD, FONT_SIZE));
        institutoLabel.setForeground(TEXT_COLOR);
        titlePanel.add(institutoLabel, gbc);

        JLabel carreraLabel = new JLabel("Ing. en Sistemas Computacionales", SwingConstants.CENTER);
        carreraLabel.setFont(new Font("Segoe UI", Font.BOLD, FONT_SIZE));
        carreraLabel.setForeground(TEXT_COLOR);
        titlePanel.add(carreraLabel, gbc);

        JLabel materiaLabel = new JLabel("Graficación", SwingConstants.CENTER);
        materiaLabel.setFont(new Font("Segoe UI", Font.BOLD, FONT_SIZE));
        materiaLabel.setForeground(TEXT_COLOR);
        titlePanel.add(materiaLabel, gbc);

        JLabel temaLabel = new JLabel("Graficación Básica por Computadora:", SwingConstants.CENTER);
        temaLabel.setFont(new Font("Segoe UI", Font.BOLD, FONT_SIZE));
        temaLabel.setForeground(TEXT_COLOR);
        titlePanel.add(temaLabel, gbc);

        JLabel figurasLabel = new JLabel("Figuras Geométricas Simples", SwingConstants.CENTER);
        figurasLabel.setFont(new Font("Segoe UI", Font.BOLD, FONT_SIZE));
        figurasLabel.setForeground(TEXT_COLOR);
        titlePanel.add(figurasLabel, gbc);

        headerPanel.add(titlePanel, BorderLayout.CENTER);

        // Panel central con botones
        JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));

        // Botones
        goToLineButton = createStyledButton("Graficadora de Líneas");
        goToConicsButton = createStyledButton("Graficadora de Cónicas");
        goToPolilineasButton = createStyledButton("Graficadora de Polilineas");
        creditsButton = createStyledButton("Créditos");



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

        // Agregar un JScrollPane al panel principal si el tamaño es demasiado pequeño
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Ajustar tamaño de fuente en función del tamaño de la ventana
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustFontSizes();
            }
        });

        // Agregar ActionListeners para los botones
        goToLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuDeLineas menuDeLineas = new MenuDeLineas();
                menuDeLineas.setSize(ajustesVentana.getWindowSize());
                menuDeLineas.setLocationRelativeTo(null);
                menuDeLineas.setVisible(true);
                dispose();
            }
        });

        goToConicsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuDeConicas menuDeConicas = new MenuDeConicas();
                menuDeConicas.setSize(ajustesVentana.getWindowSize());
                menuDeConicas.setLocationRelativeTo(null);
                menuDeConicas.setVisible(true);
                dispose();
            }
        });

        goToPolilineasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuDePolilineas menuPolilineas = new MenuDePolilineas();
                menuPolilineas.setSize(ajustesVentana.getWindowSize());
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

        setVisible(true);
    }

    private JButton createStyledButton(String title) {
        JButton button = new JButton(title);
        button.setFont(new Font("Segoe UI", Font.BOLD, FONT_SIZE));
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
        Dimension size = getSize();
        int newFontSize = Math.max(12, (int) (size.width / 64)); // Ajusta el factor de escala según sea necesario
        Font newFont = new Font("Segoe UI", Font.BOLD, newFontSize);

        // Ajustar la fuente de todos los botones
        goToLineButton.setFont(newFont);
        goToConicsButton.setFont(newFont);
        goToPolilineasButton.setFont(newFont);
        creditsButton.setFont(newFont);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaginaPrincipal().setVisible(true));
    }
}