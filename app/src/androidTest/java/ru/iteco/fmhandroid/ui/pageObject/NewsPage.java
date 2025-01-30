package ru.iteco.fmhandroid.ui.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.iteco.fmhandroid.ui.pageObject.EditorialPage.NEWS_LIST;

import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;

import org.hamcrest.core.IsInstanceOf;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.Helper;


public class NewsPage {
    public static final String STATUS_ACTIVE = Helper.getStringFromResource(R.string.news_control_panel_active);
    public static final String STATUS_NOT_ACTIVE = Helper.getStringFromResource(R.string.news_control_panel_not_active);
    public static final int NEWS_LIST_RECYCLER_VIEW = R.id.news_list_recycler_view;
    public static final int NEWS_ITEM_PUBLISHED_TEXT_VIEW = R.id.news_item_published_text_view;
    public static final int NEWS_ITEM_PUBLICATION_DATE_VIEW = R.id.news_item_publication_date_text_view;
    public static ViewInteraction NEWS_MANAGEMENT_BUTTON = onView(withId(R.id.edit_news_material_button));
    public static ViewInteraction SORT_NEWS_MATERIAL = onView(withId(R.id.sort_news_material_button));
    public static ViewInteraction FILTER_NEWS_MATERIAL = onView(withId(R.id.filter_news_material_button));
    public static ViewInteraction ACTIVE_CHECK_BOX = onView(withId(R.id.filter_news_active_material_check_box));
    public static ViewInteraction INACTIVE_CHECK_BOX = onView(withId(R.id.filter_news_inactive_material_check_box));
    public static ViewInteraction FROM_WHAT_DATE = onView(withId(R.id.news_item_publish_date_start_text_input_edit_text));
    public static ViewInteraction UNTIL_WHAT_DATE = onView(withId(R.id.news_item_publish_date_end_text_input_edit_text));
    public static ViewInteraction APPLY_FILTER_BUTTON = onView(withId(R.id.filter_button));

    public View decorView;

    public void showNewsManagementButton() {
        Allure.step("Показать кнопку ведущую на страницу управления новостями");
        NEWS_MANAGEMENT_BUTTON.check(matches(allOf(isDisplayed(), isClickable())));
    }

    public void openNewsManagementPage() {
        Allure.step("Отркрыть страницу управления новостями");
        NEWS_MANAGEMENT_BUTTON.check(matches(isDisplayed())).perform(click());
    }

    // Сортировка
    public int getItemCount() {
        Allure.step("Получить количество элементов в списке новостей");
        return Helper.getRecyclerViewItemCount(NEWS_LIST_RECYCLER_VIEW);
    }

    public String getDateAtPosition(int position) {
        Allure.step("Получить дату новости по позиции " + position);
        return Helper.getTextFromNews(NEWS_ITEM_PUBLICATION_DATE_VIEW, position);
    }

    private void scrollToItem(int position) {
        Allure.step("Скролить список новостей до элемента на позиции " + position);
        onView(withId(NEWS_LIST_RECYCLER_VIEW)).perform(RecyclerViewActions.scrollToPosition(position));
    }

    public void clickOnSortingNews() {
        Allure.step("Нажать кнопку сортировки новостей");
        SORT_NEWS_MATERIAL.check(matches(allOf(isDisplayed(), isClickable()))).perform(click());
    }

    public String getFirstNewsDate() {
        Allure.step("Получить дату первой новости");
        scrollToItem(0);
        return getDateAtPosition(0);
    }

    public String getLastNewsDate() {
        Allure.step("Получить дату последней новости");
        scrollToItem(getItemCount() - 1);
        return getDateAtPosition(getItemCount() - 1);
    }

    // Фильтр
    public void openNewsFilter() {
        Allure.step("Открыть фильтр новостей");
        FILTER_NEWS_MATERIAL.check(matches(isDisplayed())).perform(click());
    }

    public void enterFromWhatDate(int days) {
        Allure.step("Ввести применить фильтр от '" + days + "' числа даты");
        FROM_WHAT_DATE.check(matches(isDisplayed())).perform(replaceText(Helper.getDate(days)), closeSoftKeyboard());
    }

    public void enterUntilWhatDate(int days) {
        Allure.step("Ввести применить фильтр до '" + days + "' числа даты");
        UNTIL_WHAT_DATE.check(matches(isDisplayed())).perform(replaceText(Helper.getDate(days)), closeSoftKeyboard());
    }

    public void clickOnCheckBoxActive() {
        Allure.step("Нажать чек-бокс Active");
        ACTIVE_CHECK_BOX.check(matches(isDisplayed())).perform(click());
    }

    public void clickOnCheckBoxNotActive() {
        Allure.step("Нажать чек-бокс Not Active");
        INACTIVE_CHECK_BOX.check(matches(isDisplayed())).perform(click());
    }

    public void clickOnApplyFilterButton() {
        Allure.step("Применить фильтр");
        APPLY_FILTER_BUTTON.check(matches(isDisplayed())).perform(click());
    }

    public int getNewsItemCount() {
        Allure.step("Получить количество элементов новостей");
        return Helper.getRecyclerViewItemCount(NEWS_LIST_RECYCLER_VIEW);
    }

    public void scrollToNewsItem(int position) {
        Allure.step("Прокручиваем к элементу новостей с позицией: " + position);
        NEWS_LIST.perform(scrollToPosition(position))
                .perform(actionOnItemAtPosition(position, scrollTo()))
                .check(matches(isDisplayed()));
    }

    public void checkAllNewsStatus(String expectedStatus) {
        Allure.step("Проверить, что все элементы в списке новостей имеют статус: " + expectedStatus);
        for (int i = 0; i < getNewsItemCount(); i++) {
            scrollToNewsItem(i);
            String actualStatus = Helper.getTextFromNews(NEWS_ITEM_PUBLISHED_TEXT_VIEW, i);
            assertEquals("Ожидается статус '" + expectedStatus + "' для элемента " + i,
                    expectedStatus, actualStatus);
        }
    }

    public void checkAllNewsDateRange(int fromWhatDay, int untilWhatDay) {
        String startDateStr = Helper.getDate(fromWhatDay);
        String endDateStr = Helper.getDate(untilWhatDay);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);
        Allure.step("Проверить, что все элементы в списке новостей имеют дату публикации в диапазоне от: " + startDate + " до: " + endDate);
        for (int i = 0; i < getNewsItemCount(); i++) {
            scrollToNewsItem(i);
            String actualDateStr = Helper.getTextFromNews(NEWS_ITEM_PUBLICATION_DATE_VIEW, i);
            LocalDate actualDate = LocalDate.parse(actualDateStr, formatter);
            assertTrue("Ожидается, что дата для элемента " + i + " будет в диапазоне от " + startDate + " до " + endDate,
                    (actualDate.isEqual(startDate) || actualDate.isEqual(endDate) ||
                            (actualDate.isAfter(startDate) && actualDate.isBefore(endDate))));
        }
    }

    public void verifyTextAfterCancelingFilter(String text) {
        Allure.step("Отображение текста '" + text + "' после отмены применения фильтра");
        ViewInteraction textView = onView(allOf(withText(text),
                withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                isDisplayed()));
        textView.check(matches(withText(text)));
    }
}
