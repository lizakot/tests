package com.codepath.android.booksearch.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.view.MenuItemCompat;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.codepath.android.booksearch.GlideApp;
import com.codepath.android.booksearch.R;
import com.codepath.android.booksearch.models.Book;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;



import org.parceler.Parcels;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class BookDetailActivity extends AppCompatActivity {
    private static final String KEY_BOOK = "bookInquired";

    private ImageView ivBookCover;
    private TextView tvTitle;
    private TextView tvAuthor;

    private TextView tvDescription;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        // Fetch views
        ivBookCover = findViewById(R.id.ivBookCover);
        tvTitle = findViewById(R.id.tvTitle);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvDescription = findViewById(R.id.tvDescription);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Extract book object from intent extras
        Intent i = getIntent();
        Book bookReceived = Parcels.unwrap(i.getParcelableExtra(KEY_BOOK));

        // Use book object to populate data into views
        tvTitle.setText(bookReceived.getTitle());

        // Обновите отображение авторов
        String[] authors = bookReceived.getAuthors();
        if (authors != null && authors.length > 0) {
            tvAuthor.setText(TextUtils.join(", ", authors));
        } else {
            tvAuthor.setText(""); // Очистите поле, если нет информации об авторах
        }

        List<String> description = bookReceived.getDescription();
        if (description != null && !description.isEmpty()) {
            String descriptionText = TextUtils.join("\n", description);
            tvDescription.setText(descriptionText);
        } else {
            tvDescription.setText("No description");
        }

        Objects.requireNonNull(getSupportActionBar()).setTitle(bookReceived.getTitle());

        // Load the book cover
        Picasso
                .get()
                .load(bookReceived.getCoverUrl())
                .error(R.drawable.ic_nocover)
                .into(ivBookCover, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        // Handle error if needed
                    }
                });
    }
}
