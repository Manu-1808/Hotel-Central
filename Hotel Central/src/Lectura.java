import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Lectura {
    private ObjectInputStream lectura;

    public Lectura(String archivo) throws IOException {
        this.lectura = new ObjectInputStream(new FileInputStream(archivo));
    }

    public Object LeerObjetos() throws EOFException, IOException, ClassNotFoundException {
        return lectura.readObject();
    }

    public void CerrarLectura() throws IOException {
        lectura.close();
    }
}