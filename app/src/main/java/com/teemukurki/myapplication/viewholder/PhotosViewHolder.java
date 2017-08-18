package com.teemukurki.myapplication.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teemukurki.myapplication.R;
import com.teemukurki.myapplication.databinding.ListPhotoBinding;


/**
 * Created by teemu on 16.8.2017.
 */

public class PhotosViewHolder extends RecyclerView.ViewHolder {


    public ListPhotoBinding binding;

    public PhotosViewHolder(ListPhotoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;


    }


}
