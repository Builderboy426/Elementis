package com.builderboy.elementis.client.elementalaltar;

import com.builderboy.elementis.client.StaffSlot;
import com.builderboy.elementis.item.ElementalAltarInventory;
import com.builderboy.elementis.item.StaffItem;
import com.builderboy.elementis.registries.ModContainerRegistry;
import com.builderboy.elementis.registries.ModRecipeRegistry;
import com.builderboy.elementis.reicpe.ElementalAltarShapedRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Optional;

public class ElementalAltarContainer extends Container implements IRecipeHelperPopulator, IRecipeHolder {
    private final ElementalAltarInventory craftInventory;
    private final CraftResultInventory resultInventory;
    private Slot resultSlot;
    private final PlayerEntity player;
    private final World world;
    private IRecipe<ElementalAltarInventory> recipe;

    public ElementalAltarContainer(int id, PlayerInventory playerInventory) {
        super(ModContainerRegistry.ELEMENTAL_ALTAR.get(), id);
        this.player = playerInventory.player;
        this.world = playerInventory.player.world;

        this.resultInventory = new CraftResultInventory();
        this.craftInventory = new ElementalAltarInventory(this);
        this.craftInventory.openInventory(this.player);

        //Container (0 - 10)
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(this.craftInventory, j * 3 + i, 8 + j * 18, 17 + i * 18));
            }
        }

        this.addSlot(new StaffSlot(this.craftInventory, 9, 170, 52));
        resultSlot = this.addSlot(new CraftingResultSlot(playerInventory.player, this.craftInventory, this.resultInventory, 10, 102, 34));

        //Player Inventory (11 - 47)
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, (j * 18) - 14, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, (k * 18) - 14, 142));
        }

        onCraftMatrixChanged(craftInventory);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            stack = slotStack.copy();

            if (index == 0) {
                IWorldPosCallable.of(world, player.getPosition()).consume((world, pos) -> {
                    slotStack.getItem().onCreated(slotStack, world, player);
                });
                if (!this.mergeItemStack(slotStack, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(slotStack, stack);
            }

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
    public void onCraftMatrixChanged(IInventory inventory) {
        IWorldPosCallable.of(world, player.getPosition()).consume((world, pos) -> {
            if (!world.isRemote) {
                ServerPlayerEntity playerMP = (ServerPlayerEntity) player;
                ItemStack stack = ItemStack.EMPTY;
                Optional<ElementalAltarShapedRecipe> optional = world.getServer().getRecipeManager().getRecipe(ModRecipeRegistry.ELEMENTAL_ALTAR_SHAPED, craftInventory, world);
                if (optional.isPresent()) {
                    ElementalAltarShapedRecipe recipe = optional.get();
                    if (resultInventory.canUseRecipe(world, playerMP, recipe)) {
                        stack = recipe.getCraftingResult(craftInventory);
                    }
                }

                resultInventory.setInventorySlotContents(resultSlot.slotNumber, stack);
                playerMP.connection.sendPacket(new SSetSlotPacket(windowId, resultSlot.slotNumber, stack));
            }
        });
    }



    @Override
    public boolean canDragIntoSlot(Slot slot) {
        if (slot.slotNumber == 9 || slot.slotNumber == 10) { return false; }
        return true;
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return true;
    }

    @Override
    public void onContainerClosed(PlayerEntity player) {
        super.onContainerClosed(player);
        IWorldPosCallable.of(world, player.getPosition()).consume((world, pos) -> {
            this.clearContainer(player, world, craftInventory);
        });
    }

    public StaffItem getStaff() { return hasStaff() ? (StaffItem)craftInventory.getStackInSlot(9).getItem() : (StaffItem)ItemStack.EMPTY.getItem(); }

    @OnlyIn(Dist.CLIENT)
    public int getManaScaled() {
        ItemStack stack = this.craftInventory.getStackInSlot(9);
        if (isStaff(stack)) {
            StaffItem staff = (StaffItem) stack.getItem();
            int cm = staff.getMana(stack);
            int mm = staff.getMaxMana();

            return (cm * 60) / mm;
        }

        return 0;
    }

    private boolean isStaff(ItemStack stack) { return stack.getItem() instanceof StaffItem; }
    private boolean hasStaff() { return !craftInventory.getStackInSlot(9).isEmpty(); }

    @Override
    public void fillStackedContents(RecipeItemHelper helper) {

    }

    @Override
    public void setRecipeUsed(@Nullable IRecipe<?> recipe) {}

    @Nullable
    @Override
    public IRecipe<?> getRecipeUsed() {
        return null;
    }
}