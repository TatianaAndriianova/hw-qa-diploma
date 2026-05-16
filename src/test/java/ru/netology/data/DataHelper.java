package ru.netology.data;

import lombok.Value;

public class DataHelper {

    private DataHelper() {
    }

    public static CardInfo getApprovedCard() {
        return new CardInfo(
                "4444 4444 4444 4441",
                getValidMonth(),
                getValidYear(),
                getValidHolder(),
                getValidCVC()
        );
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo(
                "4444 4444 4444 4442",
                getValidMonth(),
                getValidYear(),
                getValidHolder(),
                getValidCVC()
        );
    }

    public static CardInfo getCardWithEmptyNumber() {
        return new CardInfo("", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardWithInvalidNumber() {
        return new CardInfo("1234 5678 9012 3456", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardWithShortNumber() {
        return new CardInfo("4444 4444 4444", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardWithExpiredMonth() {
        return new CardInfo("4444 4444 4444 4441", "01", "23", getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardWithExpiredYear() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), "20", getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardWithFutureYear() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), "30", getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardWithInvalidMonth() {
        return new CardInfo("4444 4444 4444 4441", "13", getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardWithZeroMonth() {
        return new CardInfo("4444 4444 4444 4441", "00", getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardWithEmptyMonth() {
        return new CardInfo("4444 4444 4444 4441", "", getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardWithEmptyYear() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), "", getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardWithCyrillicHolder() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), getValidYear(), "Иван Иванов", getValidCVC());
    }

    public static CardInfo getCardWithEmptyHolder() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), getValidYear(), "", getValidCVC());
    }

    public static CardInfo getCardWithSpecialSymbolsHolder() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), getValidYear(), "!@#$%^", getValidCVC());
    }

    public static CardInfo getCardWithNumbersHolder() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), getValidYear(), "1234 5678", getValidCVC());
    }

    public static CardInfo getCardWithShortCVC() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), getValidYear(), getValidHolder(), "12");
    }

    public static CardInfo getCardWithEmptyCVC() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), getValidYear(), getValidHolder(), "");
    }

    private static String getValidMonth() {
        return "08";
    }

    private static String getValidYear() {
        return "27";
    }

    private static String getValidHolder() {
        return "Ivan Ivanov";
    }

    private static String getValidCVC() {
        return "123";
    }

    @Value
    public static class CardInfo {
        String number;
        String month;
        String year;
        String holder;
        String cvc;
    }
}