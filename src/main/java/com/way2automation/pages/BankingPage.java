package com.way2automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

/**
 * Page Object класс для банковского приложения.
 * Содержит элементы и методы для взаимодействия с различными функциями банковской системы:
 * управление клиентами, счетами, транзакциями, а также работу с формами.
 */
public class BankingPage extends BasePage {

    /**
     * URL банковского приложения
     */
    private static final String BANKING_URL = "https://www.way2automation.com/angularjs-protractor/banking/#/login";

    /**
     * Кнопка для открытия примера формы
     */
    @FindBy(xpath = "//a[contains(text(), 'Sample Form')]")
    private WebElement sampleFormButton;

    /**
     * Поле ввода имени в форме
     */
    @FindBy(id = "firstName")
    private WebElement sampleFormFirstName;

    /**
     * Поле ввода фамилии в форме
     */
    @FindBy(id = "lastName")
    private WebElement sampleFormLastName;

    /**
     * Поле ввода email в форме
     */
    @FindBy(id = "email")
    private WebElement sampleFormEmail;

    /**
     * Поле ввода пароля в форме
     */
    @FindBy(id = "password")
    private WebElement sampleFormPassword;

    /**
     * Чекбокс хобби "Reading" в форме
     */
    @FindBy(xpath = "//input[@name='hobbies' and @value='Reading']")
    private WebElement hobbyReadingCheckbox;

    /**
     * Чекбокс хобби "Traveling" в форме
     */
    @FindBy(xpath = "//input[@name='hobbies' and @value='Traveling']")
    private WebElement hobbyTravelingCheckbox;

    /**
     * Чекбокс хобби "Sports" в форме
     */
    @FindBy(xpath = "//input[@name='hobbies' and @value='Sports']")
    private WebElement hobbySportsCheckbox;

    /**
     * Выпадающий список для выбора пола в форме
     */
    @FindBy(id = "gender")
    private WebElement genderSelect;

    /**
     * Список всех чекбоксов хобби в форме
     */
    @FindBy(xpath = "//input[@name='hobbies']")
    private List<WebElement> hobbiesCheckboxes;

    /**
     * Текстовое поле "О себе" в форме
     */
    @FindBy(id = "about")
    private WebElement aboutYourselfTextarea;

    /**
     * Кнопка отправки формы
     */
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement registerSubmitButton;

    /**
     * Сообщение об успешной регистрации в форме
     */
    @FindBy(id = "successMessage")
    private WebElement registrationSuccessMessage;

    /**
     * Кнопка входа для банковского менеджера
     */
    @FindBy(xpath = "//button[contains(text(), 'Bank Manager Login')]")
    private WebElement bankManagerLoginButton;

    /**
     * Вкладка "Add Customer" в интерфейсе менеджера
     */
    @FindBy(xpath = "//button[contains(text(), 'Add Customer') and contains(@class, 'tab')]")
    private WebElement addCustomerTab;

    /**
     * Вкладка "Open Account" в интерфейсе менеджера
     */
    @FindBy(xpath = "//button[contains(text(), 'Open Account') and contains(@class, 'tab')]")
    private WebElement openAccountTab;

    /**
     * Вкладка "Customers" в интерфейсе менеджера
     */
    @FindBy(xpath = "//button[contains(text(), 'Customers') and contains(@class, 'tab')]")
    private WebElement customersTab;

    /**
     * Поле ввода имени клиента при добавлении
     */
    @FindBy(xpath = "//input[@ng-model='fName']")
    private WebElement addCustomerFirstName;

    /**
     * Поле ввода фамилии клиента при добавлении
     */
    @FindBy(xpath = "//input[@ng-model='lName']")
    private WebElement addCustomerLastName;

    /**
     * Поле ввода почтового индекса клиента при добавлении
     */
    @FindBy(xpath = "//input[@ng-model='postCd']")
    private WebElement addCustomerPostCode;

    /**
     * Кнопка отправки формы добавления клиента
     */
    @FindBy(xpath = "//form[@name='myForm']//button[@type='submit']")
    private WebElement addCustomerSubmitBtn;

    /**
     * Выпадающий список для выбора клиента при открытии счета
     */
    @FindBy(id = "userSelect")
    private WebElement customerSelectDropdown;

    /**
     * Выпадающий список для выбора валюты при открытии счета
     */
    @FindBy(id = "currency")
    private WebElement currencySelectDropdown;

    /**
     * Кнопка "Process" для подтверждения открытия счета
     */
    @FindBy(xpath = "//form[@ng-submit='process()']//button[@type='submit']")
    private WebElement processButton;

    /**
     * Кнопка входа для клиента
     */
    @FindBy(xpath = "//button[contains(text(), 'Customer Login')]")
    private WebElement customerLoginButton;

    /**
     * Выпадающий список для выбора имени клиента при входе
     */
    @FindBy(xpath = "//select[@id='userSelect']")
    private WebElement customerNameSelect;

    /**
     * Кнопка "Login" для подтверждения входа клиента
     */
    @FindBy(xpath = "//button[@type='submit' and contains(text(), 'Login')]")
    private WebElement customerLoginSubmitBtn;

    /**
     * Приветственное сообщение после входа клиента
     */
    @FindBy(xpath = "//span[@class='fontBig ng-binding' or contains(text(), 'Welcome')]")
    private WebElement welcomeMessage;

    /**
     * Кнопка "Deposit" (пополнение счета)
     */
    @FindBy(xpath = "//button[contains(text(), 'Deposit')]")
    private WebElement depositButton;

    /**
     * Кнопка "Withdrawl" (снятие средств)
     */
    @FindBy(xpath = "//button[contains(text(), 'Withdrawl')]")
    private WebElement withdrawButton;

    /**
     * Кнопка "Transactions" для просмотра истории операций
     */
    @FindBy(xpath = "//button[contains(text(), 'Transactions')]")
    private WebElement transactionsButton;

    /**
     * Кнопка "Logout" для выхода из системы
     */
    @FindBy(xpath = "//button[contains(text(), 'Logout')]")
    private WebElement logoutButton;

    /**
     * Поле ввода суммы для операций
     */
    @FindBy(xpath = "//input[@type='number' and @placeholder='amount']")
    private WebElement amountInputField;

    /**
     * Поле ввода суммы для депозита (альтернативный локатор)
     */
    private WebElement amountInputFieldD;

    /**
     * Поле ввода суммы для снятия (альтернативный локатор)
     */
    @FindBy(xpath = "//input[@placeholder='amount' or @ng-model='amount']")
    private WebElement amountInputFieldW;

    /**
     * Кнопка отправки формы пополнения счета
     */
    @FindBy(xpath = "//button[@type='submit' and contains(text(), 'Deposit')]")
    private WebElement depositSubmitBtn;

    /**
     * Кнопка отправки формы снятия средств
     */
    @FindBy(xpath = "//button[@type='submit' and contains(text(), 'Withdraw')]")
    private WebElement withdrawSubmitBtn;

    /**
     * Сообщение о результате транзакции
     */
    @FindBy(xpath = "//span[@class='error ng-binding' and contains(@ng-show, 'message')]")
    private WebElement transactionMessage;

    /**
     * Элемент с текущим балансом клиента
     */
    @FindBy(xpath = "//div[@ng-hide='noAccount']//strong[@class='ng-binding'][2]")
    private WebElement balanceLabel;

    /**
     * Строки таблицы с историей транзакций
     */
    @FindBy(css = "table.table tbody tr")
    private List<WebElement> transactionRows;

    /**
     * Кнопка "Reset" для очистки истории транзакций
     */
    @FindBy(xpath = "//button[contains(text(), 'Reset')]")
    private WebElement resetTransactionsButton;

    /**
     * Кнопка "Back" для возврата из истории транзакций
     */
    @FindBy(xpath = "//button[contains(text(), 'Back')]")
    private WebElement backFromTransactionsButton;

    /**
     * Поле поиска клиентов
     */
    @FindBy(xpath = "//input[@placeholder='Search Customer']")
    private WebElement customerSearchInput;

    /**
     * Строки таблицы с клиентами
     */
    @FindBy(css = "table.table-bordered tbody tr")
    private List<WebElement> customerTableRows;

    /**
     * Кнопка "Home" для возврата на главную страницу
     */
    @FindBy(xpath = "//button[contains(text(), 'Home')]")
    private WebElement homeButton;

    /**
     * Конструктор класса BankingPage.
     * Инициализирует элементы страницы с помощью PageFactory.
     *
     * @param driver экземпляр WebDriver для управления браузером
     */
    public BankingPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Открывает банковское приложение по указанному URL.
     * Переходит на страницу логина и ожидает загрузки.
     */
    public void openBankingApp() {
        driver.get(BANKING_URL);
        waitForPageToLoad();
    }

    /**
     * Открывает пример формы на странице.
     * Кликает по кнопке Sample Form с использованием JavaScript.
     */
    public void openSampleForm() {
        clickWithJs(sampleFormButton);
    }

    /**
     * Вводит имя в поле формы.
     *
     * @param firstName имя для ввода
     */
    public void setFirstName(String firstName) {
        enterText(sampleFormFirstName, firstName);
    }

    /**
     * Вводит фамилию в поле формы.
     *
     * @param lastName фамилия для ввода
     */
    public void setLastName(String lastName) {
        enterText(sampleFormLastName, lastName);
    }

    /**
     * Вводит email в поле формы.
     *
     * @param email email для ввода
     */
    public void setEmail(String email) {
        enterText(sampleFormEmail, email);
    }

    /**
     * Вводит пароль в поле формы.
     *
     * @param password пароль для ввода
     */
    public void setPassword(String password) {
        enterText(sampleFormPassword, password);
    }

    /**
     * Выбирает хобби по значению.
     * Поддерживает значения: "sports", "reading", "traveling".
     *
     * @param hobbyValue значение хобби для выбора
     */
    public void selectHobby(String hobbyValue) {
        switch(hobbyValue.toLowerCase()) {
            case "sports":
                clickWithJs(hobbySportsCheckbox);
                break;
            case "reading":
                clickWithJs(hobbyReadingCheckbox);
                break;
            case "traveling":
                clickWithJs(hobbyTravelingCheckbox);
                break;
        }
    }

    /**
     * Выбирает пол из выпадающего списка.
     *
     * @param gender значение пола для выбора ("male", "female", и т.д.)
     */
    public void selectGender(String gender) {
        Select genderDropdown = new Select(genderSelect);
        genderDropdown.selectByValue(gender);
    }

    /**
     * Вводит текст в поле "О себе" с добавлением самого длинного слова из хобби.
     *
     * @param text текст для ввода
     */
    public void enterAboutYourself(String text) {
        enterText(aboutYourselfTextarea, text + getLongestHobbyWord());
    }

    /**
     * Получает список всех значений хобби из формы.
     *
     * @return список значений хобби
     */
    public List<String> getAllHobbyValues() {
        List<String> hobbyValues = new ArrayList<>();
        List<WebElement> hobbyCheckboxes = driver.findElements(
                By.xpath("//input[@type='checkbox' and @name='hobbies']"));

        for (WebElement checkbox : hobbyCheckboxes) {
            String value = checkbox.getAttribute("value");
            hobbyValues.add(value);
        }
        return hobbyValues;
    }

    /**
     * Находит самое длинное слово из списка хобби.
     *
     * @return самое длинное слово хобби или пустую строку, если хобби нет
     */
    public String getLongestHobbyWord() {
        List<String> hobbies = getAllHobbyValues();
        if (hobbies.isEmpty()) {
            return "";
        }

        String longest = hobbies.get(0);
        for (String hobby : hobbies) {
            if (hobby.length() > longest.length()) {
                longest = hobby;
            }
        }
        return longest;
    }

    /**
     * Отправляет заполненную форму.
     * Кликает по кнопке отправки формы с использованием JavaScript.
     */
    public void submitSampleForm() {
        clickWithJs(registerSubmitButton);
    }

    /**
     * Проверяет, успешно ли прошла регистрация в форме.
     * Ожидает появления сообщения об успехе.
     *
     * @return true если сообщение об успешной регистрации отображается, иначе false
     */
    public boolean isRegistrationSuccessful() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(registrationSuccessMessage));
        String message = registrationSuccessMessage.getText();
        return message.contains("User registered successfully!");
    }

    /**
     * Выполняет вход в систему как банковский менеджер.
     * Кликает по соответствующей кнопке и ожидает появления вкладок менеджера.
     */
    public void loginAsBankManager() {
        clickWithJs(bankManagerLoginButton);
        wait.until(ExpectedConditions.visibilityOf(addCustomerTab));
    }

    /**
     * Открывает вкладку "Add Customer".
     * Кликает по вкладке и ожидает появления полей формы.
     */
    public void openAddCustomerTab() {
        clickWithJs(addCustomerTab);
        wait.until(ExpectedConditions.visibilityOf(addCustomerFirstName));
    }

    /**
     * Добавляет нового клиента в систему.
     * Заполняет форму данными клиента и отправляет ее.
     *
     * @param firstName имя клиента
     * @param lastName фамилия клиента
     * @param postCode почтовый индекс клиента
     */
    public void addNewCustomer(String firstName, String lastName, String postCode) {
        openAddCustomerTab();
        enterText(addCustomerFirstName, firstName);
        enterText(addCustomerLastName, lastName);
        enterText(addCustomerPostCode, postCode);
        clickWithJs(addCustomerSubmitBtn);
    }

    /**
     * Проверяет наличие alert-сообщения об успешном добавлении клиента.
     *
     * @return true если alert содержит текст об успешном добавлении, иначе false
     */
    public boolean isCustomerAddedAlertPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.alertIsPresent());
            String alertText = driver.switchTo().alert().getText();
            System.out.println("Alert text: " + alertText); // Для отладки
            boolean containsSuccess = alertText.contains("Customer added successfully");
            driver.switchTo().alert().accept();
            return containsSuccess;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Открывает вкладку "Open Account".
     * Кликает по вкладке и ожидает появления элементов формы.
     */
    public void openOpenAccountTab() {
        clickWithJs(openAccountTab);
        wait.until(ExpectedConditions.visibilityOf(customerSelectDropdown));
    }

    /**
     * Открывает счет для выбранного клиента в указанной валюте.
     *
     * @param customerFullName полное имя клиента
     * @param currency валюта счета
     */
    public void openAccountForCustomer(String customerFullName, String currency) {
        openOpenAccountTab();
        Select customerSelect = new Select(customerSelectDropdown);
        customerSelect.selectByVisibleText(customerFullName);
        Select currencySelect = new Select(currencySelectDropdown);
        currencySelect.selectByVisibleText(currency);
        clickWithJs(processButton);
    }

    /**
     * Проверяет наличие alert-сообщения об успешном открытии счета.
     *
     * @return true если alert содержит текст об успешном открытии счета, иначе false
     */
    public boolean isAccountOpenedAlertPresent() {
        WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        alertWait.until(ExpectedConditions.alertIsPresent());
        String alertText = driver.switchTo().alert().getText();
        boolean containsSuccess = alertText.contains("Account created successfully");
        driver.switchTo().alert().accept();
        return containsSuccess;
    }

    /**
     * Выполняет вход в систему как клиент.
     *
     * @param customerName имя клиента для входа
     */
    public void loginAsCustomer(String customerName) {
        clickWithJs(customerLoginButton);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(customerNameSelect));
        Select select = new Select(customerNameSelect);
        select.selectByVisibleText(customerName);
        clickWithJs(customerLoginSubmitBtn);
        wait.until(ExpectedConditions.visibilityOf(depositButton));
    }

    /**
     * Проверяет, успешно ли клиент вошел в систему.
     *
     * @param expectedCustomerName ожидаемое имя клиента
     * @return true если приветственное сообщение содержит имя клиента, иначе false
     */
    public boolean isCustomerLoggedIn(String expectedCustomerName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(welcomeMessage));
        String welcomeText = welcomeMessage.getText();
        return welcomeText.contains("Welcome " + expectedCustomerName) ||
                welcomeText.contains(expectedCustomerName);
    }

    /**
     * Выполняет операцию пополнения счета.
     *
     * @param amount сумма для пополнения
     */
    public void deposit(String amount) {
        clickWithJs(depositButton);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(amountInputField));
        amountInputField.clear();
        amountInputField.sendKeys(amount);
        wait.until(driver -> {
            String value = amountInputField.getAttribute("value");
            return value != null && value.equals(amount);
        });
        clickWithJs(depositSubmitBtn);
    }

    /**
     * Выполняет операцию снятия средств со счета.
     *
     * @param amount сумма для снятия
     */
    public void withdraw(String amount) {
        clickWithJs(withdrawButton);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(amountInputField));
        amountInputField.clear();
        amountInputField.sendKeys(amount);
        wait.until(driver -> {
            String value = amountInputField.getAttribute("value");
            return value != null && value.equals(amount);
        });
        clickWithJs(withdrawSubmitBtn);
    }

    /**
     * Получает сообщение о результате транзакции.
     *
     * @return текст сообщения о транзакции или пустую строку, если сообщение не найдено
     */
    public String getTransactionMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.visibilityOf(transactionMessage));
            String message = transactionMessage.getText();
            return message;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Получает текущий баланс клиента в виде строки.
     *
     * @return баланс в виде строки или "0", если не удалось получить
     */
    public String getBalance() {
        try {
            String balance = balanceLabel.getText();
            System.out.println("✓ Баланс: " + balance);
            return balance;
        } catch (Exception e) {
            System.out.println("✗ Не удалось получить баланс");
            return "0";
        }
    }

    /**
     * Получает текущий баланс клиента в виде целого числа.
     *
     * @return баланс в виде целого числа или 0, если не удалось получить
     */
    public int getBalanceAsInt() {
        try {
            String balanceText = getBalance();
            String numbersOnly = balanceText.replaceAll("[^0-9]", "");
            return Integer.parseInt(numbersOnly);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Проверяет, находится ли пользователь на странице транзакций.
     *
     * @return true если кнопки Reset и Back отображаются, иначе false
     */
    private boolean isOnTransactionsPage() {
        try {
            return resetTransactionsButton.isDisplayed() &&
                    backFromTransactionsButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Открывает страницу с историей транзакций.
     * Если пользователь уже на странице транзакций, не выполняет переход.
     */
    public void openTransactions() {
        boolean alreadyOnTransactions = isOnTransactionsPage();
        if (!alreadyOnTransactions) {
            clickWithJs(transactionsButton);
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("table.table")
                    ),
                    ExpectedConditions.visibilityOf(backFromTransactionsButton)
            ));
        }
        transactionRows = driver.findElements(
                By.cssSelector("table.table tbody tr")
        );
    }

    /**
     * Ищет транзакцию с указанной суммой в истории.
     *
     * @param amount сумма для поиска
     * @return true если транзакция с указанной суммой найдена, иначе false
     */
    public boolean findTransaction(String amount) {
        transactionRows = driver.findElements(By.cssSelector("table.table tbody tr"));
        List<WebElement> rows = driver.findElements(
                By.cssSelector("table.table tbody tr")
        );

        for (WebElement row : rows) {
            String rowText = row.getText();
            System.out.println("Проверяем строку: " + rowText);
            String[] parts = rowText.split("\\s+");
            for (String part : parts) {
                String cleanedPart = part.replaceAll("[^0-9]", "");
                if (cleanedPart.equals(amount)) {
                    return true;
                }
            }

            if (rowText.contains(" " + amount + " ") ||
                    rowText.endsWith(" " + amount) ||
                    rowText.startsWith(amount + " ")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Проверяет наличие транзакции с точной суммой и указанием типа (Credit/Debit).
     *
     * @param amount сумма для проверки
     * @return true если транзакция с точной суммой найдена, иначе false
     */
    public boolean isTransactionWithExactAmount(String amount) {
        for (WebElement row : transactionRows) {
            String rowText = row.getText();
            Pattern pattern = Pattern.compile("\\s" + amount + "\\s(Credit|Debit)");
            Matcher matcher = pattern.matcher(rowText);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Получает количество видимых транзакций в истории.
     *
     * @return количество отображаемых транзакций
     */
    public int getTransactionCount() {
        List<WebElement> rows = driver.findElements(
                By.cssSelector("table.table tbody tr")
        );

        int visibleCount = 0;
        for (WebElement row : rows) {
            if (row.isDisplayed()) {
                visibleCount++;
            }
        }
        return visibleCount;
    }

    /**
     * Проверяет наличие транзакции с указанной суммой (поиск подстроки).
     *
     * @param amount сумма для поиска (как подстрока)
     * @return true если транзакция содержит указанную сумму, иначе false
     */
    public boolean isTransactionPresent(String amount) {
        for (WebElement row : transactionRows) {
            if (row.getText().contains(amount)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Вычисляет баланс на основе всех транзакций в истории.
     * Credit операции увеличивают баланс, Debit - уменьшают.
     *
     * @return рассчитанный баланс на основе транзакций
     */
    public int calculateBalanceFromTransactions() {
        int total = 0;
        transactionRows = driver.findElements(
                By.cssSelector("table.table tbody tr")
        );

        for (WebElement row : transactionRows) {
            String rowText = row.getText();
            String[] parts = rowText.split("\\s+");

            String amountStr = "";
            String transactionType = "";
            for (int i = parts.length - 1; i >= 0; i--) {
                if (parts[i].equalsIgnoreCase("Credit") || parts[i].equalsIgnoreCase("Debit")) {
                    transactionType = parts[i];
                    if (i > 0) {
                        amountStr = parts[i - 1];
                    }
                    break;
                }
            }

            if (!amountStr.isEmpty() && amountStr.matches("\\d+")) {
                int amount = Integer.parseInt(amountStr);
                if (transactionType.equalsIgnoreCase("Credit")) {
                    total += amount;
                }
                else if (transactionType.equalsIgnoreCase("Debit")) {
                    total -= amount;
                }
            }
        }
        return total;
    }

    /**
     * Обновляет историю транзакций.
     * Возвращается на предыдущую страницу и снова открывает транзакции.
     */
    public void refreshTransactions() {
        if (backFromTransactionsButton.isDisplayed()) {
            clickWithJs(backFromTransactionsButton);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wait.until(ExpectedConditions.visibilityOf(depositButton));
            openTransactions();
        }
    }

    /**
     * Сбрасывает историю транзакций.
     * Если транзакций нет, не выполняет сброс.
     */
    public void resetTransactions() {
        if (!isOnTransactionsPage()) {
            openTransactions();
        }

        int beforeReset = getTransactionCount();

        if (beforeReset == 0) {
            return;
        }
        clickWithJs(resetTransactionsButton);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращается со страницы транзакций на предыдущую страницу.
     */
    public void goBackFromTransactions() {
        clickWithJs(backFromTransactionsButton);
        wait.until(ExpectedConditions.visibilityOf(depositButton));
    }

    /**
     * Выполняет выход клиента из системы и возврат на домашнюю страницу.
     */
    public void logoutCustomer() {
        clickWithJs(logoutButton);
        clickWithJs(homeButton);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(text(), 'Customer Login')]")
        ));
    }

    /**
     * Открывает вкладку "Customers" для управления клиентами.
     */
    public void openCustomersTab() {
        clickWithJs(customersTab);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(customerSearchInput));
    }

    /**
     * Выполняет поиск клиента по имени.
     *
     * @param firstName имя клиента для поиска
     */
    public void searchCustomer(String firstName) {
        openCustomersTab();
        enterText(customerSearchInput, firstName);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверяет, найден ли клиент в таблице клиентов.
     *
     * @param firstName имя клиента для проверки
     * @return true если клиент найден, иначе false
     */
    public boolean isCustomerFound(String firstName) {
        for (WebElement row : customerTableRows) {
            if (row.getText().contains(firstName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Удаляет клиента из системы по имени.
     *
     * @param firstName имя клиента для удаления
     */
    public void deleteCustomer(String firstName) {
        for (WebElement row : customerTableRows) {
            if (row.getText().contains(firstName)) {
                WebElement deleteBtn = row.findElement(By.xpath(".//button[contains(text(), 'Delete')]"));
                clickWithJs(deleteBtn);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }

    /**
     * Очищает поле поиска клиентов.
     */
    public void clearSearch() {
        customerSearchInput.clear();
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Переходит на домашнюю страницу банковского приложения.
     */
    public void goToHomePage() {
        clickWithJs(homeButton);
        wait.until(ExpectedConditions.visibilityOf(customerLoginButton));
    }
}
