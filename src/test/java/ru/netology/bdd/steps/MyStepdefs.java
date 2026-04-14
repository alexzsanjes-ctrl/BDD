package ru.netology.bdd.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.PendingException;
import io.cucumber.java.ru.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import ru.netology.bdd.data.DataHelper;
import ru.netology.bdd.page.DashboardPage;
import ru.netology.bdd.page.LoginPage;
import ru.netology.bdd.page.ReplenishmentPage;
import ru.netology.bdd.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;

public class MyStepdefs {
    private static LoginPage loginPage;
    private static VerificationPage verificationPage;
    private static DashboardPage dashboardPage;
    private static ReplenishmentPage replenishmentPage;

    @Дано("открыта страница с формой авторизации {string}")
    public void открытаСтраницаСФормойАвторизации(String url) {
        loginPage = open(url, LoginPage.class);
    }

    @Пусть("пользователь логинется с именем {string} и паролем {string}")
    public void пустьПользовательЛогинетсяСИменемИПаролем(String login, String password) {
        verificationPage = loginPage.validLogin(login, password);
    }

    @И("пользователь вводит проверочный код из смс {string}")
    public void пользовательВводитПроверочныйКодИзСмс(String code) {
        dashboardPage = verificationPage.validVerification(code);
    }

    @Когда("пользователь переводит {int} рублей с карты с номером {string} на свою 1-ю карту с главной страницы")
    public void пользовательПереводитРублейСКартыСНомеромНаСвоюКартуСГлавнойСтраницы(int amount, String cardNumber) {
        replenishmentPage = dashboardPage.firstCard();
        replenishmentPage.transfer(amount, cardNumber);
    }

    @Тогда("баланс его 1-ой карты из списка на главной странице должен стать {int} рублей.")
    public void балансЕгоКартыИзСпискаНаГлавнойСтраницеДолженСтатьРублей(int balance) {
        Assertions.assertEquals(dashboardPage.getFirstCardBalance(),balance);
    }
}
