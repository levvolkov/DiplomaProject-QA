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
import ru.iteco.fmhandroid.ui.pageObject.AboutPage;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.EditorialPage;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;
import ru.iteco.fmhandroid.ui.pageObject.OurMissionPage;


@RunWith(AllureAndroidJUnit4.class)
@Epic(value = "Приветствующая страница «News»")
public class NewsTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authPage = new AuthorizationPage();
    NewsPage newsPage = new NewsPage();
    MainPage mainPage = new MainPage();
    AboutPage aboutPage = new AboutPage();
    OurMissionPage ourMissionPage = new OurMissionPage();
    EditorialPage editorialPage = new EditorialPage();

    private View decorView;

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
        mainPage.clickOnNews();
    }


    @Test
    @DisplayName("Навигация к странице Main с приветствующей страницы 'News'")
    public void shouldGoToMainPageFromNewsPage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnMain();
        mainPage.theAllNewsItemIsDisplayed();
    }

    @Test // Тест не проходит! Кнопка "About" в гамбургер-меню не активна с приветствующей страницы "News"
    @DisplayName("Навигация к странице About с приветствующей страницы 'News'")
    public void shouldGoToAboutPageFromNewsPage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnAbout();
        aboutPage.backButtonVisibility();
    }

    @Test
    @DisplayName("Навигация к странице Our Mission с приветствующей страницы 'News'")
    public void shouldGoToOurMissionPageFromNewsPage() {
        mainPage.openPageWithQuotes();
        ourMissionPage.visibilityTitleLoveIsAll();
    }

    @Test
    @DisplayName("Выход из учетной записи с приветствующей страницы 'News'")
    public void shouldLogOutOfAccountOnTheNewsPage() {
        authPage.clickOnProfileImage();
        authPage.clickOnLogout();
        authPage.assertLoginPage();
    }

    @Test
    @DisplayName("Ввод поочередно каждой категории в фильтре новостей")
    public void theFieldShouldAcceptAllNewsCategories() {
        newsPage.openNewsFilter();
        editorialPage.verifySelectedCategories();
    }

    @Test
    @DisplayName("Фильтрация новостей по диапазону дат")
    public void shouldFilterNewsWithinDateRange() {
        newsPage.openNewsFilter();
        newsPage.enterFromWhatDate(-7); // Дней назад
        newsPage.enterUntilWhatDate(7); // Дней вперед
        newsPage.clickOnApplyFilterButton();
        newsPage.checkAllNewsDateRange(-7, 7);
    }

    @Test
    @DisplayName("Отмена применения фильтра новостей")
    public void shouldCancelNewsFilterApplication() {
        newsPage.openNewsFilter();
        editorialPage.pressCancel();
        newsPage.verifyTextAfterCancelingFilter(Data.NEWS_TEXT);
    }
}
