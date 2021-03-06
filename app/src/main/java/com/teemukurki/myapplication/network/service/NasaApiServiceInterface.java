package com.teemukurki.myapplication.network.service;

import com.teemukurki.myapplication.model.Page;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by teemu on 14.8.2017.
 */

public interface NasaApiServiceInterface {
    @GET("mars-photos/api/v1/rovers/{rover}/photos")
    Call<Page> search(@Path("rover")String rover,@Query("earth_date") String date, @Query("api_key")String apikey, @Query("page")int page);
}
