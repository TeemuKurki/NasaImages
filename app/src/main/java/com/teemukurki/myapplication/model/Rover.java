package com.teemukurki.myapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by teemu on 16.8.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rover extends RealmObject {
    @PrimaryKey
    private int id;

    private String name;
    private String status;
    private String landing_date;
    private String launch_date;
    private String max_sol;
    private String max_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLanding_date() {
        return landing_date;
    }

    public void setLanding_date(String landing_date) {
        this.landing_date = landing_date;
    }

    public String getLaunch_date() {
        return launch_date;
    }

    public void setLaunch_date(String launch_date) {
        this.launch_date = launch_date;
    }

    public String getMax_sol() {
        return max_sol;
    }

    public void setMax_sol(String max_sol) {
        this.max_sol = max_sol;
    }

    public String getMax_date() {
        return max_date;
    }

    public void setMax_date(String max_date) {
        this.max_date = max_date;
    }
}
