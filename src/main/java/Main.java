import java.io.*;


public class Main {
    private static final String PATHKEY = "C://ИБ//ЛР1//КАЮ_key.key";

    private static void writeToFile(String path, byte[] buffer) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        FileInputStream fin = new FileInputStream(args[0]);
        try {
            byte[] bufferValue = new byte[8];
            fin.read(bufferValue, 0, bufferValue.length);

            byte[] key = Tea.generateKey();
            int[] value = Transfer.byteToInt(bufferValue);

            writeToFile(PATHKEY, key);
            writeToFile(args[1], Tea.encrypt(value, Transfer.byteToInt(key)));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            fin.close();
        }

    }
}
