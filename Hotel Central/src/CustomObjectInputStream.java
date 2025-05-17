import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public class CustomObjectInputStream extends ObjectInputStream {
    public CustomObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    @Override
    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        ObjectStreamClass resultClassDescriptor = super.readClassDescriptor();

        // Solución para clases con nombres diferentes pero misma estructura
        if (resultClassDescriptor.getName().equals("hSimple")) {
            resultClassDescriptor = ObjectStreamClass.lookup(hSimple.class);
        } else if (resultClassDescriptor.getName().equals("hFamiliar")) {
            resultClassDescriptor = ObjectStreamClass.lookup(hFamiliar.class);
        } else if (resultClassDescriptor.getName().equals("hSuite")) {
            resultClassDescriptor = ObjectStreamClass.lookup(hSuite.class);
        }

        return resultClassDescriptor;
    }
}