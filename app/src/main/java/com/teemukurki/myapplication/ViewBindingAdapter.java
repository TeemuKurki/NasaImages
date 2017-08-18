package com.teemukurki.myapplication;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by teemu on 17.8.2017.
 */

public class ViewBindingAdapter {

    @BindingAdapter("url")
    public static void lataaKuva(ImageView imageView, String url){
        Picasso.with(imageView.getContext())
                .load(url)
                .noFade()
                .placeholder(R.drawable.loading_animation)
                .into(imageView);
    }
}
