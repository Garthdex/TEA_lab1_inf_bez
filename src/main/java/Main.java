import java.io.*;

/**
 * Created by Antoine on 09.02.2017.
 */
public class Main {
    private static final String PATHKEY = "C://ИБ//ЛР1//КАЮ_key.txt";
    private static final String FIRST = "C://ИБ//ЛР1//1.txt";
    private static final String SECOND = "C://ИБ//ЛР1//2.txt";
    private static final String TRIRD = "C://ИБ//ЛР1//3.txt";
    private static final int DELTA = 0x9e3779b9;

    private static void writeToFile(String path, byte[] buffer) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void writeKeyToFile(String path, int[] mas) {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(mas);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int[] readKey(String path) {
        int[] mas = null;
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            mas = (int[])in.readObject();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return mas;
    }

    private static byte[] encrypt(int[] v, int[] k) {
        int v0 = v[0];
        int v1 = v[1];
        int sum = 0;

        int k0 = k[0];
        int k1 = k[1];
        int k2 = k[2];
        int k3 = k[3];

        for (int i = 0; i < 32; i++) {
            sum += DELTA;
            v0 += ((v1 << 4) + k0) ^ (v1 + sum) ^ ((v1 >> 5) + k1);
            v1 += ((v0 << 4) + k2) ^ (v0 + sum) ^ ((v0 >> 5) + k3);
        }

        v[0] = v0;
        v[1] = v1;
        return getBytes(v);
    }

    private static byte[] decrypt (int[]  v, int[] k) {
        int v0 = v[0];
        int v1 = v[1];
        int sum = 0xC6EF3720;

        int k0 = k[0];
        int k1 = k[1];
        int k2 = k[2];
        int k3 = k[3];

        for (int i = 0; i < 32; i++) {
            v1 -= ((v0 << 4) + k2) ^ (v0 + sum) ^ ((v0 >> 5) + k3);
            v0 -= ((v1 << 4) + k0) ^ (v1 + sum) ^ ((v1 >> 5) + k1);

            sum -= DELTA;
        }

        v[0] = v0;
        v[1] = v1;
        return getBytes(v);
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
            k[i] = (int)(Math.random() * 100000);
        }
        return k;
    }


    public static void main(String[] args) throws IOException {
        FileInputStream fin = new FileInputStream(FIRST);
        try {
            byte[] buffer = new byte[8];
            fin.read(buffer, 0, buffer.length);

            int[] key = generateKey();
            int[] value = getInts(buffer);

            writeKeyToFile(PATHKEY, key);
            writeToFile(SECOND, encrypt(value, key));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            fin.close();
        }

        FileInputStream fin1 = new FileInputStream(SECOND);
        try {
            byte[] buffer = new byte[8];
            fin1.read(buffer, 0, buffer.length);

            int[] value = getInts(buffer);
            int[] key = readKey(PATHKEY);

            writeToFile(TRIRD, decrypt(value, key));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            fin1.close();
        }

    }
}
