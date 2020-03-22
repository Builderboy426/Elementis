package com.builderboy.elementis.item;

import com.builderboy.elementis.client.elementalaltar.ElementalAltarContainer;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;

public class ElementalAltarInventory extends CraftingInventory {
    private ItemStack staff = ItemStack.EMPTY;
    private ElementalAltarContainer container;

    public ElementalAltarInventory(ElementalAltarContainer container) {
        super(container, 3, 3);
        this.container = container;
    }

    @Override
    public int getSizeInventory() {
        return 10;
    }

    @Override
    public ItemStack getStackInSlot(int id) {
        return id == 9 ? staff : super.getStackInSlot(id);
    }

    @Override
    public void setInventorySlotContents(int id, ItemStack stack) {
        if (id < 9) {
            super.setInventorySlotContents(id, stack);
        } else if (id == 9) {
            staff = stack;
        }

        container.onCraftMatrixChanged(this);
    }

    @Override
    public ItemStack removeStackFromSlot(int id) {
        if (id == 9) {
            ItemStack copy = staff.copy();
            staff = ItemStack.EMPTY;
            return copy;
        }

        container.onCraftMatrixChanged(this);
        return super.removeStackFromSlot(id);
    }

    public ItemStack getStaff() {
        return staff;
    }
}