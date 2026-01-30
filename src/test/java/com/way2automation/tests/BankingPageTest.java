package com.way2automation.tests;

import com.way2automation.pages.BankingPage;
import com.way2automation.utils.WebDriverManagerUtil;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Тестовый класс для проверки функциональности банковского приложения.
 * Содержит тесты для проверки операций с клиентами, счетами, транзакциями и формами.
 */
public class BankingPageTest extends BaseTest{

    /**
     * Экземпляр WebDriver для управления браузером
     */
    private WebDriver driver;

    /**
     * Экземпляр BankingPage для взаимодействия с элементами банковского приложения
     */
    private BankingPage bankingPage;

    /**
     * Генератор случайных чисел для создания уникальных тестовых данных
     */
    private Random random;

    /**
     * Имя тестового клиента
     */
    private String testCustomerFirstName;

    /**
     * Фамилия тестового клиента
     */
    private String testCustomerLastName;

    /**
     * Почтовый индекс тестового клиента
     */
    private String testCustomerPostCode;

    /**
     * Полное имя тестового клиента
     */
    private String testCustomerFullName;

    /**
     * Email тестового клиента
     */
    private String testEmail;

    /**
     * Метод настройки перед выполнением каждого теста.
     * Инициализирует WebDriver, BankingPage и генерирует уникальные тестовые данные.
     */
    @BeforeMethod
    public void setUp() {
        super.setUp();
        driver = WebDriverManagerUtil.getDriver();
        bankingPage = new BankingPage(driver);
        random = new Random();

        int randomNum = random.nextInt(10000);
        testCustomerFirstName = "TestUser" + randomNum;
        testCustomerLastName = "LastName" + randomNum;
        testCustomerPostCode = "PC" + randomNum;
        testCustomerFullName = testCustomerFirstName + " " + testCustomerLastName;
        testEmail = "test" + randomNum + "@example.com";

        bankingPage.openBankingApp();
    }

    /**
     * Тест заполнения и отправки образца формы.
     * Проверяет корректность работы формы ввода данных.
     */
    @Test
    public void SampleForm() {
        bankingPage.openSampleForm();
        bankingPage.setFirstName("John");
        bankingPage.setLastName("Doe");
        bankingPage.setEmail(testEmail);
        bankingPage.setPassword("password123");
        bankingPage.selectHobby("Sports");
        bankingPage.selectGender("male");
        bankingPage.enterAboutYourself("Самое длинное слово из предложенных хобби - ");
        bankingPage.submitSampleForm();
        bankingPage.isRegistrationSuccessful();
    }

    /**
     * Тест добавления нового клиента в банковскую систему.
     * Проверяет создание клиента и появление подтверждающего уведомления.
     */
    @Test
    public void AddCustomer() {
        bankingPage.loginAsBankManager();
        bankingPage.addNewCustomer(
                testCustomerFirstName,
                testCustomerLastName,
                testCustomerPostCode
        );
        boolean alertPresent = bankingPage.isCustomerAddedAlertPresent();
        Assert.assertTrue(alertPresent, "Alert о добавлении клиента не появился");
    }

    /**
     * Тест открытия счета для существующего клиента.
     * Проверяет создание клиента и открытие для него счета в указанной валюте.
     */
    @Test
    public void OpenAccount() {
        bankingPage.loginAsBankManager();
        bankingPage.addNewCustomer(
                testCustomerFirstName,
                testCustomerLastName,
                testCustomerPostCode
        );
        bankingPage.isCustomerAddedAlertPresent();
        bankingPage.openAccountForCustomer(testCustomerFullName, "Dollar");
        boolean accountAlert = bankingPage.isAccountOpenedAlertPresent();
        Assert.assertTrue(accountAlert, "Alert об открытии счета не появился");
    }

    /**
     * Комплексный тест операций клиента: логин, депозит, снятие средств, проверка транзакций.
     * Проверяет полный цикл операций клиента в банковской системе.
     */
    @Test
    public void CustomerLoginAndTransactions() {
        bankingPage.loginAsBankManager();
        bankingPage.addNewCustomer(
                testCustomerFirstName,
                testCustomerLastName,
                testCustomerPostCode
        );
        Assert.assertTrue(bankingPage.isCustomerAddedAlertPresent(),
                "Не удалось создать клиента");
        bankingPage.openAccountForCustomer(testCustomerFullName, "Dollar");
        Assert.assertTrue(bankingPage.isAccountOpenedAlertPresent(),
                "Не удалось открыть счет");
        bankingPage.goToHomePage();
        bankingPage.loginAsCustomer(testCustomerFullName);
        Assert.assertTrue(bankingPage.isCustomerLoggedIn(testCustomerFirstName),
                "Клиент не вошел в систему");
        String depositAmount = "100321";
        bankingPage.deposit(depositAmount);
        String depositMessage = bankingPage.getTransactionMessage();
        Assert.assertTrue(depositMessage.contains("Deposit Successful"),
                "Сообщение 'Deposit Successful' не появилось. Получено: " + depositMessage);

        bankingPage.openTransactions();
        boolean hasDepositTransaction = bankingPage.findTransaction(depositAmount);
        Assert.assertTrue(hasDepositTransaction,
                "Транзакция пополнения на " + depositAmount + " не найдена в истории");

        bankingPage.goBackFromTransactions();
        String invalidDeposit = "0";
        bankingPage.deposit(invalidDeposit);
        String depositMessage2 = bankingPage.getTransactionMessage();
        boolean hasSuccessfulMessage = depositMessage2.contains("Deposit Successful");
        Assert.assertFalse(hasSuccessfulMessage,
                "Не должно быть сообщения 'Deposit Successful' при пополнении на 0.");
        bankingPage.openTransactions();
        boolean hasInvalidTransaction = bankingPage.isTransactionWithExactAmount(invalidDeposit);
        Assert.assertFalse(hasInvalidTransaction,
                "Не должно быть транзакции на 0. Проверьте историю транзакций.");
        int transactionCountAfterInvalid = bankingPage.getTransactionCount();
        Assert.assertEquals(transactionCountAfterInvalid, 1,
                "После пополнения на 0 не должно быть новой транзакции. Ожидалось: 1, Фактически: " + transactionCountAfterInvalid);

        bankingPage.goBackFromTransactions();
        int currentBalance = bankingPage.getBalanceAsInt();
        Assert.assertTrue(currentBalance > 0, "Баланс должен быть больше 0 для теста снятия");

        Random random = new Random();
        int randomWithdrawAmount = random.nextInt(currentBalance) + 1;
        String withdrawAmount = String.valueOf(randomWithdrawAmount);
        bankingPage.withdraw(withdrawAmount);
        String withdrawMessage = bankingPage.getTransactionMessage();
        Assert.assertTrue(withdrawMessage.contains("Transaction successful"),
                "Сообщение 'Transaction successful' не появилось. Получено: " + withdrawMessage);
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bankingPage.openTransactions();
        if (bankingPage.getTransactionCount() == 0) {
            bankingPage.refreshTransactions();
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        boolean hasWithdrawTransaction = bankingPage.findTransaction(withdrawAmount);
        int attempts = 0;
        while (!hasWithdrawTransaction && attempts < 2) {
            bankingPage.refreshTransactions();
            try {
                sleep(3000 + (attempts * 2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            hasWithdrawTransaction = bankingPage.findTransaction(withdrawAmount);
            attempts++;
        }
        Assert.assertTrue(hasWithdrawTransaction,
                "Транзакция снятия на " + withdrawAmount + " не найдена в истории после " + attempts + " попыток");
        bankingPage.goBackFromTransactions();
        String largeWithdraw = "1000000";
        bankingPage.withdraw(largeWithdraw);
        String errorMessage = bankingPage.getTransactionMessage();
        boolean hasError = errorMessage.contains("Transaction Failed") ||
                errorMessage.contains("You can not withdraw amount more than the balance");
        Assert.assertTrue(hasError,
                "Должно быть сообщение об ошибке при попытке снять больше баланса. Получено: " + errorMessage);
        bankingPage.openTransactions();
        boolean hasFailedWithdraw = bankingPage.findTransaction(largeWithdraw);
        Assert.assertFalse(hasFailedWithdraw,
                "Не должно быть транзакции на " + largeWithdraw + " (неуспешная операция)");
        int totalTransactions = bankingPage.getTransactionCount();
        Assert.assertEquals(totalTransactions, 2,
                "Всего должно быть 2 транзакции (депозит и успешное снятие). Найдено: " + totalTransactions);

        bankingPage.goBackFromTransactions();
        int displayedBalance = bankingPage.getBalanceAsInt();
        bankingPage.openTransactions();
        int calculatedBalance = bankingPage.calculateBalanceFromTransactions();
        Assert.assertEquals(calculatedBalance, displayedBalance,
                "Баланс из транзакций (" + calculatedBalance + ") не совпадает с отображаемым (" + displayedBalance + ")");
        bankingPage.goBackFromTransactions();
        int remainingBalance = bankingPage.getBalanceAsInt();

        if (remainingBalance > 0) {
            bankingPage.withdraw(String.valueOf(remainingBalance));
            String finalWithdrawMessage = bankingPage.getTransactionMessage();
            Assert.assertTrue(finalWithdrawMessage.contains("Transaction successful"),
                    "Сообщение 'Transaction successful' не появилось при снятии оставшихся средств. Получено: " + finalWithdrawMessage);
            int finalBalance = bankingPage.getBalanceAsInt();
            Assert.assertEquals(finalBalance, 0,
                    "После снятия всех средств баланс должен быть 0. Текущий: " + finalBalance);
        }
        bankingPage.openTransactions();
        bankingPage.resetTransactions();
        bankingPage.goBackFromTransactions();
        int finalBalanceCheck = bankingPage.getBalanceAsInt();
        Assert.assertEquals(finalBalanceCheck, 0,
                "Баланс должен быть 0 после всех операций. Текущий: " + finalBalanceCheck);
        bankingPage.logoutCustomer();
    }

    /**
     * Тест удаления клиента из банковской системы.
     * Проверяет полный цикл: добавление клиента, поиск, удаление и проверку удаления.
     */
    @Test
    public void DeleteCustomer() {
        bankingPage.loginAsBankManager();
        bankingPage.addNewCustomer(
                testCustomerFirstName,
                testCustomerLastName,
                testCustomerPostCode
        );
        bankingPage.isCustomerAddedAlertPresent();
        bankingPage.openCustomersTab();
        bankingPage.searchCustomer(testCustomerFirstName);
        boolean customerFound = bankingPage.isCustomerFound(testCustomerFirstName);
        Assert.assertTrue(customerFound, "Клиент не найден после добавления");
        bankingPage.deleteCustomer(testCustomerFirstName);
        bankingPage.clearSearch();
        bankingPage.searchCustomer(testCustomerFirstName);
        boolean stillExists = bankingPage.isCustomerFound(testCustomerFirstName);
        Assert.assertFalse(stillExists, "Клиент все еще существует после удаления");
    }

    /**
     * Метод очистки после выполнения каждого теста.
     * Закрывает браузер и освобождает ресурсы WebDriver.
     */
    @AfterMethod
    public void tearDown() {
        WebDriverManagerUtil.quitDriver();
    }
}
