package com.example.project_text.data.okhttp;

import com.example.project_text.BuildConfig;
import com.example.project_text.utile.AddHeaderInterceptor;
import com.example.project_text.utile.SaveCookieInterceptor;
import com.example.project_text.view.AppConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataService {
    private static final long DEFAULT_TIMEOUT = 20000;


    private static volatile ApiService mApiService;

    public static ApiService getService() {

        if (mApiService == null) {
            synchronized (DataService.class) {
                if (mApiService == null) {
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

                    if (BuildConfig.DEBUG) {
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                    } else {
                        logging.setLevel(HttpLoggingInterceptor.Level.NONE);
                    }


                    OkHttpClient httpClient = new OkHttpClient.Builder()
                            .addInterceptor(logging)
                            .addInterceptor(new SaveCookieInterceptor())
                            .addInterceptor(new AddHeaderInterceptor())
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .build();

                    Retrofit retrofit = new Retrofit.Builder()
                            .client(httpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl(AppConstant.BASEURL)
                            .build();

                    mApiService = retrofit.create(ApiService.class);
                }
            }
        }
        return mApiService;
    }
}
