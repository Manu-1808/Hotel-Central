import java.io.Serializable;

public class hSuite extends Habitacion implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean tieneJacuzzi;

    public hSuite(int numero, double precioBase, boolean tieneJacuzzi) {
        super(numero, precioBase);
        this.tieneJacuzzi = tieneJacuzzi;
    }

    @Override
    public double calcularPrecio(int dias) {
        double precio = super.getPrecioBase() * dias;
        return tieneJacuzzi ? precio + 150 : precio; // $100 extra por jacuzzi
    }

    @Override
    public String toString() {
        return super.toString() + " - Suite" + (tieneJacuzzi ? " (Jacuzzi incluido)" : "");
    }
}