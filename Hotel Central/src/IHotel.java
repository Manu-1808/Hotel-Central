import java.util.List;
import java.time.LocalDate;

public interface IHotel {
    void agregarReserva(Reserva reserva) throws HabitacionOcupadaException;
    List<Reserva> getHistorial();
    void registrarCliente(Cliente cliente);
    void agregarHabitacion(Habitacion habitacion);
    List<Habitacion> buscarHabitacionesDisponibles(LocalDate inicio, LocalDate fin);
}

