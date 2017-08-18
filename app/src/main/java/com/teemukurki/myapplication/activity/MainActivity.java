package com.teemukurki.myapplication.activity;


import android.app.Application;
import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.teemukurki.myapplication.BuildConfig;
import com.teemukurki.myapplication.ImageApp;
import com.teemukurki.myapplication.ItemsAdapter;
import com.teemukurki.myapplication.R;
import com.teemukurki.myapplication.model.Items;
import com.teemukurki.myapplication.model.Photos;
import com.teemukurki.myapplication.model.Page;
import com.teemukurki.myapplication.view.MyRecyclerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MyRecyclerView.LoadMoreListener {

    private static String TAG = "MainActivity";

    private Realm realm;
    private Items items;
    private Page page;

    private String earth_date = "2017-2-22";
    private String rover = "curiosity";

    private Button tyhjenna;

    private MyRecyclerView recycler;
    private ItemsAdapter adapter;
    private EditText eDate;
    private Calendar myCalendar;

    private boolean isLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myCalendar = Calendar.getInstance();

        tyhjenna = (Button) findViewById(R.id.clear_db);
        eDate = (EditText) findViewById(R.id.date);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };

        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        adapter = new ItemsAdapter();

        recycler = (MyRecyclerView) findViewById(R.id.recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);
        recycler.setLoadMoreListener(this);

        tyhjenna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //earth_date = date.getText().toString();
                if(items != null && realm != null){
                    realm.beginTransaction();
                    items.reset();
                    Log.d(TAG, String.valueOf(items.getCurrentPage()));
                    realm.commitTransaction();
                }
                getPage();
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



        if(items.getCurrentPage() <= 0){
            getPage();
        } else {
            Log.d(TAG, "Results from database");
            for (Photos result : items.getItems()) {
                Log.d(TAG, result.getId() + "| Url: "+ result.getImg_src());
            }
        }

        adapter.initialize(items);
    }

    @Override
    public void onPause(){

        if(realm == null && !realm.isClosed()){
            realm.close();
        }

        super.onPause();
    }

    private void getPage(){
        Log.d(TAG, String.valueOf(items.getCurrentPage()));

        isLoading = true;
        adapter.setIsLoading(true);
        ImageApp.getInstance().getApiService().search(rover,earth_date, BuildConfig.API_KEY, items.getCurrentPage()).enqueue(new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {
                adapter.setIsLoading(false);
                isLoading = false;

                List<Photos> results = response.body().getPhotos();
                Log.d(TAG, "Results from web");
                for(Photos result : results){
                    Log.d(TAG, "Id: "+result.getId() + " | Url: "+result.getImg_src() +" | Sol: "+result.getSol() + "| Camera: "+result.getCamera().getFull_name());
                }

                realm.beginTransaction();
                items.setCurrentPage(items.getCurrentPage() + 1);
                items.getItems().addAll(results);
                long lastUpdate = new Date().getTime();
                items.setLastUpdate(lastUpdate);
                realm.commitTransaction();
            }

            @Override
            public void onFailure(Call<Page> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                adapter.setIsLoading(false);
                isLoading = false;
            }
        });
    }


    @Override
    public void shouldLoadMore() {
        if(!isLoading){
            Log.d(TAG, "shouldLoadMore: Starting to load more");
            getPage();
        }
    }

}
