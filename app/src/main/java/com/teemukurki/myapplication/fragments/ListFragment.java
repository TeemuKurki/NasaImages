package com.teemukurki.myapplication.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.teemukurki.myapplication.BuildConfig;
import com.teemukurki.myapplication.ImageApp;
import com.teemukurki.myapplication.ItemsAdapter;
import com.teemukurki.myapplication.R;
import com.teemukurki.myapplication.activity.MainActivity;
import com.teemukurki.myapplication.model.Items;
import com.teemukurki.myapplication.model.Page;
import com.teemukurki.myapplication.model.Photos;
import com.teemukurki.myapplication.view.MyRecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by teemu on 18.8.2017.
 */

public class ListFragment extends Fragment implements MyRecyclerView.LoadMoreListener {

    private static String TAG = "ListFragment";
    private String landing_date = "2012-08-06";

    private Realm realm;
    private Items items;

    private String earth_date;
    private String rover ="curiosity";

    private MyRecyclerView recycler;
    private ItemsAdapter adapter;
    private TextView eDate;
    private SwipeRefreshLayout swipe;
    private Calendar myCalendar;

    private boolean isLoading;

    public static ListFragment newInstance(String rover){
        Bundle args = new Bundle();
        args.putString("query",rover);
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        rover = args.getString("query");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_list,container,false);

        swipe = (SwipeRefreshLayout) root.findViewById(R.id.swipe);

        myCalendar = Calendar.getInstance();

        eDate = (TextView) root.findViewById(R.id.date);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                eDate.setText(getDateFromCalendar(myCalendar));
                earth_date = getDateFromCalendar(myCalendar);
                if(items != null && realm != null){
                    realm.beginTransaction();
                    items.reset();
                    realm.commitTransaction();
                }
                getPage();
            }
        };

        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  DatePickerDialog dpd = new DatePickerDialog(ListFragment.this.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date minDate = new Date();
                Date maxDate = new Date();
                try{
                    minDate = simpleDateFormat.parse(landing_date);
                    maxDate = new Date();
                }catch (ParseException e){
                    e.printStackTrace();
                }

                dpd.getDatePicker().setMinDate(minDate.getTime());
                dpd.getDatePicker().setMaxDate(maxDate.getTime());
                dpd.show();

            }
        });

        adapter = new ItemsAdapter();

        recycler = (MyRecyclerView) root.findViewById(R.id.recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);
        recycler.setLoadMoreListener(this);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {;
                if(items != null && realm != null){
                    realm.beginTransaction();
                    items.reset();
                    realm.commitTransaction();
                }
                getPage();
                swipe.setRefreshing(false);
            }
        });

        eDate.setText(getDateFromCalendar(myCalendar));
        earth_date = getDateFromCalendar(myCalendar);

        return root;
    }

    @Override
    public void onResume(){
        super.onResume();

        realm = Realm.getDefaultInstance();
        items = realm.where(Items.class).equalTo("id",earth_date).findFirst();
        if(items == null){
            items = new Items();
            items.reset();
            items.setId(earth_date);
            realm.beginTransaction();
            items = realm.copyToRealm(items);
            realm.commitTransaction();
        }

        eDate.setText(getDateFromCalendar(myCalendar));

        if(items.getCurrentPage() <= 1){
            getPage();
        } else {
            Log.d(TAG,String.valueOf(items.getCurrentPage()));
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
                        Log.d(TAG, "Id: "+result.getId() + " | Url: "+result.getImg_src() +" | Sol: "+result.getSol() + "| Camera: "+result.getCamera().getFull_name() + " | "+result.getRover().getName());
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

    public String getDateFromCalendar(Calendar calendar){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return dateFormat.format(calendar.getTimeInMillis());
    }
}
