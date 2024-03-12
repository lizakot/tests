package com.codepath.android.booksearch.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.codepath.android.booksearch.net.BookClient;

public class BookListViewModelFactory implements ViewModelProvider.Factory {
    private final BookClient bookClient;

    public BookListViewModelFactory(BookClient bookClient) {
        this.bookClient = bookClient;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == BookListViewModel.class) {
            return (T) new BookListViewModel(bookClient);
        }
        return null;
    }
}

