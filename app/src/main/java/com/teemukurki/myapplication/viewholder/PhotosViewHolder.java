package com.teemukurki.myapplication.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teemukurki.myapplication.R;


/**
 * Created by teemu on 16.8.2017.
 */

public class PhotosViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView urlView;
    public ImageView img;

    public PhotosViewHolder(View itemView) {
        super(itemView);


        titleView = (TextView) itemView.findViewById(R.id.title);
        urlView = (TextView) itemView.findViewById(R.id.url);
    }


}
