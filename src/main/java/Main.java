import java.io.*;

/**
 * Created by Antoine on 09.02.2017.
 */
public class Main {
    private static final String PATHKEY = "C://ИБ//ЛР1//КАЮ_key.txt";
    private static final int DELTA = 0x9e3779b9;

    private static void writeToFile(String path, byte[] buffer) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void encrypt(byte[] b, int[] k) {

    }

    private static int[] getInts(byte[] b) {
        int[] data = new int[2];
        data[0] = ((b[0] & 0xFF) << 24) + ((b[1] & 0xFF) << 16) + ((b[2] & 0xFF) << 8) + (b[3] & 0xFF);
        data[1] = ((b[4] & 0xFF) << 24) + ((b[5] & 0xFF) << 16) + ((b[6] & 0xFF) << 8) + (b[7] & 0xFF);
        return data;
    }

    private static byte[] getBytes(int[] data) {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (data[0] >> 24);
        bytes[1] = (byte) (data[0] >> 16);
        bytes[2] = (byte) (data[0] >> 8);
        bytes[3] = (byte) (data[0]);
        bytes[4] = (byte) (data[1] >> 24);
        bytes[5] = (byte) (data[1] >> 16);
        bytes[6] = (byte) (data[1] >> 8);
        bytes[7] = (byte) (data[1]);
        return bytes;
    }

    private static int[] generateKey() {
        int[] k = new int[4];
        for (int i = 0; i < 4; i++) {
            k[i] = (int)(Math.random() * 10);
        }
        return k;
    }


    public static void main(String[] args) throws IOException {
        FileInputStream fin = new FileInputStream("C://ИБ//ЛР1//1.txt");
        try {
            byte[] buffer = new byte[8];
            fin.read(buffer, 0, buffer.length);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            fin.close();
        }

    }
}
