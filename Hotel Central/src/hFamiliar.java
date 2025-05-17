import java.io.Serializable;

public class hFamiliar extends Habitacion implements Serializable {
    private static final long serialVersionUID = 1L;
    private byte capacidad;

    public hFamiliar(int numero, double precioBase, byte capacidad) {
        super(numero, precioBase);
        setCapacidad(capacidad);
    }


    public void setCapacidad(byte capacidad) {
        if (capacidad < 2 || capacidad > 6) {
            throw new IllegalArgumentException("Capacidad debe ser entre 2 y 6 personas");
        }
        this.capacidad = capacidad;
    }

    @Override
    public double calcularPrecio(int dias) {
        double precio = super.getPrecioBase() * dias;
        return dias > 7 ? precio * 0.9 : precio; // 10% descuento para si se quedan mas de unsa semana
    }

    @Override
    public String toString() {
        return super.toString() + " - Familiar (Capacidad: " + capacidad + " personas)";
    }
}