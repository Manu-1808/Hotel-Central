import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Escritura {
    private String nomArch;
    private ObjectOutputStream escritura;

    public Escritura(String archivo) throws IOException {
        this.nomArch = archivo;
        this.escritura = new ObjectOutputStream(new FileOutputStream(archivo));
    }

    public void EscribirObjeto(Object obj) throws IOException {
        escritura.writeObject(obj);
    }

    public String EscribirObjeto2(Object obj) {
        String salida = "";
        try {
            escritura.writeObject(obj);
            salida = "Escrito correctamente";
        } catch (IOException IOe) {
            salida = IOe.getMessage();
        } finally {
            salida += "\nProceso concluido";
        }
        return salida;
    }

    public void CerrarEscritura() throws IOException {
        escritura.close();
    }
}