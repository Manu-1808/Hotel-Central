import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HotelManagementApp {
    private JFrame frame;
    private Hotel hotel;
    private JTabbedPane tabbedPane;
    private CardLayout cardLayout;
    private JPanel mainContainer;
    private Bienvenida bienvenida;
    MainDashboardPanel dashboard;
    JComboBox<Cliente> clientCombo;
    JComboBox<Habitacion> roomCombo;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                HotelManagementApp window = new HotelManagementApp();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public HotelManagementApp() {
        hotel = new Hotel();
        initialize();
    }


    private void initialize() {
        frame = new JFrame("Sistema de Gestión Hotelera");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                guardarYSalir();
            }
        });
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // Pantalla de bienvenida
        bienvenida = new Bienvenida(this::showMainApplication);
        mainContainer.add(bienvenida, "Bienvenido");

        // Panel principal de la aplicación
        JPanel mainAppPanel = createMainApplicationPanel();
        mainContainer.add(mainAppPanel, "mainApp");

        frame.setContentPane(mainContainer);
        cardLayout.show(mainContainer, "Bienvenido");
    }

    private void guardarYSalir() {
        dashboard.cleanup();
        int option = JOptionPane.showConfirmDialog(frame,
                "           ¿Desea salir?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            hotel.guardarDatos();
            System.exit(0);
        }
    }

    public void showMainApplication() {
        cardLayout.show(mainContainer, "mainApp");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private JPanel createMainApplicationPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 245, 250));

        // Titulo barra
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 80, 120));
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 80));

        JLabel titleLabel = new JLabel("Hotel Central                             ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        JButton backButton = new JButton("← Volver al Inicio");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setBackground(new Color(40, 100, 150));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        backButton.setFocusPainted(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            cardLayout.show(mainContainer, "Bienvenido");
            frame.setSize(1000, 700);
            frame.setLocationRelativeTo(null);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);

        headerPanel.add(buttonPanel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Panel de pestañas
        tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabbedPane.setBackground(new Color(240, 245, 250));

        dashboard = new MainDashboardPanel(hotel);
        tabbedPane.addTab("Inicio", new ImageIcon("home.png"), dashboard);

        createReservationTab();
        createRoomsTab();
        createClientsTab();
        createReportsTab();

        JButton infoButton = new JButton("Información de Datos");
        infoButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,
                    "Los datos se guardan en:\n" + hotel.getDataFilePath(),
                    "Información de Persistencia",
                    JOptionPane.INFORMATION_MESSAGE);
        });


        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.add(infoButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        return mainPanel;
    }

    private void createReservationTab() {
        JPanel reservationPanel = new JPanel(new BorderLayout(10, 10));
        reservationPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        reservationPanel.setBackground(new Color(240, 245, 250));

        // Panel de formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                new TitledBorder(BorderFactory.createLineBorder(new Color(200, 210, 220)), "Nueva Reserva"),
                        new EmptyBorder(10, 10, 10, 10)
                ));
        formPanel.setBackground(Color.WHITE);

        // Panel de campos del formulario
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 15, 10));
        fieldsPanel.setBackground(Color.WHITE);

        // Componentes del formulario
        JLabel clientLabel = new JLabel("Cliente:");
        clientLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        clientCombo = new JComboBox<>();
        clientCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        hotel.getClientes().forEach(clientCombo::addItem);

        JLabel roomLabel = new JLabel("Habitación:");
        roomLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roomCombo = new JComboBox<>();
        roomCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        hotel.getHabitaciones().forEach(roomCombo::addItem);

        JLabel startDateLabel = new JLabel("Fecha Inicio:");
        startDateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField startDateField = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        startDateField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel endDateLabel = new JLabel("Fecha Fin:");
        endDateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField endDateField = new JTextField(LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        endDateField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        fieldsPanel.add(clientLabel);
        fieldsPanel.add(clientCombo);
        fieldsPanel.add(roomLabel);
        fieldsPanel.add(roomCombo);
        fieldsPanel.add(startDateLabel);
        fieldsPanel.add(startDateField);
        fieldsPanel.add(endDateLabel);
        fieldsPanel.add(endDateField);

        // Botón de reservar
        JButton reserveButton = new JButton("Confirmar Reserva");
        reserveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        reserveButton.setBackground(new Color(30, 80, 120));
        reserveButton.setForeground(Color.WHITE);
        reserveButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        reserveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        reserveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // donde esta el boton
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(new EmptyBorder(15, 0, 5, 0));
        buttonPanel.add(reserveButton);


        formPanel.add(fieldsPanel);
        formPanel.add(buttonPanel);

        // lista de reservas
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createCompoundBorder(
                new TitledBorder(new LineBorder(new Color(200, 210, 220), 1), "Reservas Actuales"),
                new EmptyBorder(10, 10, 10, 10)
        ));
        listPanel.setBackground(Color.WHITE);

        DefaultListModel<Reserva> listModel = new DefaultListModel<>();
        hotel.getHistorial().forEach(listModel::addElement);
        JList<Reserva> reservationList = new JList<>(listModel);
        reservationList.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        reservationList.setCellRenderer(new ReservaListRenderer());
        reservationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(reservationList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Acción del botón de reserva
        reserveButton.addActionListener(e -> {
            try {
                Cliente cliente = (Cliente) clientCombo.getSelectedItem();
                Habitacion habitacion = (Habitacion) roomCombo.getSelectedItem();
                LocalDate inicio = LocalDate.parse(startDateField.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate fin = LocalDate.parse(endDateField.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                Reserva reserva = new Reserva(cliente, habitacion, inicio, fin);
                hotel.agregarReserva(reserva);
                listModel.addElement(reserva);

                JOptionPane.showMessageDialog(frame,
                        "<html><div style='text-align: center;'>"
                                + "<h3 style='color: #1E3A8A;'>Reserva confirmada</h3>"
                                + "<p>Habitación: " + habitacion.getNumero() + "</p>"
                                + "<p>Cliente: " + cliente.getNombre() + "</p>"
                                + "<p>Total: $" + reserva.getCostoTotal() + "</p></div></html>",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,
                        "<html><div style='text-align: center; color: #B91C1C;'>"
                                + "<p>Error al procesar la reserva:</p>"
                                + "<p>" + ex.getMessage() + "</p></div></html>",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Agregar paneles a la pestaña
        reservationPanel.add(formPanel, BorderLayout.NORTH);
        reservationPanel.add(listPanel, BorderLayout.CENTER);

        tabbedPane.addTab("Reservas", reservationPanel);
    }

    private void createRoomsTab() {
        JPanel roomsPanel = new JPanel(new BorderLayout());
        roomsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        roomsPanel.setBackground(new Color(240, 245, 250));

        // lista de hab
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createCompoundBorder(
                new TitledBorder(BorderFactory.createLineBorder(new Color(200, 210, 220)), "Habitaciones Disponibles"),
                        new EmptyBorder(10, 10, 10, 10)
                ));
        listPanel.setBackground(Color.WHITE);

        DefaultListModel<Habitacion> listModel = new DefaultListModel<>();
        hotel.getHabitaciones().forEach(listModel::addElement);
        JList<Habitacion> roomList = new JList<>(listModel);
        roomList.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        roomList.setCellRenderer(new HabitacionListRenderer());
        roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(roomList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        listPanel.add(scrollPane, BorderLayout.CENTER);

        // boton
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(new Color(240, 245, 250));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JButton addButton = createActionButton("Añadir Habitación", new Color(30, 80, 120));
        addButton.addActionListener(e -> showAddRoomDialog(listModel));

        buttonPanel.add(addButton);

        roomsPanel.add(listPanel, BorderLayout.CENTER);
        roomsPanel.add(buttonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Habitaciones",roomsPanel);
    }

    private void showAddRoomDialog(DefaultListModel<Habitacion> listModel) {
        JDialog dialog = new JDialog(frame, "Añadir Nueva Habitación", true);
        dialog.setSize(450, 350);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(frame);

        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Panel formulario
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        fieldsPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel typeLabel = new JLabel("Tipo de Habitación:");
        typeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Simple", "Familiar", "Suite"});
        typeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel numberLabel = new JLabel("Número:");
        numberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField numberField = new JTextField();
        numberField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel priceLabel = new JLabel("Precio Base:");
        priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField priceField = new JTextField();
        priceField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel extraLabel = new JLabel("Características:");
        extraLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JPanel extraPanel = new JPanel();
        extraPanel.setLayout(new BoxLayout(extraPanel, BoxLayout.Y_AXIS));

        typeCombo.addActionListener(e -> {
            extraPanel.removeAll();
            String type = (String) typeCombo.getSelectedItem();
            switch (type) {
                case "Simple":
                    JCheckBox vistaCheck = new JCheckBox("Vista al mar");
                    vistaCheck.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    extraPanel.add(vistaCheck);
                    break;
                case "Familiar":
                    JPanel capacityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel capacityLabel = new JLabel("Capacidad:");
                    capacityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    JSpinner capacitySpinner = new JSpinner(new SpinnerNumberModel(4, 2, 6, 1));
                    capacitySpinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    capacityPanel.add(capacityLabel);
                    capacityPanel.add(capacitySpinner);
                    extraPanel.add(capacityPanel);
                    break;
                case "Suite":
                    JCheckBox jacuzziCheck = new JCheckBox("Incluye Jacuzzi");
                    jacuzziCheck.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    extraPanel.add(jacuzziCheck);
                    break;
            }
            extraPanel.revalidate();
            extraPanel.repaint();
        });
        typeCombo.setSelectedIndex(0);

        fieldsPanel.add(typeLabel);
        fieldsPanel.add(typeCombo);
        fieldsPanel.add(numberLabel);
        fieldsPanel.add(numberField);
        fieldsPanel.add(priceLabel);
        fieldsPanel.add(priceField);
        fieldsPanel.add(extraLabel);
        fieldsPanel.add(extraPanel);

        contentPanel.add(fieldsPanel);

        //botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JButton cancelButton = createActionButton("Cancelar", new Color(120, 120, 120));
        cancelButton.addActionListener(e -> dialog.dispose());

        JButton saveButton = createActionButton("Guardar", new Color(30, 80, 120));
        saveButton.addActionListener(e -> {
            try {
                int numero = Integer.parseInt(numberField.getText());
                double precio = Double.parseDouble(priceField.getText());
                Habitacion habitacion = null;

                switch ((String) typeCombo.getSelectedItem()) {
                    case "Simple":
                        JCheckBox vistaCheck = (JCheckBox) extraPanel.getComponent(0);
                        habitacion = new hSimple(numero, precio, vistaCheck.isSelected());
                        break;
                    case "Familiar":
                        JSpinner capacitySpinner = (JSpinner) ((JPanel)extraPanel.getComponent(0)).getComponent(1);
                        habitacion = new hFamiliar(numero, precio, ((Number)capacitySpinner.getValue()).byteValue());
                        break;
                    case "Suite":
                        JCheckBox jacuzziCheck = (JCheckBox) extraPanel.getComponent(0);
                        habitacion = new hSuite(numero, precio, jacuzziCheck.isSelected());
                        break;
                }

                hotel.agregarHabitacion(habitacion);
                listModel.addElement(habitacion);
                dialog.dispose();

                JOptionPane.showMessageDialog(frame,
                        "<html><div style='text-align: center;'>"
                                + "<h3 style='color: #1E3A8A;'>Habitación añadida</h3>"
                                + "<p>" + habitacion.toString() + "</p></div></html>",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                roomCombo.removeAllItems();
                hotel.getHabitaciones().forEach(roomCombo::addItem);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog,
                        "<html><div style='text-align: center; color: #B91C1C;'>"
                                + "<p>Error al crear habitación:</p>"
                                + "<p>" + ex.getMessage() + "</p></div></html>",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        contentPanel.add(buttonPanel);

        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void createClientsTab() {
        JPanel clientsPanel = new JPanel(new BorderLayout());
        clientsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        clientsPanel.setBackground(new Color(240, 245, 250));

        //lista de clientes
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createCompoundBorder(
                new TitledBorder(BorderFactory.createLineBorder(new Color(200, 210, 220)), "Clientes Registrados"),
                        new EmptyBorder(10, 10, 10, 10)
                ));
        listPanel.setBackground(Color.WHITE);

        DefaultListModel<Cliente> listModel = new DefaultListModel<>();
        hotel.getClientes().forEach(listModel::addElement);
        JList<Cliente> clientList = new JList<>(listModel);
        clientList.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        clientList.setCellRenderer(new ClienteListRenderer());
        clientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(clientList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        listPanel.add(scrollPane, BorderLayout.CENTER);

        //botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(new Color(240, 245, 250));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JButton addButton = createActionButton("Añadir Cliente", new Color(30, 80, 120));
        addButton.addActionListener(e -> showAddClientDialog(listModel));

        buttonPanel.add(addButton);

        clientsPanel.add(listPanel, BorderLayout.CENTER);
        clientsPanel.add(buttonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Clientes", clientsPanel);
    }

    private void showAddClientDialog(DefaultListModel<Cliente> listModel) {
        JDialog dialog = new JDialog(frame, "Registrar Nuevo Cliente", true);
        dialog.setSize(450, 300);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(frame);

        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // campos
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        fieldsPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel idLabel = new JLabel("ID:");
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField idField = new JTextField();
        idField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel nameLabel = new JLabel("Nombre:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel phoneLabel = new JLabel("Teléfono:");
        phoneLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField phoneField = new JTextField();
        phoneField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        fieldsPanel.add(idLabel);
        fieldsPanel.add(idField);
        fieldsPanel.add(nameLabel);
        fieldsPanel.add(nameField);
        fieldsPanel.add(phoneLabel);
        fieldsPanel.add(phoneField);

        contentPanel.add(fieldsPanel);

        //botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JButton cancelButton = createActionButton("Cancelar", new Color(120, 120, 120));
        cancelButton.addActionListener(e -> dialog.dispose());

        JButton saveButton = createActionButton("Guardar", new Color(30, 80, 120));
        saveButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String nombre = nameField.getText();
                String telefono = phoneField.getText();

                Cliente cliente = new Cliente(id, nombre, telefono);
                hotel.registrarCliente(cliente);
                listModel.addElement(cliente);
                dialog.dispose();

                JOptionPane.showMessageDialog(frame,
                        "<html><div style='text-align: center;'>"
                                + "<h3 style='color: #1E3A8A;'>Cliente registrado</h3>"
                                + "<p>" + cliente.toString() + "</p></div></html>",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                clientCombo.removeAllItems();
                hotel.getClientes().forEach(clientCombo::addItem);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog,
                        "<html><div style='text-align: center; color: #B91C1C;'>"
                                + "<p>Error al registrar cliente:</p>"
                                + "<p>" + ex.getMessage() + "</p></div></html>",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        contentPanel.add(buttonPanel);

        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void createReportsTab() {
        JPanel reportsPanel = new JPanel(new BorderLayout());
        reportsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        reportsPanel.setBackground(new Color(240, 245, 250));

        // reportes
        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        reportArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                new TitledBorder(new LineBorder(new Color(200, 210, 220), 1), "Reportes"),
                new EmptyBorder(5, 5, 5, 5)
        ));
        scrollPane.setBackground(Color.WHITE);

        // botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        buttonPanel.setBackground(new Color(240, 245, 250));
        buttonPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JButton roomsReportButton = createActionButton("Reporte Habitaciones", new Color(30, 80, 120));
        JButton reservationsReportButton = createActionButton("Reporte Reservas", new Color(30, 80, 120));
        JButton clientsReportButton = createActionButton("Reporte Clientes", new Color(30, 80, 120));

        roomsReportButton.addActionListener(e -> {
            StringBuilder report = new StringBuilder("REPORTE DE HABITACIONES\n\n");
            hotel.getHabitaciones().forEach(h -> report.append(h.toString()).append("\n"));
            reportArea.setText(report.toString());
        });

        reservationsReportButton.addActionListener(e -> {
            StringBuilder report = new StringBuilder("REPORTE DE RESERVAS\n\n");
            hotel.getHistorial().forEach(r -> report.append(r.toString()).append("\n"));
            reportArea.setText(report.toString());
        });

        clientsReportButton.addActionListener(e -> {
            StringBuilder report = new StringBuilder("REPORTE DE CLIENTES\n\n");
            hotel.getClientes().forEach(c -> report.append(c.toString()).append("\n"));
            reportArea.setText(report.toString());
        });

        buttonPanel.add(roomsReportButton);
        buttonPanel.add(reservationsReportButton);
        buttonPanel.add(clientsReportButton);

        reportsPanel.add(buttonPanel, BorderLayout.NORTH);
        reportsPanel.add(scrollPane, BorderLayout.CENTER);

        tabbedPane.addTab("Reportes", reportsPanel);
    }

    private JButton createActionButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(bgColor.getRed()-20, bgColor.getGreen()-20, bgColor.getBlue()-20), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Efecto hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(
                        Math.min(bgColor.getRed() + 20, 255),
                        Math.min(bgColor.getGreen() + 20, 255),
                        Math.min(bgColor.getBlue() + 20, 255)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    //cambiar el estilo al usar algun componente o asi
    class ReservaListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Reserva) {
                Reserva reserva = (Reserva) value;
                setText(String.format("Habitación %d - %s (%s a %s) - $%.2f",
                        reserva.getHabitacion().getNumero(),
                        reserva.getCliente().getNombre(),
                        reserva.getFechaInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        reserva.getFechaFin().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        reserva.getCostoTotal()));
            }
            if (isSelected) {
                setBackground(new Color(220, 235, 247));
                setBorder(BorderFactory.createLineBorder(new Color(180, 210, 235)));
            }
            return this;
        }
    }

    class HabitacionListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Habitacion) {
                Habitacion habitacion = (Habitacion) value;
                setText(habitacion.toString());
            }
            if (isSelected) {
                setBackground(new Color(220, 235, 247));
                setBorder(BorderFactory.createLineBorder(new Color(180, 210, 235)));
            }
            return this;
        }
    }

    class ClienteListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Cliente) {
                Cliente cliente = (Cliente) value;
                setText(cliente.toString());
            }
            if (isSelected) {
                setBackground(new Color(220, 235, 247));
                setBorder(BorderFactory.createLineBorder(new Color(180, 210, 235)));
            }
            return this;
        }
    }
}