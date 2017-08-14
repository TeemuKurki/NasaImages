package com.teemukurki.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.teemukurki.myapplication.ImageApp;
import com.teemukurki.myapplication.R;
import com.teemukurki.myapplication.model.Photos;
import com.teemukurki.myapplication.model.Page;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageApp.getInstance().getApiService().search("2016-8-13","NNKOjkoul8n1CH18TWA9gwngW1s1SmjESPjNoUFo", 0).enqueue(new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {
               List<Photos> results = response.body().getPhotos();
                for(Photos result : results){
                    Log.d("MainActivity", "Id: "+result.getId() + " | Url: "+result.getImg_src() + " | Camera: "+result.getCamera().getFull_name() + " | Sol: "+result.getSol());
                }
            }

            @Override
            public void onFailure(Call<Page> call, Throwable t) {
                Log.d("MainActivity", "onFailure: " + t.getMessage());
            }
        });
    }
}
