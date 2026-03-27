package ru.netology.bdd.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.bdd.data.DashboardPage;
import ru.netology.bdd.data.DataHelper;
import ru.netology.bdd.data.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

class CardTransferTest {

//    @BeforeAll
//    static void setupAll() {
//        Configuration.browser = "firefox";
//        Configuration.holdBrowserOpen = false;
//    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("The transfer from the first card to the second should be successfully completed")
    void shouldSuccessTransferFromFirstToSecondCard() {
        int amount = 5000;

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerification(verificationCode);
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();

        if (firstCardBalance < amount) {
            amount = firstCardBalance;
        }

        var replenishmentPage = dashboardPage.secondCard();
        var cardNumber = DataHelper.getFirstCardNumber();
        var newDashboardPage = replenishmentPage.transfer(amount, cardNumber);

        var firstCardActualBalance = newDashboardPage.getFirstCardBalance();
        var secondCardActualBalance = newDashboardPage.getSecondCardBalance();
        int firstCardExpectedBalance = firstCardBalance - amount;
        int secondCardExpectedBalance = secondCardBalance + amount;

        assertEquals(firstCardExpectedBalance, firstCardActualBalance);
        assertEquals(secondCardExpectedBalance, secondCardActualBalance);
    }

    @Test
    @DisplayName("The transfer from the second card to the first should be successfully completed")
    void shouldSuccessTransferFromSecondToFirstCard() {
        int amount = 15100;

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerification(verificationCode);
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();

        if (secondCardBalance < amount) {
            amount = secondCardBalance;
        }

        var replenishmentPage = dashboardPage.firstCard();
        var cardNumber = DataHelper.getSecondCardNumber();
        var newDashboardPage = replenishmentPage.transfer(amount, cardNumber);

        var firstCardActualBalance = newDashboardPage.getFirstCardBalance();
        var secondCardActualBalance = newDashboardPage.getSecondCardBalance();
        int firstCardExpectedBalance = firstCardBalance + amount;
        int secondCardExpectedBalance = secondCardBalance - amount;

        assertEquals(firstCardExpectedBalance, firstCardActualBalance);
        assertEquals(secondCardExpectedBalance, secondCardActualBalance);
    }

    @Test
    @DisplayName("The balance on the cards must be saved if the card number for replenishment and debit matches")
    void shouldSavedBalanceOnTheCardsWhenNumbersIsMatches() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerification(verificationCode);
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        var replenishmentPage = dashboardPage.secondCard();
        var cardNumber = DataHelper.getSecondCardNumber();
        var newDashboardPage = replenishmentPage.transfer(1000, cardNumber);

        var firstCardActualBalance = newDashboardPage.getFirstCardBalance();
        var secondCardActualBalance = newDashboardPage.getSecondCardBalance();

        assertEquals(firstCardBalance, firstCardActualBalance);
        assertEquals(secondCardBalance, secondCardActualBalance);
    }

    @Test
    @DisplayName("An error message should be displayed if a non-existent card is entered.")
    void shouldDisplayErrorNotificationWhenTryingTransferFromUnExistingCard() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerification(verificationCode);
        var replenishmentPage = dashboardPage.firstCard();
        var cardNumber = DataHelper.getOtherrCardNumber();
        replenishmentPage.transfer(1000, cardNumber);
        replenishmentPage.error();
    }

    @Test
    @DisplayName("should be redirected to the card page if the cancel button clicked.")
    void shouldRedirectedToCardPageWhenCancelClicked() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerification(verificationCode);
        var replenishmentPage = dashboardPage.firstCard();
        var redirect = replenishmentPage.cancel();
        redirect.heading();
    }
}