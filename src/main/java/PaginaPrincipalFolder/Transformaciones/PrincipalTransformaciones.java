package PaginaPrincipalFolder.Transformaciones;

import PaginaPrincipalFolder.AjustesVentana;
import PaginaPrincipalFolder.Transformaciones.Componentes.TransformacionesBasicas;
import PaginaPrincipalFolder.Transformaciones.Componentes.TransformacionesCompuestas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PrincipalTransformaciones {
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 250);
    private static final Color BUTTON_COLOR = new Color(70, 130, 180);
    private static final Color HOVER_COLOR = new Color(100, 149, 237);
    private static final Color TEXT_COLOR = new Color(25, 25, 25);
    static AjustesVentana ajustesVentana = new AjustesVentana();

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Transformaciones Geométricas en 2D");
        frame.setSize(ajustesVentana.getWindowSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Panel superior con título
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("Transformaciones Geométricas en 2D ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Panel central con botones
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        // Creación de botones personalizados
        JButton basicasButton = createStyledButton("Transformaciones Geométricas 2D Básicas", "Traslación, Escalación y Rotación");
        JButton compuestasButton = createStyledButton("Transformaciones Geométricas 2D Compuestas", "Transformaciones sucesivas");

        basicasButton.addActionListener(e -> {
            frame.dispose();
            new TransformacionesBasicas();
        });

        compuestasButton.addActionListener(e -> {
            frame.dispose();
            new TransformacionesCompuestas();
        });

        buttonPanel.add(basicasButton);
        buttonPanel.add(compuestasButton);

        // Panel inferior con créditos
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(BACKGROUND_COLOR);

        JLabel creditosLabel = new JLabel("<html><center>Asesor: ING. José Lino Hernández Omaña<br>Cd. Victoria, Tamaulipas - Septiembre 2024</center></html>", SwingConstants.CENTER);
        creditosLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        creditosLabel.setForeground(new Color(100, 100, 100));
        footerPanel.add(creditosLabel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static JButton createStyledButton(String title, String description) {
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

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(HOVER_COLOR);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
            }
        });

        return button;
    }
}

