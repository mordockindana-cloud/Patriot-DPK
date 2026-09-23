import java.util.Scanner;

public class practicagvozd

{
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

    // 1. Конвертер плотности (dp в px)
    public static void task1() {
        System.out.println("=== Задание 1: Конвертер dp в px ===");
        
        float dp = 16.0f;       // Размер в независимых пикселях
        float density = 2.0f;  // Коэффициент плотности (например, 2.0 для xhdpi)

        // Пиксели = dp * коэффициент плотности
        int px = Math.round(dp * density);

        System.out.println("Размер dp: " + dp);
        System.out.println("Коэффициент плотности (density): " + density);
        System.out.println("Результат в пикселях (px): " + px);
    }

    // 2. Парсинг таймстемпа
    public static void task2() {
        System.out.println("=== Задание 2: Парсинг таймстемпа ===");
        
        long millis = 12545000L; // Пример: 12 545 000 миллисекунд

        long totalSeconds = millis / 1000;
        long seconds = totalSeconds % 60;
        
        long totalMinutes = totalSeconds / 60;
        long minutes = totalMinutes % 60;
        
        long hours = totalMinutes / 60;

        System.out.println("Входное время (мс): " + millis);
        System.out.println("Результат: " + hours + " ч " + minutes + " мин " + seconds + " сек");
    }

    // 3. Расчет расхода батареи
    public static void task3() {
        System.out.println("=== Задание 3: Расчет расхода батареи ===");
        
        double batteryCapacity = 4000.0; // Емкость аккумулятора в мАч
        double commsConsumption = 150.0; // Среднее потребление связи в мА
        double displayConsumption = 250.0; // Среднее потребление дисплея в мА

        double totalConsumption = commsConsumption + displayConsumption; // Суммарный ток
        double batteryLifeHours = batteryCapacity / totalConsumption;

        System.out.println("Емкость аккумулятора: " + batteryCapacity + " мАч");
        System.out.println("Общее потребление: " + totalConsumption + " мА (Связь: " 
                            + commsConsumption + " мА + Дисплей: " + displayConsumption + " мА)");
        System.out.printf("Ориентировочное время работы: %.2f часов\n", batteryLifeHours);
    }

    // 4. Валидатор диапазона координат
    public static void task4() {
        System.out.println("=== Задание 4: Валидатор координат ===");
        
        double latitude = 55.7558;   // Широта (от -90.0 до 90.0)
        double longitude = 37.6173;  // Долгота (от -180.0 до 180.0)

        // Проверка на валидность с помощью логического И (&&)
        boolean isLatValid = latitude >= -90.0 && latitude <= 90.0;
        boolean isLonValid = longitude >= -180.0 && longitude <= 180.0;
        boolean isValid = isLatValid && isLonValid;

        // Проверка на ошибку с помощью логического ИЛИ (||)
        boolean isInvalid = latitude < -90.0 || latitude > 90.0 || longitude < -180.0 || longitude > 180.0;

        System.out.println("Координаты: Широта = " + latitude + ", Долгота = " + longitude);
        System.out.println("Координаты в допустимом диапазоне (&&): " + isValid);
        System.out.println("Координаты выходят за пределы (||): " + isInvalid);
    }

    // 5. Побитовые флаги разрешений
    public static void task5() {
        System.out.println("=== Задание 5: Побитовые флаги разрешений ===");
        
        final int CAMERA = 1;   // 001 в двоичной системе
        final int LOCATION = 2; // 010 в двоичной системе
        final int STORAGE = 4;  // 100 в двоичной системе

        int permissions = 0; // Флаги сброшены (000)

        // 1. Установка разрешений с помощью побитового ИЛИ (|)

        permissions |= CAMERA;
        permissions |= STORAGE;
        System.out.println("Установили CAMERA и STORAGE.");
        System.out.println("Маска разрешений: " + permissions + " (в binary: " + Integer.toBinaryString(permissions) + ")");

        // 2. Проверка наличия разрешения с помощью побитового И (&)
        boolean hasCamera = (permissions & CAMERA) != 0;
        boolean hasLocation = (permissions & LOCATION) != 0;
        System.out.println("Разрешение CAMERA установлено? " + hasCamera);
        System.out.println("Разрешение LOCATION установлено? " + hasLocation);

        // 3. Снятие разрешения с помощью побитового И (&) и НЕ (~)
        permissions &= ~CAMERA;
        System.out.println("\nСняли разрешение CAMERA.");
        System.out.println("Маска разрешений: " + permissions + " (в binary: " + Integer.toBinaryString(permissions) + ")");

        hasCamera = (permissions & CAMERA) != 0;
        System.out.println("Разрешение CAMERA установлено? " + hasCamera);
    }
}