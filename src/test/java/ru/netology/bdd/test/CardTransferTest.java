package ru.netology.bdd.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.bdd.data.DataHelper;
import ru.netology.bdd.page.LoginPage;

import java.util.Random;

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
        Random random = new Random();

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerification(verificationCode);
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();

        int randomAmount = random.nextInt(firstCardBalance+1);

        var replenishmentPage = dashboardPage.secondCard();
        var cardNumber = DataHelper.getFirstCardNumber();
        var newDashboardPage = replenishmentPage.transfer(randomAmount, cardNumber);

        var firstCardActualBalance = newDashboardPage.getFirstCardBalance();
        var secondCardActualBalance = newDashboardPage.getSecondCardBalance();
        int firstCardExpectedBalance = firstCardBalance - randomAmount;
        int secondCardExpectedBalance = secondCardBalance + randomAmount;

        assertEquals(firstCardExpectedBalance, firstCardActualBalance);
        assertEquals(secondCardExpectedBalance, secondCardActualBalance);
    }

    @Test
    @DisplayName("The transfer from the second card to the first should be successfully completed")
    void shouldSuccessTransferFromSecondToFirstCard() {
        Random random = new Random();

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerification(verificationCode);
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();

        int randomAmount = random.nextInt(secondCardBalance+1);

        var replenishmentPage = dashboardPage.firstCard();
        var cardNumber = DataHelper.getSecondCardNumber();
        var newDashboardPage = replenishmentPage.transfer(randomAmount, cardNumber);

        var firstCardActualBalance = newDashboardPage.getFirstCardBalance();
        var secondCardActualBalance = newDashboardPage.getSecondCardBalance();
        int firstCardExpectedBalance = firstCardBalance + randomAmount;
        int secondCardExpectedBalance = secondCardBalance - randomAmount;

        assertEquals(firstCardExpectedBalance, firstCardActualBalance);
        assertEquals(secondCardExpectedBalance, secondCardActualBalance);
    }

    @Test
    @DisplayName("The balance on the cards must be saved if the card number for replenishment and debit matches")
    void shouldSavedBalanceOnTheCardsWhenNumbersIsMatches() {
        Random random = new Random();

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerification(verificationCode);
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();

        int randomAmount = random.nextInt(secondCardBalance+1);

        var replenishmentPage = dashboardPage.secondCard();
        var cardNumber = DataHelper.getSecondCardNumber();
        var newDashboardPage = replenishmentPage.transfer(randomAmount, cardNumber);

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