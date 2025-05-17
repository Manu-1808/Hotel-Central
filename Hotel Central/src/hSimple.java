import java.io.Serializable;
public class hSimple extends Habitacion implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean tieneVistaAlMar;

    public hSimple(int numero, double precioBase, boolean tieneVistaAlMar) {
        super(numero, precioBase);
        this.tieneVistaAlMar = tieneVistaAlMar;
    }

    @Override
    public double calcularPrecio(int dias) {
        double precio = super.getPrecioBase() * dias;
        return tieneVistaAlMar ? precio * 1.2 : precio;
    }

    @Override
    public String toString() {
        return super.toString() + " - Simple" + (tieneVistaAlMar ? " (Vista al mar)" : "");
    }
}