package ru.iteco.fmhandroid.ui.test;

import androidx.test.espresso.intent.Intents;
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
import ru.iteco.fmhandroid.ui.pageObject.AboutPage;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;

@RunWith(AllureAndroidJUnit4.class)
@Epic(value = "Страница о приложении «About»")
public class AboutTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authPage = new AuthorizationPage();
    AboutPage aboutPage = new AboutPage();
    MainPage mainPage = new MainPage();

    @Before
    public void setUp() {
        try {
            authPage.verifySignInButtonVisible();
        } catch (Exception e) {
            authPage.clickOnProfileImage();
            authPage.clickOnLogout();
        }
        authPage.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.VALID_PASSWORD);
        authPage.clickOnSignIn();
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnAbout();
    }

    @Test
    @DisplayName("Выход из раздела About кнопкой назад в приложении")
    public void checkingAllElemInAbout() {
        aboutPage.clickOnBack();
        mainPage.theAllNewsItemIsDisplayed();
    }

    @Test
    @DisplayName("Отображение лейбла приложения")
    public void shouldDisplayTheAppLabel() {
        aboutPage.assertDisplayApplicationName();
    }

    @Test
    @DisplayName("Отображение текста версии приложения")
    public void displayTheApplicationVersionText() {
        aboutPage.appVersionTextApproval();
    }

    @Test
    @DisplayName("Отображение номера версии приложения")
    public void displayTheApplicationVersionNumber() {
        aboutPage.appVersionNumberApproval();
    }

    @Test
    @DisplayName("Отображение метки политики конфиденциальности")
    public void shouldDisplayPrivacyPolicyLabel() {
        aboutPage.assertDisplayOfPrivacyPolicyLabel();
    }

    @Test
    @DisplayName("Отображение метки условия эксплуатации")
    public void shouldDisplayTermsOfUseLabel() {
        aboutPage.assertDisplayOfTermsOfUseLabel();
    }

    @Test
    @DisplayName("Отображение лейбла компании")
    public void shouldDisplayTheCompanyNameLabel() {
        aboutPage.assertDisplayTheCompanyNameLabel();
    }

    @Test
    @DisplayName("Должен иметь правильный URL Политика конфиденциальности")
    public void thereMustBeCorrectPrivacyPolicyUrl() {
        Intents.init();
        aboutPage.clickOnPrivacyPolicy();
        aboutPage.verifyIntent(Data.PRIVACY_POLICY_URL);
        Intents.release();
    }

    @Test
    @DisplayName("Должен иметь правильный URL Условия эксплуатации")
    public void thereMustBeCorrectTermsOfUseUrl() {
        Intents.init();
        aboutPage.clickOnTermsOfUse();
        aboutPage.verifyIntent(Data.TERMS_OF_USE_URL);
        Intents.release();
    }
}
