package PaginaPrincipalFolder.GraficadoraBasica;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreditosParaFG {

    public CreditosParaFG(){
    }

    public static void mostrarCreditos(Component parent) {
        // Crear el panel principal
        JPanel creditPanel = new JPanel();
        creditPanel.setLayout(new BoxLayout(creditPanel, BoxLayout.Y_AXIS));
        creditPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        creditPanel.setBackground(Color.WHITE);

        // Estilo de fuente
        Font titleFont = new Font("Segoe UI", Font.BOLD, 22);
        Font headerFont = new Font("Segoe UI", Font.BOLD, 20);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 18);

        // Encabezado
        JLabel tituloLabel = new JLabel("Software Utilizado para Crear Figuras Geométricas");
        tituloLabel.setFont(titleFont);
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar título
        creditPanel.add(tituloLabel);

        creditPanel.add(Box.createVerticalStrut(15));

        // Logos (usando texto, reemplaza con imágenes si tienes)
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE);
        logoPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Centrar elementos en logoPanel
        // Asegúrate de que las imágenes existan en la ruta especificada
        ImageIcon javaIcon = new ImageIcon(CreditosParaFG.class.getResource("/images/Java.png"));
        ImageIcon netbeansIcon = new ImageIcon(CreditosParaFG.class.getResource("/images/netbeans.png"));

        JLabel javaLogoLabel = new JLabel("", javaIcon, JLabel.CENTER);  // Etiqueta con texto e imagen
        JLabel netbeansLogoLabel = new JLabel("", netbeansIcon, JLabel.CENTER);  // Etiqueta con texto e imagen

        logoPanel.add(javaLogoLabel);
        logoPanel.add(netbeansLogoLabel);
        creditPanel.add(logoPanel);

        creditPanel.add(Box.createVerticalStrut(15));

        // Integrantes del equipo
        JLabel integrantesLabel = new JLabel("INTEGRANTES DEL EQUIPO:");
        integrantesLabel.setFont(headerFont);
        integrantesLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar encabezado de integrantes
        creditPanel.add(integrantesLabel);

        JTextArea integrantesText = new JTextArea(
                "22380382 --- Dario Rafael García Bárcenas\n" +
                        "22380426 --- Juan Carlos Torres Reyna"
        );
        integrantesText.setFont(textFont);
        integrantesText.setLineWrap(true);
        integrantesText.setWrapStyleWord(true);
        integrantesText.setEditable(false);
        integrantesText.setBackground(creditPanel.getBackground());
        integrantesText.setBorder(new EmptyBorder(5, 10, 5, 10));
        integrantesText.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar texto de integrantes
        creditPanel.add(integrantesText);

        creditPanel.add(Box.createVerticalStrut(15));

        // Copyright
        JLabel copyrightLabel = new JLabel("Copyright:");
        copyrightLabel.setFont(headerFont);
        copyrightLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar encabezado de copyright
        creditPanel.add(copyrightLabel);

        JTextArea copyrightText = new JTextArea(
                "Este software es una obra intelectual desarrollada por alumnos de la carrera de Ing. en Sistemas Computacionales del Instituto Tecnológico de Ciudad Victoria. " +
                        "Prohibida su reproducción total o parcial sin consentimiento de los autores. Los autores de este tipo de producto no se hacen responsables por el uso indebido de esta información. " +
                        "Prohibida su comercialización, ya que es un software de propósito Educativo."
        );
        copyrightText.setFont(textFont);
        copyrightText.setLineWrap(true);
        copyrightText.setWrapStyleWord(true);
        copyrightText.setEditable(false);
        copyrightText.setBackground(creditPanel.getBackground());
        copyrightText.setBorder(new EmptyBorder(5, 10, 5, 10));
        copyrightText.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar texto de copyright
        creditPanel.add(copyrightText);

        creditPanel.add(Box.createVerticalStrut(15));

// Fila de iconos
        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Centrar iconos
        iconPanel.setBackground(Color.WHITE); // Color de fondo para el panel de iconos

// Asegúrate de que las imágenes existan en la ruta especificada
        ImageIcon icon1 = resizeIcon(new ImageIcon(CreditosParaFG.class.getResource("/images/mexico.png")), 100, 50);
        ImageIcon icon2 = resizeIcon(new ImageIcon(CreditosParaFG.class.getResource("/images/tec.jpg")), 80, 80);
        ImageIcon icon3 = resizeIcon(new ImageIcon(CreditosParaFG.class.getResource("/images/hechoMexico.png")), 80, 80);
        ImageIcon icon4 = resizeIcon(new ImageIcon(CreditosParaFG.class.getResource("/images/tamaulipas.png")), 80, 80);

        JLabel iconLabel1 = new JLabel(icon1);
        JLabel iconLabel2 = new JLabel(icon2);
        JLabel iconLabel3 = new JLabel(icon3);
        JLabel iconLabel4 = new JLabel(icon4);

        iconPanel.add(iconLabel1);
        iconPanel.add(iconLabel2);
        iconPanel.add(iconLabel3);
        iconPanel.add(iconLabel4);

        creditPanel.add(iconPanel);


        // Requerimientos de Hardware
        JLabel hardwareReqLabel = new JLabel("REQUERIMIENTOS DEL HARDWARE:");
        hardwareReqLabel.setFont(headerFont);
        hardwareReqLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar encabezado de hardware
        creditPanel.add(hardwareReqLabel);

        JTextArea hardwareReq = new JTextArea(
                "- Procesador: Pentium II 600 MHz.\n" +
                        "- Disco Duro: Mb espacio ocupado.\n" +
                        "- Resolución de Pantalla: 1650 x 960 Pixeles mínimo.\n" +
                        "- Unidad de USB."
        );
        hardwareReq.setFont(textFont);
        hardwareReq.setLineWrap(true);
        hardwareReq.setWrapStyleWord(true);
        hardwareReq.setEditable(false);
        hardwareReq.setBackground(creditPanel.getBackground());
        hardwareReq.setBorder(new EmptyBorder(5, 10, 5, 10));
        hardwareReq.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar texto de hardware
        creditPanel.add(hardwareReq);

        creditPanel.add(Box.createVerticalStrut(15));

        // Requerimientos de Software
        JLabel softwareReqLabel = new JLabel("REQUERIMIENTOS DEL SOFTWARE:");
        softwareReqLabel.setFont(headerFont);
        softwareReqLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar encabezado de software
        creditPanel.add(softwareReqLabel);

        JTextArea softwareReq = new JTextArea(
                "- Sistema Operativo: Windows XP, Vista, Windows 7, Windows 8, Windows 10, Windows 11\n" +
                        "- Lenguaje de Programación: Java (NetBeans)"
        );
        softwareReq.setFont(textFont);
        softwareReq.setLineWrap(true);
        softwareReq.setWrapStyleWord(true);
        softwareReq.setEditable(false);
        softwareReq.setBackground(creditPanel.getBackground());
        softwareReq.setBorder(new EmptyBorder(5, 10, 5, 10));
        softwareReq.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar texto de software
        creditPanel.add(softwareReq);

        creditPanel.add(Box.createVerticalStrut(15));

        // Ejecución de las figuras geométricas
        JLabel ejecucionLabel = new JLabel("EJECUCIÓN DE LAS FIGURAS GEOMÉTRICAS:");
        ejecucionLabel.setFont(headerFont);
        ejecucionLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar encabezado de ejecución
        creditPanel.add(ejecucionLabel);

        JTextArea ejecucionText = new JTextArea(
                "- Insertar la USB con el archivo.\n" +
                        "- Ejecutar el Programa: FiGeo.exe"
        );
        ejecucionText.setFont(textFont);
        ejecucionText.setLineWrap(true);
        ejecucionText.setWrapStyleWord(true);
        ejecucionText.setEditable(false);
        ejecucionText.setBackground(creditPanel.getBackground());
        ejecucionText.setBorder(new EmptyBorder(5, 10, 5, 10));
        ejecucionText.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar texto de ejecución
        creditPanel.add(ejecucionText);

        // Crear el JDialog con tamaño personalizado
        JDialog dialog = new JDialog((JFrame) parent, "Créditos", true);
        dialog.setSize(1280, 800); // Tamaño personalizado de la ventana
        dialog.setLocationRelativeTo(null);


        // Agregar el panel a un JScrollPane por si el contenido es muy largo
        JScrollPane scrollPane = new JScrollPane(creditPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        dialog.add(scrollPane);

        // Mostrar el diálogo
        dialog.setVisible(true);
    }
    // Método para redimensionar iconos
    private static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage(); // obtener la imagen
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH); // redimensionar la imagen
        return new ImageIcon(newImg); // devolver nueva imagen
    }
}
