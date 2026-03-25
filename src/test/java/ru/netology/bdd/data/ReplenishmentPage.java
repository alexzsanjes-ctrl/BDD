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
