package com.builderboy.elementis.utils.mana;

import net.minecraft.item.ItemStack;

public interface IItemManaContiner {

    int getMana(ItemStack stack);
    int getMaxMana();

    void updateMana(ItemStack stack, int mana);
    void changeMana(ItemStack stack, int changeBy);
}