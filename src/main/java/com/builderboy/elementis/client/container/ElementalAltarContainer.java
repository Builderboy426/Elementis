package com.builderboy.elementis.client.container;

import com.builderboy.elementis.common.item.StaffItem;
import com.builderboy.elementis.common.recipe.ElementalAltarRecipe;
import com.builderboy.elementis.core.ModContainers;
import com.builderboy.elementis.core.ModRecipes;
import com.builderboy.elementis.utils.item.slots.ResultSlot;
import com.builderboy.elementis.utils.item.slots.StaffSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ElementalAltarContainer extends Container {
    private final IInventory inventory = new Inventory(11);
    private final IRecipeType<ElementalAltarRecipe> recipeType;
    private final World world;

    public ElementalAltarContainer(int id, PlayerInventory playerInventory) {
        super(ModContainers.ELEMENTAL_ALTAR.get(), id);
        this.recipeType = ModRecipes.ALTAR;
        assertInventorySize(inventory, inventory.getSizeInventory());
        this.world = playerInventory.player.world;

        //Container (0 - 10)
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(this.inventory, j * 3 + i, 8 + j *18, 17 + i *18));
            }
        }

        this.addSlot(new StaffSlot(this.inventory, 9, 170, 52));
        this.addSlot(new ResultSlot(playerInventory.player, this.inventory, 10, 102, 34));

        //Player Inventory (11 - 47)
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, (j * 18) - 14, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, (k * 18) - 14, 142));
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            stack = slotStack.copy();

            if (index < 11) {
                if (this.mergeItemStack(slotStack, 11, 47, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(slotStack, stack);
            } else {
                if (isStaff(slotStack)) {
                    if (!this.mergeItemStack(slotStack, 9, 10, false)) {
                        return ItemStack.EMPTY;
                    } else if (!this.mergeItemStack(slotStack, 0, 8, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (!this.mergeItemStack(slotStack, 0, 8, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (slotStack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (slotStack.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotStack);
        }

        return stack;
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return this.inventory.isUsableByPlayer(player);
    }

    @Override
    public void onContainerClosed(PlayerEntity player) {
        for (int c = 0; c < 11; c++) {
            Slot slot = this.inventorySlots.get(c);
            ItemStack slotStack = slot.getStack();
            if (!slotStack.isEmpty()) {
                player.inventory.addItemStackToInventory(slotStack);
            }
        }
    }

    private boolean isStaff(ItemStack stack) { return stack.getItem() instanceof StaffItem; }

    @OnlyIn(Dist.CLIENT)
    public int getManaScaled() {
        ItemStack stack = this.inventory.getStackInSlot(9);
        if (isStaff(stack)) {
            StaffItem staff = (StaffItem) stack.getItem();
            int cm = staff.getMana(stack);
            int mm = staff.getMaxMana();

            return (cm * 60) / mm;
        }

        return 0;
    }
}