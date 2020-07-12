package com.builderboy.elementis.item;

import com.builderboy.elementis.Elementis;
import com.builderboy.elementis.mana.IManaChargeable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ManaContainerItem extends BaseItem implements IManaChargeable {
    private int defaultMana = 0;
    private int maxMana;

    public ManaContainerItem() {
        super(new Item.Properties().group(Elementis.GROUP).maxStackSize(1));
    }

    public int getMana(ItemStack stack) {
        CompoundNBT nbt = constructNBT(stack);
        return nbt.getInt("mana");
    }


    public int getMaxMana() { return this.maxMana; }

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

    @Override
    public void chargeMana(ItemStack stack, int mana) {
        if (this.canCharge()) {
            this.changeMana(stack, mana);
        }
    }

    protected CompoundNBT constructNBT(ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateTag();
        if (!nbt.contains("mana")) { nbt.putInt("mana", defaultMana); }
        stack.setTag(nbt);

        return nbt;
    }
}