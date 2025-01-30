package ru.iteco.fmhandroid.ui.test;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Data;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;


@RunWith(AllureAndroidJUnit4.class)
@Epic("Страница авторизации «Authorization»")
public class AuthorizationTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authorizationPage = new AuthorizationPage();
    MainPage mainPage = new MainPage();
    private View decorView;

    @Before
    public void setUp() {
        mActivityScenarioRule.getScenario().onActivity(activity -> decorView = activity.getWindow().getDecorView());
        try {
            authorizationPage.verifySignInButtonVisible();
        } catch (Exception e) {
            authorizationPage.clickOnProfileImage();
            authorizationPage.clickOnLogout();
        }
    }


    @Test
    @DisplayName("Авторизация зарегистрированного пользователя")
    public void registeredUserAuthorization() {
        authorizationPage.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.VALID_PASSWORD);
        authorizationPage.clickOnSignIn();
        mainPage.theAllNewsItemIsDisplayed();
    }

    @Test
    @DisplayName("Авторизация не зарегистрированного пользователя")
    public void unregisteredUserAuthorization() {
        authorizationPage.fillInTheAuthorizationFields(Data.INVALID_LOGIN, Data.INVALID_PASSWORD);
        authorizationPage.clickOnSignIn();
        authorizationPage.authorizationErrorMessageDisplay();
    }

    @Test
    @DisplayName("Авторизация с пустым полем Login")
    public void authorizationWithEmptyLogin() {
        authorizationPage.fillInTheAuthorizationFields(Data.EMPTY_FIELD, Data.VALID_PASSWORD);
        authorizationPage.clickOnSignIn();
        authorizationPage.emptyFieldErrorMessageDisplay();
    }

    @Test
    @DisplayName("Авторизация с пустым полем Password")
    public void authorizationWithEmptyPassword() {
        authorizationPage.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.EMPTY_FIELD);
        authorizationPage.clickOnSignIn();
        authorizationPage.emptyFieldErrorMessageDisplay();
    }

    @Test
    @DisplayName("Авторизация зарегистрированного пользователя c данными в верхнем регистре")
    public void authorizationWithUppercaseData() {
        authorizationPage.fillInTheAuthorizationFields(Data.INVALID_LOGIN_VALUE, Data.INVALID_PASSWORD_VALUE);
        authorizationPage.clickOnSignIn();
        authorizationPage.authorizationErrorMessageDisplay();
    }

    @Test
    @DisplayName("Простая строка SQL инъекции в поле логин")
    public void simpleSqlInjectionInLoginField() {
        authorizationPage.fillInTheAuthorizationFields(Data.SQL_OR_1_IS_1, Data.VALID_PASSWORD);
        authorizationPage.clickOnSignIn();
        authorizationPage.authorizationErrorMessageDisplay();
    }

    @Test
    @DisplayName("Простая строка SQL инъекции в поле пароль")
    public void simpleSqlInjectionInPasswordField() {
        authorizationPage.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.SQL_OR_1_IS_1);
        authorizationPage.clickOnSignIn();
        authorizationPage.authorizationErrorMessageDisplay();
    }

    @Test
    @DisplayName("Простая строка SQL инъекции в поля логин и пароль одновременно")
    public void simpleSqlInjectionInBothFields() {
        authorizationPage.fillInTheAuthorizationFields(Data.SQL_OR_1_IS_1, Data.SQL_OR_1_IS_1);
        authorizationPage.clickOnSignIn();
        authorizationPage.authorizationErrorMessageDisplay();
    }

    @Test
    @DisplayName("Простая строка XSS инъекции в поле логин")
    public void simpleXSSInjectionInLoginField() {
        authorizationPage.fillInTheAuthorizationFields(Data.XSS_SCRIPT_ALERT, Data.VALID_PASSWORD);
        authorizationPage.clickOnSignIn();
        authorizationPage.authorizationErrorMessageDisplay();
    }

    @Test
    @DisplayName("Простая строка XSS инъекции в поле пароль")
    public void simpleXSSInjectionInPasswordField() {
        authorizationPage.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.XSS_SCRIPT_ALERT);
        authorizationPage.clickOnSignIn();
        authorizationPage.authorizationErrorMessageDisplay();
    }

    @Test
    @DisplayName("Простая строка XSS инъекции в поля логин и пароль одновременно")
    public void simpleXssInjectionInBothFields() {
        authorizationPage.fillInTheAuthorizationFields(Data.XSS_SCRIPT_ALERT, Data.XSS_SCRIPT_ALERT);
        authorizationPage.clickOnSignIn();
        authorizationPage.authorizationErrorMessageDisplay();
    }
}
