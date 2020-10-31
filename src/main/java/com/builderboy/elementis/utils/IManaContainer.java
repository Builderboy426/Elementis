package com.builderboy.elementis.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public interface IManaContainer {
    void updateMana(ItemStack stack, int mana);
    void changeMana(ItemStack stack, int changeBy);

    default int getMana(ItemStack stack) {
        IManaContainer container = stack.getItem() instanceof IManaContainer ? (IManaContainer) stack.getItem() : null;
        if (container != null) {
            CompoundNBT nbt = stack.getOrCreateTag();
            if (nbt.contains("mana")) {
                return nbt.getInt("mana");
            }
        }

        return 0;
    }
}