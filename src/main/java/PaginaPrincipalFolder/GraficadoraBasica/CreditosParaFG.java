package PaginaPrincipalFolder.GraficadoraBasica;

import PaginaPrincipalFolder.AjustesVentana;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class CreditosParaFG extends JFrame {
    AjustesVentana ajustesVentana = new AjustesVentana();

    public CreditosParaFG() {
        setTitle("Créditos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        // Obtener tamaño de pantalla y establecer pantalla completa

        setSize(ajustesVentana.getWindowSize());
        //setUndecorated(true); // Elimina el borde y título de la ventana
        //setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza la ventana

        // Panel principal con BorderLayout
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botón de "Menú" en la parte superior izquierda
        JButton volverMenuButton = new JButton("Menú");
        volverMenuButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        volverMenuButton.addActionListener(e -> {
            PaginaPrincipal menu = new PaginaPrincipal();
            menu.setVisible(true);
            dispose();
        });
        contentPanel.add(volverMenuButton, BorderLayout.PAGE_START);

        // Panel de contenido con información
        JPanel creditPanel = createCreditPanel();
        JScrollPane scrollPane = new JScrollPane(creditPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        add(contentPanel);
        setLocationRelativeTo(null);
    }

    private JPanel createCreditPanel() {
        JPanel creditPanel = new JPanel(new GridBagLayout());
        creditPanel.setBackground(Color.WHITE);

        // Estilos de fuente
        Font titleFont = new Font("Segoe UI", Font.BOLD, 22);
        Font headerFont = new Font("Segoe UI", Font.BOLD, 20);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 18);

        // Títulos y contenido
        addLabelAndText(creditPanel, "Software Utilizado para Crear Figuras Geométricas", titleFont, 0, 0);
        addLogosPanel(creditPanel, 0, 1);
        addLabelAndText(creditPanel, "INTEGRANTES DEL EQUIPO:", headerFont, 0, 2);
        addTextArea(creditPanel, "22380382 --- Dario Rafael García Bárcenas\n22380426 --- Juan Carlos Torres Reyna", textFont, 0, 3);
        addLabelAndText(creditPanel, "Copyright:", headerFont, 0, 4);
        addTextArea(creditPanel, "Este software es una obra intelectual desarrollada por alumnos de la carrera\n" +
                "de Ing. en Sistemas Computacionales del Instituto Tecnológico de Ciudad\n" +
                "Victoria. Prohibida su reproducción total o parcial sin consentimiento de los\n" +
                "autores. Los autores de este tipo de producto no se hacen responsables por\n" +
                "el uso indebido de esta información. Prohibida su comercialización, ya que es\n" +
                "un software de propósito Educativo.", textFont, 0, 5);
        addIconsPanel(creditPanel, 0, 6);
        addLabelAndText(creditPanel, "REQUERIMIENTOS DEL HARDWARE:", headerFont, 0, 7);
        addTextArea(creditPanel, "- Procesador: Pentium II 600 MHz.\n- Disco Duro: Mb espacio ocupado...\n", textFont, 0, 8);
        addLabelAndText(creditPanel, "REQUERIMIENTOS DEL SOFTWARE:", headerFont, 0, 9);
        addTextArea(creditPanel, "- Sistema Operativo: Windows XP, Vista, Windows 7, Windows 8, Windows 10, Windows 11\n- Lenguaje de Programación: Java (NetBeans)", textFont, 0, 10);
        addLabelAndText(creditPanel, "EJECUCIÓN DE LAS FIGURAS GEOMÉTRICAS:", headerFont, 0, 11);
        addTextArea(creditPanel, "- Insertar la USB con el archivo.\n- Ejecutar el Programa: FiGeo.exe", textFont, 0, 12);

        return creditPanel;
    }

    private void addLabelAndText(JPanel panel, String labelText, Font font, int gridX, int gridY) {
        JLabel label = new JLabel(labelText, SwingConstants.CENTER);
        label.setFont(font);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(label, gbc);
    }

    private void addTextArea(JPanel panel, String text, Font font, int gridX, int gridY) {
        JTextArea textArea = new JTextArea(text);
        textArea.setFont(font);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setFocusable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(scrollPane, gbc);
    }

    private void addLogosPanel(JPanel panel, int gridX, int gridY) {
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE);
        ImageIcon javaIcon = new ImageIcon(getClass().getResource("/images/Java.png"));
        ImageIcon netbeansIcon = new ImageIcon(getClass().getResource("/images/netbeans.png"));
        logoPanel.add(new JLabel(javaIcon));
        logoPanel.add(new JLabel(netbeansIcon));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(logoPanel, gbc);
    }

    private void addIconsPanel(JPanel panel, int gridX, int gridY) {
        JPanel iconPanel = new JPanel();
        iconPanel.setBackground(Color.WHITE);
        ImageIcon icon1 = resizeIcon(new ImageIcon(getClass().getResource("/images/mexico.png")), 100, 50);
        ImageIcon icon2 = resizeIcon(new ImageIcon(getClass().getResource("/images/tec.jpg")), 80, 80);
        ImageIcon icon3 = resizeIcon(new ImageIcon(getClass().getResource("/images/hechoMexico.png")), 80, 80);
        ImageIcon icon4 = resizeIcon(new ImageIcon(getClass().getResource("/images/tamaulipas.png")), 80, 80);
        iconPanel.add(new JLabel(icon1));
        iconPanel.add(new JLabel(icon2));
        iconPanel.add(new JLabel(icon3));
        iconPanel.add(new JLabel(icon4));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(iconPanel, gbc);
    }

    private static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CreditosParaFG frame = new CreditosParaFG();
            frame.setVisible(true);
        });
    }
}