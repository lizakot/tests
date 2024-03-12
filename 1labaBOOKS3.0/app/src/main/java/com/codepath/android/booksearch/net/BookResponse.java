package com.codepath.android.booksearch.net;
import com.codepath.android.booksearch.models.Book;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class BookResponse {
    @SerializedName("docs")
    private ArrayList<Book> books;

    public ArrayList<Book> getBooks() {
        return books;
    }
}