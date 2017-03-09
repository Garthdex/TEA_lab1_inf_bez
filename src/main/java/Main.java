import java.io.*;

public class Main {

    private static void writeToFile(String path, byte[] buffer) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printHelpToConsole() {
        System.out.println("Вы должны ввести параметры в таком порядке:" + "\n"
                + "Encrypt-1.jar param1 param2 param3" + "\n"
                + "где param1 - полный путь к текстовому файлу для кодирования" + "\n"
                + "пример: C:\\ИБ\\ЛР1\\1.txt" + "\n"
                + "где param2 - полный путь к зашифрованному файлу" + "\n"
                + "пример: C:\\ИБ\\ЛР1\\2.enc" + "\n"
                + "где param3 - полный путь к сохранению файла ключа" + "\n"
                + "пример: C:\\ИБ\\ЛР1\\КАЮ_Key.key" + "\n");
    }

    public static void main(String[] args) throws IOException {
        if (args[0].equals("?")) {
            printHelpToConsole();
            return;
        }
        FileInputStream fin = new FileInputStream(args[0]);
        try {
            byte[] bufferValue = new byte[8];
            fin.read(bufferValue, 0, bufferValue.length);

            byte[] key = Tea.generateKey();
            int[] value = Transfer.byteToInt(bufferValue);

            writeToFile(args[2], key);
            writeToFile(args[1], Tea.encrypt(value, Transfer.byteToInt(key)));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            fin.close();
        }
    }
}
