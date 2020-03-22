package com.builderboy.elementis.item;

import com.builderboy.elementis.Elementis;
import com.builderboy.elementis.client.spellparcel.SpellParcelContainer;
import com.builderboy.elementis.registries.ModStatsRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SpellParcelItem extends InteractibleItem implements INamedContainerProvider {
    private final int MAX_INVENTORY_SIZE = 12;
    NonNullList<ItemStack> inventory = NonNullList.withSize(MAX_INVENTORY_SIZE, ItemStack.EMPTY);

    public SpellParcelItem(ItemGroup group) {
        super(group);
    }

    @Override
    public ActionResult<ItemStack> serverRightClick(World world, PlayerEntity player, ItemStack held, boolean isSneaking) {

        if (isSneaking) {
            CompoundNBT nbt = this.constructNBT(held);

        }

        player.openContainer(this);
        player.addStat(ModStatsRegistry.INTERACT_WITH_SPELL_PARCEL);

        return new ActionResult<>(ActionResultType.PASS, held);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container." + Elementis.MODID + ".spell_parcel");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInv, PlayerEntity player) {
        return new SpellParcelContainer(id, playerInv);
    }

    public NonNullList<ItemStack> getInventory() { return this.inventory; }

    private void getInventoryFromNBT(CompoundNBT nbt) {
        NonNullList<ItemStack> items = NonNullList.withSize(MAX_INVENTORY_SIZE, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, items);
        inventory = items;
    }

    private CompoundNBT constructNBT(ItemStack stack) {
        CompoundNBT nbt = stack.getTag();
        if (nbt == null) {
            nbt = new CompoundNBT();
            ItemStackHelper.saveAllItems(nbt, inventory);
        }
        stack.setTag(nbt);

        return nbt;
    }
}