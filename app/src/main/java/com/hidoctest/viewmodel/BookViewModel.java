package com.hidoctest.viewmodel;

import android.util.Log;

import com.hidoctest.model.Book;
import com.hidoctest.model.BookList;
import com.hidoctest.retrofit.BookService;
import com.hidoctest.retrofit.RetrofitUtil;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookViewModel extends ViewModel {

    String TAG = this.getClass().getName();
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    MutableLiveData<Boolean> isError = new MutableLiveData<>();
    MutableLiveData<BookList> bookMutableLiveData = new MutableLiveData<>();
    Call<BookList> apiCalUser;
    private BookService bookService;
    public LiveData<Boolean> error() {
        Log.d(TAG, "return isError  : " + isError);
        return isError;
    }

    public LiveData<BookList> bookDataReceived() {
        Log.d(TAG, "return onHomeDataReceive  : " + bookMutableLiveData);
        return bookMutableLiveData;
    }

    public LiveData<Boolean> isLoading() {
        Log.d(TAG, "return isLoading  : " + isLoading);
        return isLoading;
    }

    public void getBooksData(){
        bookService = RetrofitUtil.getInstance().getRetrofit().create(BookService.class);
        isLoading.setValue(true);
        apiCalUser = bookService.getInitialBooks();
        Log.d(TAG, "Start getHome call 2 : ");

        apiCalUser.enqueue(new Callback<BookList>() {
            @Override
            public void onResponse(Call<BookList> call, Response<BookList> response) {
                if (response.body() != null)
                    Log.d(TAG, " getHomeByID call onResponse 1 : " + response.body().toString());


                if (response.code() == 200 && response.body() != null) {
                    Log.d(TAG, " getHome call onResponse 2 : " + response.body().toString());

                    isError.setValue(false);
                    bookMutableLiveData.setValue(response.body());

                } else {
                    isError.setValue(true);
                    bookMutableLiveData.setValue(null);
                }
                isLoading.setValue(false);
                apiCalUser = null;

            }

            @Override
            public void onFailure(Call<BookList> call, Throwable t) {
                Log.d(TAG, " getHome call onFailure : " + t.toString());
                isError.setValue(true);
                isLoading.setValue(false);
                apiCalUser = null;
            }
        });


    }

    public void getSearchBooksData(String bookname){
        bookService = RetrofitUtil.getInstance().getRetrofit().create(BookService.class);
        isLoading.setValue(true);
        apiCalUser = bookService.getSearchBooks(bookname);
        Log.d(TAG, "Start getHome call 2 : ");

        apiCalUser.enqueue(new Callback<BookList>() {
            @Override
            public void onResponse(Call<BookList> call, Response<BookList> response) {
                if (response.body() != null)
                    Log.d(TAG, " getHomeByID call onResponse 1 : " + response.body().toString());


                if (response.code() == 200 && response.body() != null) {
                    Log.d(TAG, " getHome call onResponse 2 : " + response.body().toString());

                    isError.setValue(false);
                    bookMutableLiveData.setValue(response.body());

                } else {
                    isError.setValue(true);
                    bookMutableLiveData.setValue(null);
                }
                isLoading.setValue(false);
                apiCalUser = null;

            }

            @Override
            public void onFailure(Call<BookList> call, Throwable t) {
                Log.d(TAG, " getHome call onFailure : " + t.toString());
                isError.setValue(true);
                isLoading.setValue(false);
                apiCalUser = null;
            }
        });


    }
}
