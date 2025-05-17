import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public abstract class Habitacion implements Serializable {
    private static final long serialVersionUID = 1L;
    private int numero;
    private double precioBase;
    private List<Reserva> reservas;

    protected Habitacion() {
        this.reservas = new ArrayList<>();
    }

    public Habitacion(int numero, double precioBase) {
        setNumero(numero);
        setPrecioBase(precioBase);
        this.reservas = new ArrayList<>();
    }

    // desnudate ahora y apaga la luz un instanteee
    //rolon
    public int getNumero() { return numero; }
    public void setNumero(int numero) {
        if (numero <= 0) throw new IllegalArgumentException("Número debe ser positivo");
        this.numero = numero;
    }

    public double getPrecioBase() { return precioBase; }
    public void setPrecioBase(double precioBase) {
        if (precioBase <= 0) throw new IllegalArgumentException("Precio debe ser positivo");
        this.precioBase = precioBase;
    }

    public List<Reserva> getReservas() { return new ArrayList<>(reservas); }

    public void agregarReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    public boolean estaDisponible(LocalDate fechaInicio, LocalDate fechaFin) {
        if (this.reservas == null) {
            this.reservas = new ArrayList<>();
        }
        return reservas.stream().noneMatch(r ->
                !fechaFin.isBefore(r.getFechaInicio()) &&
                        !fechaInicio.isAfter(r.getFechaFin())
        );
    }

    public abstract double calcularPrecio(int dias);

    @Override
    public String toString() {
        return "Habitación #" + numero + " - Precio base: $" + precioBase;
    }
}