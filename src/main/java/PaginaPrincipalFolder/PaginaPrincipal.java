package PaginaPrincipalFolder;

import DrawingClasses.DrawingFrame;
import DrawingClasses.FiguraAnonimaDrawMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaginaPrincipal extends JFrame {

    public PaginaPrincipal() {
        setTitle("Página Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        // Diseño general: Usaremos un BorderLayout para organizar los elementos
        setLayout(new BorderLayout());

        // Panel superior: Aquí irá el título y la imagen (si decides usarla)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.decode("#f0f0f0")); // Color de fondo gris claro
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen interno

        // Título
        JLabel titleLabel = new JLabel("Graficación Básica por Computadora");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Fuente más grande y en negrita
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(titleLabel, BorderLayout.NORTH);

        // Imagen (opcional): Si decides usar la imagen, descomenta estas líneas
        /*
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/inicio.jpeg")); // Asegúrate de tener la imagen en la carpeta "images"
        Image image = imageIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH); // Redimensionar la imagen
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(imageLabel, BorderLayout.CENTER);
        */

        add(topPanel, BorderLayout.NORTH); // Añadir el panel superior a la ventana

        // Panel central: Aquí irán los botones principales
        JPanel centerPanel = new JPanel(new GridBagLayout()); // GridBagLayout para centrar los botones
        centerPanel.setBackground(Color.decode("#f0f0f0"));

        // Botón para ir a DrawingFrame
        JButton goToDrawingButton = new JButton("Graficadora Universal");
        goToDrawingButton.setFont(new Font("Arial", Font.BOLD, 16));
        goToDrawingButton.setBackground(Color.decode("#007bff")); // Color de fondo azul
        goToDrawingButton.setForeground(Color.WHITE); // Texto en blanco
        goToDrawingButton.setFocusPainted(false); // Quitar el borde feo al hacer clic
        goToDrawingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DrawingFrame().setVisible(true);
                dispose();
            }
        });
//
        // Botón para ir a FiguraAnonimaDrawMenu
        JButton goToFiguraAnonimaButton = new JButton("Figura de 8 puntos");
        goToFiguraAnonimaButton.setFont(new Font("Arial", Font.BOLD, 16));
        goToFiguraAnonimaButton.setBackground(Color.decode("#28a745")); // Color de fondo verde
        goToFiguraAnonimaButton.setForeground(Color.WHITE); // Texto en blanco
        goToFiguraAnonimaButton.setFocusPainted(false); // Quitar el borde feo al hacer clic
        goToFiguraAnonimaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FiguraAnonimaDrawMenu frame = new FiguraAnonimaDrawMenu();
                frame.initializeAndDraw(frame.xInicio, frame.yInicio);
                frame.setVisible(true);
                dispose();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0); // Margen superior e inferior
        centerPanel.add(goToDrawingButton, gbc);

        gbc.gridy = 1; // Cambiar la posición para el segundo botón
        centerPanel.add(goToFiguraAnonimaButton, gbc);

        add(centerPanel, BorderLayout.CENTER); // Añadir el panel central a la ventana

        // Panel inferior: Información del asesor y ubicación
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
        bottomPanel.setBackground(Color.decode("#f0f0f0"));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        JLabel nameLabel = new JLabel("Asesor: ING. José Lino Hernandez Omaña");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel locationLabel = new JLabel("Cd Victoria, Tamaulipas Septiembre 2024");
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        locationLabel.setHorizontalAlignment(JLabel.CENTER);

        bottomPanel.add(nameLabel);
        bottomPanel.add(locationLabel);

        add(bottomPanel, BorderLayout.SOUTH); // Añadir el panel inferior a la ventana

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaginaPrincipal().setVisible(true));
    }
}