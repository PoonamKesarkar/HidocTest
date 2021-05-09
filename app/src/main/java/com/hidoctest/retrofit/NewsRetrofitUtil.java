package com.hidoctest.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.hidoctest.util.Constant.BookBaseUrl;
import static com.hidoctest.util.Constant.NewsBaseUrl;

public class NewsRetrofitUtil {
    private static Retrofit retrofit = null;
    private static NewsRetrofitUtil retrofitUtilInstance;

    private NewsRetrofitUtil() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(NewsBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static synchronized NewsRetrofitUtil getInstance() {
        if (retrofitUtilInstance == null)
            retrofitUtilInstance = new NewsRetrofitUtil();
        return retrofitUtilInstance;
    }
    public Retrofit getRetrofit(){
        return retrofit;
    }
}
