package crafting.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

public class SerializationUtils {
    public static <T extends Serializable> String serialize(T item) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ObjectOutputStream objectOutputStream;
        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(item);
        objectOutputStream.close();
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    public static <T extends Serializable> T deserialize(String data) throws IOException, ClassNotFoundException {
        byte[] dataBytes = Base64.getDecoder().decode(data);
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(dataBytes);
        final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        @SuppressWarnings({"unchecked"})
        final T obj = (T) objectInputStream.readObject();

        objectInputStream.close();
        return obj;
    }
}
