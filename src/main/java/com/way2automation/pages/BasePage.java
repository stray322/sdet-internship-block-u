package com.way2automation.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Базовый класс для всех Page Object.
 * Содержит общие поля и методы для работы с веб-элементами.
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected JavascriptExecutor js;

    /**
     * Конструктор базовой страницы.
     *
     * @param driver экземпляр WebDriver
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Ожидает полной загрузки страницы.
     */
    protected void waitForPageToLoad() {
        wait.until(driver -> "complete".equals(
                js.executeScript("return document.readyState")));
    }

    /**
     * Скроллит к указанному элементу.
     *
     * @param element элемент для скролла
     */
    protected void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    /**
     * Кликает по элементу с использованием JavaScript.
     *
     * @param element элемент для клика
     */
    protected void clickWithJs(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    protected void enterText(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Получает текущий URL страницы.
     *
     * @return текущий URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Получает заголовок страницы.
     *
     * @return заголовок страницы
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Проверяет отображение элемента с обработкой исключений.
     *
     * @param element проверяемый элемент
     * @return true если элемент отображается
     */
    protected boolean isElementDisplayed(WebElement element) {
        return element.isDisplayed();
    }
}
