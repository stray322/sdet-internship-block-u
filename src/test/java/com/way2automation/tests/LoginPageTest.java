package com.way2automation.tests;

import com.way2automation.pages.LoginPage;
import com.way2automation.utils.WebDriverManagerUtil;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * Тестовый класс для проверки функциональности страницы логина.
 * Содержит тесты для проверки авторизации, валидации полей и разлогина.
 */
public class LoginPageTest extends BaseTest {

    /**
     * Невалидное имя пользователя для тестов
     */
    private static final String INVALID_USERNAME = "invalid";

    /**
     * Невалидный пароль для тестов
     */
    private static final String INVALID_PASSWORD = "invalid123";

    /**
     * Экземпляр страницы логина для взаимодействия с элементами
     */
    private LoginPage loginPage;

    /**
     * Метод настройки перед выполнением каждого теста.
     * Инициализирует WebDriver, открывает страницу логина и создает SoftAssert.
     */
    @BeforeMethod
    public void setUp() {
        super.setUp();
        driver = WebDriverManagerUtil.getDriver();
        loginPage = new LoginPage(driver);
        loginPage.open();
        softAssert = new SoftAssert();
    }

    /**
     * Проверка открытия страницы логина.
     * Тест проверяет, что страница логина успешно открывается
     * и отображается информационное сообщение с учетными данными.
     */
    @Test(description = "Проверка открытия страницы логина")
    public void testLoginPageOpening() {
        boolean isLoginPageOpened = loginPage.isOpened();
        String credentialsInfo = loginPage.getCredentialsInfoText();

        softAssert.assertTrue(isLoginPageOpened,
                "Страница логина должна открываться");
        softAssert.assertTrue(credentialsInfo.contains("angular") &&
                        credentialsInfo.contains("password"),
                "Должно отображаться информационное сообщение с учетными данными");

        softAssert.assertAll();
    }

    /**
     * Тест успешной авторизации с валидными учетными данными.
     * Проверяет, что после успешного логина отображается сообщение об успехе
     * или ссылка для выхода из системы.
     */
    @Test(description = "Успешный логин с валидными учетными данными")
    public void testSuccessfulLogin() {
        loginPage.loginWithValidCredentials();
        boolean isSuccessDisplayed = loginPage.isSuccessMessageDisplayed();
        boolean isLogoutDisplayed = loginPage.isLogoutLinkDisplayed();
        boolean containsSuccessRussian = loginPage.pageContainsSuccessText();
        boolean containsLogoutRussian = loginPage.pageContainsLogoutText();
        boolean loginSuccessful = isSuccessDisplayed || isLogoutDisplayed || containsSuccessRussian || containsLogoutRussian;
        softAssert.assertTrue(loginSuccessful,
                "После успешного логина должно отображаться сообщение об успехе или ссылка выхода");
        softAssert.assertAll();
    }

    /**
     * Тест неуспешной авторизации с невалидными учетными данными.
     * Проверяет, что при вводе невалидных данных отображается сообщение об ошибке,
     * которое не является пустым или null.
     */
    @Test(description = "Неуспешный логин с невалидными учетными данными")
    public void testInvalidLogin() {
        loginPage.loginWithInvalidCredentials(INVALID_USERNAME, INVALID_PASSWORD);
        boolean isErrorDisplayed = loginPage.isErrorMessageDisplayed();
        String errorMessage = loginPage.getErrorMessageText();
        softAssert.assertTrue(isErrorDisplayed,
                "При невалидном логине должно отображаться сообщение об ошибке");
        softAssert.assertNotNull(errorMessage,
                "Сообщение об ошибке не должно быть null");
        softAssert.assertFalse(errorMessage.isEmpty(),
                "Сообщение об ошибке не должно быть пустым");
        softAssert.assertAll();
    }

    /**
     * Проверка валидации пустых полей ввода.
     * Тест проверяет, что кнопка логина неактивна, когда поля username и password пусты.
     */
    @Test(description = "Проверка валидации пустых полей")
    public void testEmptyFieldsValidation() {
        String disabled = loginPage.getLoginButton().getAttribute("disabled");
        boolean isButtonEnabled = loginPage.getLoginButton().isEnabled();
        softAssert.assertFalse(isButtonEnabled,
                "Кнопка Login должна быть неактивной при пустых полях");
        softAssert.assertAll();
    }

    /**
     * Проверка состояния кнопки логина при различных сценариях ввода данных.
     * Тест проверяет три состояния:
     * 1. Кнопка неактивна при пустых полях
     * 2. Кнопка остается неактивной при невалидных данных
     * 3. Кнопка становится активной при валидных данных
     */
    @Test(description = "Проверка состояния кнопки логина при невалидных данных")
    public void testLoginButtonState() {
        boolean isButtonEnabledInitially = loginPage.getLoginButton().isEnabled();
        softAssert.assertFalse(isButtonEnabledInitially,
                "1. Кнопка Login должна быть неактивной при пустых полях");

        loginPage.enterUsername("ab");
        loginPage.enterPassword("pa");
        loginPage.enterDynamicUsername("ab");

        boolean isButtonEnabledAfterInvalidInput = loginPage.getLoginButton().isEnabled();
        softAssert.assertFalse(isButtonEnabledAfterInvalidInput,
                "2. Кнопка Login должна оставаться неактивной при невалидных данных");

        loginPage.enterUsername("validuser");
        loginPage.enterPassword("validpassword");
        loginPage.enterDynamicUsername("validuser");
        loginPage.waitForLoginButtonToBeEnabled();

        boolean isButtonEnabledAfterValidInput = loginPage.getLoginButton().isEnabled();
        softAssert.assertTrue(isButtonEnabledAfterValidInput,
                "3. Кнопка Login должна стать активной при валидных данных");

        softAssert.assertAll();
    }

    /**
     * Тест успешного разлогина из системы.
     * Проверяет полный сценарий: вход в систему, проверка успешного входа,
     * выход из системы и проверка возврата на страницу логина.
     */
    @Test(description = "Проверка успешного разлогина")
    public void testSuccessfulLogout() {
        loginPage.loginWithValidCredentials();
        boolean isLoggedIn = loginPage.isSuccessMessageDisplayed() ||
                loginPage.isLogoutLinkDisplayed();
        softAssert.assertTrue(isLoggedIn, "Должны быть залогинены");

        boolean logoutSuccessful = loginPage.logout();
        softAssert.assertTrue(logoutSuccessful, "Разлогин должен быть выполнен успешно");

        boolean isOnLoginPage = driver.getCurrentUrl().contains("login") ||
                driver.findElements(By.id("username")).size() > 0;
        softAssert.assertTrue(isOnLoginPage,
                "После разлогина должна отображаться страница логина");

        boolean isLoginFormVisible = driver.findElements(By.id("username")).size() > 0 &&
                driver.findElements(By.id("password")).size() > 0;
        softAssert.assertTrue(isLoginFormVisible,
                "Форма логина должна быть видима после разлогина");

        softAssert.assertAll();
    }

    /**
     * Метод очистки после выполнения каждого теста.
     * Закрывает браузер и освобождает ресурсы.
     */
    @AfterMethod
    public void tearDown() {
        WebDriverManagerUtil.quitDriver();
    }
}
