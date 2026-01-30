package com.way2automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object класс для главной страницы Way2Automation.
 * Содержит методы для взаимодействия с элементами главной страницы и проверки их состояния.
 */
public class MainPage extends BasePage {

    /**
     * Базовый URL главной страницы Way2Automation
     */
    private static final String BASE_URL = "https://www.way2automation.com/";

    /**
     * Фрагмент URL для проверки навигации на страницу Lifetime Membership
     */
    private static final String LIFETIME_MEMBERSHIP_URL_FRAGMENT = "lifetime-membership-club";

    /**
     * Минимальное количество видимых карточек курсов для успешной проверки
     */
    private static final int MIN_VISIBLE_COURSE_CARDS = 3;

    /**
     * Смещение для скролла страницы (в пикселях)
     */
    private static final int SCROLL_OFFSET = 500;

    /**
     * Время ожидания анимации (в миллисекундах)
     */
    private static final int ANIMATION_WAIT_MS = 500;

    /**
     * Элемент навигационного меню сайта
     */
    @FindBy(id = "site-navigation")
    private WebElement navigationMenu;

    /**
     * Первая ссылка на WhatsApp в контактной информации
     */
    @FindBy(xpath = "//a[contains(@href, 'wa.me/+919711111558')]")
    private WebElement whatsappPhone1;

    /**
     * Вторая ссылка на WhatsApp в контактной информации
     */
    @FindBy(xpath = "//a[contains(@href, 'wa.me/+919711191558')]")
    private WebElement whatsappPhone2;

    /**
     * Ссылка на телефон в США в контактной информации
     */
    @FindBy(xpath = "//a[contains(@href, 'tel:+16464800603')]")
    private WebElement usPhone;

    /**
     * Ссылка на Skype в контактной информации
     */
    @FindBy(xpath = "//a[contains(@href, 'skype:seleniumcoaching')]")
    private WebElement skypeLink;

    /**
     * Ссылка на email в контактной информации
     */
    @FindBy(xpath = "//a[contains(@href, 'mailto:trainer@way2automation.com')]")
    private WebElement emailLink;

    /**
     * Ссылка на Facebook в контактной информации
     */
    @FindBy(xpath = "//a[contains(@href, 'facebook.com/way2automation')]")
    private WebElement facebookLink;

    /**
     * Ссылка на LinkedIn в контактной информации
     */
    @FindBy(xpath = "//a[contains(@href, 'linkedin.com/in/rahul-arora')]")
    private WebElement linkedinLink;

    /**
     * Ссылка на Google Plus в контактной информации
     */
    @FindBy(xpath = "//a[contains(@href, 'plus.google.com/u/0/+RamanAhujatheseleniumguru')]")
    private WebElement googlePlusLink;

    /**
     * Ссылка на YouTube в контактной информации
     */
    @FindBy(xpath = "//a[contains(@href, 'youtube.com/c/seleniumappiumtutorialtraining')]")
    private WebElement youtubeLink;

    /**
     * Заголовок блока популярных курсов по тестированию ПО
     */
    @FindBy(xpath = "//h2[contains(text(), 'Most Popular Software Testing Courses')]")
    private WebElement coursesTitle;

    /**
     * Контейнер карусели с карточками курсов
     */
    @FindBy(css = ".pp-info-box-carousel")
    private WebElement coursesCarouselContainer;

    /**
     * Список всех карточек курсов
     */
    @FindBy(css = ".pp-info-box.swiper-slide")
    private List<WebElement> allCourseCards;

    /**
     * Элемент меню "All Courses"
     */
    @FindBy(xpath = "//li[contains(@class, 'menu-item-27580')]")
    private WebElement allCoursesMenuItem;

    /**
     * Ссылка меню "All Courses"
     */
    @FindBy(xpath = "//li[contains(@class, 'menu-item-27580')]//a[@class='menu-link']")
    private WebElement allCoursesLink;

    /**
     * Текстовый элемент меню "All Courses"
     */
    @FindBy(xpath = "//li[contains(@class, 'menu-item-27580')]//span[contains(@class, 'menu-text') and text()='All Courses']")
    private WebElement allCoursesText;

    /**
     * Подменю элемента "All Courses"
     */
    @FindBy(xpath = "//li[contains(@class, 'menu-item-27580')]//ul[@class='sub-menu']")
    private WebElement allCoursesSubMenu;

    /**
     * Ссылка на Lifetime Membership в подменю
     */
    @FindBy(xpath = "//li[contains(@class, 'menu-item-27580')]//a[.//span[text()='Lifetime Membership']]")
    private WebElement lifetimeMembershipLink;

    /**
     * Список заголовков карточек курсов
     */
    @FindBy(css = ".pp-info-box-title")
    private List<WebElement> courseTitles;

    /**
     * Список кнопок "Get Started" на карточках курсов
     */
    @FindBy(css = ".pp-info-box-button")
    private List<WebElement> getStartedButtons;

    /**
     * Элемент футера страницы
     */
    @FindBy(xpath = "//div[@data-elementor-type='footer']")
    private WebElement footerElement;

    /**
     * Список элементов с адресом в футере
     */
    @FindBy(xpath = "//span[contains(@class, 'elementor-icon-list-text') and contains(., 'Noida')]")
    private List<WebElement> footerAddressElement;

    /**
     * Список ссылок на телефоны в футере
     */
    @FindBy(xpath = "//a[contains(@href, 'tel:')]")
    private List<WebElement> footerPhones;

    /**
     * Список ссылок на email в футере
     */
    @FindBy(xpath = "//a[contains(@href, 'mailto:')]")
    private List<WebElement> footerEmails;

    /**
     * Конструктор класса MainPage.
     * Инициализирует элементы страницы с помощью PageFactory.
     *
     * @param driver экземпляр WebDriver для управления браузером
     */
    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Открывает главную страницу Way2Automation.
     * Переходит по базовому URL и ожидает полной загрузки страницы.
     */
    public void openMainPage() {
        driver.get(BASE_URL);
        waitForPageToLoad();
    }

    /**
     * Проверяет, открыта ли главная страница.
     * Ожидает видимости навигационного меню и проверяет URL страницы.
     *
     * @return true если страница открыта и URL соответствует базовому, иначе false
     */
    public boolean isPageOpened() {
        waitForElementToBeVisible(navigationMenu);
        return BASE_URL.equals(driver.getCurrentUrl());
    }

    /**
     * Проверяет отображение контактной информации в хедере страницы.
     * Проверяет видимость всех основных контактных элементов.
     *
     * @return true если все контактные элементы отображаются, иначе false
     */
    public boolean isContactInfoDisplayed() {
        return isElementDisplayed(whatsappPhone1) &&
                isElementDisplayed(whatsappPhone2) &&
                isElementDisplayed(usPhone) &&
                isElementDisplayed(skypeLink) &&
                isElementDisplayed(emailLink);
    }

    /**
     * Проверяет отображение блока курсов на главной странице.
     * Ожидает видимости заголовка блока, контейнера карусели и наличие карточек курсов.
     *
     * @return true если блок курсов полностью отображается, иначе false
     */
    public boolean isCoursesBlockDisplayed() {
        waitForElementToBeVisible(coursesTitle);
        waitForElementToBeVisible(coursesCarouselContainer);
        wait.until(d -> allCourseCards.size() > 0);
        return true;
    }

    /**
     * Проверяет контент карточек курсов.
     * Убеждается, что каждая карточка содержит заголовок и кнопку.
     *
     * @return true если как минимум MIN_VISIBLE_COURSE_CARDS карточек имеют валидный контент, иначе false
     */
    public boolean verifyCourseCardsContent() {
        if (allCourseCards.isEmpty()) return false;

        long validCardsCount = allCourseCards.stream()
                .filter(card ->
                        !card.findElements(By.cssSelector(".pp-info-box-title")).isEmpty() &&
                                !card.findElements(By.cssSelector(".pp-info-box-button")).isEmpty())
                .count();

        return validCardsCount >= MIN_VISIBLE_COURSE_CARDS;
    }

    /**
     * Скроллит страницу к футеру.
     * Ожидает видимости футера и выполняет скролл до него.
     */
    public void scrollToFooter() {
        wait.until(ExpectedConditions.visibilityOf(footerElement));
        js.executeScript("arguments[0].scrollIntoView(true);", footerElement);
    }

    /**
     * Проверяет отображение футера на странице.
     *
     * @return true если футер отображается, иначе false
     */
    public boolean isFooterDisplayed() {
        return isElementDisplayed(footerElement);
    }

    /**
     * Проверяет наличие адреса в футере.
     * Ищет элементы, содержащие информацию об адресе (Noida, CDR, Sector).
     *
     * @return true если найден хотя бы один элемент с адресом, иначе false
     */
    public boolean hasAddress() {
        return footerAddressElement.stream()
                .anyMatch(element -> {
                    String text = element.getText();
                    return text.contains("Noida") ||
                            text.contains("CDR") ||
                            text.contains("Sector");
                });
    }

    /**
     * Проверяет наличие телефонных номеров в футере.
     *
     * @return true если в футере есть хотя бы один телефонный номер, иначе false
     */
    public boolean hasPhones() {
        return !footerPhones.isEmpty();
    }

    /**
     * Проверяет наличие email адресов в футере.
     *
     * @return true если в футере есть хотя бы один email, иначе false
     */
    public boolean hasEmails() {
        return !footerEmails.isEmpty();
    }

    /**
     * Проверяет полноту футера.
     * Проверяет наличие футера, адреса, телефонов и email.
     *
     * @return true если футер содержит все необходимые элементы, иначе false
     */
    public boolean isFooterComplete() {
        return isFooterDisplayed() && hasAddress() && hasPhones() && hasEmails();
    }

    /**
     * Проверяет видимость меню после скролла страницы.
     * Проверяет видимость меню до и после скролла на заданное смещение.
     *
     * @return true если меню остается видимым после скролла, иначе false
     */
    public boolean isMenuVisibleAfterScroll() {
        waitForElementToBeVisible(allCoursesMenuItem);
        boolean isMenuVisibleBefore  = allCoursesMenuItem.isDisplayed();
        js.executeScript("window.scrollBy(0, arguments[0]);", SCROLL_OFFSET);
        waitForElementToBeVisible(allCoursesMenuItem);
        boolean isMenuVisibleAfter = allCoursesMenuItem.isDisplayed();
        js.executeScript("window.scrollTo(0, 0);");
        return isMenuVisibleBefore  && isMenuVisibleAfter;
    }

    /**
     * Наводит курсор на меню "All Courses".
     * Скроллит к элементу меню и выполняет наведение.
     * Ожидает появления подменю.
     */
    public void hoverOverAllCoursesMenu() {
        scrollToElement(allCoursesMenuItem);
        waitForElementToBeVisible(allCoursesMenuItem);
        actions.moveToElement(allCoursesMenuItem).perform();
        waitForElementToBeVisible(allCoursesSubMenu);
    }

    /**
     * Переходит на страницу Lifetime Membership.
     * Наводит курсор на меню "All Courses", кликает по ссылке Lifetime Membership
     * и ожидает загрузки целевой страницы.
     *
     * @return заголовок целевой страницы Lifetime Membership
     */
    public String navigateToLifetimeMembership() {
        hoverOverAllCoursesMenu();
        waitForElementToBeVisible(lifetimeMembershipLink);
        waitForElementToBeClickable(lifetimeMembershipLink);
        lifetimeMembershipLink.click();
        waitForPageToLoad();
        waitForUrlContains(LIFETIME_MEMBERSHIP_URL_FRAGMENT);
        return driver.getTitle();
    }

    /**
     * Проверяет, находится ли пользователь на странице Lifetime Membership.
     * Проверяет URL и заголовок страницы.
     *
     * @return true если URL содержит соответствующий фрагмент и заголовок содержит "lifetime membership", иначе false
     */
    public boolean isOnLifetimeMembershipPage() {
        String currentUrl = driver.getCurrentUrl();
        String pageTitle = driver.getTitle().toLowerCase();

        return currentUrl.contains(LIFETIME_MEMBERSHIP_URL_FRAGMENT) &&
                pageTitle.contains("lifetime membership");
    }

    /**
     * Получает текст меню "All Courses".
     *
     * @return текст элемента меню "All Courses"
     */
    public String getAllCoursesMenuText() {
        return allCoursesText.getText();
    }

    /**
     * Проверяет отображение подменю "All Courses".
     *
     * @return true если подменю отображается, иначе false
     */
    public boolean isSubMenuDisplayed() {
        return isElementDisplayed(allCoursesSubMenu);
    }

    /**
     * Получает заголовок текущей страницы.
     *
     * @return заголовок страницы
     */
    public String getPageTitle() {
        return driver.getTitle();
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
     * Ожидает, пока URL страницы содержит указанный фрагмент.
     *
     * @param fragment фрагмент URL для проверки
     */
    private void waitForUrlContains(String fragment) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains(fragment));
    }

    /**
     * Ожидает, пока элемент станет кликабельным.
     * Использует WebDriverWait с таймаутом 10 секунд.
     *
     * @param element элемент для ожидания
     */
    private void waitForElementToBeClickable(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Ожидает видимость элемента на странице.
     * Использует WebDriverWait с таймаутом 10 секунд.
     *
     * @param element элемент для ожидания
     */
    private void waitForElementToBeVisible(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(element));
    }
}
