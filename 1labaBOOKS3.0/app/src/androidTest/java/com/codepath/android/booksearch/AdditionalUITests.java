package com.codepath.android.booksearch;

import android.content.pm.ActivityInfo;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.codepath.android.booksearch.activities.BookDetailActivity;
import com.codepath.android.booksearch.activities.BookListActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AdditionalUITests {

    @Test
    public void testOrientationChange() {
        // Запускаем активность списка книг
        ActivityScenario<BookListActivity> scenario = ActivityScenario.launch(BookListActivity.class);

        // Проверяем, что приложение реагирует на поворот экрана
        scenario.onActivity(activity -> {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        });

        Espresso.onView(ViewMatchers.withId(R.id.rvBooks))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Возвращаемся в портретный режим
        scenario.onActivity(activity -> {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        });

        Espresso.onView(ViewMatchers.withId(R.id.rvBooks))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testItemClickOpensBookDetailActivity() {
        // Запускаем активность списка книг
        ActivityScenario<BookListActivity> scenario = ActivityScenario.launch(BookListActivity.class);

        // Нажимаем на первую книгу в списке
        Espresso.onView(ViewMatchers.withId(R.id.rvBooks))
                .perform(ViewActions.click());

        // Замедляем выполнение теста на 2 секунды перед проверкой отображения элементов на экране BookDetailActivity
        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Проверяем, что переход произошел на активность с деталями книги
        Espresso.onView(ViewMatchers.withId(R.id.tvTitle))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.tvAuthor))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.tvDescription))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
