import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class practicagvozd5 {
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
    // 1. Контракт хранилища данных
    // =========================================================================
    interface KeyValueStorage {
        void save(String key, String value);
        String get(String key);
        void clear();
    }

    static class MemoryStorage implements KeyValueStorage {
        private final Map<String, String> storage = new HashMap<>();

        @Override
        public void save(String key, String value) {
            storage.put(key, value);
            System.out.println(" [MemoryStorage] Сохранено: " + key + " -> " + value);
        }

        @Override
        public String get(String key) {
            return storage.get(key);
        }

        @Override
        public void clear() {
            storage.clear();
            System.out.println(" [MemoryStorage] Хранилище очищено.");
        }
    }

    public static void task1() {
        System.out.println("=== Задание 1: Контракт хранилища данных ===");

        KeyValueStorage storage = new MemoryStorage();
        storage.save("auth_token", "eyJhbGciOiJIUzI1NiJ9...");
        storage.save("user_id", "42");

        System.out.println("Запрос token: " + storage.get("auth_token"));
        System.out.println("Запрос user_id: " + storage.get("user_id"));

        storage.clear();
        System.out.println("Запрос token после очистки: " + storage.get("auth_token"));
    }

    // =========================================================================
    // 2. Колбэк загрузки изображения
    // =========================================================================
    interface ImageLoadCallback {
        void onSuccess(String bitmapRef);
        void onError(Throwable error);
    }

    public static void loadImage(String url, ImageLoadCallback callback) {
        System.out.println(" [ImageLoader] Загрузка изображения с " + url + "...");
        if (url != null && url.startsWith("https://")) {
            callback.onSuccess("BitmapRef[0x8F4A, size 1024x768]");
        } else {
            callback.onError(new IllegalArgumentException("Небезопасный или неверный URL: " + url));
        }
    }

    public static void task2() {
        System.out.println("=== Задание 2: Колбэк загрузки изображения ===");

        // Успешный вызов
        loadImage("https://example.com/avatar.png", new ImageLoadCallback() {
            @Override
            public void onSuccess(String bitmapRef) {
                System.out.println(" Успех: Изображение загружено -> " + bitmapRef);
            }

            @Override
            public void onError(Throwable error) {
                System.out.println(" Ошибка: " + error.getMessage());
            }
        });

        System.out.println();

        // Неуспешный вызов
        loadImage("http://insecure-site.com/image.jpg", new ImageLoadCallback() {
            @Override
            public void onSuccess(String bitmapRef) {
                System.out.println(" Успех: " + bitmapRef);
            }

            @Override
            public void onError(Throwable error) {
                System.out.println(" Перехвачена ошибка: " + error.getMessage());
            }
        });
    }

    // =========================================================================
    // 3. Слушатель жизненного цикла фоновой задачи
    // ===============================
 



    interface BackgroundTaskListener {
        void onStart();
        void onComplete();

        // Default-метод с базовой реализацией
        default void onProgress(int percentage) {
            System.out.println(" [Прогресс по умолчанию] Выполнено: " + percentage + "%");
        }
    }

    public static void task3() {
        System.out.println("=== Задание 3: Слушатель жизненного цикла фоновой задачи ===");

        BackgroundTaskListener listener = new BackgroundTaskListener() {
            @Override
            public void onStart() {
                System.out.println("Задача запущена.");
            }

            @Override
            public void onComplete() {
                System.out.println("Задача успешно завершена!");
            }

            @Override
            public void onProgress(int percentage) {
                System.out.println("Кастомный прогресс бар: [" + "=".repeat(percentage / 10) + "] " + percentage + "%");
            }
        };

        listener.onStart();
        listener.onProgress(30);
        listener.onProgress(75);
        listener.onComplete();
    }

    // =========================================================================
    // 4. Множественная реализация
    // =========================================================================
    interface Playable {
        void play();
        void stop();
    }

    interface Shareable {
        void shareViaBluetooth();
    }

    static class MediaFile implements Playable, Shareable {
        private final String title;

        public MediaFile(String title) {
            this.title = title;
        }

        @Override
        public void play() {
            System.out.println(" [Плеер] Воспроизведение трека: " + title);
        }

        @Override
        public void stop() {
            System.out.println(" [Плеер] Воспроизведение остановлено: " + title);
        }

        @Override
        public void shareViaBluetooth() {
            System.out.println(" [Bluetooth] Передача файла \"" + title + "\" по Bluetooth...");
        }
    }

    public static void task4() {
        System.out.println("=== Задание 4: Множественная реализация ===");

        MediaFile track = new MediaFile("favorite_song.mp3");

        // Использование как Playable
        Playable playable = track;
        playable.play();
        playable.stop();

        // Использование как Shareable
        Shareable shareable = track;
        shareable.shareViaBluetooth();
    }

    // =========================================================================
    // 5. Функциональный интерфейс для фильтрации
    // =========================================================================
    @FunctionalInterface
    interface PredicateValidator<T> {
        boolean validate(T data);
    }

    public static void task5() {
        System.out.println("=== Задание 5: Функциональный интерфейс ===");

        // Валидатор строки на пустую или null
        PredicateValidator<String> notEmptyValidator = str -> str != null && !str.trim().isEmpty();

        // Валидатор возраста (должен быть >= 18)
        PredicateValidator<Integer> adultAgeValidator = age -> age != null && age >= 18;

        System.out.println("Проверка строки 'Android': " + notEmptyValidator.validate("Android"));
        System.out.println("Проверка пустой строки '   ': " + notEmptyValidator.validate("   "));

        System.out.println("Проверка возраста 21: " + adultAgeValidator.validate(21));
        System.out.println("Проверка возраста 15: " + adultAgeValidator.validate(15));
    }
}