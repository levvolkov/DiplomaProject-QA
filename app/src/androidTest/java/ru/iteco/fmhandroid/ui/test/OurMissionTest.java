package ru.iteco.fmhandroid.ui.test;

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
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;
import ru.iteco.fmhandroid.ui.pageObject.OurMissionPage;


@RunWith(AllureAndroidJUnit4.class)
@Epic("Cтраница c тематическими цитатами «Our Mission»")
public class OurMissionTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authPage = new AuthorizationPage();
    OurMissionPage ourMissionPage = new OurMissionPage();
    MainPage mainPage = new MainPage();
    NewsPage newsPage = new NewsPage();
    AboutPage aboutPage = new AboutPage();

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
        mainPage.openPageWithQuotes();
    }


    @Test
    @DisplayName("Навигация к странице Main со страницы с цитатами")
    public void shouldGoToMainPageFromQuotePage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnMain();
        mainPage.theAllNewsItemIsDisplayed();
    }

    @Test
    @DisplayName("Навигация к странице News со страницы c цитатами")
    public void shouldGoToNewsPageFromQuotePage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnNews();
        newsPage.showNewsManagementButton();
    }

    @Test
    @DisplayName("Навигация к странице About со страницы с цитатами")
    public void shouldGoToAboutPageFromQuotePage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnAbout();
        aboutPage.backButtonVisibility();
    }

    @Test
    @DisplayName("Выход из учетной записи со страницы с цитатами")
    public void shouldLogOutOfAccountOnTheQuotePage() {
        authPage.clickOnProfileImage();
        authPage.clickOnLogout();
        authPage.assertLoginPage();
    }

    @Test
    @DisplayName("Отображение заголовока 'Love is all' на странице с цитатами")
    public void shouldDisplayThePageTitle() {
        ourMissionPage.visibilityTitleLoveIsAll();
    }

    @Test
    @DisplayName("Отображение заголовков цитат")
    public void verifyQuoteTitle() {
        ourMissionPage.assertDisplayOfAllQuoteTitles();
    }

    @Test
    @DisplayName("Отображение описания развернутой цитаты 1")
    public void shouldDisplayDescriptionOfQuotes1() {
        ourMissionPage.expandQuoteByPosition(0);
        ourMissionPage.scrollToQuotePosition(0);
        ourMissionPage.checkQuoteDescription(Data.QUOTE_1_DESCRIPTION);
    }

    @Test
    @DisplayName("Отображение описания развернутой цитаты 2")
    public void shouldDisplayDescriptionOfQuotes2() {
        ourMissionPage.expandQuoteByPosition(1);
        ourMissionPage.scrollToQuotePosition(1);
        ourMissionPage.checkQuoteDescription(Data.QUOTE_2_DESCRIPTION);
    }

    @Test
    @DisplayName("Отображение описания развернутой цитаты 3")
    public void shouldDisplayDescriptionOfQuotes3() {
        ourMissionPage.expandQuoteByPosition(2);
        ourMissionPage.scrollToQuotePosition(2);
        ourMissionPage.checkQuoteDescription(Data.QUOTE_3_DESCRIPTION);
    }

    @Test
    @DisplayName("Отображение описания развернутой цитаты 4")
    public void shouldDisplayDescriptionOfQuotes4() {
        ourMissionPage.expandQuoteByPosition(3);
        ourMissionPage.scrollToQuotePosition(3);
        ourMissionPage.checkQuoteDescription(Data.QUOTE_4_DESCRIPTION);
    }

    @Test
    @DisplayName("Отображение описания развернутой цитаты 5")
    public void shouldDisplayDescriptionOfQuotes5() {
        ourMissionPage.expandQuoteByPosition(4);
        ourMissionPage.scrollToQuotePosition(4);
        ourMissionPage.checkQuoteDescription(Data.QUOTE_5_DESCRIPTION);
    }

    @Test
    @DisplayName("Отображение описания развернутой цитаты 6")
    public void shouldDisplayDescriptionOfQuotes6() {
        ourMissionPage.expandQuoteByPosition(5);
        ourMissionPage.scrollToQuotePosition(5);
        ourMissionPage.checkQuoteDescription(Data.QUOTE_6_DESCRIPTION);
    }

    @Test
    @DisplayName("Отображение описания развернутой цитаты 7")
    public void shouldDisplayDescriptionOfQuotes7() {
        ourMissionPage.expandQuoteByPosition(6);
        ourMissionPage.scrollToQuotePosition(6);
        ourMissionPage.checkQuoteDescription(Data.QUOTE_7_DESCRIPTION);
    }

    @Test
    @DisplayName("Отображение описания развернутой цитаты 8")
    public void shouldDisplayDescriptionOfQuotes8() {
        ourMissionPage.expandQuoteByPosition(7);
        ourMissionPage.scrollToQuotePosition(7);
        ourMissionPage.checkQuoteDescription(Data.QUOTE_8_DESCRIPTION);
    }
}
