import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.io.Serializable;

public class Reserva implements Serializable {
    private Cliente cliente;
    private Habitacion habitacion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Reserva(Cliente cliente, Habitacion habitacion, LocalDate fechaInicio, LocalDate fechaFin) {
        setCliente(cliente);
        setHabitacion(habitacion);
        setFechas(fechaInicio, fechaFin);
    }

    private void setFechas(LocalDate inicio, LocalDate fin) {
        if (inicio == null || fin == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }
        if (inicio.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser en el pasado");
        }
        if (fin.isBefore(inicio)) {
            throw new IllegalArgumentException("La fecha fin debe ser posterior a la fecha inicio");
        }
        this.fechaInicio = inicio;
        this.fechaFin = fin;
    }

    public Cliente getCliente() { return cliente; }
    public Habitacion getHabitacion() { return habitacion; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }


    public void setCliente(Cliente cliente) {
        if (cliente == null)
            throw new IllegalArgumentException("Cliente no puede ser nulo");
        this.cliente = cliente;
    }

    public void setHabitacion(Habitacion habitacion) {
        if (habitacion == null)
            throw new IllegalArgumentException("Habitación no puede ser nula");
        this.habitacion = habitacion;
    }

    public byte getDias() {
        return (byte) ChronoUnit.DAYS.between(fechaInicio, fechaFin);
    }

    public double getCostoTotal() {
        return habitacion.calcularPrecio(getDias());
    }

    @Override
    public String toString() {
        return "Reserva: " + cliente.getNombre() +
                " en Hab. #" + habitacion.getNumero() +
                " (" + fechaInicio + " a " + fechaFin + ")" +
                " - Total: $" + getCostoTotal();
    }
}