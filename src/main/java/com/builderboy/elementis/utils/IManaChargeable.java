package com.builderboy.elementis.utils;

import net.minecraft.item.ItemStack;

public interface IManaChargeable {
    //Charge the item with the inputted mana
    void chargeMana(ItemStack stack, int mana);

    //Whether the item can be charged
    default boolean canCharge() { return false; }
}