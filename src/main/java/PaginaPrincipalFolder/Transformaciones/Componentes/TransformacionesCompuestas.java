package PaginaPrincipalFolder.Transformaciones.Componentes;

import DrawingClasses.Transformaciones.Compuestas.PolilineasEscalacionSuc;
import DrawingClasses.Transformaciones.Compuestas.PolilineasRotacionSuc;
import DrawingClasses.Transformaciones.Compuestas.PolilineasTraslacionSuc;
import PaginaPrincipalFolder.Transformaciones.PrincipalTransformaciones;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TransformacionesCompuestas extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 250);
    private static final Color BUTTON_COLOR = new Color(70, 130, 180);
    private static final Color HOVER_COLOR = new Color(100, 149, 237);
    private static final Color SEPARATOR_COLOR = new Color(200, 200, 220);

    public TransformacionesCompuestas() {
        setTitle("Transformaciones Compuestas en 2D");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Panel superior con título y descripción
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        headerPanel.setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("Transformaciones Compuestas en 2D", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JLabel descLabel = new JLabel("Aplique múltiples transformaciones del mismo tipo en secuencia", SwingConstants.CENTER);
        descLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        descLabel.setForeground(new Color(100, 100, 100));

        headerPanel.add(titleLabel);
        headerPanel.add(descLabel);

        // Panel central con las opciones de transformación
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 15, 15));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        // Crear los botones para cada tipo de transformación sucesiva
        JButton traslacionesBtn = createTransformationButton("Traslaciones Sucesivas",
                "Aplicar múltiples traslaciones en secuencia");

        JButton escalacionesBtn = createTransformationButton("Escalaciones Sucesivas",
                "Aplicar múltiples escalaciones en secuencia");

        JButton rotacionesBtn = createTransformationButton("Rotaciones Sucesivas",
                "Aplicar múltiples rotaciones en secuencia");

        // Agregar los action listeners
        traslacionesBtn.addActionListener(e -> {
            dispose();
            PolilineasTraslacionSuc frame = new PolilineasTraslacionSuc();
            frame.drawFiguraOriginal(2, 2, 2);
            frame.aumentoComboBox.setSelectedIndex(1);
        });

        escalacionesBtn.addActionListener(e -> {
            dispose();
            PolilineasEscalacionSuc frame = new PolilineasEscalacionSuc();
            frame.drawFiguraOriginal(2, 2, 2);
            frame.aumentoComboBox.setSelectedIndex(1);
        });

        rotacionesBtn.addActionListener(e -> {
            dispose();
            PolilineasRotacionSuc frame = new PolilineasRotacionSuc();
            frame.drawFiguraOriginal(2, 2, 2);
            frame.aumentoComboBox.setSelectedIndex(1);
        });

        // Agregar los botones al panel
        buttonPanel.add(traslacionesBtn);
        buttonPanel.add(escalacionesBtn);
        buttonPanel.add(rotacionesBtn);

        // Panel inferior con botón de regreso y nota
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(BACKGROUND_COLOR);

        // Botón de regreso
        JButton backButton = new JButton("← Regresar al Menú Principal");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setForeground(BUTTON_COLOR);
        backButton.setBackground(BACKGROUND_COLOR);
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        backButton.addActionListener(e -> {
            dispose();
            PrincipalTransformaciones.main(new String[]{});
        });

        // Nota informativa
        JLabel noteLabel = new JLabel("Nota: Las transformaciones se aplicarán en el orden que las defina",
                SwingConstants.CENTER);
        noteLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        noteLabel.setForeground(new Color(100, 100, 100));

        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(noteLabel, BorderLayout.SOUTH);

        // Agregar todos los paneles al panel principal
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JButton createTransformationButton(String title, String description) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());

        JPanel content = new JPanel(new GridLayout(2, 1, 5, 5));
        content.setOpaque(false);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel descLabel = new JLabel(description, SwingConstants.CENTER);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        content.add(titleLabel);
        content.add(descLabel);

        button.add(content);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SEPARATOR_COLOR, 1),
                BorderFactory.createEmptyBorder(15, 25, 15, 25)
        ));

        // Efectos hover
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(HOVER_COLOR);
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
            }
        });

        return button;
    }
}