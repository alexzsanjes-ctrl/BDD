package ru.netology.bdd.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.bdd.data.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

class LoginPageTest {

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
    void ShouldSuccessTransferFromFirstToSecondCard() {
        int amount = 10000;
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin();
        var verificationCode = verificationPage.validVerification();
        var startFirstCardBalance = verificationCode.getFirstCardBalance();
        var startSecondCardBalance = verificationCode.getSecondCardBalance();
        var deposit = verificationCode.firstCard();
        var transfer = deposit.transfer(amount,"5559 0000 0000 0002");
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
    void ShouldSuccessTransferFromSecondToFirstCard() {
        int amount = 20100;
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin();
        var verificationCode = verificationPage.validVerification();
        var startFirstCardBalance = verificationCode.getFirstCardBalance();
        var startSecondCardBalance = verificationCode.getSecondCardBalance();
        var deposit = verificationCode.secondCard();
        var transfer = deposit.transfer(amount,"5559 0000 0000 0001");
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
    void ShouldDisplayErrorNotificationWhenTryingTransferFromUnexistingCard () {
        int amount = 1000;
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin();
        var verificationCode = verificationPage.validVerification();
        var deposit = verificationCode.secondCard();
        var transfer = deposit.transfer(amount,"5559 0000 0000 0003");
        deposit.error();
    }
}