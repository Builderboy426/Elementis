package com.builderboy.elementis.client;

import com.builderboy.elementis.item.StaffItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class StaffSlot extends Slot {
    public StaffSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) { return stack.getItem() instanceof StaffItem; }
}