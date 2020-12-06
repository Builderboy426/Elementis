package com.builderboy.elementis.utils;

public enum ManaCoreType {
    NORMAL(0, 1.0f),
    REINFORCED(150, 1.4f),
    GILDED(350, 1.8f),
    MOLTEN(600, 2.2f);

    private int manaAddon;
    private float manaMultiplier;

    ManaCoreType(int manaAddon, float manaMultiplier) {
        this.manaAddon = manaAddon;
        this.manaMultiplier = manaMultiplier;
    }

    public int getManaAddon() { return this.manaAddon; }

    public float getManaMultiplier() { return this.manaMultiplier; }
}