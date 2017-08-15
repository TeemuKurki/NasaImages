package com.teemukurki.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.teemukurki.myapplication.BuildConfig;
import com.teemukurki.myapplication.ImageApp;
import com.teemukurki.myapplication.R;
import com.teemukurki.myapplication.model.Items;
import com.teemukurki.myapplication.model.Photos;
import com.teemukurki.myapplication.model.Page;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    int currentPage = 0;

    private Realm realm;
    private Items items;

    private String earth_date = "2017-2-22";
    private String rover = "curiosity";

    private Button tyhjenna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tyhjenna = (Button) findViewById(R.id.clear_db);
        tyhjenna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(items != null && realm != null){
                    realm.beginTransaction();
                    items.setItems(new RealmList<Photos>());
                    items.setLastUpdate(0);
                    realm.commitTransaction();
                }
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();

        realm = Realm.getDefaultInstance();
        items = realm.where(Items.class).equalTo("id",earth_date).findFirst();
        if(items == null){
            items = new Items();
            items.setId(earth_date);

            realm.beginTransaction();
            items = realm.copyToRealm(items);
            realm.commitTransaction();
        }

        if(items.getItems().size() == 0){
            getPage();
        } else {
            Log.d(TAG, "Results from database");
            for (Photos result : items.getItems()) {
                Log.d(TAG, result.getId() + "| Url: "+ result.getImg_src());
            }
        }
    }

    @Override
    public void onPause(){

        if(realm == null && !realm.isClosed()){
            realm.close();
        }

        super.onPause();
    }

    private void getPage(){
        ImageApp.getInstance().getApiService().search(rover,earth_date, BuildConfig.API_KEY, currentPage).enqueue(new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {
                List<Photos> results = response.body().getPhotos();
                Log.d(TAG, "Results from web");
                for(Photos result : results){
                    Log.d("MainActivity", "Id: "+result.getId() + " | Url: "+result.getImg_src() +" | Sol: "+result.getSol());
                }

                realm.beginTransaction();
                items.getItems().addAll(results);
                long lastUpdate = new Date().getTime();
                items.setLastUpdate(lastUpdate);
                realm.commitTransaction();
            }

            @Override
            public void onFailure(Call<Page> call, Throwable t) {
                Log.d("MainActivity", "onFailure: " + t.getMessage());
            }
        });
    }
}
