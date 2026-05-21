package ru.netology;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты оплаты картой")
public class PaymentTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10_000;
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeAllListeners();
    }

    @BeforeEach
    void cleanUp() {
        SQLHelper.cleanDatabase();
    }

    @Test
    @DisplayName("Успешная оплата одобренной картой")
    void shouldPayWithApprovedCard() {
        var card = DataHelper.getApprovedCard();
        var paymentPage = new MainPage().clickBuy();
        paymentPage.fillForm(card).clickContinue();
        paymentPage.shouldShowSuccess();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("Отказ при оплате отклонённой картой")
    void shouldDeclinePaymentWithDeclinedCard() {
        var card = DataHelper.getDeclinedCard();
        var paymentPage = new MainPage().clickBuy();
        paymentPage.fillForm(card).clickContinue();
        paymentPage.shouldShowError();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("Пустой номер карты")
    void shouldShowErrorForEmptyCardNumber() {
        var card = DataHelper.getCardWithEmptyNumber();
        var paymentPage = new MainPage().clickBuy();
        paymentPage.fillForm(card).clickContinue();

        paymentPage.shouldShowCardNumberError("Неверный формат");
    }

    @Test
    @DisplayName("Короткий номер карты (меньше 16 цифр)")
    void shouldShowErrorForShortCardNumber() {
        var card = DataHelper.getCardWithShortNumber();
        var paymentPage = new MainPage().clickBuy();
        paymentPage.fillForm(card).clickContinue();

        paymentPage.shouldShowCardNumberError("Неверный формат");
    }

    @Test
    @DisplayName("Номер карты не из набора эмулятора")
    void shouldDeclineUnknownCard() {
        var card = DataHelper.getCardWithInvalidNumber();
        var paymentPage = new MainPage().clickBuy();
        paymentPage.fillForm(card).clickContinue();

        paymentPage.shouldShowError();
    }

    @Test
    @DisplayName("Пустой месяц")
    void shouldShowErrorForEmptyMonth() {
        var card = DataHelper.getCardWithEmptyMonth();
        var paymentPage = new MainPage().clickBuy();
        paymentPage.fillForm(card).clickContinue();

        paymentPage.shouldShowMonthError("Неверный формат");
    }

    @Test
    @DisplayName("Невалидный месяц — 13")
    void shouldShowErrorForInvalidMonth() {
        var card = DataHelper.getCardWithInvalidMonth();
        var paymentPage = new MainPage().clickBuy();
        paymentPage.fillForm(card).clickContinue();

        paymentPage.shouldShowMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Нулевой месяц — 00")
    void shouldShowErrorForZeroMonth() {
        var card = DataHelper.getCardWithZeroMonth();
        var paymentPage = new MainPage().clickBuy();
        paymentPage.fillForm(card).clickContinue();

        paymentPage.shouldShowMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Истёкший месяц (карта с прошедшей датой)")
    void shouldShowErrorForExpiredMonth() {
        var card = DataHelper.getCardWithExpiredMonth();
        var paymentPage = new MainPage().clickBuy();
        paymentPage.fillForm(card).clickContinue();

        paymentPage.shouldShowMonthError("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Пустой год")
    void shouldShowErrorForEmptyYear() {
        var card = DataHelper.getCardWithEmptyYear();
        var paymentPage = new MainPage().clickBuy();
        paymentPage.fillForm(card).clickContinue();

        paymentPage.shouldShowYearError("Неверный формат");
    }

    @Test
    @DisplayName("Истёкший год")
    void shouldShowErrorForExpiredYear() {
        var card = DataHelper.getCardWithExpiredYear();
        var paymentPage = new MainPage().clickBuy();
        paymentPage.fillForm(card).clickContinue();

        paymentPage.shouldShowYearError("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Год слишком далеко в будущем (более 5 лет)")
    void shouldShowErrorForFarFutureYear() {
        var card = DataHelper.getCardWithFutureYear();
        var paymentPage = new MainPage().clickBuy();
        paymentPage.fillForm(card).clickContinue();

        paymentPage.shouldShowYearError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Пустое поле владельца")
    void shouldShowErrorForEmptyHolder() {
        var card = DataHelper.getCardWithEmptyHolder();
        var paymentPage = new MainPage().clickBuy();
        paymentPage.fillForm(card).clickContinue();

        paymentPage.shouldShowHolderError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Владелец кириллицей")
    void shouldShowErrorForCyrillicHolder() {
        var card = DataHelper.getCardWithCyrillicHolder();
        var paymentPage = new MainPage().clickBuy();
        paymentPage.fillForm(card).clickContinue();

        paymentPage.shouldShowHolderError("Неверный формат");
    }

    @Test
    @DisplayName("Владелец — спецсимволы")
    void shouldShowErrorForSpecialSymbolsHolder() {
        var card = DataHelper.getCardWithSpecialSymbolsHolder();
        var paymentPage = new MainPage().clickBuy();
        paymentPage.fillForm(card).clickContinue();

        paymentPage.shouldShowHolderError("Неверный формат");
    }

    @Test
    @DisplayName("Владелец — цифры")
    void shouldShowErrorForNumbersHolder() {
        var card = DataHelper.getCardWithNumbersHolder();
        var paymentPage = new MainPage().clickBuy();
        paymentPage.fillForm(card).clickContinue();

        paymentPage.shouldShowHolderError("Неверный формат");
    }

    @Test
    @DisplayName("Пустой CVC")
    void shouldShowErrorForEmptyCVC() {
        var card = DataHelper.getCardWithEmptyCVC();
        var paymentPage = new MainPage().clickBuy();
        paymentPage.fillForm(card).clickContinue();

        paymentPage.shouldShowCvcError("Неверный формат");
    }

    @Test
    @DisplayName("CVC из 2 цифр")
    void shouldShowErrorForShortCVC() {
        var card = DataHelper.getCardWithShortCVC();
        var paymentPage = new MainPage().clickBuy();
        paymentPage.fillForm(card).clickContinue();

        paymentPage.shouldShowCvcError("Неверный формат");
    }
}