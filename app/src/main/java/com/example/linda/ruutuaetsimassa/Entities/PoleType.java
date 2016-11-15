package com.example.linda.ruutuaetsimassa.Entities;

import static android.R.attr.value;

/**
 * Created by Linda on 15/11/16.
 */

public enum PoleType {
    NEMA15("NEMA 15"),
    NEMA20("NEMA 20"),
    NEMA50("NEMA 50"),
    TESLA("Tesla"),
    J1772("J1772"),
    SAECOMBO("SAE Combo"),
    CHADEMO("CHAdeMO");

    private final String name;

    private PoleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}