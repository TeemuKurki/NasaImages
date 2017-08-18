package com.teemukurki.myapplication.activity;


import android.app.Application;
import android.app.DatePickerDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.teemukurki.myapplication.PagerTabsAdapter;
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

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    TabLayout tabs;
    ViewPager pager;
    PagerTabsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = (TabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);

        adapter = new PagerTabsAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        tabs.setupWithViewPager(pager);

    }


}
