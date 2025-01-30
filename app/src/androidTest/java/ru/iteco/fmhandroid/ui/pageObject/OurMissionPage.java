package ru.iteco.fmhandroid.ui.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static ru.iteco.fmhandroid.ui.data.Helper.childAtPosition;

import android.view.View;

import androidx.test.espresso.ViewInteraction;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.Data;


public class OurMissionPage {
    public static ViewInteraction PAGE_TITLE_QUOTE = onView(Matchers.allOf(withId(R.id.our_mission_title_text_view), withText("Love is all")));
    public static ViewInteraction QUOTES_CARD = onView(withId(R.id.our_mission_item_list_recycler_view));
    private static final int ITEM_TITLE_TEXT_VIEW = R.id.our_mission_item_title_text_view;
    private static final int MATERIAL_CARD_VIEW = R.id.our_mission_item_material_card_view;
    public final int OUR_MISSION_ITEM_LIST_RECYCLER = R.id.our_mission_item_list_recycler_view;
    public final int OUR_MISSION_DESCRIPTION = R.id.our_mission_item_description_text_view;
    public final String CONSTRAINT_LAYOUT_CLASS_NAME = "androidx.constraintlayout.widget.ConstraintLayout";


    public void visibilityTitleLoveIsAll() {
        Allure.step("Отображение заголовка на странице");
        PAGE_TITLE_QUOTE.check(matches(isDisplayed()));
    }

    public void assertDisplayOfAllQuoteTitles() {
        String[] quoteTitles = Data.getQuoteTitles();
        for (int i = 0; i < quoteTitles.length; i++) {
            Allure.step("Прокрутка до позиции " + (i));
            scrollToQuotePosition(i);
            Matcher<View> titleMatcher = allOf(withId(ITEM_TITLE_TEXT_VIEW), withText(quoteTitles[i]),
                    withParent(withParent(withId(MATERIAL_CARD_VIEW))), isDisplayed());
            Allure.step("Проверка отображения текста цитаты с позицией " + (i));
            ViewInteraction textView = onView(titleMatcher);
            textView.check(matches(isDisplayed()));
            textView.check(matches(withText(quoteTitles[i])));
        }
    }

    public void scrollToQuotePosition(int position) {
        Allure.step("Прокрутить до позиции " + position);
        QUOTES_CARD.check(matches(isDisplayed())).perform(actionOnItemAtPosition(position, scrollTo()));
    }

    public void expandQuoteByPosition(int position) {
        Allure.step("Нажимаем на элемент списка по позиции: " + position);
        onView(allOf(withId(OUR_MISSION_ITEM_LIST_RECYCLER),
                childAtPosition(withClassName(is(CONSTRAINT_LAYOUT_CLASS_NAME)), 0)))
                .perform(actionOnItemAtPosition(position, click()));
    }

    public void checkQuoteDescription(String expectedText) {
        Allure.step("Проверяем текст описания цитаты: " + expectedText);
        onView(allOf(withId(OUR_MISSION_DESCRIPTION), withText(expectedText),
                withParent(withParent(withId(MATERIAL_CARD_VIEW))), isDisplayed()))
                .check(matches(isCompletelyDisplayed()));
    }
}
