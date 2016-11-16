package com.example.linda.ruutuaetsimassa.Entities;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Linda on 14/11/16.
 */

public class Charger {

    private String name;
    private String description;
    private String address;
    private boolean isFree;
    private double power;
    private PoleType poleType;
    private LatLng coords;

    public Charger(String name, String description, String address, boolean free, double pow, PoleType type, LatLng loc) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.power = pow;
        this.poleType = type;
        this.coords = loc;
        this.isFree = free;
    }

    public LatLng getCoords() {
        return coords;
    }

    public PoleType getPoleType() {
        return poleType;
    }

    public double getPower() {
        return power;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setAsFree(boolean free) { this.isFree = free; }

}
