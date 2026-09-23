import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class practicagvozd6 {
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
    // 1. Удаление дубликатов контактов
    // =========================================================================
    public static void task1() {
        System.out.println("=== Задание 1: Удаление дубликатов контактов ===");

        List<String> rawPhoneNumbers = List.of(
                "+7 (999) 111-22-33",
                "+7 (999) 222-33-44",
                "+7 (999) 111-22-33", // дубликат
                "+7 (999) 555-66-77",
                "+7 (999) 222-33-44"  // дубликат
        );

        System.out.println("Исходный список (" + rawPhoneNumbers.size() + " элементов):");
        rawPhoneNumbers.forEach(number -> System.out.println(" - " + number));

        // LinkedHashSet сохраняет порядок добавления и удаляет дубликаты
        Set<String> uniquePhoneNumbers = new LinkedHashSet<>(rawPhoneNumbers);

        System.out.println("\nОчищенный список (" + uniquePhoneNumbers.size() + " элементов):");
        uniquePhoneNumbers.forEach(number -> System.out.println(" - " + number));
    }

    // =========================================================================
    // 2. Очередь сетевых запросов
    // =========================================================================
    public static void task2() {
        System.out.println("=== Задание 2: Очередь сетевых запросов (FIFO) ===");

        Queue<String> syncQueue = new ArrayDeque<>();

        // Наполнение очереди
        syncQueue.add("POST /api/v1/user/profile");
        syncQueue.add("PUT /api/v1/settings/theme");
        syncQueue.add("POST /api/v1/analytics/event");
        syncQueue.add("DELETE /api/v1/cache/temp");

        System.out.println("Запросов в очереди: " + syncQueue.size());

        // Последовательная обработка (First-In, First-Out)
        int step = 1;
        while (!syncQueue.isEmpty()) {
            String request = syncQueue.poll(); // Извлекает главный элемент из головы очереди
            System.out.println("Шаг " + step++ + ": Синхронизация -> " + request);
        }

        System.out.println("Все запросы обработаны. Очередь пуста: " + syncQueue.isEmpty());
    }

    // =========================================================================
    // 3. Обобщенный ответ API (Generic ApiResponse)
    // =========================================================================
    static class ApiResponse<T> {
        private final int statusCode;
        private final T data;
        private final String errorMessage;

        public ApiResponse(int statusCode, T data, String errorMessage) {
            this.statusCode = statusCode;
            this.data = data;
            this.errorMessage = errorMessage;
        }

        public static <T> ApiResponse<T> success(T data) {
            return new ApiResponse<>(200, data, null);
        }

        public static <T> ApiResponse<T> error(int statusCode, String errorMessage) {
            return new ApiResponse<>(statusCode, null, errorMessage);
        }

        public boolean isSuccessful() {
            return statusCode >= 200 && statusCode < 300;
        }

        public int getStatusCode() { return statusCode; }
        public T getData


() { return data; }
        public String getErrorMessage() { return errorMessage; }

        @Override
        public String toString() {
            return "ApiResponse{" +
                    "statusCode=" + statusCode +
                    ", data=" + data +
                    ", errorMessage='" + errorMessage + '\'' +
                    '}';
        }
    }

    // DTO для демонстрации
    record UserProfile(String id, String name) {}

    public static void task3() {
        System.out.println("=== Задание 3: Обобщенный ответ API (ApiResponse<T>) ===");

        // Успешный ответ с типом данных UserProfile
        ApiResponse<UserProfile> userResponse = ApiResponse.success(new UserProfile("u_42", "Иван Иванов"));
        System.out.println("Запрос пользователя:");
        System.out.println("Успешно? " + userResponse.isSuccessful());
        System.out.println("Данные: " + userResponse.getData());

        System.out.println();

        // Ошибка с типом данных String
        ApiResponse<String> tokenResponse = ApiResponse.error(401, "Токен авторизации истек");
        System.out.println("Запрос токена:");
        System.out.println("Успешно? " + tokenResponse.isSuccessful());
        System.out.println("Ошибка: " + tokenResponse.getErrorMessage());
    }

    // =========================================================================
    // 4. Сортировка товаров по цене и популярности
    // =========================================================================
    static class Product {
        private final String title;
        private final double price;
        private final double rating;

        public Product(String title, double price, double rating) {
            this.title = title;
            this.price = price;
            this.rating = rating;
        }

        public String getTitle() { return title; }
        public double getPrice() { return price; }
        public double getRating() { return rating; }

        @Override
        public String toString() {
            return String.format("%-22s | Цена: %7.2f ₽ | Рейтинг: %.1f", title, price, rating);
        }
    }

    public static void task4() {
        System.out.println("=== Задание 4: Сортировка товаров ===");

        List<Product> products = new ArrayList<>(List.of(
                new Product("Зарядное устройство", 1500.0, 4.5),
                new Product("Защитное стекло", 500.0, 4.2),
                new Product("Кабель Type-C", 500.0, 4.9), // Одинаковая цена, выше рейтинг
                new Product("Чехол силиконовый", 500.0, 4.0), // Одинаковая цена, ниже рейтинг
                new Product("Беспроводные наушники", 3500.0, 4.8)
        ));

        System.out.println("До сортировки:");
        products.forEach(System.out::println);

        // Сначала по возрастанию цены, а при равной цене — по убыванию рейтинга
        products.sort(Comparator.comparingDouble(Product::getPrice)
                .thenComparing(Comparator.comparingDouble(Product::getRating).reversed()));

        System.out.println("\nПосле сортировки (Цена ↑, затем Рейтинг ↓):");
        products.forEach(System.out::println);
    }

    // =========================================================================
    // 5. Кэш экранов (LRU Cache концепт)
    // =========================================================================
    static class LruScreenCache<K, V> extends LinkedHashMap<K, V> {
        private final int capacity;

        public LruScreenCache(int capacity) {
            // accessOrder = true означает, что порядок элементов перестраивается при обращениях (get/put)
            super(capacity, 0.75f, true);
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            // Автоматически выселяет самый старый элемент при превышении емкости
            boolean shouldRemove = size() > capacity;
            if (shouldRemove) {
                System.out.println("[LRU Cache] Лимит достигнут (" + capacity + "). Выселяем экран: " + eldest.getValue());
            }
            return shouldRemove;
        }
    }

    public static void task5() {
        System.out.println("=== Задание 5: LRU Кэш экранов (Емкость = 5) ===");

        LruScreenCache<String, String> fragmentCache = new LruScreenCache<>(5);

        System.out.println("Открываем 5 экранов подряд:");
        fragmentCache.put("frag_1", "HomeFragment");
        fragmentCache.put("frag_2", "CatalogFragment");
        fragmentCache.put("frag_3", "ProfileFragment");
        fragmentCache.put("frag_4", "SettingsFragment");
        fragmentCache.put("frag_5", "CartFragment");

        System.out.println("Текущий кэш (" + fragmentCache.size() + "): " + fragmentCache.values());

        System.out.println("\nОбращаемся к 'CatalogFragment' (перемещает его в конец как недавно использованный):");
        fragmentCache.get("frag_2");
        System.out.println("Текущий кэш: " + fragmentCache.values());

        System.out.println("\nОткрываем 6-й и 7-й экраны (должны выселить 'HomeFragment' и 'ProfileFragment'):");
        fragmentCache.put("frag_6", "CheckoutFragment");
        fragmentCache.put("frag_7", "PaymentStatusFragment");

        System.out.println("\nИтоговое состояние кэша (" + fragmentCache.size() + " элементов):");
        fragmentCache.values().forEach(fragment -> System.out.println(" - " + fragment));
    }
}