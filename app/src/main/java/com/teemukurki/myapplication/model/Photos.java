package com.teemukurki.myapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by teemu on 14.8.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Photos {
    private int id;
    private String img_src;
    private int sol;
    private Camera camera;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg_src() {
        return img_src;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }

    public int getSol() {
        return sol;
    }

    public void setSol(int sol) {
        this.sol = sol;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
