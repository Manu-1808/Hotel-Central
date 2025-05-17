import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
public class Cliente implements Serializable {
    private int id;
    private String nombre;
    private String telefono;
    private List<Reserva> reservas;



    public int getId() { return id; }
    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID debe ser positivo");
        this.id = id;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre no puede estar vacío");
        }
        if(nombre.trim().length()<=2){
            throw  new IllegalArgumentException("Nombre no valido");
        }
        if(!Character.isUpperCase(nombre.trim().charAt(0))) {
            throw new IllegalArgumentException("El nombre debe empezar en Mayusculas");
        }
        this.nombre = nombre.trim();
    }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) {
        if (telefono == null || !telefono.matches("\\d{10}")) {
            throw new IllegalArgumentException("Teléfono debe tener 10 dígitos");
        }
        for (int i = 0; i < telefono.length(); i++) {
            if (!Character.isDigit(telefono.charAt(i))) {
                throw new IllegalArgumentException("Solo se aceptan numeros");
            }
        }
        this.telefono = telefono;
    }

    public List<Reserva> getReservas() { return new ArrayList<>(reservas); }

    public Cliente(int id, String nombre, String telefono) {
        setId(id);
        setNombre(nombre);
        setTelefono(telefono);
        this.reservas = new ArrayList<>();
    }

    public void agregarReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    @Override
    public String toString() {
        return nombre + " (ID: " + id + ") - Tel: " + telefono + " - Reservas: " + reservas.size();
    }
}