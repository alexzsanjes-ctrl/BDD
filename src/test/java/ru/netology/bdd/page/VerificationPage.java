package ru.netology.bdd.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.bdd.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement verificationField = $("[data-test-id=code] input");
    private SelenideElement verificationButton = $("[data-test-id=action-verify]");

    public DashboardPage validVerification(DataHelper.VerificationCode code) {
        verificationField.setValue(code.getCode());
        verificationButton.click();
        return new DashboardPage();
    }
}
