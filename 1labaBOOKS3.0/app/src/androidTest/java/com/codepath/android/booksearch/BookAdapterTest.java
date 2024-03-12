package com.codepath.android.booksearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;

import com.codepath.android.booksearch.adapters.BookAdapter;
import com.codepath.android.booksearch.models.Book;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BookAdapterTest {

    @Test
    public void testBookAdapterCreation() {
        // Создаем список книг
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());

        // Создаем адаптер и RecyclerView для тестирования
        Context context = ApplicationProvider.getApplicationContext();
        BookAdapter adapter = new BookAdapter(context, books);
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setAdapter(adapter);

        // Проверяем, что адаптер создан без ошибок и количество элементов в нем соответствует размеру списка книг
        assertNotNull(adapter);
        assertNotNull(recyclerView.getAdapter());
        assertEquals(3, recyclerView.getAdapter().getItemCount());
    }

}
