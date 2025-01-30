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
@Epic("Основная страница «Main»")
public class MainTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authPage = new AuthorizationPage();
    MainPage mainPage = new MainPage();
    NewsPage newsPage = new NewsPage();
    AboutPage aboutPage = new AboutPage();
    OurMissionPage ourMissionPage = new OurMissionPage();

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
    }

    @Test
    @DisplayName("Навигация к странице News со страницы Main")
    public void shouldGoToNewsPageFromMainPage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnNews();
        newsPage.showNewsManagementButton();
    }

    @Test
    @DisplayName("Навигация к странице About со страницы Main")
    public void shouldGoToAboutPageFromMainPage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnAbout();
        aboutPage.backButtonVisibility();
    }

    @Test
    @DisplayName("Перейти на страницу Our Mission со страницы Main")
    public void shouldGoToOurMissionFromMainPage() {
        mainPage.openPageWithQuotes();
        ourMissionPage.visibilityTitleLoveIsAll();
    }

    @Test
    @DisplayName("Выход из учетной записи со страницы управления новостями")
    public void shouldLogOutOfAccountOnTheMainPage() {
        authPage.clickOnProfileImage();
        authPage.clickOnLogout();
        authPage.assertLoginPage();
    }

    @Test
    @DisplayName("Аккордеон списка новостей на основной странице Main")
    public void checkMainNewsAccordion() {
        mainPage.expandMaterialButton();
        mainPage.allNewsItemNotDisplayed();
        mainPage.expandMaterialButton();
        mainPage.theAllNewsItemIsDisplayed();
    }

    @Test
    @DisplayName("Перейти на страницу News нажав на кнопку-ссылку ALL NEWS")
    public void goToTheButtonAllNews() {
        mainPage.clickOnAllNews();
        newsPage.showNewsManagementButton();
    }
}
