package com.codepath.android.booksearch.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codepath.android.booksearch.models.Book;
import com.codepath.android.booksearch.net.BookClient;
import com.codepath.android.booksearch.net.BookResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BookListViewModel extends ViewModel {
    private final MutableLiveData<List<Book>> booksLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    private final BookClient bookClient;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BookListViewModel(BookClient bookClient) {
        this.bookClient = bookClient;
    }

    public LiveData<List<Book>> getBooksLiveData() {
        return booksLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }


    public void fetchBooks(String query) {
        loadingLiveData.setValue(true);

        compositeDisposable.add(
                bookClient.searchBooks(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                bookResponse -> {
                                    loadingLiveData.setValue(false);
                                    List<Book> books = bookResponse.getBooks();
                                    booksLiveData.setValue(books);
                                },
                                throwable -> {
                                    loadingLiveData.setValue(false);
                                    // Handle the error
                                }
                        )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}