package PaginaPrincipalFolder.GraficadoraBasica;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreditosParaFG extends JFrame {

    public CreditosParaFG() {
        // Configuración del JFrame
        setTitle("Créditos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Tamaño adaptable a la pantalla
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza en pantallas grandes

        // Crear el panel principal con GridBagLayout
        JPanel creditPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado
        creditPanel.setBackground(Color.WHITE);

        // Estilo de fuente
        Font titleFont = new Font("Segoe UI", Font.BOLD, 22);
        Font headerFont = new Font("Segoe UI", Font.BOLD, 20);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 18);

        // Título
        JLabel tituloLabel = new JLabel("Software Utilizado para Crear Figuras Geométricas", SwingConstants.CENTER);
        tituloLabel.setFont(titleFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        creditPanel.add(tituloLabel, gbc);

        // Logos
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE);
        ImageIcon javaIcon = new ImageIcon(getClass().getResource("/images/Java.png"));
        ImageIcon netbeansIcon = new ImageIcon(getClass().getResource("/images/netbeans.png"));
        logoPanel.add(new JLabel(javaIcon));
        logoPanel.add(new JLabel(netbeansIcon));
        gbc.gridy = 1;
        creditPanel.add(logoPanel, gbc);

        // Integrantes del equipo
        JLabel integrantesLabel = new JLabel("INTEGRANTES DEL EQUIPO:", SwingConstants.CENTER);
        integrantesLabel.setFont(headerFont);
        gbc.gridy = 2;
        creditPanel.add(integrantesLabel, gbc);

        JTextArea integrantesText = createTextArea(
                "22380382 --- Dario Rafael García Bárcenas\n22380426 --- Juan Carlos Torres Reyna",
                textFont, creditPanel.getBackground());
        JScrollPane integrantesScroll = new JScrollPane(integrantesText);
        gbc.gridy = 3;
        creditPanel.add(integrantesScroll, gbc);

        // Copyright
        JLabel copyrightLabel = new JLabel("Copyright:", SwingConstants.CENTER);
        copyrightLabel.setFont(headerFont);
        gbc.gridy = 4;
        creditPanel.add(copyrightLabel, gbc);

        JTextArea copyrightText = createTextArea(
                "Este software es una obra intelectual desarrollada por alumnos de la carrera de Ing. en Sistemas Computacionales del Instituto Tecnológico de Ciudad Victoria...",
                textFont, creditPanel.getBackground());
        JScrollPane copyrightScroll = new JScrollPane(copyrightText);
        gbc.gridy = 5;
        creditPanel.add(copyrightScroll, gbc);

        // Fila de iconos
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
        gbc.gridy = 6;
        creditPanel.add(iconPanel, gbc);

        // Requerimientos de Hardware
        JLabel hardwareReqLabel = new JLabel("REQUERIMIENTOS DEL HARDWARE:", SwingConstants.CENTER);
        hardwareReqLabel.setFont(headerFont);
        gbc.gridy = 7;
        creditPanel.add(hardwareReqLabel, gbc);

        JTextArea hardwareReq = createTextArea(
                "- Procesador: Pentium II 600 MHz.\n- Disco Duro: Mb espacio ocupado...\n",
                textFont, creditPanel.getBackground());
        JScrollPane hardwareReqScroll = new JScrollPane(hardwareReq);
        gbc.gridy = 8;
        creditPanel.add(hardwareReqScroll, gbc);

        // Requerimientos de Software
        JLabel softwareReqLabel = new JLabel("REQUERIMIENTOS DEL SOFTWARE:", SwingConstants.CENTER);
        softwareReqLabel.setFont(headerFont);
        gbc.gridy = 9;
        creditPanel.add(softwareReqLabel, gbc);

        JTextArea softwareReq = createTextArea(
                "- Sistema Operativo: Windows XP, Vista, Windows 7, Windows 8, Windows 10, Windows 11\n" +
                        "- Lenguaje de Programación: Java (NetBeans)",
                textFont, creditPanel.getBackground());
        JScrollPane softwareReqScroll = new JScrollPane(softwareReq);
        gbc.gridy = 10;
        creditPanel.add(softwareReqScroll, gbc);

        // Ejecución de las figuras geométricas
        JLabel ejecucionLabel = new JLabel("EJECUCIÓN DE LAS FIGURAS GEOMÉTRICAS:", SwingConstants.CENTER);
        ejecucionLabel.setFont(headerFont);
        gbc.gridy = 11;
        creditPanel.add(ejecucionLabel, gbc);

        JTextArea ejecucionText = createTextArea(
                "- Insertar la USB con el archivo.\n- Ejecutar el Programa: FiGeo.exe",
                textFont, creditPanel.getBackground());
        JScrollPane ejecucionScroll = new JScrollPane(ejecucionText);
        gbc.gridy = 12;
        creditPanel.add(ejecucionScroll, gbc);

        // Botón de regreso al menú
        JButton volverMenuButton = new JButton("Menú");
        volverMenuButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        volverMenuButton.addActionListener(e -> {
            PaginaPrincipal menu = new PaginaPrincipal();
            menu.setVisible(true);
            dispose();
        });
        gbc.gridy = 13;
        creditPanel.add(volverMenuButton, gbc);

        // Scroll principal para el panel de créditos
        JScrollPane scrollPane = new JScrollPane(creditPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);
    }

    private JTextArea createTextArea(String text, Font font, Color bg) {
        JTextArea textArea = new JTextArea(text);
        textArea.setFont(font);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setBackground(bg);
        return textArea;
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
