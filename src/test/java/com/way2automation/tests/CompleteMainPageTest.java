package com.way2automation.tests;

import com.way2automation.pages.MainPage;
import com.way2automation.utils.WebDriverManagerUtil;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * Комплексный тестовый класс для проверки главной страницы Way2Automation.
 * Содержит тесты для проверки различных элементов и функциональности главной страницы.
 */
public class CompleteMainPageTest extends BaseTest {

    /**
     * Ожидаемый URL главной страницы
     */
    private static final String EXPECTED_URL = "https://www.way2automation.com/";

    /**
     * Текст для проверки навигации к странице Lifetime Membership
     */
    private static final String LIFETIME_MEMBERSHIP_TEXT = "Lifetime Membership";

    /**
     * Экземпляр WebDriver для управления браузером
     */
    private WebDriver driver;

    /**
     * Экземпляр страницы MainPage для взаимодействия с элементами главной страницы
     */
    private MainPage mainPage;

    /**
     * Экземпляр SoftAssert для мягких проверок в тестах
     */
    private SoftAssert softAssert;

    /**
     * Метод настройки перед выполнением каждого теста.
     * Инициализирует WebDriver, открывает главную страницу и создает SoftAssert.
     */
    @BeforeMethod
    public void setUp() {
        super.setUp();
        driver = WebDriverManagerUtil.getDriver();
        mainPage = new MainPage(driver);
        mainPage.openMainPage();
        softAssert = new SoftAssert();
    }

    /**
     * Тест проверки открытия главной страницы.
     * Проверяет, что страница успешно открывается и URL соответствует ожидаемому.
     */
    @Test
    public void testPageOpening() {
        boolean isPageOpened = mainPage.isPageOpened();
        String currentUrl = mainPage.getCurrentUrl();

        softAssert.assertTrue(isPageOpened, "Главная страница должна открываться");
        softAssert.assertEquals(currentUrl, EXPECTED_URL,
                "URL должен быть " + EXPECTED_URL);

        softAssert.assertAll();
    }

    /**
     * Тест проверки контактной информации в хедере страницы.
     * Проверяет, что контактная информация отображается корректно.
     */
    @Test
    public void testHeaderContactInfo() {
        boolean isContactInfoDisplayed = mainPage.isContactInfoDisplayed();

        softAssert.assertTrue(isContactInfoDisplayed,
                "Контактная информация в хедере должна отображаться");

        softAssert.assertAll();
    }

    /**
     * Тест проверки блока курсов на главной странице.
     * Проверяет отображение блока курсов и содержание карточек курсов.
     */
    @Test
    public void testCoursesBlock() {
        boolean isCoursesBlockDisplayed = mainPage.isCoursesBlockDisplayed();
        boolean hasValidCourseCardsContent = mainPage.verifyCourseCardsContent();

        softAssert.assertTrue(isCoursesBlockDisplayed,
                "Блок 'Most Popular Software Testing Courses' должен отображаться");
        softAssert.assertTrue(hasValidCourseCardsContent,
                "Карточки курсов должны содержать заголовок и кнопку");

        softAssert.assertAll();
    }

    /**
     * Тест проверки требований к футеру страницы.
     * Проверяет отображение футера и наличие обязательной информации:
     * адреса, номеров телефонов и email.
     */
    @Test
    public void testFooterRequirements() {
        mainPage.scrollToFooter();

        softAssert.assertTrue(mainPage.isFooterDisplayed(),
                "Футер должен отображаться на странице");

        softAssert.assertTrue(mainPage.hasAddress(),
                "Футер должен содержать адрес");

        softAssert.assertTrue(mainPage.hasPhones(),
                "Футер должен содержать номера телефонов");

        softAssert.assertTrue(mainPage.hasEmails(),
                "Футер должен содержать email");

        softAssert.assertAll();
    }

    /**
     * Тест проверки поведения меню при скролле страницы.
     * Проверяет, что меню 'All Courses' содержит правильный текст
     * и остается видимым после скролла страницы.
     */
    @Test
    public void testMenuOnScroll() {
        String menuText = mainPage.getAllCoursesMenuText();
        boolean isMenuVisibleAfterScroll = mainPage.isMenuVisibleAfterScroll();

        softAssert.assertTrue(menuText.contains("All Courses"),
                "Меню должно содержать текст 'All Courses'");
        softAssert.assertTrue(isMenuVisibleAfterScroll,
                "Меню 'All Courses' должно оставаться видимым после скролла");

        softAssert.assertAll();
    }

    /**
     * Тест навигации к странице Lifetime Membership.
     * Проверяет корректность перехода на страницу Lifetime Membership,
     * наличие правильного заголовка и URL.
     */
    @Test
    public void testNavigationToLifetimeMembership() {
        String pageTitle = mainPage.navigateToLifetimeMembership();
        String currentUrl = mainPage.getCurrentUrl();
        boolean isOnLifetimeMembershipPage = mainPage.isOnLifetimeMembershipPage();

        softAssert.assertNotNull(pageTitle, "Заголовок страницы не должен быть null");
        softAssert.assertTrue(pageTitle.toLowerCase().contains(LIFETIME_MEMBERSHIP_TEXT.toLowerCase()),
                "Заголовок должен содержать '" + LIFETIME_MEMBERSHIP_TEXT + "'");
        softAssert.assertTrue(currentUrl.contains("lifetime-membership-club"),
                "URL должен содержать 'lifetime-membership-club'");
        softAssert.assertTrue(isOnLifetimeMembershipPage,
                "Должна быть выполнена навигация на страницу Lifetime Membership");

        softAssert.assertAll();
    }

    /**
     * Тест проверки отображения подменю при наведении на пункт меню 'All Courses'.
     * Проверяет, что подменю корректно отображается при наведении курсора.
     */
    @Test
    public void testAllCoursesSubmenu() {
        mainPage.hoverOverAllCoursesMenu();
        boolean isSubMenuDisplayed = mainPage.isSubMenuDisplayed();

        softAssert.assertTrue(isSubMenuDisplayed,
                "Подменю 'All Courses' должно отображаться при наведении");

        softAssert.assertAll();
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
