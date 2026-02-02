package com.way2automation.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

/**
 * Утилитарный класс для управления WebDriver с поддержкой многопоточности.
 * Обеспечивает создание, хранение и управление экземплярами WebDriver в ThreadLocal.
 */
public class WebDriverManagerUtil {

    /**
     * ThreadLocal для хранения экземпляра WebDriver, обеспечивающий изоляцию между потоками.
     */
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    /**
     * Приватный конструктор для предотвращения создания экземпляров класса.
     */
    private WebDriverManagerUtil() {
        // Утилитарный класс - не предназначен для инстанцирования
    }

    /**
     * Получает экземпляр WebDriver для текущего потока.
     * Если драйвер еще не инициализирован, создает новый экземпляр.
     *
     * @return экземпляр WebDriver для текущего потока
     */
    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            initializeDriver();
        }
        return driverThreadLocal.get();
    }

    /**
     * Инициализирует новый экземпляр ChromeDriver с настройками по умолчанию.
     * Настраивает менеджер драйверов, опции браузера и таймауты.
     */
    private static void initializeDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        driverThreadLocal.set(driver);
    }

    /**
     * Перезапускает драйвер для текущего потока.
     * Закрывает существующий драйвер и создает новый экземпляр.
     */
    public static void resetDriver() {
        quitDriver();
        initializeDriver();
    }

    /**
     * Получает существующий экземпляр WebDriver без его инициализации.
     * Возвращает null, если драйвер не был инициализирован.
     *
     * @return существующий экземпляр WebDriver или null
     */
    public static WebDriver getExistingDriver() {
        return driverThreadLocal.get();
    }

    /**
     * Завершает работу WebDriver и очищает ThreadLocal.
     * Вызывает метод quit() для закрытия браузера и освобождения ресурсов.
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}
