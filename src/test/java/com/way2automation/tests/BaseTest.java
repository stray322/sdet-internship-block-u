package com.way2automation.tests;

import com.way2automation.utils.WebDriverManagerUtil;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

/**
 * Базовый класс для всех тестов.
 * Содержит общую логику инициализации и завершения тестов.
 */
public abstract class BaseTest {

    protected WebDriver driver;
    protected SoftAssert softAssert;

    /**
     * Настройка перед каждым тестом.
     * Инициализирует WebDriver и SoftAssert.
     */
    @BeforeMethod
    public void setUp() {
        driver = WebDriverManagerUtil.getDriver();
        softAssert = new SoftAssert();
    }

    /**
     * Очистка после каждого теста.
     * Выполняет проверки SoftAssert и закрывает WebDriver.
     */
    @AfterMethod
    public void tearDown() {
        softAssert.assertAll();
    }
}
