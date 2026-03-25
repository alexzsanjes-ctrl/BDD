package ru.netology.bdd.data;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement verificationField = $("[data-test-id=code] input");
    private  SelenideElement verificationButton = $("[data-test-id=action-verify]");
    private final String code = "12345";

    public DashboardPage validVerification () {
        verificationField.setValue(code);
        verificationButton.click();
        return new DashboardPage();
    }
}
