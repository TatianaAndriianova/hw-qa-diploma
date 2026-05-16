package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    private final SelenideElement buyButton = $("button[class*='button']:nth-of-type(1)");
    private final SelenideElement buyCreditButton = $("button[class*='button_view_extra']");

    public MainPage() {
        open("http://localhost:8080");
        $("h2").shouldHave(text("Путешествие дня"));
    }

    public PaymentPage clickBuy() {
        $$("button").findBy(exactText("Купить")).click();
        return new PaymentPage();
    }

    public CreditPage clickBuyCredit() {
        $$("button").findBy(exactText("Купить в кредит")).click();
        return new CreditPage();
    }
}