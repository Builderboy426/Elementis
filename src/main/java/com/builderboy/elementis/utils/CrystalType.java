package com.builderboy.elementis.utils;

public enum CrystalType {
    NONE(0.0f),
    MANIK(1.0f),
    ELEMENTIK(1.0f),
    ALDENIK(1.2f),
    MALDENIK(1.6f),
    ZANIC(2.1f),
    PALDINIK(2.8f),
    TALENTIK(3.2f);

    private float manaMultiplier;

    CrystalType(float mult) {
        this.manaMultiplier = mult;
    }

    public float getManaMultiplier() {
        return this.manaMultiplier;
    }
}