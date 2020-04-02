package com.builderboy.elementis.utils;

import net.minecraft.item.ItemStack;

public interface IItemManaContainer {

    int getMana(ItemStack stack);
    int getMaxMana();

    void updateMana(ItemStack stack, int mana);
    void changeMana(ItemStack stack, int changeBy);
}