package com.teemukurki.myapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by teemu on 14.8.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    String status;

    List<Photos> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Photos> getResults() {
        return results;
    }

    public void setResults(List<Photos> results) {
        this.results = results;
    }
}
