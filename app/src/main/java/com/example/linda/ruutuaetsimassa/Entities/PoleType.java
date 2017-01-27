package com.example.linda.ruutuaetsimassa.Entities;

import static android.R.attr.value;

/**
 * Created by Linda on 15/11/16.
 */

public enum PoleType {
    TYPE1("Tyyppi 1"),
    TYPE2("Tyyppi 2"),
    CHADEMO("CHAdeMo"),
    CCSCOMBO("CCS Combo");

    private String name;

    private PoleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}