package com.hidoctest.retrofit;

import com.hidoctest.model.Book;
import com.hidoctest.model.BookList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookService {
    @GET("volumes?q=2021&orderBy=newest&langRestrict=en&maxResults=10")
     Call<BookList> getInitialBooks();

    @GET("volumes?orderBy=newest&langRestrict=en&maxResults=10")
    Call<BookList> getSearchBooks(@Query("q") String searchStr);
}
