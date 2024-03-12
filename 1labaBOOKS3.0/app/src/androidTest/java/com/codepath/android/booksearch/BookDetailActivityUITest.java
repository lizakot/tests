package com.codepath.android.booksearch;


import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import com.codepath.android.booksearch.activities.BookDetailActivity;
import com.codepath.android.booksearch.activities.BookListActivity;

import org.junit.Before;
import org.junit.Test;

public class BookDetailActivityUITest {

    @Before
    public void setUp() {
        // Запускаем активность списка книг
        ActivityScenario.launch(BookListActivity.class);

        try {
            Thread.sleep(120000); // 2 минуты
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testBookDetailActivityOpens() {
        // Нажимаем на первую книгу в списке
        Espresso.onView(ViewMatchers.withId(R.id.rvBooks))
                .perform(ViewActions.click());

                // Проверяем, что переход произошел на активность с деталями книги
        Espresso.onView(ViewMatchers.withId(R.id.tvTitle))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.tvAuthor))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.tvDescription))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}

