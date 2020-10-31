package com.builderboy.elementis.utils;

public enum CoreType {
    DEFAULT(1.0f, 0),
    REINFORCED(1.35f, 150),
    GILDED(1.8f, 400),
    CRYSTALIZE(2.15f, 850),
    MOLTEN(2.9f, 1450);

    private float manaMultiplier;
    private int bonusMana;

    CoreType(float mult, int bonus) {
        this.manaMultiplier = mult;
        this.bonusMana = bonus;
    }

    public float getManaMultiplier() {
        return manaMultiplier;
    }
}