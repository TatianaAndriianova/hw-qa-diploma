package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Slf4j
public class PaymentPage {

    private final SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthField = $("[placeholder='08']");
    private final SelenideElement yearField = $("[placeholder='22']");
    private final SelenideElement holderField = $$("input").get(3);
    private final SelenideElement cvcField = $("[placeholder='999']");
    private final SelenideElement continueButton = $$("button").findBy(exactText("Продолжить"));
    private final SelenideElement successNotification = $(".notification_status_ok");
    private final SelenideElement errorNotification = $(".notification_status_error");

    public PaymentPage() {
        log.info("Открыта страница оплаты");
        $x("//h3[text()='Оплата по карте']").shouldHave(visible);
    }

    public PaymentPage fillForm(DataHelper.CardInfo card) {

        log.info("Заполнение формы: номер={}, месяц={}, год={}, владелец={}, cvc={}",
                card.getNumber(),
                card.getMonth(),
                card.getYear(),
                card.getHolder(),
                card.getCvc());

        cardNumberField.setValue(card.getNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        holderField.setValue(card.getHolder());
        cvcField.setValue(card.getCvc());

        return this;
    }

    public PaymentPage clickContinue() {
        log.info("Нажатие кнопки Продолжить");
        continueButton.click();
        return this;
    }

    public void shouldShowSuccess() {
        log.info("Проверка успешного уведомления");

        successNotification
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Операция одобрена Банком."));
    }

    public void shouldShowError() {
        log.info("Проверка уведомления об ошибке");

        errorNotification
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Ошибка! Банк отказал в проведении операции."));
    }

    public void shouldShowCardNumberError(String expectedText) {
        log.info("Проверка ошибки поля Номер карты: {}", expectedText);
        cardNumberField.parent().parent().$(".input__sub").shouldHave(text(expectedText));
    }

    public void shouldShowMonthError(String expectedText) {
        log.info("Проверка ошибки поля Месяц: {}", expectedText);
        monthField.parent().parent().$(".input__sub").shouldHave(text(expectedText));
    }

    public void shouldShowYearError(String expectedText) {
        log.info("Проверка ошибки поля Год: {}", expectedText);
        yearField.parent().parent().$(".input__sub").shouldHave(text(expectedText));
    }

    public void shouldShowHolderError(String expectedText) {
        log.info("Проверка ошибки поля Владелец: {}", expectedText);
        holderField.parent().parent().$(".input__sub").shouldHave(text(expectedText));
    }

    public void shouldShowCvcError(String expectedText) {
        log.info("Проверка ошибки поля CVC: {}", expectedText);
        cvcField.parent().parent().$(".input__sub").shouldHave(text(expectedText));
    }
}