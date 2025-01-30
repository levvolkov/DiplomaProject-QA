package ru.iteco.fmhandroid.ui.test;

import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.data.Helper.generateRandomThreeDigitString;
import static ru.iteco.fmhandroid.ui.pageObject.NewsPage.STATUS_ACTIVE;
import static ru.iteco.fmhandroid.ui.pageObject.NewsPage.STATUS_NOT_ACTIVE;

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
@Epic(value = "Поиск новостей на странице управления «News»")
public class NewsSearchTest {

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
        newsPage.openNewsManagementPage();
    }

    String randomTitle = Data.NEWS_TITLE_TEXT + generateRandomThreeDigitString();

    @Test
    @DisplayName("Навигация к странице Main со страницы управления новостями")
    public void shouldGoToMainPageFromNewsManagementPage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnMain();
        mainPage.theAllNewsItemIsDisplayed();
    }

    @Test
    @DisplayName("Навигация к  приветствующей странице News со страницы управления новостями")
    public void shouldGoToNewsPageFromNewsManagementPage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnNews();
        newsPage.showNewsManagementButton();
    }

    @Test
    @DisplayName("Навигация к странице About со страницы управления новостями")
    public void shouldGoToAboutPageFromNewsManagementPage() {
        mainPage.clickOnHamburgerMenu();
        mainPage.clickOnAbout();
        aboutPage.backButtonVisibility();
    }

    @Test
    @DisplayName("Навигация к странице Our Mission со страницы управления новостями")
    public void shouldGoToOurMissionPageFromNewsManagementPage() {
        mainPage.openPageWithQuotes();
        ourMissionPage.visibilityTitleLoveIsAll();
    }

    @Test
    @DisplayName("Выход из учетной записи со страницы управления новостями")
    public void shouldLogOutOfAccountOnTheNewsManagementPage() {
        authPage.clickOnProfileImage();
        authPage.clickOnLogout();
        authPage.assertLoginPage();
    }

    @Test
    @DisplayName("Ввод поочередно каждой категории в фильтре новостей")
    public void enterAllCategoriesInTheFilterOneByOne() {
        newsPage.openNewsFilter();
        editorialPage.verifySelectedCategories();
    }

    @Test
    @DisplayName("Проверить сортировку даты новостей")
    public void verifyNewsDateSorting() {
        String firstDateBeforeSorting = newsPage.getFirstNewsDate();
        String lastDateBeforeSorting = newsPage.getLastNewsDate();
        newsPage.clickOnSortingNews();
        String firstDateAfterSorting = newsPage.getFirstNewsDate();
        String lastDateAfterSorting = newsPage.getLastNewsDate();
        assertEquals(lastDateBeforeSorting, firstDateAfterSorting);
        assertEquals(firstDateBeforeSorting, lastDateAfterSorting);
    }

    @Test
    @DisplayName("Фильтрация всех новостей по диапазону дат")
    public void shouldFilterNewsByDateRange() {
        newsPage.openNewsFilter();
        newsPage.enterFromWhatDate(-14); // Дней назад
        newsPage.enterUntilWhatDate(14); // Дней вперед
        newsPage.clickOnApplyFilterButton();
        newsPage.checkAllNewsDateRange(-14, 14);
    }

    @Test
    @DisplayName("Поиск новости через фильтр по дате")
    public void shouldSearchForNewsViaFilterForCurrentDate() {
        editorialPage.createNews(Data.SALARY_CATEGORY, randomTitle, 30, "20:00", Data.DESCRIPTION_TEXT);
        newsPage.openNewsFilter();
        editorialPage.enterCategoryNews(Data.SALARY_CATEGORY);
        newsPage.enterFromWhatDate(30);
        newsPage.enterUntilWhatDate(30);
        newsPage.clickOnApplyFilterButton();
        editorialPage.scrollingThroughTheNewsFeed(randomTitle);
        editorialPage.checkSearchResultIsDisplayed(randomTitle);
    }

    @Test
    @DisplayName("Фильтрация всех новостей по статусу 'ACTIVE'")
    public void checkAllNewsAreActive() {
        newsPage.openNewsFilter();
        newsPage.clickOnCheckBoxNotActive();
        newsPage.clickOnApplyFilterButton();
        newsPage.checkAllNewsStatus(STATUS_ACTIVE);
    }

    @Test
    @DisplayName("Фильтрация всех новостей по статусу 'NOT ACTIVE'")
    public void checkAllNewsAreNotActive() {
        newsPage.openNewsFilter();
        newsPage.clickOnCheckBoxActive();
        newsPage.clickOnApplyFilterButton();
        newsPage.checkAllNewsStatus(STATUS_NOT_ACTIVE);
    }

    @Test
    @DisplayName("Поиск через фильтр новости со статусом NOT ACTIVE")
    public void shouldFilterNewsByStatusNotActive() {
        editorialPage.theStatusOfTheEditedNewsIsNotActive(randomTitle);
        editorialPage.scrollingThroughTheNewsFeed(randomTitle);
        newsPage.openNewsFilter();
        newsPage.clickOnCheckBoxActive();
        newsPage.clickOnApplyFilterButton();
        editorialPage.scrollingThroughTheNewsFeed(randomTitle);
        editorialPage.checkStatusOfEditedNews(randomTitle, STATUS_NOT_ACTIVE);
    }

    @Test
    @DisplayName("Поиск через фильтр новости со статусом ACTIVE")
    public void shouldFilterNewsByStatusActive() {
        editorialPage.createNews(Data.MASSAGE_CATEGORY, randomTitle, 0, "20:00", Data.DESCRIPTION_TEXT);
        newsPage.openNewsFilter();
        newsPage.clickOnCheckBoxNotActive();
        newsPage.clickOnApplyFilterButton();
        editorialPage.scrollingThroughTheNewsFeed(randomTitle);
        editorialPage.checkStatusOfEditedNews(randomTitle, STATUS_ACTIVE);
    }

    @Test
    @DisplayName("Отмена применения фильтра новостей")
    public void shouldAllowToCancelNewsFilterApplication() {
        newsPage.openNewsFilter();
        editorialPage.pressCancel();
        newsPage.verifyTextAfterCancelingFilter(Data.CONTROL_PANEL_TEXT);
    }
}
