package com.teemukurki.myapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by teemu on 14.8.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Camera {
    private String full_name;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
