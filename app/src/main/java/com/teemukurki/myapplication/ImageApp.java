package com.teemukurki.myapplication;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.teemukurki.myapplication.network.service.NasaApiService;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by teemu on 14.8.2017.
 */

public class ImageApp extends MultiDexApplication{

    private String TAG = "ImageApp";
    private static ImageApp instance;
    private NasaApiService apiService;

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG, "onCreate");
        //Täällä initalisoidaan jotain
        instance = this;
        apiService = new NasaApiService();
        initRealm();
    }

    private void initRealm(){
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static ImageApp getInstance(){
        return instance;
    }
    public NasaApiService getApiService(){
        return apiService;
    }
}
