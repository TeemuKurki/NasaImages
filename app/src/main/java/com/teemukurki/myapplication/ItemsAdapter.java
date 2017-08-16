package com.teemukurki.myapplication;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.teemukurki.myapplication.model.Items;
import com.teemukurki.myapplication.model.Photos;
import com.teemukurki.myapplication.viewholder.LoadingViewHolder;
import com.teemukurki.myapplication.viewholder.PhotosViewHolder;

import io.realm.RealmChangeListener;

/**
 * Created by teemu on 16.8.2017.
 */

public class ItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RealmChangeListener<Items> {

    private static final int KEY_POSITION_LOADING = 0;
    private static final int KEY_POSITION_ITEM = 1;

    private Items items;
    private boolean isLoading = false;

    public void initialize(Items items){
        this.items = items;
        notifyDataSetChanged();
        items.addChangeListener(this);
    }

    @Override
    public void onChange(Items items) {
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if(viewType == KEY_POSITION_ITEM){
            View view = layoutInflater.inflate(R.layout.list_photo,parent,false);
            return new PhotosViewHolder(view);
        }else if(viewType == KEY_POSITION_LOADING){
            View view = layoutInflater.inflate(R.layout.list_loading,parent,false);
            return new LoadingViewHolder(view);
        }


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof PhotosViewHolder){

            Photos photo = items.getItems().get(position);

            //Tämä asettaa itemin listalle

            ((PhotosViewHolder) holder).titleView.setText(photo.getCamera().getFull_name() +" | Sol: "+ photo.getSol() + " | " + photo.getRover().getName());
            ((PhotosViewHolder) holder).urlView.setText(photo.getImg_src());
        }
    }

    public void setIsLoading(boolean currentlyLoading){
        isLoading = currentlyLoading;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if(items != null && items.getItems() != null){
            count += items.getItems().size();
        }

        if(isLoading){
            count++;
        }

        return count;
    }

    @Override
    public int getItemViewType(int position){
        if(isLoading){
            if(items != null && items.getItems() != null && items.getItems().size() == position){
                return KEY_POSITION_LOADING;
            }
        }

        return KEY_POSITION_ITEM;
    }
}
