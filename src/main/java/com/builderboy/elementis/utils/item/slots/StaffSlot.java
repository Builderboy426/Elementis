package com.builderboy.elementis.utils.item.slots;

import com.builderboy.elementis.common.item.StaffItem;
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