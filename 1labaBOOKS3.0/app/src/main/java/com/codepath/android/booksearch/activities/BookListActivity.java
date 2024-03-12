package com.codepath.android.booksearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.android.booksearch.R;
import com.codepath.android.booksearch.ViewModel.BookListViewModel;
import com.codepath.android.booksearch.ViewModel.BookListViewModelFactory;
import com.codepath.android.booksearch.adapters.BookAdapter;
import com.codepath.android.booksearch.models.Book;
import com.codepath.android.booksearch.net.BookClient;
import com.codepath.android.booksearch.net.OpenLibraryApi;
import com.codepath.android.booksearch.net.BookResponse;

import org.parceler.Parcels;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

    public class BookListActivity extends AppCompatActivity {
    private static final String KEY_BOOK = "bookInquired";

    ProgressBar progressBar;
    RecyclerView rvBooks;
    BookAdapter bookAdapter;
    ArrayList<Book> abooks;
    BookClient bookClient;
    String searchText;
    Disposable disposable;

    BookListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        abooks = new ArrayList<>();
        rvBooks = findViewById(R.id.rvBooks);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState != null) {
            searchText = savedInstanceState.getString(QUERY_KEY);
        } else {
            searchText = DEFAULT_QUERY;
        }

        bookClient = new BookClient();

        bookAdapter = new BookAdapter(this, abooks);
        bookAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Toast.makeText(
                        BookListActivity.this,
                        "Book number " + position + " is loading",
                        Toast.LENGTH_SHORT).show();

                // Get Book at the given position
                Book book = abooks.get(position);

                // Create Intent to start BookDetailActivity
                Intent i = new Intent(BookListActivity.this, BookDetailActivity.class);
                i.putExtra(KEY_BOOK, Parcels.wrap(book));
                startActivity(i);
            }
        });

        rvBooks.setAdapter(bookAdapter);
        rvBooks.setLayoutManager(new LinearLayoutManager(this));

        ViewModelProvider.Factory factory = new BookListViewModelFactory(bookClient);
        viewModel = new ViewModelProvider(this, factory).get(BookListViewModel.class);





        viewModel.getBooksLiveData().observe(this, books -> {
            abooks.clear();
            abooks.addAll(books);
            bookAdapter.notifyDataSetChanged();
        });

        viewModel.getLoadingLiveData().observe(this, isLoading -> {
            if (isLoading) {
                progressBar.setVisibility(ProgressBar.VISIBLE);
            } else {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchBooks(searchText);
    }

    private void fetchBooks(String query) {
        viewModel.fetchBooks(query);
    }

    @Override
    // Purpose: Inflates and initializes the SearchView --> onQueryTextSubmit(): queries for a specific book through here!
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_book_list, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast toast = Toast.makeText(getApplicationContext(), "Searching for books", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.show();

                fetchBooks(query);
                searchText = query;
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(QUERY_KEY, searchText);
    }

    private void cancelSubscription() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    static String DEFAULT_QUERY = "da";
    static String QUERY_KEY = "query";
}
