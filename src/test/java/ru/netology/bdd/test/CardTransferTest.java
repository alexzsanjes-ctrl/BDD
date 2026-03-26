package ru.netology.bdd.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.bdd.data.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

class CardTransferTest {

    @BeforeAll
    static void setupAll() {
        Configuration.browser = "firefox";
        Configuration.holdBrowserOpen = false;
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("The transfer from the first card to the second should be successfully completed if there are enough funds")
    void ShouldSuccessTransferFromFirstToSecondCard() {
        int amount = 10000;
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin();
        var verificationCode = verificationPage.validVerification();
        var startFirstCardBalance = verificationCode.getFirstCardBalance();
        var startSecondCardBalance = verificationCode.getSecondCardBalance();
        var deposit = verificationCode.firstCard();
        var transfer = deposit.transfer(amount,deposit.getSecondCard());
        if ((startSecondCardBalance - amount) < 0) {
            deposit.error();
        } else {
            var firstCardActualBalance = transfer.getFirstCardBalance();
            var secondCardActualBalance = transfer.getSecondCardBalance();
            int firstCardExpectedBalance = startFirstCardBalance + amount;
            int secondCardExpectedBalance = startSecondCardBalance - amount;

            assertEquals(firstCardExpectedBalance, firstCardActualBalance);
            assertEquals(secondCardExpectedBalance, secondCardActualBalance);
        }
    }

    @Test
    @DisplayName("The transfer from the second card to the first should be successfully completed if there are enough funds")
    void ShouldSuccessTransferFromSecondToFirstCard() {
        int amount = 20100;
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin();
        var verificationCode = verificationPage.validVerification();
        var startFirstCardBalance = verificationCode.getFirstCardBalance();
        var startSecondCardBalance = verificationCode.getSecondCardBalance();
        var deposit = verificationCode.secondCard();
        var transfer = deposit.transfer(amount,deposit.getFirstCard());
        if ((startFirstCardBalance - amount) < 0) {
            deposit.error();
        } else {
            var firstCardActualBalance = transfer.getFirstCardBalance();
            var secondCardActualBalance = transfer.getSecondCardBalance();
            int firstCardExpectedBalance = startFirstCardBalance - amount;
            int secondCardExpectedBalance = startSecondCardBalance + amount;

            assertEquals(firstCardExpectedBalance, firstCardActualBalance);
            assertEquals(secondCardExpectedBalance, secondCardActualBalance);
        }
    }

    @Test
    @DisplayName("An error message should be displayed if a non-existent card is entered.")
    void ShouldDisplayErrorNotificationWhenTryingTransferFromUnExistingCard () {
        int amount = 1000;
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin();
        var verificationCode = verificationPage.validVerification();
        var deposit = verificationCode.secondCard();
        var transfer = deposit.transfer(amount,"5559 0000 0000 0003");
        deposit.error();
    }

    @Test
    @DisplayName("An error should be displayed if the card being debited and the card being replenished are the same.")
    void ShouldDisplayErrorWhenCardToAndCardFromIsEquals() {
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin();
        var verificationCode = verificationPage.validVerification();
        var deposit = verificationCode.firstCard();
        var transfer = deposit.transfer(100,deposit.getFirstCard());
        deposit.error();
    }

    @Test
    @DisplayName("Should be redirected to the card page if the cancel button clicked.")
    void ShouldRedirectedToCardPageWhenCancelClicked () {
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin();
        var verificationCode = verificationPage.validVerification();
        var deposit = verificationCode.secondCard();
        var redirect = deposit.cancel();
        redirect.heading();
    }
}