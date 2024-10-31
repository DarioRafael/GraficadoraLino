package PaginaPrincipalFolder.GraficadoraBasica;

import DrawingClasses.GraficadoraBasica.Lineas.DrawingFrameLineas;
import DrawingClasses.GraficadoraBasica.Lineas.Separadas.DrawingFrameDiagonal;
import DrawingClasses.GraficadoraBasica.Lineas.Separadas.DrawingFrameHorizontal;
import DrawingClasses.GraficadoraBasica.Lineas.Separadas.DrawingFrameVertical;
import PaginaPrincipalFolder.GraficadoraBasica.PaginaPrincipal;

import javax.swing.*;
import java.awt.*;

public class MenuDeLineas extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 250);
    private static final Color BUTTON_COLOR = new Color(70, 130, 180);
    private static final Color HOVER_COLOR = new Color(100, 149, 237);
    private static final Color TEXT_COLOR = new Color(25, 25, 25);

    // Método para redimensionar iconos
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    public MenuDeLineas() {
        setTitle("Graficación Básica por Computadora: Figuras Geométricas Simples - LINEAS");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Panel superior con título
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("Graficación Básica por Computadora: Figuras Geométricas Simples - LINEAS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Panel central con botones
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 20, 20));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        // Cargar los iconos
        ImageIcon horizontalIcon = new ImageIcon(getClass().getResource("/images/LineaHorizontal.jpeg")) ;
        ImageIcon verticalIcon = new ImageIcon(getClass().getResource("/images/LineaVertical.jpeg")) ;
        ImageIcon diagonalIcon = new ImageIcon(getClass().getResource("/images/LineaDiagonal.jpeg"));

        // Botones
        JButton generalLineButton = createStyledButton("Graficadora General de Líneas", "Líneas con parámetros generales", null, false);        generalLineButton.addActionListener(e -> {
            dispose();

            DrawingFrameLineas frameVen = new DrawingFrameLineas();
            frameVen.handlerclear();
            frameVen.setVisible(true);
        });

        JPanel specificLinesPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        specificLinesPanel.setBackground(BACKGROUND_COLOR);

        JButton horizontalLineButton = createStyledButton("Líneas Horizontales", "Dibujar líneas horizontales", horizontalIcon, false);
        horizontalLineButton.addActionListener(e -> {
            dispose();

            DrawingFrameHorizontal frameVen = new DrawingFrameHorizontal();
            frameVen.handlerclear();
            frameVen.setVisible(true);
        });

        JButton verticalLineButton = createStyledButton("Líneas Verticales", "Dibujar líneas verticales", verticalIcon, false);
        verticalLineButton.addActionListener(e -> {
            dispose();

            DrawingFrameVertical frameVen = new DrawingFrameVertical();
            frameVen.handlerclear();
            frameVen.setVisible(true);
        });

        JButton diagonalLineButton = createStyledButton("Líneas Diagonales", "Dibujar líneas diagonales", diagonalIcon, false);
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
        JButton backButton = createStyledButton("Volver a Página Principal", "Regresar al menú principal", null, false);
        backButton.addActionListener(e -> {
            new PaginaPrincipal().setVisible(true);
            dispose();
        });

        buttonPanel.add(generalLineButton);
        buttonPanel.add(specificLinesPanel);
        buttonPanel.add(backButton);

        // Agregar panels al panel principal
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
        pack();
        setVisible(true);
    }

    private JButton createStyledButton(String title, String description, ImageIcon icon, boolean isMainButton) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());

        JPanel buttonContent = new JPanel(new GridLayout(icon == null ? 2 : 3, 1));
        buttonContent.setOpaque(false);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, isMainButton ? 24 : 22));

        JLabel descLabel = new JLabel(description, SwingConstants.CENTER);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, isMainButton ? 16 : 15));

        buttonContent.add(titleLabel);
        if (icon != null) {
            // Resize the icon to make it larger
            ImageIcon resizedIcon = resizeIcon(icon, 300, 200);  // Increased size from default
            JLabel iconLabel = new JLabel(resizedIcon, SwingConstants.CENTER);
            buttonContent.add(iconLabel);
        }
        buttonContent.add(descLabel);

        button.add(buttonContent, BorderLayout.CENTER);

        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(isMainButton ? 30 : 20, 30, isMainButton ? 30 : 20, 30));
        button.setPreferredSize(new Dimension(button.getPreferredSize().width, isMainButton ? 300 : 250));  // Increased height

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

    // Add an enhanced resizeIcon method for more control

       public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuDeLineas().setVisible(true));
    }
}