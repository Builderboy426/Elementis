package com.builderboy.elementis.utils.mana;

import com.builderboy.elementis.utils.item.InteractiveItem;
import net.minecraft.item.ItemStack;

public class ManaContainerItem extends InteractiveItem implements IItemManaContiner {

    protected int defaultMana;
    protected int maxMana;

    @Override
    public void changeMana(ItemStack stack, int changeBy) {}

    @Override
    public void updateMana(ItemStack stack, int mana) {}

    @Override
    public int getMana(ItemStack stack) { return defaultMana; }

    @Override
    public int getMaxMana() { return maxMana; }
}