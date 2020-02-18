package com.builderboy.elementis.common.item;

import com.builderboy.elementis.utils.item.InteractiveItem;
import com.builderboy.elementis.utils.mana.IItemManaContiner;
import com.builderboy.elementis.utils.mana.ManaContainerItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class StaffItem extends ManaContainerItem {

    private StaffType type;

    public StaffItem(StaffType type) {
        this(type, 0);
    }

    public StaffItem(StaffType type, int defaultMana) {
        this.type = type;
        this.defaultMana = defaultMana;
        this.maxMana = type.calculateStoredMana();
    }

    @Override
    public ActionResult<ItemStack> serverRightClick(World world, PlayerEntity player, ItemStack held, boolean isSneaking) {

        if (isSneaking) {
            this.changeMana(held, (200 * this.type.getTier()));
            return new ActionResult<>(ActionResultType.SUCCESS, held);
        }

        return new ActionResult<>(ActionResultType.PASS, held);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) { this.getDurabilityForDisplay(stack); }

    @Override
    public boolean showDurabilityBar(ItemStack stack) { return true; }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        StaffItem staff = (StaffItem) stack.getItem();
        StaffType type = staff.getType();

        CompoundNBT nbt = this.getNBT(stack);

        int current = nbt.getInt("mana");
        int max = type.calculateStoredMana();

        return 1 - (double)current / (double)max;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        CompoundNBT nbt = this.getNBT(stack);

        int current = nbt.getInt("mana");
        int max = this.type.calculateStoredMana();
        String string = I18n.format(current + " / " + max);
        ITextComponent info = new StringTextComponent(string);
        tooltip.add(info);
    }

    @Override
    public void updateMana(ItemStack stack, int mana) {
        CompoundNBT nbt = this.getNBT(stack);

        if (mana < 0) { mana = 0; }
        else if (mana > this.maxMana) { mana = this.getMaxMana(); }

        nbt.putInt("mana", mana);

        stack.setTag(nbt);
    }

    @Override
    public void changeMana(ItemStack stack, int changeBy) {
        CompoundNBT nbt = this.getNBT(stack);
        int oldMana = nbt.getInt("mana");
        this.updateMana(stack, oldMana + changeBy);
    }

    @Override
    public int getMana(ItemStack stack) {
        CompoundNBT nbt = this.getNBT(stack);

        return nbt.getInt("mana");
    }

    @Override
    public int getMaxMana() { return this.maxMana; }

    public StaffType getType() { return this.type; }

    private CompoundNBT getNBT(ItemStack stack) {
        CompoundNBT nbt = stack.getTag();
        if (nbt == null) { nbt= new CompoundNBT(); }
        if (!nbt.contains("mana")) { nbt.putInt("mana", this.defaultMana); }
        stack.setTag(nbt);

        return nbt;
    }

    public enum StaffType {
        ELEMENTIK(0),
        ALDENIK(1),
        MALDINIK(2);

        private int tier;

        StaffType(int tier) {
            this.tier = tier;
        }

        public int getTier() { return tier + 1; }
        public int calculateStoredMana() { return (int)Math.floor((2000 * this.getTier()) - (750 / this.getTier()) + 3); }
    }
}