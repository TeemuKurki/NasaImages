package com.teemukurki.myapplication.model;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by teemu on 15.8.2017.
 */

public class Items extends RealmObject {

    @PrimaryKey
    private String id;

    private RealmList<Photos> items = new RealmList<Photos>();
    private long lastUpdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RealmList<Photos> getItems() {
        return items;
    }

    public void setItems(RealmList<Photos> items) {
        this.items = items;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
