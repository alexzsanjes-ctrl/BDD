package ru.netology.bdd.data;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class ReplenishmentPage {

    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement cancelButton = $("[data-test-id=action-cancel]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification] .notification__title");
    private final String firstCard = "5559 0000 0000 0001";
    private final String secondCard = "5559 0000 0000 0002";

    public String getFirstCard() {
        return firstCard;
    }

    public String getSecondCard() {
        return secondCard;
    }

    public DashboardPage transfer (int amount, String from) {
    amountField.setValue(String.valueOf(amount));
    fromField.setValue(from);
    transferButton.click();
    return new DashboardPage();
    }
    
    public DashboardPage cancel () {
        cancelButton.click();
        return new DashboardPage();
    }
    
    public SelenideElement error() {
        return errorNotification.shouldBe(Condition.visible).
                shouldBe(Condition.exactText("Ошибка"));
    }
}
