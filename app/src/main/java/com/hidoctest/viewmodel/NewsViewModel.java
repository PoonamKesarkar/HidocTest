package com.hidoctest.viewmodel;

import android.util.Log;
import android.view.View;

import com.hidoctest.model.BookList;
import com.hidoctest.model.News;
import com.hidoctest.retrofit.BookService;
import com.hidoctest.retrofit.NewsRetrofitUtil;
import com.hidoctest.retrofit.NewsService;
import com.hidoctest.retrofit.RetrofitUtil;
import com.hidoctest.util.NetworkManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import butterknife.internal.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hidoctest.util.Constant.API_KEY;

public class NewsViewModel extends ViewModel {

    String TAG = this.getClass().getName();
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    MutableLiveData<Boolean> isError = new MutableLiveData<>();
    MutableLiveData<News> newsMutableLiveData = new MutableLiveData<>();
    Call<News> apiCalUser;
    private NewsService newsService;
    public LiveData<Boolean> error() {
        Log.d(TAG, "return isError  : " + isError);
        return isError;
    }

    public LiveData<News> newsDataReceived() {
        Log.d(TAG, "return onHomeDataReceive  : " + newsMutableLiveData);
        return newsMutableLiveData;
    }

    public LiveData<Boolean> isLoading() {
        Log.d(TAG, "return isLoading  : " + isLoading);
        return isLoading;
    }

    public void getNewsData(){
        newsService = NewsRetrofitUtil.getInstance().getRetrofit().create(NewsService.class);
        isLoading.setValue(true);
        String country = NetworkManager.getCountry();
        apiCalUser = newsService.getNews(country, API_KEY);
        Log.d(TAG, "Start getHome call 2 : ");
        apiCalUser.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.code() == 200 && response.body() != null) {
                    Log.d(TAG, " getHome call onResponse 2 : " + response.body().toString());

                    isError.setValue(false);
                    newsMutableLiveData.setValue(response.body());

                } else {
                    isError.setValue(true);
                    newsMutableLiveData.setValue(null);
                }
                isLoading.setValue(false);
                apiCalUser = null;
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.d(TAG, " getHome call onFailure : " + t.toString());
                isError.setValue(true);
                isLoading.setValue(false);
                apiCalUser = null;
            }
        });
    }

    public void getRecentNewsData(String keyword){
        newsService = NewsRetrofitUtil.getInstance().getRetrofit().create(NewsService.class);
        isLoading.setValue(true);
        String country = NetworkManager.getCountry();
        apiCalUser = newsService.getNewsSearch(keyword, "en", "publishedAt", API_KEY);
        Log.d(TAG, "Start getHome call 2 : ");
        apiCalUser.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.code() == 200 && response.body() != null) {
                    Log.d(TAG, " getHome call onResponse 2 : " + response.body().toString());

                    isError.setValue(false);
                    newsMutableLiveData.setValue(response.body());

                } else {
                    isError.setValue(true);
                    newsMutableLiveData.setValue(null);
                }
                isLoading.setValue(false);
                apiCalUser = null;
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.d(TAG, " getHome call onFailure : " + t.toString());
                isError.setValue(true);
                isLoading.setValue(false);
                apiCalUser = null;
            }
        });
    }
}
