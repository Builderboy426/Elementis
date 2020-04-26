package com.builderboy.elementis.item;

import com.builderboy.elementis.Elementis;
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

public class StaffItem extends ManaContainerItem {

    private StaffType type;

    public StaffItem(StaffType type) {
        this(type, 0);
    }

    public StaffItem(StaffType type, int defaultMana) {
        super(Elementis.GROUP);
        this.type = type;
        this.defaultMana = defaultMana;
        this.maxMana = type.calculateMaxMana();
    }

    @Override
    public ActionResult<ItemStack> serverRightClick(World world, PlayerEntity player, ItemStack held, boolean isSneaking) {

        //TODO: Spell Functionality
        //TODO: Check Spell Parcel
        //TODO: Assemble Multiblock Structures

        if (isSneaking) {
            this.changeMana(held, 200);
            return new ActionResult<>(ActionResultType.SUCCESS, held);
        }

        return new ActionResult<>(ActionResultType.PASS, held);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) { return true; }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        StaffItem staff = (StaffItem) stack.getItem();
        StaffType type = staff.getType();
        CompoundNBT nbt = staff.constructNBT(stack);

        double current = nbt.getInt("mana");
        double max = type.calculateMaxMana();
        double durability = (current/max);

        return 1.0D - durability;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> components, ITooltipFlag flag) {
        StaffItem staff = (StaffItem) stack.getItem();
        StaffType type = staff.getType();
        CompoundNBT nbt = staff.constructNBT(stack);

        int current = nbt.getInt("mana");
        int max = type.calculateMaxMana();

        String text = I18n.format(current + "/" + max);
        ITextComponent info = new StringTextComponent(text);
        components.add(info);
    }

    public StaffType getType() { return this.type; }

    public enum StaffType {
        ELEMENTIK(1),
        ALDENIK(2),
        MALDINIK(3),
        PALDINIK(4),
        TALENTIK(5);

        private int tier;

        StaffType(int tier) {
            this.tier = tier;
        }

        public int getTier() { return tier; }
        public int calculateMaxMana() { return (int)Math.floor((2000 * this.getTier()) - (750 / this.getTier()) + 3); }

        public static StaffType getFromTier(int tier) {
            switch (tier) {
                case 1:
                    return ELEMENTIK;
                case 2:
                    return ALDENIK;
                case 3:
                    return MALDINIK;
                case 4:
                    return PALDINIK;
                case 5:
                    return TALENTIK;
            }

            Elementis.LOGGER.warn("A tier of " + tier + " does not exist!");
            return ELEMENTIK;
        }
    }
}