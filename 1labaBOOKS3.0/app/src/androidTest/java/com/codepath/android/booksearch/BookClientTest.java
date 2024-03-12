package com.codepath.android.booksearch;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.TestObserver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.codepath.android.booksearch.net.BookClient;
import com.codepath.android.booksearch.net.BookResponse;
import com.codepath.android.booksearch.net.OpenLibraryApi;

public class BookClientTest {

    private BookClient bookClient;
    private OpenLibraryApi mockApi;

    @Before
    public void setup() {
        // Создаем mock объект для OpenLibraryApi
        mockApi = mock(OpenLibraryApi.class);
        bookClient = new BookClient(mockApi);
    }

    @Test
    public void testSearchBooks() throws InterruptedException {
        // Готовим данные для тестирования
        BookResponse response = new BookResponse();

        // Создаем mock объект для Observable<BookResponse>
        Observable<BookResponse> observable = Observable.just(response);

        // Когда метод searchBooks вызывается с определенным запросом,
        // возвращаем наш Observable с mock данными
        when(mockApi.searchBooks("test")).thenReturn(observable);

        // Вызываем метод searchBooks с тестовым запросом
        TestObserver<BookResponse> testObserver = bookClient.searchBooks("test").test();

        // Ожидаем, что мы получим результат
        testObserver.await().assertComplete();


        // Проверяем, что запрос был успешным
        testObserver.assertComplete();
        // Проверяем, что мы получили правильный результат
        testObserver.assertValue(response);
    }
}


