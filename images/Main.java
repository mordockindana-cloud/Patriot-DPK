import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        task1();
        waitForEnter();

        task2();
        waitForEnter();

        task3();
        waitForEnter();

        task4();
        waitForEnter();

        task5();
    }

    private static void waitForEnter() {
        System.out.println("\n---> Нажмите Enter, чтобы перейти к следующему заданию...");
        scanner.nextLine();
    }

    // 1. Перегрузка валидатора
    public static void task1() {
        System.out.println("=== Задание 1: Перегрузка валидатора ===");

        String email1 = "android.dev@example.com";
        String email2 = "user@unknown-domain.xyz";

        System.out.println("Базовая проверка ('" + email1 + "'): " + isValid(email1));
        System.out.println("Строгая проверка домена ('" + email1 + "'): " + isValid(email1, true));
        System.out.println("Строгая проверка домена ('" + email2 + "'): " + isValid(email2, true));
    }

    public static boolean isValid(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    public static boolean isValid(String email, boolean checkDomain) {
        if (!isValid(email)) {
            return false;
        }
        if (!checkDomain) {
            return true;
        }
        // Проверка допустимых доменных зон
        String domain = email.substring(email.lastIndexOf('.') + 1).toLowerCase();
        return domain.equals("com") || domain.equals("ru") || domain.equals("org") || domain.equals("net");
    }

    // 2. Форматирование валюты
    public static void task2() {
        System.out.println("=== Задание 2: Форматирование валюты ===");

        double price1 = 12500.50;
        double price2 = 99.9;

        System.out.println("Товар 1: " + formatCurrency(price1, "₽"));
        System.out.println("Товар 2: " + formatCurrency(price2, "$"));
    }

    public static String formatCurrency(double amount, String currencySymbol) {
        return String.format("%,.2f %s", amount, currencySymbol);
    }

    // 3. Калькулятор суммарного размера кэша (varargs)
    public static void task3() {
        System.out.println("=== Задание 3: Калькулятор размера кэша ===");

        long imgCache = 1_048_576L; // 1 MB
        long videoCache = 5_242_880L; // 5 MB
        long textCache = 524_288L; // 0.5 MB

        double totalMb = calculateCache(imgCache, videoCache, textCache);
        System.out.printf("Суммарный размер кэша: %.2f МБ\n", totalMb);
    }

    public static double calculateCache(long... fileSizesInBytes) {
        long totalBytes = 0;
        for (long size : fileSizesInBytes) {
            totalBytes += size;
        }
        return totalBytes / (1024.0 * 1024.0);
    }

    // 4. Рекурсивный поиск вложений
    static class Folder {
        String name;
        int fileCount;
        List<Folder> subFolders = new ArrayList<>();

        Folder(String name, int fileCount) {
            this.name = name;
            this.fileCount = fileCount;
        }
    }

    public static void task4() {
        System.out.println("=== Задание 4: Рекурсивный поиск вложений ===");

        // Демо-структура папок на устройстве
        Folder root = new Folder("sdcard", 2);
        Folder dcim = new Folder("DCIM", 1);
        Folder camera = new Folder("Camera", 15);
        Folder documents = new Folder("Documents", 4);

        dcim.subFolders.add(camera);
        root.subFolders.add(dcim);
        root.subFolders.add(documents);

        int totalItems = countTotalElements(root);
        System.out.println("Общее количество вложенных элементов (файлов и папок): " + totalItems);
    }

    public static int countTotalElements(Folder folder) {
        if (folder == null) return 0;

        int count = folder.fileCount; // файлы в текущей папке

        for (Folder subFolder : folder.subFolders) {
            count += 1; // сама вложенная папка
            count += countTotalElements(subFolder); // рекурсивный обход подпапки
        }

        return count;
    }

    // 5. Сравнение версий приложения
    public static void task5() {
        System.out.println("=== Задание 5: Сравнение версий приложения ===");

        String v1 = "1.12.0";
        String v2 = "1.9.4";

        System.out.println("Сравнение '" + v1 + "' и '" + v2 + "': " + compareVersions(v1, v2)); // 1
        System.out.println("Сравнение '2.0.1' и '2.1.0': " + compareVersions("2.0.1", "2.1.0")); // -1
        System.out.println("Сравнение '1.0' и '1.0.0': " + compareVersions("1.0", "1.0.0")); // 0
    }

    public static int compareVersions(String v1, String v2) {
        String[] parts1 = v1.split("\\.");
        String[] parts2 = v2.split("\\.");

        int maxLength = Math.max(parts1.length, parts2.length);

        for (int i = 0; i < maxLength; i++) {
            int num1 = i < parts1.length ? Integer.parseInt(parts1[i]) : 0;
            int num2 = i < parts2.length ? Integer.parseInt(parts2[i]) : 0;

            if (num1 > num2) {
                return 1;
            } else if (num1 < num2) {
                return -1;
            }
        }

        return 0;
    }
}