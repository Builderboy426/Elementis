package com.builderboy.elementis.client;

import com.builderboy.elementis.item.ManaTabletItem;
import com.builderboy.elementis.item.StaffItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ManaTabletSlot extends Slot {
    public ManaTabletSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) { return stack.getItem() instanceof ManaTabletItem; }
}