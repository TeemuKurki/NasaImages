package com.teemukurki.myapplication;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by teemu on 17.8.2017.
 */

public class ViewBindingAdapter {

    private static String TAG = "ViewBindingAdapter";

    @BindingAdapter("url")
    public static void lataaKuva(ImageView imageView, String url){
        Log.d(TAG, url);
        Picasso.with(imageView.getContext())
                .load(url)
                .noFade()
                .placeholder(R.drawable.loading_animation)
                .into(imageView);
    }
}
