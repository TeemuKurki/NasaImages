package com.teemukurki.myapplication.network.service;

import com.teemukurki.myapplication.model.Page;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by teemu on 14.8.2017.
 */

public class NasaApiService {

    private static final String API_BASE_URL = "https://api.nasa.gov/";
    private OkHttpClient httpClient;
    private NasaApiServiceInterface service;


    public NasaApiService() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(1, TimeUnit.MINUTES).build();

        service = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(httpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(NasaApiServiceInterface.class);
    }
    public Call<Page> search(String date, String apikey, int page){
        return service.search(date, apikey, page);
    }

}
