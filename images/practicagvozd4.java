import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class practicagvozd4 {
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
    // 1. Иерархия экранов приложения
    // =========================================================================
    static abstract class BaseScreen {
        protected String title;

        public BaseScreen(String title) {
            this.title = title;
        }

        public void onOpen() {
            System.out.println("Открытие экрана: [" + title + "]");
        }

        public void onClose() {
            System.out.println("Закрытие экрана: [" + title + "]");
        }
    }

    static class LoginScreen extends BaseScreen {
        public LoginScreen() {
            super("Авторизация");
        }

        @Override
        public void onOpen() {
            super.onOpen();
            System.out.println(" -> Инициализация полей логина и пароля");
        }
    }

    static class HomeScreen extends BaseScreen {
        public HomeScreen() {
            super("Главная");
        }

        @Override
        public void onOpen() {
            super.onOpen();
            System.out.println(" -> Загрузка ленты новостей и профиля");
        }
    }

    static class SettingsScreen extends BaseScreen {
        public SettingsScreen() {
            super("Настройки");
        }

        @Override
        public void onOpen() {
            super.onOpen();
            System.out.println(" -> Считывание текущих конфигураций");
        }
    }

    public static void task1() {
        System.out.println("=== Задание 1: Иерархия экранов приложения ===");

        BaseScreen login = new LoginScreen();
        BaseScreen home = new HomeScreen();
        BaseScreen settings = new SettingsScreen();

        login.onOpen();
        login.onClose();
        System.out.println();

        home.onOpen();
        home.onClose();
        System.out.println();

        settings.onOpen();
        settings.onClose();
    }

    // =========================================================================
    // 2. Полиморфный обработчик аналитики
    // =========================================================================
    static abstract class AnalyticsEvent {
        private final long timestamp = System.currentTimeMillis();

        public abstract String getEventName();
        public abstract String getDetails();

        public void log() {
            System.out.println("[" + timestamp + "] " + getEventName() + " | " + getDetails());
        }
    }

    static class ClickEvent extends AnalyticsEvent {
        private final String buttonId;

        public ClickEvent(String buttonId) {
            this.buttonId = buttonId;
        }

        @Override public String getEventName() { return "CLICK_EVENT"; }
        @Override public String getDetails() { return "Клик по элементу: " + buttonId; }
    }

    static class PurchaseEvent extends AnalyticsEvent {
        private final String productId;
        private final double price;

        public PurchaseEvent(String productId, double price) {
            this.productId = productId;
            this.price = price;
        }

        @Override public String getEventName() { return "PURCHASE_EVENT"; }
        @Override public String getDetails() { return "Покупка товара " + productId + " на сумму " + price + " ₽"; }
    }

    static class ScreenViewEvent extends AnalyticsEvent {
        private final String screenName;

        public ScreenViewEvent(String screenName) {
            this.screenName = screenName;
        }

        @Override public String getEventName() { return "SCREEN_VIEW_EVENT"; }
        @Override public String getDetails() { return "Переход на экран: " + screenName; }
    }

    static class AnalyticsService {
        public void trackEvent(AnalyticsEvent event) {
            // Полиморфный вызов
            event.log();
        }
    }

    public static void task2() {
        System.out.println("=== Задание 2: Полиморфный обработчик аналитики ===");

        AnalyticsService analytics = new AnalyticsService();

        AnalyticsEvent e1 = new ScreenViewEvent("HomeScreen");
        AnalyticsEvent e2 = new ClickEvent("btn_buy_now");
        AnalyticsEvent e3 = new PurchaseEvent("item_9921", 1490.0);

        analytics.trackEvent(e1);
        analytics.trackEvent(e2);
        analytics.trackEvent(e3);
    }

    // =========================================================================
    // 3. Модели сенсоров смартфона
    // =========================================================================
    static abstract class DeviceSensor {
        private final String name;

        public DeviceSensor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public abstract String readData();
    }

    static class GyroscopeSensor extends DeviceSensor {
        public GyroscopeSensor() {
            super("Гироскоп");
        }

        @Override
        public String readData() {
            return "X: 0.12 rad/s, Y: -0.45 rad/s, Z: 9.81 rad/s";
        }
    }

    static class LightSensor extends DeviceSensor {
        public LightSensor() {
            super("Датчик освещенности");
        }

        @Override
        public String readData() {
            return "450 Lux (Средняя освещенность)";
        }
    }

    public static void task3() {
        System.out.println("=== Задание 3: Модели сенсоров смартфона ===");

        List<DeviceSensor> sensors = List.of(new GyroscopeSensor(), new LightSensor());

        for (DeviceSensor sensor : sensors) {
            System.out.println("Сенсор [" + sensor.getName() + "]: " + sensor.readData());
        }
    }

    // =========================================================================
    // 4. Виджеты с кастомной отрисовкой
    // =========================================================================
    static abstract class UiComponent {
        private final String id;

        public UiComponent(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public abstract void render();
    }

    static class Button extends UiComponent {
        private final String text;

        public Button(String id, String text) {
            super(id);
            this.text = text;
        }

        @Override
        public void render() {
            System.out.println("Отрисовка кнопки [" + getId() + "] -> \"" + text + "\"");
        }
    }

    static class ImageView extends UiComponent {
        private final String url;

        public ImageView(String id, String url) {
            super(id);
            this.url = url;
        }

        @Override
        public void render() {
            System.out.println("Отрисовка изображения [" + getId() + "] -> URL: " + url);
        }
    }

    static class TextView extends UiComponent {
        private final String text;

        public TextView(String id, String text) {
            super(id);
            this.text = text;
        }

        @Override
        public void render() {
            System.out.println("Отрисовка текста [" + getId() + "] -> \"" + text + "\"");
        }
    }

    public static void drawScreen(List<UiComponent> components) {
        System.out.println("--- Отрисовка экрана (всего компонентов: " + components.size() + ") ---");
        for (UiComponent component : components) {
            component.render();
        }
    }

    public static void task4() {
        System.out.println("=== Задание 4: Виджеты с кастомной отрисовкой ===");

        List<UiComponent> screenComponents = new ArrayList<>();
        screenComponents.add(new TextView("tv_header", "Добро пожаловать!"));
        screenComponents.add(new ImageView("img_avatar", "https://example.com/avatar.png"));
        screenComponents.add(new Button("btn_submit", "Войти в систему"));

        drawScreen(screenComponents);
    }

    // =========================================================================
    // 5. Тарифные планы подписки
    // =========================================================================
    static abstract class Subscription {
        private final String planName;
        private final double baseMonthlyPrice;

        public Subscription(String planName, double baseMonthlyPrice) {
            this.planName = planName;
            this.baseMonthlyPrice = baseMonthlyPrice;
        }

        public String getPlanName() { return planName; }
        public double getBaseMonthlyPrice() { return baseMonthlyPrice; }

        public abstract double calculateCost();
    }

    static class MonthlySubscription extends Subscription {
        public MonthlySubscription(double baseMonthlyPrice) {
            super("Ежемесячная", baseMonthlyPrice);
        }

        @Override
        public double calculateCost() {
            return getBaseMonthlyPrice();
        }
    }

    static class FamilySubscription extends Subscription {
        private final int userCount;
        private static final double EXTRA_USER_FEE = 150.0; // Доплата за пользователя

        public FamilySubscription(double baseMonthlyPrice, int userCount) {
            super("Семейная", baseMonthlyPrice);
            this.userCount = userCount;
        }

        @Override
        public double calculateCost() {
            int extraUsers = Math.max(0, userCount - 1);
            return getBaseMonthlyPrice() + (extraUsers * EXTRA_USER_FEE);
        }
    }

    static class AnnualDiscountSubscription extends Subscription {
        private final double discountPercent;

        public AnnualDiscountSubscription(double baseMonthlyPrice, double discountPercent) {
            super("Годовая со скидкой", baseMonthlyPrice);
            this.discountPercent = discountPercent;
        }

        @Override
        public double calculateCost() {
            double fullYearCost = getBaseMonthlyPrice() * 12;
            return fullYearCost * (1.0 - discountPercent / 100.0);
        }
    }

    public static void task5() {
        System.out.println("=== Задание 5: Тарифные планы подписки ===");

        Subscription mSub = new MonthlySubscription(399.0);
        Subscription fSub = new FamilySubscription(399.0, 4); // 4 пользователя
        Subscription aSub = new AnnualDiscountSubscription(399.0, 20.0); // 20% скидка за год

        System.out.printf("Тариф '%s': %.2f ₽ / мес\n", mSub.getPlanName(), mSub.calculateCost());
        System.out.printf("Тариф '%s' (на 4 чела): %.2f ₽ / мес\n", fSub.getPlanName(), fSub.calculateCost());
        System.out.printf("Тариф '%s' (за год со скидкой 20%%): %.2f ₽ / год\n", aSub.getPlanName(), aSub.calculateCost());
    }
}