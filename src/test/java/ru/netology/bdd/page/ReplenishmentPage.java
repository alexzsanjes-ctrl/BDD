package ru.netology.bdd.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.bdd.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class ReplenishmentPage {

    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement cancelButton = $("[data-test-id=action-cancel]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification] .notification__title");

    public DashboardPage transfer(int amount, DataHelper.CardNumber number) {
        amountField.setValue(String.valueOf(amount));
        fromField.setValue(number.getNumber());
        transferButton.click();
        return new DashboardPage();
    }

    public DashboardPage cancel() {
        cancelButton.click();
        return new DashboardPage();
    }

    public void error() {
        errorNotification.shouldBe(Condition.visible).
                shouldBe(Condition.exactText("Ошибка"));
    }
}
