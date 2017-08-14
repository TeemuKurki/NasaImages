package com.teemukurki.myapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by teemu on 14.8.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Page {

    private List<Photos> photos;

    public List<Photos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photos> photos) {
        this.photos = photos;
    }

}
