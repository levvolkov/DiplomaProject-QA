package ru.iteco.fmhandroid.ui.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

import android.content.Intent;

import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class AboutPage {
    public static ViewInteraction BACK_BUTTON_FROM_ABOUT_PAGE = onView(withId(R.id.about_back_image_button));
    public static ViewInteraction APPLICATION_NAME = onView(withId(R.id.trademark_image_view));
    public static ViewInteraction VERSION_TEXT = onView(withId(R.id.about_version_title_text_view));
    public static ViewInteraction VERSION_NUMBER = onView(withId(R.id.about_version_value_text_view));
    public static ViewInteraction LABEL_PRIVACY_POLICY = onView(withId(R.id.about_privacy_policy_label_text_view));
    public static ViewInteraction LABEL_TERMS_OF_USE = onView(withId(R.id.about_terms_of_use_label_text_view));
    public static ViewInteraction LABEL_COMPANY_TEXT = onView(withId(R.id.about_company_info_label_text_view));
    public static ViewInteraction PRIVACY_POLICY_BUTTON_LINK = onView(withId(R.id.about_privacy_policy_value_text_view));
    public static ViewInteraction TERMS_OF_USE_BUTTON_LINK = onView(withId(R.id.about_terms_of_use_value_text_view));

    public void backButtonVisibility() {
        Allure.step("Отображение кнопки назад на странице About");
        BACK_BUTTON_FROM_ABOUT_PAGE.check(matches(isDisplayed()));
    }

    public void assertDisplayApplicationName() {
        Allure.step("Отображение лейбла приложения");
        APPLICATION_NAME.check(matches(isDisplayed()));
    }

    public void appVersionTextApproval() {
        Allure.step("Отображение текста версии приложения");
        VERSION_TEXT.check(matches(isDisplayed()));
    }

    public void appVersionNumberApproval() {
        Allure.step("Отображение номера версии приложения");
        VERSION_NUMBER.check(matches(isDisplayed()));
    }

    public void assertDisplayOfPrivacyPolicyLabel() {
        Allure.step("Отображение метки политики конфиденциальности");
        LABEL_PRIVACY_POLICY.check(matches(isDisplayed()));
    }

    public void assertDisplayOfTermsOfUseLabel() {
        Allure.step("Отображение метки условия эксплуатации");
        LABEL_TERMS_OF_USE.check(matches(isDisplayed()));
    }

    public void assertDisplayTheCompanyNameLabel() {
        Allure.step("Отображение текста компании ");
        LABEL_COMPANY_TEXT.check(matches(isDisplayed()));
    }

    public void clickOnBack() {
        Allure.step("Нажать  кнопку назад на странице About");
        BACK_BUTTON_FROM_ABOUT_PAGE.check(matches(isDisplayed())).perform(click());
    }

    public void clickOnPrivacyPolicy() {
        Allure.step("Клик на ссылку-кнопку Политика конфиденциальности");
        PRIVACY_POLICY_BUTTON_LINK.check(matches(isDisplayed())).perform(click());
    }

    public void clickOnTermsOfUse() {
        Allure.step("Клик на ссылку-кнопку Условия эксплуатации");
        TERMS_OF_USE_BUTTON_LINK.check(matches(isDisplayed())).perform(click());
    }

    public void verifyIntent(String expectedUrl) {
        Allure.step("Проверка, что инициирован Intent с действием VIEW и правильным URL");
        intended(allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(expectedUrl)
        ));
    }
}
