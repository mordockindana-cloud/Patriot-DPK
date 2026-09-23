import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class practicagvozd2 {
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

    // 1. Нормализация поискового запроса
    public static void task1() {
        System.out.println("=== Задание 1: Нормализация поискового запроса ===");
        
        String inputQuery = "   Купить   Смартфон   Android   в  Москве   ";
        System.out.println("Исходный запрос: \"" + inputQuery + "\"");

        // trim() удаляет концевые пробелы,
        // toLowerCase() приводит к нижнему регистру,
        // replaceAll("\\s+", " ") заменяет 1 и более пробелов на одиночный
        String normalizedQuery = inputQuery.trim()
                                           .toLowerCase()
                                           .replaceAll("\\s+", " ");

        System.out.println("Нормализованный запрос: \"" + normalizedQuery + "\"");
    }

    // 2. Реверс массива кадров анимации (in-place)
    public static void task2() {
        System.out.println("=== Задание 2: Реверс массива кадров ===");
        
        String[] frames = {"frame_0.png", "frame_1.png", "frame_2.png", "frame_3.png", "frame_4.png"};
        System.out.println("Исходный массив: " + Arrays.toString(frames));

        // Разворот массива без создания второго массива (двумя указателями)
        for (int i = 0; i < frames.length / 2; i++) {
            String temp = frames[i];
            frames[i] = frames[frames.length - 1 - i];
            frames[frames.length - 1 - i] = temp;
        }

        System.out.println("Развернутый массив: " + Arrays.toString(frames));
    }

    // 3. Маскирование номера банковской карты
    public static void task3() {
        System.out.println("=== Задание 3: Маскирование номера карты ===");
        
        String cardNumber = "4532758912345678"; // 16 цифр
        System.out.println("Исходный номер: " + cardNumber);

        String maskedCardNumber = maskCardNumber(cardNumber);
        System.out.println("Замаскированный номер: " + maskedCardNumber);
    }

    private static String maskCardNumber(String card) {
        if (card == null || card.length() < 4) {
            return "****";
        }
        // Берем последние 4 цифры
        String lastFourDigits = card.substring(card.length() - 4);
        return "**** **** **** " + lastFourDigits;
    }

    // 4. Поиск пиковых значений акселерометра
    public static void task4() {
        System.out.println("=== Задание 4: Максимальный всплеск акселерометра ===");
        
        // Генерируем массив из 100 значений датчика
        double[] sensorData = new double[100];
        Random random = new Random();
        for (int i = 0; i < sensorData.length; i++) {
            sensorData[i] = random.nextDouble() * 10.0; // значения от 0.0 до 10.0
        }

        // Поиск максимальной разности между соседними элементами
        double maxDelta = 0.0;
        int peakIndex = 0;

        for (int i = 0; i < sensorData.length - 1; i++) {
            double delta = Math.abs(sensorData[i + 1] - sensorData[i]);
            if (delta > maxDelta) {
                maxDelta = delta;
                peakIndex = i;
            }
        }

        System.out.printf("Сгенерировано %d измерений.\n", sensorData.length);
        System.out.printf("Максимальный всплеск: %.3f (между элементами [%d] = %.2f и [%d] = %.2f)\n",
                maxDelta, peakIndex, sensorData[peakIndex], peakIndex + 1, sensorData[peakIndex + 1]);
    }

    // 5. Генератор URL-параметров
    public static void task5() {
        System.out.println(


"=== Задание 5: Генератор URL-параметров ===");
        
        String[] keys = {"query", "page", "sort", "limit"};
        String[] values = {"android", "2", "desc", "50"};

        StringBuilder urlBuilder = new StringBuilder("?");

        for (int i = 0; i < keys.length; i++) {
            urlBuilder.append(keys[i])
                      .append("=")
                      .append(values[i]);

            // Добавляем '&' только между параметрами, но не после последнего
            if (i < keys.length - 1) {
                urlBuilder.append("&");
            }
        }

        String finalUrl = urlBuilder.toString();
        System.out.println("Ключи: " + Arrays.toString(keys));
        System.out.println("Значения: " + Arrays.toString(values));
        System.out.println("Сформированная строка параметров: " + finalUrl);
    }
}