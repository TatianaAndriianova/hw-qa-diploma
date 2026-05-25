package ru.netology;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты оформления кредита")
public class CreditTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10_000;
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeAllListeners();
        Selenide.closeWebDriver();
    }

    @BeforeEach
    void cleanUp() {
        SQLHelper.cleanDatabase();
    }

    @Test
    @DisplayName("Успешное оформление кредита одобренной картой")
    void shouldGetCreditWithApprovedCard() {
        var card = DataHelper.getApprovedCard();
        var creditPage = new MainPage().clickBuyCredit();
        creditPage.fillForm(card).clickContinue();
        creditPage.shouldShowSuccess();
        assertEquals("APPROVED", SQLHelper.getCreditStatus());
    }

    @Test
    @DisplayName("Отказ в кредите по отклонённой карте")
    void shouldDeclineCreditWithDeclinedCard() {
        var card = DataHelper.getDeclinedCard();
        var creditPage = new MainPage().clickBuyCredit();
        creditPage.fillForm(card).clickContinue();
        creditPage.shouldShowError();
        assertEquals("DECLINED", SQLHelper.getCreditStatus());
    }

    @Test
    @DisplayName("Пустой номер карты")
    void shouldShowErrorForEmptyCardNumber() {
        var card = DataHelper.getCardWithEmptyNumber();
        var creditPage = new MainPage().clickBuyCredit();
        creditPage.fillForm(card).clickContinue();

        creditPage.shouldShowCardNumberError("Неверный формат");
    }

    @Test
    @DisplayName("Короткий номер карты")
    void shouldShowErrorForShortCardNumber() {
        var card = DataHelper.getCardWithShortNumber();
        var creditPage = new MainPage().clickBuyCredit();
        creditPage.fillForm(card).clickContinue();

        creditPage.shouldShowCardNumberError("Неверный формат");
    }

    @Test
    @DisplayName("Карта не из набора эмулятора")
    void shouldDeclineUnknownCard() {
        var card = DataHelper.getCardWithInvalidNumber();
        var creditPage = new MainPage().clickBuyCredit();
        creditPage.fillForm(card).clickContinue();
        creditPage.shouldShowError();
    }

    @Test
    @DisplayName("Пустой месяц")
    void shouldShowErrorForEmptyMonth() {
        var card = DataHelper.getCardWithEmptyMonth();
        var creditPage = new MainPage().clickBuyCredit();
        creditPage.fillForm(card).clickContinue();
        creditPage.shouldShowMonthError("Неверный формат");
    }

    @Test
    @DisplayName("Невалидный месяц — 13")
    void shouldShowErrorForInvalidMonth() {
        var card = DataHelper.getCardWithInvalidMonth();
        var creditPage = new MainPage().clickBuyCredit();
        creditPage.fillForm(card).clickContinue();
        creditPage.shouldShowMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Нулевой месяц — 00")
    void shouldShowErrorForZeroMonth() {
        var card = DataHelper.getCardWithZeroMonth();
        var creditPage = new MainPage().clickBuyCredit();
        creditPage.fillForm(card).clickContinue();
        creditPage.shouldShowMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Истёкший месяц")
    void shouldShowErrorForExpiredMonth() {
        var card = DataHelper.getCardWithExpiredMonth();
        var creditPage = new MainPage().clickBuyCredit();
        creditPage.fillForm(card).clickContinue();
        creditPage.shouldShowMonthError("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Пустой год")
    void shouldShowErrorForEmptyYear() {
        var card = DataHelper.getCardWithEmptyYear();
        var creditPage = new MainPage().clickBuyCredit();
        creditPage.fillForm(card).clickContinue();
        creditPage.shouldShowYearError("Неверный формат");
    }

    @Test
    @DisplayName("Истёкший год")
    void shouldShowErrorForExpiredYear() {
        var card = DataHelper.getCardWithExpiredYear();
        var creditPage = new MainPage().clickBuyCredit();
        creditPage.fillForm(card).clickContinue();
        creditPage.shouldShowYearError("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Год слишком далеко в будущем")
    void shouldShowErrorForFarFutureYear() {
        var card = DataHelper.getCardWithFutureYear();
        var creditPage = new MainPage().clickBuyCredit();
        creditPage.fillForm(card).clickContinue();
        creditPage.shouldShowYearError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Пустое поле владельца")
    void shouldShowErrorForEmptyHolder() {
        var card = DataHelper.getCardWithEmptyHolder();
        var creditPage = new MainPage().clickBuyCredit();
        creditPage.fillForm(card).clickContinue();
        creditPage.shouldShowHolderError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Владелец кириллицей")
    void shouldShowErrorForCyrillicHolder() {
        var card = DataHelper.getCardWithCyrillicHolder();
        var creditPage = new MainPage().clickBuyCredit();
        creditPage.fillForm(card).clickContinue();
        creditPage.shouldShowHolderError("Неверный формат");
    }

    @Test
    @DisplayName("Владелец — спецсимволы")
    void shouldShowErrorForSpecialSymbolsHolder() {
        var card = DataHelper.getCardWithSpecialSymbolsHolder();
        var creditPage = new MainPage().clickBuyCredit();
        creditPage.fillForm(card).clickContinue();
        creditPage.shouldShowHolderError("Неверный формат");
    }

    @Test
    @DisplayName("Пустой CVC")
    void shouldShowErrorForEmptyCVC() {
        var card = DataHelper.getCardWithEmptyCVC();
        var creditPage = new MainPage().clickBuyCredit();
        creditPage.fillForm(card).clickContinue();
        creditPage.shouldShowCvcError("Неверный формат");
    }

    @Test
    @DisplayName("CVC из 2 цифр")
    void shouldShowErrorForShortCVC() {
        var card = DataHelper.getCardWithShortCVC();
        var creditPage = new MainPage().clickBuyCredit();
        creditPage.fillForm(card).clickContinue();
        creditPage.shouldShowCvcError("Неверный формат");
    }
}