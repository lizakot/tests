package com.codepath.android.booksearch;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.codepath.android.booksearch.R;
import com.codepath.android.booksearch.activities.BookListActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.notNullValue;



@RunWith(AndroidJUnit4.class)
@LargeTest
public class BookListActivityTest {

    @Before
    public void setUp() throws Exception {
        ActivityScenario<BookListActivity> scenario = ActivityScenario.launch(BookListActivity.class);
        // Ожидаем завершения запуска активности
        scenario.onActivity(activity -> {
            // Место для дополнительной инициализации, если необходимо
        });
    }

    @Test
    public void testBookAdapterCreation() {
        // Проверяем, что RecyclerView, используемый для отображения списка книг, существует
        RecyclerView recyclerView = getActivityInstance().findViewById(R.id.rvBooks);
        // Проверяем, что адаптер RecyclerView, BookAdapter, не равен null
        assert recyclerView != null;
        assert recyclerView.getAdapter() != null;
        assert recyclerView.getAdapter().getItemCount() == 0; // Проверяем, что список книг пуст
    }

    // Метод для получения экземпляра активности
    private BookListActivity getActivityInstance() {
        final BookListActivity[] activity = new BookListActivity[1];
        // Запускаем сценарий для активности и получаем ссылку на нее
        ActivityScenario<BookListActivity> scenario = ActivityScenario.launch(BookListActivity.class);
        scenario.onActivity(new ActivityScenario.ActivityAction<BookListActivity>() {
            @Override
            public void perform(BookListActivity bookListActivity) {
                activity[0] = bookListActivity;
            }
        });
        return activity[0];
    }
}
