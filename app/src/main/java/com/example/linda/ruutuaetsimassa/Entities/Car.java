package com.example.linda.ruutuaetsimassa.Entities;

/**
 * Created by Linda on 24/11/16.
 */

public class Car {

    private String carId;
    private String registerNo;
    private PoleType[] pluginTypes;

    public Car(String id, String regNo, PoleType[] pTypes) {
        this.carId = id;
        this.registerNo = regNo;
        this.pluginTypes = pTypes;
    }

    public String getCarId() {
        return carId;
    }

    public PoleType[] getPluginTypes() {
        return pluginTypes;
    }

    public String getRegisterNo() {
        return registerNo;
    }
}
