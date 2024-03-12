package com.codepath.android.booksearch.models;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Book {

    public Book(String title, String[] authors, String openLibraryId) {
        this.title = title;
        this.authors = authors;
        this.openLibraryId = openLibraryId;
    }
    @SerializedName("cover_edition_key")
    private String openLibraryId;
    @SerializedName("author_name")
    private String[] authors;
    private String title;

    @SerializedName("first_sentence")
    private List<String> description;

    public Book(){}     //required empty constructor by Parceler library

    public String getOpenLibraryId() {
        return openLibraryId;
    }

    public List<String> getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String[] getAuthors() {
        return authors;
    }

    public String getCoverUrl() {
        return "https://covers.openlibrary.org/b/olid/" + openLibraryId + "-L.jpg?default=false";
    }

    // Добавляем метод для установки значения openLibraryId
    public void setOpenLibraryId(String openLibraryId) {
        this.openLibraryId = openLibraryId;
    }

    public static Book fromJson(JSONObject jsonObject) {
        Book book = new Book();
        try {
            // Десериализация JSON в поля объекта
            // Проверка доступности "cover_edition_key"
            if (jsonObject.has("cover_edition_key")) {
                book.openLibraryId = jsonObject.getString("cover_edition_key");
            }
            book.title = jsonObject.has("title_suggest") ? jsonObject.getString("title_suggest") : "";
            book.authors = getAuthors(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return book;
    }

    // Возвращает массив авторов, если их несколько
    private static String[] getAuthors(final JSONObject jsonObject) {
        try {
            final JSONArray authorsArray = jsonObject.getJSONArray("author_name");
            int numAuthors = authorsArray.length();
            final String[] authorStrings = new String[numAuthors];
            for (int i = 0; i < numAuthors; ++i) {
                authorStrings[i] = authorsArray.getString(i);
            }
            return authorStrings;
        } catch (JSONException e) {
            return new String[0]; // Возвращать пустой массив, если информация об авторах отсутствует
        }
    }

    // Декодирование массива результатов JSON в объекты модели данных
    public static ArrayList<Book> fromJson(JSONArray jsonArray) {
        ArrayList<Book> books = new ArrayList<>(jsonArray.length());
        // Обработка каждого результата в массиве JSON, декодирование и преобразование в объект бизнес-модели
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject bookJson;
            try {
                bookJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Book book = Book.fromJson(bookJson);
            if (book != null) {
                books.add(book);
            }
        }
        return books;
    }
}
