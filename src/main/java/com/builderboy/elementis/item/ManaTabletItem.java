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

public class ManaTabletItem extends ManaContainerItem {

    private ManaTabletType type;
    private ManaTabletSize size;

    public ManaTabletItem(ManaTabletType type, ManaTabletSize size) {
        super(Elementis.GROUP);
        this.type = type;
        this.size = size;
        this.maxMana = calculateMaxStoredMana(type, size);
    }

    @Override
    public ActionResult<ItemStack> serverRightClick(World world, PlayerEntity player, ItemStack held, boolean isSneaking) {

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
        ManaTabletItem manaTablet = (ManaTabletItem) stack.getItem();
        ManaTabletType type = manaTablet.getType();
        ManaTabletSize size = manaTablet.getSize();
        CompoundNBT nbt = manaTablet.constructNBT(stack);

        double current = nbt.getInt("mana");
        double max = calculateMaxStoredMana(type, size);
        double durability = (current/max);

        return 1.0D - durability;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> components, ITooltipFlag flag) {
        ManaTabletItem manaTablet = (ManaTabletItem) stack.getItem();
        ManaTabletType type = manaTablet.getType();
        ManaTabletSize size = manaTablet.getSize();

        CompoundNBT nbt = manaTablet.constructNBT(stack);

        int current = nbt.getInt("mana");
        int max = calculateMaxStoredMana(type, size);

        String text = I18n.format(current + "/" + max);
        ITextComponent info = new StringTextComponent(text);
        components.add(info);
    }

    public void chargeMana(ItemStack manaStack, int manaInput) { changeMana(manaStack, manaInput); }

    private int calculateMaxStoredMana(ManaTabletType type, ManaTabletSize size) {
        int t = type.getTier();
        int s = type.getTier();

        double mult = 0.5D * t;
        int baseMana = ((1500 * s) + (750 / t) + 3);

        return (int)Math.floor(mult * baseMana);
    }

    public ManaTabletType getType() { return this.type; }
    public ManaTabletSize getSize() { return this.size; }

    public enum ManaTabletType {
        CRUDE(1),
        SIMPLE(2),
        NORMAL(3),
        REINFORCED(4);

        private int tier;

        ManaTabletType(int tier) {
            this.tier = tier;
        }

        public int getTier() { return this.tier; }

        public static ManaTabletType getTypeFromTier(int tier) {
            switch(tier) {
                case 1:
                    return CRUDE;
                case 2:
                    return SIMPLE;
                case 3:
                    return NORMAL;
                case 4:
                    return REINFORCED;
            }

            return CRUDE;
        }
    }

    public enum ManaTabletSize {
        TINY(1),
        SMALL(2),
        NORMAL(3),
        LARGE(4);

        private int tier;

        ManaTabletSize(int tier) {
            this.tier = tier;
        }

        public int getTier() { return this.tier; }

        public static ManaTabletSize getTypeFromTier(int tier) {
            switch(tier) {
                case 1:
                    return TINY;
                case 2:
                    return SMALL;
                case 3:
                    return NORMAL;
                case 4:
                    return LARGE;
            }

            return TINY;
        }
    }
}