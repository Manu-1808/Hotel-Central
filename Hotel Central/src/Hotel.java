import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Hotel implements IHotel, Serializable {
    private static final long serialVersionUID = 1L;
    private List<Cliente> clientes;
    private List<Habitacion> habitaciones;
    private List<Reserva> reservas;
    private static final String DATA_FILE = "hotel_data.dat";

    public Hotel() {
        this.clientes = new ArrayList<>();
        this.habitaciones = new ArrayList<>();
        this.reservas = new ArrayList<>();
        cargarDatos();
    }

    // leer datos
    private void cargarDatos() {
        File file = new File(DATA_FILE);
        if (!file.exists() || file.length() == 0) {
            System.out.println("Archivo de datos no existe o está vacío. Se creará uno nuevo.");
            return;
        }

        try {
            Lectura lectura = new Lectura(DATA_FILE);
            Object objetoLeido;

            while (true) {
                try {
                    objetoLeido = lectura.LeerObjetos();
                    if (objetoLeido instanceof Hotel) {
                        Hotel hotelCargado = (Hotel) objetoLeido;
                        // Validación de datos
                        this.clientes = hotelCargado.clientes != null ? hotelCargado.clientes : new ArrayList<>();
                        this.habitaciones = hotelCargado.habitaciones != null ? hotelCargado.habitaciones : new ArrayList<>();
                        this.reservas = hotelCargado.reservas != null ? hotelCargado.reservas : new ArrayList<>();
                        break;
                    }
                } catch (EOFException e) {
                    break; // Fin del archivo
                }
            }
            lectura.CerrarLectura();
            System.out.println("Datos cargados correctamente.");

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
            this.clientes = new ArrayList<>();
            this.habitaciones = new ArrayList<>();
            this.reservas = new ArrayList<>();
        }
    }

    // guardar datos
    public void guardarDatos() {
        try {
            Escritura escritura = new Escritura(DATA_FILE);
            escritura.EscribirObjeto(this);
            escritura.CerrarEscritura();
            System.out.println("Datos guardados correctamente en: " + new File(DATA_FILE).getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Error al guardar datos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodos de ihotel
    @Override
    public void agregarReserva(Reserva reserva) throws HabitacionOcupadaException {
        if (this.reservas == null) {
            this.reservas = new ArrayList<>();
        }

        if (!reserva.getHabitacion().estaDisponible(reserva.getFechaInicio(), reserva.getFechaFin())) {
            throw new HabitacionOcupadaException("La habitación no está disponible para las fechas seleccionadas");
        }

        reservas.add(reserva);
        reserva.getCliente().agregarReserva(reserva);
        reserva.getHabitacion().agregarReserva(reserva);
        guardarDatos();
    }

    @Override
    public List<Reserva> getHistorial() {
        return new ArrayList<>(reservas);
    }

    @Override
    public void registrarCliente(Cliente cliente) {
        if (clientes.stream().anyMatch(c -> c.getId() == cliente.getId())) {
            throw new IllegalArgumentException("Ya existe un cliente con este ID");
        }
        clientes.add(cliente);
        guardarDatos();
    }

    @Override
    public void agregarHabitacion(Habitacion habitacion) {
        if (habitaciones.stream().anyMatch(h -> h.getNumero() == habitacion.getNumero())) {
            throw new IllegalArgumentException("Ya existe una habitación con este número");
        }
        habitaciones.add(habitacion);
        guardarDatos();
    }

    @Override
    public List<Habitacion> buscarHabitacionesDisponibles(LocalDate inicio, LocalDate fin) {
        return habitaciones.stream()
                .filter(h -> h.estaDisponible(inicio, fin))
                .collect(Collectors.toList());
    }

    public List<Cliente> getClientes() { return new ArrayList<>(clientes); }
    public List<Habitacion> getHabitaciones() { return new ArrayList<>(habitaciones); }
    public String getDataFilePath() { return new File(DATA_FILE).getAbsolutePath(); }
}