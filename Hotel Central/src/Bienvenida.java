import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Objects;

public class Bienvenida extends JPanel {
    private JButton enterButton;
    private Runnable onEnterCallback;

    public Bienvenida(Runnable onEnterCallback) {
        this.onEnterCallback = onEnterCallback;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 250));

        // Panel superior
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(30, 80, 120));
        topPanel.setPreferredSize(new Dimension(800, 180));

        //ImageIcon icon = new ImageIcon(new URL() Objects.requireNonNull(getClass().getResource("/logo.jpg")));
        ImageIcon icon = new ImageIcon("/home/manufg/Descargas/Hotel.jpeg");
        Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));


        JLabel titleLabel = new JLabel("HOTEL CENTRAL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);

        topPanel.add(imageLabel);
        topPanel.add(titleLabel);

        // Panel central con imagen
        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        centerPanel.setBackground(new Color(240, 245, 250));

        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<h1 style='color: #1E3A8A;'>Sistema de Gestión Hotelera</h1>"
                + "<p style='color: #4B5563; font-size: 14pt;'>Administración de reservas, huéspedes y habitaciones</p>"
                + "<p style='color: #6B7280; font-size: 12pt;'>By: Manu Flores<br>Versión 2.0</p></div></html>",
                SwingConstants.CENTER);


        centerPanel.add(welcomeLabel, BorderLayout.SOUTH);

        // Panel inferior con botón
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(240, 245, 250));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 50, 20));

        enterButton = createStyledButton("INGRESAR AL SISTEMA", 250, 60);
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onEnterCallback.run();
            }
        });

        bottomPanel.add(enterButton);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(30, 80, 120));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(width, height));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(20, 60, 100), 2),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Efecto hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(40, 100, 150));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 80, 120));
            }
        });

        return button;
    }
}