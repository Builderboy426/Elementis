package com.builderboy.elementis.common.item;

import com.builderboy.elementis.utils.item.InteractiveItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
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

public class StaffItem extends InteractiveItem {

    private StaffType type;

    public StaffItem(StaffType type) {
        super();
        this.type = type;
    }

    @Override
    public ActionResult<ItemStack> serverRightClick(World world, PlayerEntity player, ItemStack held, boolean isSneaking) {
        CompoundNBT nbt = held.getTag();
        if (nbt == null) { nbt= new CompoundNBT(); }
        if (!nbt.hasUniqueId("mana")) { nbt.putInt("mana", 0); }
        held.setTag(nbt);

        int mana = nbt.getInt("mana");

        //TODO: Do stuff

        return new ActionResult<>(ActionResultType.PASS, held);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) { return true; }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        StaffItem staff = (StaffItem) stack.getItem();
        StaffType type = staff.getType();

        CompoundNBT nbt = stack.getTag();
        if (nbt == null) { nbt= new CompoundNBT(); }
        if (!nbt.hasUniqueId("mana")) { nbt.putInt("mana", 0); }
        stack.setTag(nbt);

        int current = nbt.getInt("mana");
        int max = type.calculateStoredMana();

        return 1 - current / max;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        CompoundNBT nbt = stack.getTag();
        if (nbt == null) { nbt= new CompoundNBT(); }
        if (!nbt.hasUniqueId("mana")) { nbt.putInt("mana", 0); }
        stack.setTag(nbt);

        int current = nbt.getInt("mana");
        int max = this.type.calculateStoredMana();
        String string = I18n.format(current + " / " + max);
        ITextComponent info = new StringTextComponent(string);
        tooltip.add(info);
    }

    public StaffType getType() { return this.type; }

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