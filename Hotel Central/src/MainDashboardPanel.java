import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class MainDashboardPanel extends JPanel implements HotelStateObserver {
    private Hotel hotel;
    private JLabel lblHabitacionesDisponibles;
    private JLabel lblClientesRegistrados;
    private JLabel lblReservasActivas;


    public MainDashboardPanel(Hotel hotel) {
        this.hotel = hotel;
        hotel.addObserver(this);
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 245, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        createHeader();
        createCenterPanel();
        createStatsPanel();
    }

    private void createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 80, 120));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 120));

        // Título principal
        JLabel titleLabel = new JLabel("HOTEL CENTRAL - PANEL PRINCIPAL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        // fecha
        JLabel dateLabel = new JLabel(LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy")),
                SwingConstants.CENTER);
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateLabel.setForeground(new Color(200, 220, 255));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(dateLabel, BorderLayout.SOUTH);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        headerPanel.add(titlePanel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);
    }

    private void createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        centerPanel.setOpaque(false);

        // Panel de imagen del hotel
        JPanel imagePanel = createImagePanel();

        // Panel de bienvenida
        JPanel welcomePanel = createWelcomePanel();

        centerPanel.add(imagePanel);
        centerPanel.add(welcomePanel);
        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createImagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220),1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        ImageIcon icon = new ImageIcon("/home/manufg/Documentos/Hotel Central/src/images/hotel.jpg");
        Image image = icon.getImage().getScaledInstance(900, 500, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));

        // Pie de foto
        JLabel caption = new JLabel("Nuestras instalaciones", SwingConstants.CENTER);
        caption.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        caption.setForeground(new Color(100, 100, 100));
        panel.add(imageLabel);
        panel.add(caption, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220),1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JTextArea welcomeText = new JTextArea(
                "Bienvenido al Sistema de Gestión del Hotel Central.\n\n" +
                        "Desde este panel podrá acceder a todas las funcionalidades del sistema " +
                        "y visualizar las estadísticas clave de su operación diaria.\n\n" +
                        "Utilice el menú superior para navegar entre los diferentes módulos."
        );
        welcomeText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        welcomeText.setLineWrap(true);
        welcomeText.setWrapStyleWord(true);
        welcomeText.setEditable(false);
        welcomeText.setBackground(Color.WHITE);
        welcomeText.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel tipsTitle = new JLabel("Consejo del día:");
        tipsTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tipsTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

        JTextArea dailyTip = new JTextArea(
                "Revise regularmente las estadísticas de ocupación para optimizar " +
                        "la asignación de habitaciones y mejorar la experiencia de sus huéspedes."
        );
        dailyTip.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dailyTip.setLineWrap(true);
        dailyTip.setWrapStyleWord(true);
        dailyTip.setEditable(false);
        dailyTip.setBackground(new Color(240, 245, 250));

        panel.add(welcomeText, BorderLayout.NORTH);
        panel.add(tipsTitle, BorderLayout.CENTER);
        panel.add(dailyTip, BorderLayout.SOUTH);

        return panel;
    }


    @Override
    public void onHotelStateChanged(int availableRooms, int activeReservations, int registeredClients) {
        SwingUtilities.invokeLater(() -> {
            lblHabitacionesDisponibles.setText(String.valueOf(availableRooms));
            lblReservasActivas.setText(String.valueOf(activeReservations));
            lblClientesRegistrados.setText(String.valueOf(registeredClients));
        });
    }

    // Modificar createStatsPanel para usar JLabels que podemos actualizar
    private void createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Habitaciones disponibles
        lblHabitacionesDisponibles = new JLabel(String.valueOf(countAvailableRooms()), SwingConstants.CENTER);
        statsPanel.add(createStatCard(
                "Habitaciones Disponibles",
                lblHabitacionesDisponibles,
                new Color(70, 130, 180)
        ));

        // Clientes registrados
        lblClientesRegistrados = new JLabel(String.valueOf(hotel.getClientes().size()), SwingConstants.CENTER);
        statsPanel.add(createStatCard(
                "Clientes Registrados",
                lblClientesRegistrados,  // Ahora usando la variable de clase
                new Color(60, 179, 113))
        );

        // Reservas hoy
        lblReservasActivas = new JLabel(String.valueOf(countActiveReservations()), SwingConstants.CENTER);
        statsPanel.add(createStatCard(
                "Reservas Activas",
                lblReservasActivas,
                new Color(218, 165, 32)
        ));

        add(statsPanel, BorderLayout.SOUTH);
    }

    // Modificar createStatCard para aceptar JLabel
    private JPanel createStatCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(color.darker());

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        valueLabel.setForeground(color);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }
    private int countAvailableRooms() {
        return hotel.buscarHabitacionesDisponibles(
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        ).size();
    }

    private int countActiveReservations() {
        LocalDate today = LocalDate.now();
        return (int) hotel.getHistorial().stream()
                .filter(r -> !r.getFechaFin().isBefore(today) && !r.getFechaInicio().isAfter(today))
                .count();
    }
    public void cleanup() {
        hotel.removeObserver(this);
    }

}