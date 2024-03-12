package com.codepath.android.booksearch;

import com.codepath.android.booksearch.models.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BookTest {

    @Test
    public void testFromJsonSingleBook() throws JSONException {
        // Создаем JSON объект, представляющий одну книгу
        JSONObject jsonBook = new JSONObject();
        jsonBook.put("cover_edition_key", "OL123456M");
        jsonBook.put("title_suggest", "Test Book");
        JSONArray authorsArray = new JSONArray();
        authorsArray.put("Author 1");
        authorsArray.put("Author 2");
        jsonBook.put("author_name", authorsArray);

        // Парсим JSON и создаем объект Book
        Book book = Book.fromJson(jsonBook);

        // Проверяем, что объект Book создан и содержит правильные данные
        assertNotNull(book);
        assertEquals("OL123456M", book.getOpenLibraryId());
        assertEquals("Test Book", book.getTitle());
        assertEquals(2, book.getAuthors().length);
        assertEquals("Author 1", book.getAuthors()[0]);
        assertEquals("Author 2", book.getAuthors()[1]);
    }

    @Test
    public void testFromJsonBookList() throws JSONException {
        // Создаем JSON массив, представляющий список книг
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonBook1 = new JSONObject();
        jsonBook1.put("cover_edition_key", "OL123456M");
        jsonBook1.put("title_suggest", "Book 1");
        JSONArray authorsArray1 = new JSONArray();
        authorsArray1.put("Author 1");
        jsonBook1.put("author_name", authorsArray1);
        JSONObject jsonBook2 = new JSONObject();
        jsonBook2.put("cover_edition_key", "OL789012M");
        jsonBook2.put("title_suggest", "Book 2");
        JSONArray authorsArray2 = new JSONArray();
        authorsArray2.put("Author 2");
        jsonBook2.put("author_name", authorsArray2);
        jsonArray.put(jsonBook1);
        jsonArray.put(jsonBook2);

        // Парсим JSON массив и создаем список объектов Book
        ArrayList<Book> books = Book.fromJson(jsonArray);

        // Проверяем, что список книг создан и содержит правильное количество элементов
        assertNotNull(books);
        assertEquals(2, books.size());

        // Проверяем правильность данных первой книги в списке
        Book book1 = books.get(0);
        assertEquals("OL123456M", book1.getOpenLibraryId());
        assertEquals("Book 1", book1.getTitle());
        assertEquals(1, book1.getAuthors().length);
        assertEquals("Author 1", book1.getAuthors()[0]);

        // Проверяем правильность данных второй книги в списке
        Book book2 = books.get(1);
        assertEquals("OL789012M", book2.getOpenLibraryId());
        assertEquals("Book 2", book2.getTitle());
        assertEquals(1, book2.getAuthors().length);
        assertEquals("Author 2", book2.getAuthors()[0]);
    }

    @Test
    public void testGetCoverUrl() {
        // Создаем объект Book
        Book book = new Book();
        book.setOpenLibraryId("OL123456M");

        // Проверяем, что метод getCoverUrl возвращает правильный URL обложки
        assertEquals("https://covers.openlibrary.org/b/olid/OL123456M-L.jpg?default=false", book.getCoverUrl());
    }
}

