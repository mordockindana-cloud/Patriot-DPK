import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class practicagvozd7 {
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
    // 1. Пользовательское исключение отсутствия сети
    // =========================================================================
    // Проверяемое исключение (extends Exception)
    static class NoInternetException extends Exception {
        public NoInternetException(String message) {
            super(message);
        }
    }

    public static void fetchData(boolean hasConnection) throws NoInternetException {
        if (!hasConnection) {
            throw new NoInternetException("Ошибка: Соединение с интернетом отсутствует!");
        }
        System.out.println("Успешно: Данные профиля пользователя загружены из сети.");
    }

    public static void task1() {
        System.out.println("=== Задание 1: Пользовательское исключение отсутствия сети ===");

        // Симуляция успешного запроса
        try {
            fetchData(true);
        } catch (NoInternetException e) {
            System.out.println(" Перехвачено исключение: " + e.getMessage());
        }

        // Симуляция запроса без интернета
        try {
            fetchData(false);
        } catch (NoInternetException e) {
            System.out.println(" Перехвачено исключение: " + e.getMessage());
        }
    }

    // =========================================================================
    // 2. Конструкция try-with-resources
    // =========================================================================
    public static void task2() {
        System.out.println("=== Задание 2: Try-with-resources (BufferedReader) ===");

        String mockConfigFileContent = "app_name=AndroidApp\nversion_code=102\napi_url=https://api.example.com";
        System.out.println("Имитация чтения файла конфига:");

        // Поток ввода автоматически закроется по завершении блока try
        try (BufferedReader reader = new BufferedReader(new StringReader(mockConfigFileContent))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(" [Считано] " + line);
            }
        } catch (IOException e) {
            System.out.println(" Ошибка ввода-вывода при чтении файла: " + e.getMessage());
        }
    }

    // =========================================================================
    // 3. Парсинг JSON-поля возраста
    // =========================================================================
    // Непроверяемое исключение (extends RuntimeException)
    static class InvalidUserDataException extends RuntimeException {
        public InvalidUserDataException(String message) {
            super(message);
        }
    }

    public static int parseAge(String ageStr) {
        if (ageStr == null) {
            throw new InvalidUserDataException("Поле возраста не может быть null.");
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            throw new InvalidUserDataException("Некорректный числовой формат возраста: '" + ageStr + "'");
        }

        if (age < 0 || age > 130) {
            throw new InvalidUserDataException("Возраст выходить за валидный диапазон (0..130): " + age);
        }

        return age;
    }

    public static void task3() {
        System.out.println("=== Задание 3: Парсинг JSON-поля возраста ===");

        String[] testCases = {"25", "-5", "150", "abc"};

        for (String testCase : testCases) {
            try {
                int age = parseAge(testCase);
                System.out.println(" Успешный парсинг ('" + testCase + "'): Возраст = " + age);
            } catch (InvalidUserDataException e) {
                System.out.println(" [InvalidUserDataException] Перехвачена ошибка для '" + testCase + "': " + e.getMessage());
            }
        }
    }

    // =========================================================================
    // 4. Множественные блоки catch
    // =========================================================================
    public static void processData(String str, int index) {
        try {
            System.out.println("Обработка строки: " + str);
            System.out.println("Длина строки: " + str.length()); // NPE при str == null

            char character = str.charAt(index); // IndexOutOfBoundsException при некорректном индексе
            System.out.println("Символ на позиции [" + index + "]: " + character);

            int result = 100 / index; // ArithmeticException при index == 0 (попадет в общий Exception)
            System.out.println("Результат деления 100 / " + index + " = " + result);

        } catch (NullPointerException e) {
            System.out.println(" [Catch NPE] Ссылка на строку равна null!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(" [Catch IndexOutOfBounds] Индекс " + index + " выходит за границы строки!");
        } catch (Exception e) {
            System.out.println(" [Catch General Exception] Перехвачено иное исключение: " 
                    + e.getClass().getSimpleName() + " (" + e.getMessage() + ")");
        }
    }

    public static void task4() {
        System.out.println("=== Задание 4: Множественные блоки catch ===");

        System.out.println("--- Тест 1 (Вызов NPE) ---");
        processData(null, 0);

        System.out.println("\n--- Тест 2 (Вызов IndexOutOfBoundsException) ---");
        processData("Android", 10);

        System.out.println("\n--- Тест 3 (Вызов ArithmeticException -> попадает в общий Exception) ---");
        processData("Android", 0);

        System.out.println("\n--- Тест 4 (Успешный) ---");
        processData("Android", 2);
    }

    // =========================================================================
    // 5. Безопасное извлечение значения из Bundle
    // =========================================================================
    // Имитация Android-класса Bundle для демонстрации
    static class Bundle {
        private final Map<String, Object> data = new HashMap<>();

        public void put(String key, Object value) {
            data.put(key, value);
        }

        public Object get(String key) {
            return data.get(key);
        }
    }

    public static String getStringSafely(Bundle bundle, String key, String defaultValue) {
        try {
            if (bundle == null || key == null) {
                return defaultValue;
            }
            Object value = bundle.get(key);
            if (value == null) {
                return defaultValue;
            }
            // Попытка приведения типов может сгенерировать ClassCastException
            return (String) value;
        } catch (Exception e) {
            System.out.println(" [Bundle Error] Безопасная обработка ошибки (" 
                    + e.getClass().getSimpleName() + ") для ключа '" + key + "'. Возвращено значение по умолчанию.");
            return defaultValue;
        }
    }

    public static void task5() {
        System.out.println("=== Задание 5: Безопасное извлечение из Bundle ===");

        Bundle bundle = new Bundle();
        bundle.put("user_name", "Алексей");
        bundle.put("user_id", 100500); // Сохранено число (int), а не String

        String name = getStringSafely(bundle, "user_name", "Гость");


System.out.println("Результат 1 (Существующий ключ): " + name);

        String missingKey = getStringSafely(bundle, "non_existing_key", "Значение по умолчанию");
        System.out.println("Результат 2 (Отсутствующий ключ): " + missingKey);

        String wrongType = getStringSafely(bundle, "user_id", "Идентификатор не найден");
        System.out.println("Результат 3 (Неверный тип данных в Bundle): " + wrongType);

        String nullBundle = getStringSafely(null, "user_name", "Дефолт для null-бандла");
        System.out.println("Результат 4 (Null Bundle): " + nullBundle);
    }
}