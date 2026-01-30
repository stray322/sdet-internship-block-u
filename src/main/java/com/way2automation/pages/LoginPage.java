package com.way2automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

/**
 * Класс Page Object для страницы логина.
 * Содержит элементы и методы для взаимодействия со страницей авторизации.
 */
public class LoginPage extends BasePage {

    /**
     * Базовый URL страницы логина
     */
    private static final String BASE_URL = "https://www.way2automation.com/angularjs-protractor/registeration/#/login";

    /**
     * Валидное имя пользователя для авторизации
     */
    private static final String VALID_USERNAME = "angular";

    /**
     * Валидный пароль для авторизации
     */
    private static final String VALID_PASSWORD = "password";

    /**
     * Поле ввода имени пользователя
     */
    @FindBy(xpath = "//input[@id='username']")
    private WebElement usernameInput;

    /**
     * Поле ввода пароля
     */
    @FindBy(xpath = "//input[@id='password' and @type='password']")
    private WebElement passwordInput;

    /**
     * Кнопка для выполнения логина
     */
    @FindBy(xpath = "//button[contains(@class, 'btn-danger') and contains(text(), 'Login')]")
    private WebElement loginButton;

    /**
     * Информационное сообщение с учетными данными
     */
    @FindBy(xpath = "//div[contains(@class, 'alert-info')]")
    private WebElement credentialsInfo;

    /**
     * Элемент сообщения об ошибке
     */
    @FindBy(xpath = "//div[contains(@class, 'alert-danger')]")
    private WebElement errorMessageElement;

    /**
     * Элемент ошибки валидации для обязательного поля имени пользователя
     */
    @FindBy(xpath = "//div[@ng-messages='form.username.$error']//div[@ng-message='required']")
    private WebElement usernameRequiredError;

    /**
     * Элемент ошибки валидации для обязательного поля пароля
     */
    @FindBy(xpath = "//div[@ng-messages='form.password.$error']//div[@ng-message='required']")
    private WebElement passwordRequiredError;

    /**
     * Форма логина
     */
    @FindBy(xpath = "//form[@name='form']")
    private WebElement loginForm;

    /**
     * Динамическое поле ввода имени пользователя
     */
    @FindBy(xpath = "//input[@ng-model='model[options.key]']")
    private WebElement dynamicUsernameInput;

    /**
     * Сообщение об успешном логине на русском языке
     */
    @FindBy(xpath = "//*[contains(text(), 'Вы вошли в систему!')]")
    private WebElement successMessageRussian;

    /**
     * Ссылка для выхода на русском языке
     */
    @FindBy(xpath = "//a[contains(text(), 'Выйти')]")
    private WebElement logoutLinkRussian;

    /**
     * Сообщение об успешном логине на английском языке
     */
    @FindBy(xpath = "//*[contains(text(), \"You're logged in!!\")]")
    private WebElement successMessageEnglish;

    /**
     * Ссылка для выхода на английском языке
     */
    @FindBy(xpath = "//a[contains(text(), 'Logout')]")
    private WebElement logoutLinkEnglish;

    /**
     * Конструктор класса LoginPage.
     * Инициализирует элементы страницы с помощью PageFactory.
     *
     * @param driver экземпляр WebDriver для управления браузером
     */
    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Открывает страницу логина в браузере.
     * Переходит по базовому URL и ожидает загрузки страницы.
     */
    public void open() {
        driver.get(BASE_URL);
        waitForPageToLoad();
    }

    /**
     * Проверяет, открыта ли страница логина.
     * Ожидает видимости формы логина и проверяет URL или отображение формы.
     *
     * @return true если страница логина открыта, иначе false
     */
    public boolean isOpened() {
        waitForElementToBeVisible(loginForm);
        return driver.getCurrentUrl().contains("login")
                || loginForm.isDisplayed();
    }

    /**
     * Вводит имя пользователя в соответствующее поле.
     * Ожидает видимости поля, очищает его и вводит указанное имя.
     *
     * @param username имя пользователя для ввода
     */
    public void enterUsername(String username) {
        waitForElementToBeVisible(usernameInput);
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    /**
     * Вводит пароль в соответствующее поле.
     * Ожидает видимости поля, очищает его и вводит указанный пароль.
     *
     * @param password пароль для ввода
     */
    public void enterPassword(String password) {
        waitForElementToBeVisible(passwordInput);
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    /**
     * Вводит значение в динамическое поле (третье поле).
     * Ожидает видимости поля, очищает его и вводит указанное значение.
     *
     * @param username значение для ввода в динамическое поле
     */
    public void enterDynamicUsername(String username) {
        waitForElementToBeVisible(dynamicUsernameInput);
        dynamicUsernameInput.clear();
        dynamicUsernameInput.sendKeys(username);
    }

    /**
     * Кликает по кнопке логина.
     * Ожидает, пока кнопка станет кликабельной, затем выполняет клик.
     */
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    /**
     * Ожидает, пока кнопка логина станет доступной (не disabled).
     * Проверяет атрибут disabled элемента кнопки.
     */
    public void waitForLoginButtonToBeEnabled() {
        wait.until(driver -> {
            String disabled = loginButton.getAttribute("disabled");
            return disabled == null || disabled.equals("false");
        });
    }

    /**
     * Ожидает появления контента после успешного логина.
     * Ожидает видимости одного из элементов: сообщение об успехе или ссылка выхода.
     */
    public void waitForLoginSuccess() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOf(successMessageRussian),
                ExpectedConditions.visibilityOf(logoutLinkRussian),
                ExpectedConditions.visibilityOf(successMessageEnglish),
                ExpectedConditions.visibilityOf(logoutLinkEnglish)
        ));
    }

    /**
     * Выполняет логин с валидными учетными данными.
     * Заполняет все поля валидными данными, активирует кнопку и выполняет логин.
     * Ожидает появления подтверждения успешного логина.
     */
    public void loginWithValidCredentials() {
        enterUsername(VALID_USERNAME);
        enterPassword(VALID_PASSWORD);
        enterDynamicUsername(VALID_USERNAME);
        waitForLoginButtonToBeEnabled();
        clickLoginButton();
        waitForLoginSuccess();
        waitForPageToLoad();
    }

    /**
     * Выполняет логин с невалидными учетными данными.
     * Заполняет все поля указанными невалидными данными и выполняет попытку логина.
     *
     * @param username невалидное имя пользователя
     * @param password невалидный пароль
     */
    public void loginWithInvalidCredentials(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        enterDynamicUsername(username);
        waitForLoginButtonToBeEnabled();
        clickLoginButton();
    }

    /**
     * Получает текст информационного сообщения с учетными данными.
     * Ожидает видимости элемента с учетными данными.
     *
     * @return текст информационного сообщения
     */
    public String getCredentialsInfoText() {
        waitForElementToBeVisible(credentialsInfo);
        return credentialsInfo.getText();
    }

    /**
     * Проверяет, отображается ли сообщение об ошибке.
     *
     * @return true если сообщение об ошибке отображается, иначе false
     */
    public boolean isErrorMessageDisplayed() {
        return errorMessageElement.isDisplayed();
    }

    /**
     * Получает текст сообщения об ошибке.
     *
     * @return текст ошибки авторизации
     */
    public String getErrorMessageText() {
        return errorMessageElement.getText();
    }

    /**
     * Проверяет, отображается ли ошибка валидации для поля имени пользователя.
     *
     * @return true если ошибка валидации отображается, иначе false
     */
    public boolean isUsernameValidationErrorDisplayed() {
        return usernameRequiredError.isDisplayed();
    }

    /**
     * Проверяет, отображается ли ошибка валидации для поля пароля.
     *
     * @return true если ошибка валидации отображается, иначе false
     */
    public boolean isPasswordValidationErrorDisplayed() {
        return passwordRequiredError.isDisplayed();
    }

    /**
     * Проверяет, отображается ли сообщение об успешном логине (английская версия).
     *
     * @return true если сообщение об успехе отображается, иначе false
     */
    public boolean isSuccessMessageDisplayed() {
        return successMessageEnglish.isDisplayed();
    }

    /**
     * Проверяет, отображается ли ссылка/кнопка выхода (английская версия).
     *
     * @return true если ссылка выхода отображается, иначе false
     */
    public boolean isLogoutLinkDisplayed() {
        return logoutLinkEnglish.isDisplayed();
    }

    /**
     * Получает текст сообщения об успешном логине.
     * Проверяет наличие сообщения на русском или английском языке.
     *
     * @return текст сообщения об успехе или пустая строка, если сообщение не найдено
     */
    public String getSuccessMessageText() {
        if (successMessageRussian.isDisplayed()) {
            return successMessageRussian.getText();
        }
        if (successMessageEnglish.isDisplayed()) {
            return successMessageEnglish.getText();
        }
        return "";
    }

    /**
     * Проверяет, содержит ли страница текст на английском о успешном логине.
     * Анализирует исходный код страницы.
     *
     * @return true если страница содержит текст "You're logged in!!", иначе false
     */
    public boolean pageContainsSuccessText() {
        String pageSource = driver.getPageSource();
        return pageSource.contains("You're logged in!!");
    }

    /**
     * Проверяет, содержит ли страница текст о выходе (английская версия).
     * Анализирует исходный код страницы.
     *
     * @return true если страница содержит текст "Logout", иначе false
     */
    public boolean pageContainsLogoutText() {
        String pageSource = driver.getPageSource();
        return pageSource.contains("Logout");
    }

    /**
     * Получает текущий URL страницы.
     *
     * @return текущий URL в браузере
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Получает заголовок текущей страницы.
     *
     * @return заголовок страницы из браузера
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Проверяет, находится ли пользователь на странице логина.
     * Анализирует URL на наличие ключевых фрагментов.
     *
     * @return true если URL содержит "login" или "#/login", иначе false
     */
    public boolean isOnLoginPage() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.contains("login") || currentUrl.contains("#/login");
    }

    /**
     * Возвращает экземпляр WebDriver для отладки в тестах.
     *
     * @return текущий экземпляр WebDriver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Получает элемент кнопки логина.
     *
     * @return WebElement кнопки логина
     */
    public WebElement getLoginButton() {
        return loginButton;
    }

    /**
     * Ожидает видимости указанного элемента.
     * Использует WebDriverWait для ожидания появления элемента.
     *
     * @param element для ожидания видимости
     */
    private void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Выполняет разлогин из системы.
     * Находит и кликает по ссылке выхода, используя JavaScript для надежности.
     * Проверяет, вернулся ли пользователь на страницу логина.
     *
     * @return true если разлогин выполнен успешно и отображается форма логина, иначе false
     */
    public boolean logout() {
        List<WebElement> logoutLinks = driver.findElements(
                By.xpath("//a[contains(text(), 'Logout')]")
        );
        WebElement logoutLink = logoutLinks.get(0);
        js.executeScript("arguments[0].click();", logoutLink);
        List<WebElement> loginForms = driver.findElements(By.xpath("//form[@name='form']"));
        List<WebElement> usernameFields = driver.findElements(By.id("username"));
        boolean isLoginPage = !loginForms.isEmpty() || !usernameFields.isEmpty();
        return isLoginPage;
    }
}
