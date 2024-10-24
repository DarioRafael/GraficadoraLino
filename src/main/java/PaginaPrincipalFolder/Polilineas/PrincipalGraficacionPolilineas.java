package PaginaPrincipalFolder.Polilineas;

import DrawingClasses.Polilineas.PolilineasDrawMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PrincipalGraficacionPolilineas extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 250);
    private static final Color BUTTON_COLOR = new Color(70, 130, 180);
    private static final Color HOVER_COLOR = new Color(100, 149, 237);
    private static final Color TEXT_COLOR = new Color(25, 25, 25);

    public PrincipalGraficacionPolilineas() {
        // Configuración del frame
        setTitle("Sistema de Polilíneas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Llamar a la configuración de la interfaz
        initializeUI();
    }

    private void initializeUI() {
        // Panel principal con margen y layout
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Panel superior con título
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("Polilíneas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Panel central con botón de Polilíneas
        JPanel buttonPanel = new JPanel(new GridLayout(1, 1, 20, 20));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        // Crear botón estilizado
        JButton polilineasButton = createStyledButton(
                "Polilíneas",
                "Cartesianas Absolutas, Relativas, Polares Relativas y Absolutas"
        );

        // Acción para el botón
        polilineasButton.addActionListener(e -> {
            this.dispose(); // Cierra la ventana actual
            PolilineasDrawMenu frame2 = new PolilineasDrawMenu(); // Abre la nueva ventana
            frame2.initializeAndDraw(frame2.xInicio, frame2.yInicio);
            frame2.setVisible(true);
        });

        buttonPanel.add(polilineasButton);

        // Panel inferior con créditos
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(BACKGROUND_COLOR);

        JLabel creditosLabel = new JLabel("<html><center>Asesor: ING. José Lino Hernández Omaña<br>Cd. Victoria, Tamaulipas - Septiembre 2024</center></html>", SwingConstants.CENTER);
        creditosLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        creditosLabel.setForeground(new Color(100, 100, 100));
        footerPanel.add(creditosLabel, BorderLayout.CENTER);

        // Agregar todo al panel principal
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Agregar panel al frame
        this.add(mainPanel);
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

        // Efecto hover
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

    // Método main para iniciar la aplicación
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crear e iniciar el frame
        SwingUtilities.invokeLater(() -> {
            PrincipalGraficacionPolilineas principalFrame = new PrincipalGraficacionPolilineas();
            principalFrame.setVisible(true);
        });
    }
}
