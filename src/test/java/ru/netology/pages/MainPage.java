package ru.netology.pages;

import lombok.extern.slf4j.Slf4j;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Slf4j
public class MainPage {

    public MainPage() {
        log.info("Открытие главной страницы");
        open("http://localhost:8080");
        $("h2").shouldHave(text("Путешествие дня"));
    }

    public PaymentPage clickBuy() {
        log.info("Нажатие кнопки Купить");
        $$("button").findBy(exactText("Купить")).click();
        return new PaymentPage();
    }

    public CreditPage clickBuyCredit() {
        log.info("Нажатие кнопки Купить в кредит");
        $$("button").findBy(exactText("Купить в кредит")).click();
        return new CreditPage();
    }
}