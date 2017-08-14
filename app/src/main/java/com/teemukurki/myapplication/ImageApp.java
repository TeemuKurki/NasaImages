package com.teemukurki.myapplication;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.teemukurki.myapplication.network.service.NasaApiService;

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
        //Täällä initalisoidaan jotain
        instance = this;
        apiService = new NasaApiService();
        Log.d(TAG, "onCreate");
    }
    public static ImageApp getInstance(){
        return instance;
    }
    public NasaApiService getApiService(){
        return apiService;
    }
}
