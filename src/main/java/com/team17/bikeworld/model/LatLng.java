package com.team17.bikeworld.model;

import com.google.gson.annotations.Expose;

public class LatLng {
    @Expose
    private double lat;
    @Expose
    private double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
