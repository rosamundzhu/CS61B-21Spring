package gitlet;

import java.io.*;

import static gitlet.Utils.join;

public class SerializeUtils {

    // Converts an Object to a ByteArray, only use if SHA1 is needed.
    public static byte[] toByteArray(Object obj) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(stream);
            objectStream.writeObject(obj);
            objectStream.close();
            return stream.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Internal error serializing commit.");
        }
    }

    public static void storeObjectToFile(Object obj, File outFile) {
        try {
            ObjectOutputStream out =
                    new ObjectOutputStream(new FileOutputStream(outFile));
            out.writeObject(obj);
            out.close();
        } catch (IOException e) {
            System.out.println("Error storing object to file.");
        }
    }
    public static void writeStringToFile(String text, File s, boolean appending) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(s, appending));
            out.write(text);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readStringFromFile(File path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = null;
            StringBuilder everything = new StringBuilder();
            while ((line = br.readLine()) != null) {
                everything.append(line);
            }
            return everything.toString();
        } catch (IOException e) {
            return "error";
        }
    }

    // Reconstructs an Object from ByteArray.
    public static <T> T deserialize(File fileName, Class<T> type) {
        T obj;
        try {
            ObjectInputStream inp = new ObjectInputStream(new FileInputStream(fileName));
            obj = (T) inp.readObject();
            inp.close();
        } catch (IOException | ClassNotFoundException e) {
            obj = null;
        }
        return obj;
    }
}
