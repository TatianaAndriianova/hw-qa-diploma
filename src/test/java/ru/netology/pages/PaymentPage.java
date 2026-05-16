package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class PaymentPage {

    private final SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthField = $("[placeholder='08']");
    private final SelenideElement yearField = $("[placeholder='22']");
    private final SelenideElement holderField = $$("input").get(3); // поле "Владелец" без placeholder
    private final SelenideElement cvcField = $("[placeholder='999']");
    private final SelenideElement continueButton = $$("button").findBy(exactText("Продолжить"));
    private final SelenideElement successNotification = $(".notification_status_ok");
    private final SelenideElement errorNotification = $(".notification_status_error");

    public PaymentPage() {
        $x("//h3[text()='Оплата по карте']").shouldHave(visible);
    }

    public PaymentPage fillForm(DataHelper.CardInfo card) {
        cardNumberField.setValue(card.getNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        holderField.setValue(card.getHolder());
        cvcField.setValue(card.getCvc());
        return this;
    }

    public PaymentPage clickContinue() {
        continueButton.click();
        return this;
    }

    public void shouldShowSuccess() {
        successNotification
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Операция одобрена Банком."));
    }

    public void shouldShowError() {
        errorNotification
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Ошибка! Банк отказал в проведении операции."));
    }

    public void shouldShowCardNumberError(String expectedText) {
        cardNumberField.parent().$(".input__sub").shouldHave(text(expectedText));
    }

    public void shouldShowMonthError(String expectedText) {
        monthField.parent().$(".input__sub").shouldHave(text(expectedText));
    }

    public void shouldShowYearError(String expectedText) {
        yearField.parent().$(".input__sub").shouldHave(text(expectedText));
    }

    public void shouldShowHolderError(String expectedText) {
        holderField.parent().$(".input__sub").shouldHave(text(expectedText));
    }

    public void shouldShowCvcError(String expectedText) {
        cvcField.parent().$(".input__sub").shouldHave(text(expectedText));
    }

    public void shouldNotShowAnyValidationErrors() {
        $$(".input__sub").forEach(el -> el.shouldNotBe(visible));
    }
}