import java.util.Scanner;

public class practicagvozd3 {
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
    // 1. Модель экрана настроек (SettingsModel)
    // =========================================================================
    static class SettingsModel {
        private boolean isDarkMode;
        private int volumeLevel; // от 0 до 100
        private String appLanguage;

        public SettingsModel(boolean isDarkMode, int volumeLevel, String appLanguage) {
            this.isDarkMode = isDarkMode;
            setVolumeLevel(volumeLevel); // валидация через сеттер
            this.appLanguage = appLanguage;
        }

        public boolean isDarkMode() {
            return isDarkMode;
        }

        public void setDarkMode(boolean darkMode) {
            isDarkMode = darkMode;
        }

        public int getVolumeLevel() {
            return volumeLevel;
        }

        public void setVolumeLevel(int volumeLevel) {
            if (volumeLevel < 0 || volumeLevel > 100) {
                System.out.println(" [Ошибка!] Попытка установить громкость " + volumeLevel 
                        + ". Разрешен диапазон 0..100. Установлено границами.");
                this.volumeLevel = Math.max(0, Math.min(100, volumeLevel));
            } else {
                this.volumeLevel = volumeLevel;
            }
        }

        public String getAppLanguage() {
            return appLanguage;
        }

        public void setAppLanguage(String appLanguage) {
            this.appLanguage = appLanguage;
        }
    }

    public static void task1() {
        System.out.println("=== Задание 1: Модель экрана настроек ===");

        SettingsModel settings = new SettingsModel(true, 50, "ru");
        System.out.println("Созданы настройки: Тёмная тема = " + settings.isDarkMode() 
                + ", Громкость = " + settings.getVolumeLevel() 
                + ", Язык = " + settings.getAppLanguage());

        System.out.println("\nПробуем задать громкость 150:");
        settings.setVolumeLevel(150);
        System.out.println("Текущая громкость: " + settings.getVolumeLevel());

        System.out.println("\nПробуем задать громкость -20:");
        settings.setVolumeLevel(-20);
        System.out.println("Текущая громкость: " + settings.getVolumeLevel());
    }

    // =========================================================================
    // 2. DTO корзины интернет-магазина (CartItem Record)
    // =========================================================================
    record CartItem(String id, String title, double price, int count) {
        // Дополнительный метод вычисления общей стоимости
        public double getTotalPrice() {
            return price * count;
        }
    }

    public static void task2() {
        System.out.println("=== Задание 2: DTO корзины (Record) ===");

        CartItem item1 = new CartItem("p101", "Беспроводные наушники", 4990.0, 2);
        CartItem item2 = new CartItem("p102", "Защитное стекло", 750.5, 3);

        System.out.println("Товар 1: " + item1.title() + " | Цена: " + item1.price() 
                + " | Кол-во: " + item1.count() + " | Итого: " + item1.getTotalPrice() + " ₽");
        System.out.println("Товар 2: " + item2.title() + " | Цена: " + item2.price() 
                + " | Кол-во: " + item2.count() + " | Итого: " + item2.getTotalPrice() + " ₽");

        double grandTotal = item1.getTotalPrice() + item2.getTotalPrice();
        System.out.printf("Общая стоимость корзины: %.2f ₽\n", grandTotal);
    }

    // =========================================================================
    // 3. Счетчик непрочитанных пушей (BadgeCounter)
    // =========================================================================
    static class BadgeCounter {
        private int count;

        public BadgeCounter(int initialCount) {
            setCount(initialCount);
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            if (count < 0) {
                System.out.println(" [Ошибка!] Счетчик не может быть отрицательным. Установлен в 0.");
                this.count = 0;
            } else {
                this.count = count;
            }
        }

        public void increment() {
            this.count++;
        }

        public void decrement() {
            if (this.count > 0) {
                this.count--;
            } else {
                System.out.println(" [Предупреждение] Уведомлений нет (0), уменьшить нельзя.");
            }
        }
    }

    public static void task3() {
        System.out.println("=== Задание 3: Счетчик непрочитанных пушей ===");

        BadgeCounter badge = new BadgeCounter(2);
        System.out.println("Начальное количество уведомлений: " + badge.getCount());

        badge.increment();
        System.out.println("Пришел новый пуш (increment): " + badge.getCount());

        badge.decrement();
        badge.decrement();
        System.out.println("Прочитали 2 пуша (decrement x2): " + badge.getCount());

        System.out.println("Пробуем уменьшить счетчик при 0:");
        badge.decrement();
        System.out.println("Итоговый счетчик: " + badge.getCount());

        System.out.println("Пробуем установить -5 напрямую:");
        badge.setCount(-5);
        System.out.println("Итоговый счетчик: " + badge.getCount());
    }

    // =========================================================================
    // 4. Инкапсулированный таймер сессии (SessionTracker)
    // =========================================================================
    static class SessionTracker {
        private static final long TIMEOUT_MS = 15 * 60 * 1000; // 15 минут в мс
        private final long loginTime;
        private long lastActionTime;

        public SessionTracker() {
            long now = System.currentTimeMillis();
            this.loginTime = now;
            this.lastActionTime = now;
        }

        public void updateLastAction() {
            this.lastActionTime = System.currentTimeMillis();
            System.out.println(" [Сессия] Действие пользователя зафиксировано.");
        }

        // Симуляция проверки со сторонним временем для удобства тестирования
        public boolean isExpiredAt(long currentTimeMillis) {
            return (currentTimeMillis - lastActionTime) > TIMEOUT_MS;
        }

        public boolean isExpired() {
            return isExpiredAt(System.currentTimeMillis());
        }

        public long getLoginTime() { return loginTime; }
        public long getLastActionTime() { return lastActionTime; }
    }

    public static void task4() {
        System.out.println("=== Задание 4: Инкапсулированный таймер сессии ===");

        SessionTracker session = new SessionTracker();
        long now = session.getLoginTime();

        System.out.println("Пользователь вошел в систему.");
        System.out.println("Проверка через 10 минут: Сессия истекла? " 
                + session.isExpiredAt(now + 10 * 60 * 1000));

        System.out.println("Проверка через 16 минут (без действий): Сессия истекла? " 
                + session.isExpiredAt(now + 16 * 60 * 1000));

        // Обновляем действие пользователей
        session.updateLastAction();
        long updatedTime = session.getLastActionTime();

        System.out.println("Проверка через 10 минут после последнего действия: Сессия истекла? " 
                + session.isExpiredAt(updatedTime + 10 * 60 * 1000));
    }

    // =========================================================================
    // 5. Модель геопозиции (GeoPoint)
    // =========================================================================
    static class GeoPoint {
        private final double latitude;
        private final double longitude;

        public GeoPoint(double latitude, double longitude) {
            if (latitude < -90.0 || latitude > 90.0) {
                throw new IllegalArgumentException("Некорректная широта: " + latitude + " (от -90.0 до 90.0)");
            }
            if (longitude < -180.0 || longitude > 180.0) {
                throw new IllegalArgumentException("Некорректная долгота: " + longitude + " (от -180.0 до 180.0)");
            }
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        @Override
        public String toString() {
            return "GeoPoint{lat=" + latitude + ", lon=" + longitude + "}";
        }
    }

    public static void task5() {
        System.out.println("=== Задание 5: Модель геопозиции ===");

        GeoPoint moscow = new GeoPoint(55.7558, 37.6173);
        System.out.println("Создана точка: " + moscow);

        System.out.println("\nПроверка валидации неверной широты (100.0):");
        try {
             new GeoPoint(100.0, 45.0);
        } catch (IllegalArgumentException e) {
            System.out.println(" Перехвачено исключение: " + e.getMessage());
        }

        System.out.println("\nПроверка валидации неверной долготы (-200.0):");
        try {
            new GeoPoint(45.0, -200.0);
        } catch (IllegalArgumentException e) {
            System.out.println(" Перехвачено исключение: " + e.getMessage());
        }
    }
}