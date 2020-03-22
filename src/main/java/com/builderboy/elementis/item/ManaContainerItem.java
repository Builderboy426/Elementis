package com.builderboy.elementis.item;

import com.builderboy.elementis.Elementis;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ManaContainerItem extends InteractibleItem implements IItemManaContainer {

    protected int deafultMana = 0;
    protected int maxMana;

    public ManaContainerItem(ItemGroup group) { super(group); }

    public ManaContainerItem(Properties properties) { super(properties); }


    public int getMana(ItemStack stack) {
        CompoundNBT nbt = constructNBT(stack);
        return nbt.getInt("mana");
    }

    public int getMaxMana() { return maxMana; }

    public void updateMana(ItemStack stack, int mana) {
        CompoundNBT nbt = constructNBT(stack);

        if (mana < 0) {
            mana = 0;
            Elementis.LOGGER.warn("Mana input was negative!");
        } else if (mana > this.maxMana) {
            mana = this.getMaxMana();
            Elementis.LOGGER.warn("Mana exceeded the max value of its type!");
        }

        nbt.putInt("mana", mana);
        stack.setTag(nbt);
    }

    public void changeMana(ItemStack stack, int changeBy) {
        CompoundNBT nbt = constructNBT(stack);
        int mana = nbt.getInt("mana");
        this.updateMana(stack, mana + changeBy);
    }

    protected CompoundNBT constructNBT(ItemStack stack) {
        CompoundNBT nbt = stack.getTag();
        if (nbt == null) {
            nbt = new CompoundNBT();
            nbt.putInt("mana", deafultMana);
        }
        stack.setTag(nbt);

        return nbt;
    }
}