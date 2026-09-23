import java.util.Scanner;

public class prcticagvozd8 {
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

    // =========================================================================
    // 1. Определение ориентации экрана
    // =========================================================================
    public static String getOrientation(int width, int height) {
        if (width <= 0 || height <= 0) {
            return "UNKNOWN";
        }
        if (width > height) {
            return "LANDSCAPE";
        } else if (height > width) {
            return "PORTRAIT";
        } else {
            return "SQUARE";
        }
    }

    public static void task1() {
        System.out.println("=== Задание 1: Определение ориентации экрана ===");

        System.out.println("Экран 1080x1920: " + getOrientation(1080, 1920));
        System.out.println("Экран 2560x1440: " + getOrientation(2560, 1440));
        System.out.println("Экран 1000x1000: " + getOrientation(1000, 1000));
    }

    // =========================================================================
    // 2. Классификатор статуса HTTP-ответа
    // =========================================================================
    public static String getHttpStatusCategory(int statusCode) {
        int category = statusCode / 100; // Получаем первую цифру кода (например, 404 -> 4)

        return switch (category) {
            case 1 -> "Информационный (1xx)";
            case 2 -> "Успешный (2xx)";
            case 3 -> "Перенаправление (3xx)";
            case 4 -> "Ошибка клиента (4xx)";
            case 5 -> "Ошибка сервера (5xx)";
            default -> "Неизвестный статус-код (" + statusCode + ")";
        };
    }

    public static void task2() {
        System.out.println("=== Задание 2: Классификатор HTTP-ответа ===");

        int[] codes = {200, 301, 404, 500, 101, 999};

        for (int code : codes) {
            System.out.println("Код " + code + ": " + getHttpStatusCategory(code));
        }
    }

    // =========================================================================
    // 3. Симулятор таймера повторных попыток (Exponential Backoff)
    // =========================================================================
    public static void task3() {
        System.out.println("=== Задание 3: Симулятор таймера Exponential Backoff ===");

        int maxAttempts = 5;
        int delaySeconds = 1;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            System.out.println("Попытка " + attempt + " из " + maxAttempts 
                    + " | Ожидание перед повтором: " + delaySeconds + " сек.");

            // Удваиваем задержку для следующей попытки (1 -> 2 -> 4 -> 8 -> 16)
            delaySeconds *= 2;
        }
    }

    // =========================================================================
    // 4. Пропуск поврежденных пакетов
    // =========================================================================
    public static void task4() {
        System.out.println("=== Задание 4: Пропуск поврежденных пакетов ===");

        // -1: Ошибка (continue), 0: Конец сессии (break), остальные: ID сообщений
        int[] messageIds = {101, 102, -1, 103, -1, 104, 0, 105, 106};

        System.out.println("Обработка пакетов сообщений:");
        for (int id : messageIds) {
            if (id == -1) {
                System.out.println(" [!] Пакет поврежден (ID: -1). Пропускаем (continue).");
                continue;
            }

            if (id == 0) {
                System.out.println(" [x] Получен сигналокончания сессии (ID: 0). Прерываем цикл (break).");
                break;
            }

            System.out.println(" -> Успешно обработано сообщение ID: " + id);
        }
    }

    // =========================================================================
    // 5. Контроль ввода пин-кода (do-while)
    // =========================================================================
    public static void task5() {
        System.out.println("=== Задание 5: Контроль ввода ПИН-кода ===");

        final String CORRECT_PIN = "4321";
        // Заготовленный список попыток для автоматической демонстрации
        String[] userInputs = {"1111", "0000", "4321"};
        
        int attempts = 0;
        int maxAttempts = 3;
        boolean isAccessGranted = false;

        do {
            String inputPin = userInputs[attempts]; // Имитация ввода от пользователя
            attempts++;

            System.out.println("Ввод ПИН-кода (Попытка " + attempts + " из " + maxAttempts + "): " + inputPin);

            if (CORRECT_PIN.equals(inputPin)) {
                isAccessGranted = true;
                System.out.println(" Доступ разрешен!");
                break;
            } else {
                System.out.println(" Неверный ПИН-код.");
            }

        } while (attempts < maxAttempts);

        if (!isAccessGranted) {
            System.out.println(" [!] Превышено количество попыток. Карта заблокирована!");
        }
    }
}
