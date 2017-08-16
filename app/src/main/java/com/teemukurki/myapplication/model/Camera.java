package com.teemukurki.myapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by teemu on 15.8.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Camera extends RealmObject {
    @PrimaryKey
    private String id;

    private String full_name;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}