package com.builderboy.elementis.common.block.tileentity;

import com.builderboy.elementis.Elementis;
import com.builderboy.elementis.client.container.ElementalAltarContainer;
import com.builderboy.elementis.common.block.ElementalAltarBlock;
import com.builderboy.elementis.common.item.crafting.ElementalAltarRecipe;
import com.builderboy.elementis.core.ModTileEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ElementalAltarTileEntity extends LockableTileEntity implements ITickableTileEntity {

    private ItemStackHandler handler = new ItemStackHandler(11) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            ElementalAltarTileEntity.this.checkRecipes = true;
        }
    };

    private boolean checkRecipes = false;
    private ElementalAltarRecipe recipeFound;

    public ElementalAltarTileEntity() {
        super(ModTileEntities.ELEMENTAL_ALTAR.get());
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + Elementis.MOD_ID + ".altar");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new ElementalAltarContainer(id, player);
    }

    @Override
    public void tick() {
        if (checkRecipes && recipeFound != null) {
            ElementalAltarContainer container = (ElementalAltarContainer)((ElementalAltarBlock)this.getBlockState().getBlock())
                    .getContainer(this.getBlockState(), this.getWorld(), this.getPos());

            for (ElementalAltarRecipe recipe : ElementalAltarRecipe.RECIPES) {
                if (recipe.match(container)) {
                    container.setRecipe(recipe);
                    recipeFound = recipe;
                    break;
                }
            }

            this.checkRecipes = false;
        }
    }

    @Override
    public void read(CompoundNBT nbt) {
        super.read(nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        return super.write(nbt);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return null;
    }

    @Override
    public int getSizeInventory() {
        return 11;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int i, int i1) {
        return null;
    }

    @Override
    public ItemStack removeStackFromSlot(int i) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack) {

    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity playerEntity) {
        return false;
    }

    @Override
    public void clear() {

    }

    public void setRecipe() { this.recipeFound = null; }
}