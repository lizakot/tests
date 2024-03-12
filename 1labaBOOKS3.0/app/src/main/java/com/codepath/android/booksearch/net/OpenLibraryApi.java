package com.codepath.android.booksearch.net;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenLibraryApi {
    @GET("search.json")
    Observable<BookResponse> searchBooks(@Query("q") String query);
}
