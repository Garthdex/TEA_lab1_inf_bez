import java.io.*;

//TODO 1) Ввод названия файла из командной строки(тотал командер) или интерфейса(свинг)
//TODO 2) Разделить проект на 2 проекта. Один чисто экрипт, другой чисто декрипт
//TODO 3) Создать jar для каждого проекта и запускать их с параметрами из тотал командера


public class Main {
    private static final String PATHKEY = "C://ИБ//ЛР1//КАЮ_key.txt";
    private static final String FIRST = "C://ИБ//ЛР1//1.txt";
    private static final String SECOND = "C://ИБ//ЛР1//2.enc";
    private static final String TRIRD = "C://ИБ//ЛР1//3.dec";

    private static void writeToFile(String path, byte[] buffer) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        FileInputStream fin = new FileInputStream(FIRST);
        try {
            byte[] bufferValue = new byte[8];
            fin.read(bufferValue, 0, bufferValue.length);

            byte[] key = Tea.generateKey();
            int[] value = Transfer.byteToInt(bufferValue);

            writeToFile(PATHKEY, key);
            writeToFile(SECOND, Tea.encrypt(value, Transfer.byteToInt(key)));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            fin.close();
        }

        FileInputStream fin1 = new FileInputStream(SECOND);
        FileInputStream fin2 = new FileInputStream(PATHKEY);
        try {
            byte[] bufferValue = new byte[8];
            byte[] bufferKey = new byte[16];

            fin1.read(bufferValue, 0, bufferValue.length);
            int[] value = Transfer.byteToInt(bufferValue);

            fin2.read(bufferKey, 0, bufferKey.length);
            int[] key = Transfer.byteToInt(bufferKey);

            writeToFile(TRIRD, Tea.decrypt(value, key));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            fin1.close();
            fin2.close();
        }
    }
}
