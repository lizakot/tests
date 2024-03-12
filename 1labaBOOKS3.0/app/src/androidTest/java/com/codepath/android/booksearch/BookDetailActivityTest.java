package com.codepath.android.booksearch;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import com.codepath.android.booksearch.activities.BookDetailActivity;

import org.junit.Before;
import org.junit.Test;

public class BookDetailActivityTest {

    @Before
    public void setUp() {
        // Запускаем активность
        ActivityScenario.launch(BookDetailActivity.class);
    }

    @Test
    public void testBookDisplayedCorrectly() {
        // Проверяем, что данные книги корректно отображаются
        Espresso.onView(ViewMatchers.withId(R.id.tvTitle))
                .check(ViewAssertions.matches(ViewMatchers.withText("Test Book")));
        Espresso.onView(ViewMatchers.withId(R.id.tvAuthor))
                .check(ViewAssertions.matches(ViewMatchers.withText("Author 1, Author 2")));
        Espresso.onView(ViewMatchers.withId(R.id.tvDescription))
                .check(ViewAssertions.matches(ViewMatchers.withText("Test description")));
    }

    @Test
    public void testBookCoverDisplayed() {
        ActivityScenario.launch(BookDetailActivity.class);
        // Проверяем, что обложка книги загружается
        Espresso.onView(ViewMatchers.withId(R.id.ivBookCover))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}





