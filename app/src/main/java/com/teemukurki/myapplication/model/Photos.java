package com.teemukurki.myapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by teemu on 14.8.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Photos extends RealmObject {
    @PrimaryKey
    private String id;

    private String img_src;
    private String sol;
    private Camera camera;
    private Rover rover;
    private String currentPage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_src() {
        return img_src;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }

    public String getSol() {
        return sol;
    }

    public void setSol(String sol) {
        this.sol = sol;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Rover getRover() {
        return rover;
    }

    public void setRover(Rover rover) {
        this.rover = rover;
    }
    
}
