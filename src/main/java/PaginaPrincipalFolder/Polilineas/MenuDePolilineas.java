package PaginaPrincipalFolder.Polilineas;

import DrawingClasses.Polilineas.PolilineasDrawMenu;
import PaginaPrincipalFolder.AjustesVentana;
import PaginaPrincipalFolder.GraficadoraBasica.PaginaPrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuDePolilineas extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 250);
    private static final Color BUTTON_COLOR = new Color(70, 130, 180);
    private static final Color HOVER_COLOR = new Color(100, 149, 237);
    private static final Color TEXT_COLOR = new Color(25, 25, 25);

    AjustesVentana ajustesVentana = new AjustesVentana();

    public MenuDePolilineas() {
        setTitle("Graficación Geométrica 2D Básica: Polilíneas");
        setSize(1280, 768);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);

        initializeUI();
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("<html>Graficación Geométrica 2D Básica:<br>Polilíneas</html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JButton polilineasButton = createStyledButton("Polilíneas");
        polilineasButton.addActionListener(e -> {
            this.dispose();
            PolilineasDrawMenu frame2 = new PolilineasDrawMenu();
            frame2.setSize(ajustesVentana.getWindowSize());
            frame2.setLocationRelativeTo(null);
            frame2.drawFiguraAnonimaInternal(frame2.xInicio, frame2.yInicio,1);
            frame2.xInicialFieldNuevo.setText("2");
            frame2.yInicialFieldNuevo.setText("2");
            frame2.aumentoComboBox.setSelectedIndex(0);

            frame2.setVisible(true);
        });

        JButton backButton = createStyledButton("Menú");
        backButton.addActionListener(e -> {
            new PaginaPrincipal().setVisible(true);
            dispose();
        });

        buttonPanel.add(polilineasButton);
        buttonPanel.add(backButton);



        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        this.add(mainPanel);
    }

    private JButton createStyledButton(String title) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());

        button.setOpaque(true);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));




        button.add(titleLabel);


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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MenuDePolilineas principalFrame = new MenuDePolilineas();
            principalFrame.setVisible(true);
        });
    }
}
