package com.builderboy.elementis.item;

import com.builderboy.elementis.Elementis;
import com.builderboy.elementis.utils.CrystalType;
import com.builderboy.elementis.utils.IManaChargeable;
import com.builderboy.elementis.utils.IManaContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ManaContainerItem extends BaseItem implements IManaContainer, IManaChargeable {
    private CrystalType crystalType;
    private int maxMana, defaultMana = 0;

    public ManaContainerItem(int maxMana, CrystalType type) {
        super(new Item.Properties().maxStackSize(1).group(Elementis.GROUP));
        this.maxMana = (int)(maxMana * (type.getManaMultiplier()));
        this.crystalType = type;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack held = player.getHeldItemMainhand();

        if (!world.isRemote) {
            ManaContainerItem mci = (ManaContainerItem)held.getItem();

            if (player.isCrouching()) {
                mci.changeMana(held, (mci.getMaxMana() / 25));

                return new ActionResult<>(ActionResultType.SUCCESS, held);
            }
        }

        return new ActionResult<>(ActionResultType.PASS, held);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) { return true; }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        ManaContainerItem mci = (ManaContainerItem)stack.getItem();

        double durability = ((double)mci.getMana(stack) / (double)mci.getMaxMana());

        return 1.0D - durability;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> components, ITooltipFlag flag) {
        ManaContainerItem mci = (ManaContainerItem) stack.getItem();

        int current = mci.getMana(stack);
        int max = mci.getMaxMana();

        String text = I18n.format(current + "/" + max);
        ITextComponent info = new StringTextComponent(text);
        components.add(info);
    }

    public int getMaxMana() { return this.maxMana; }

    public void updateMana(ItemStack stack, int mana) {
        CompoundNBT nbt = constructNBT(stack);

        if (mana < 0) {
            mana = 0;
            Elementis.LOGGER.warn("Mana input was negative!");
        } else if (mana > this.maxMana) {
            mana = this.getMaxMana();
            Elementis.LOGGER.warn("Mana exceeded the max value of its type!");
        }

        nbt.putInt("mana", mana);
        stack.setTag(nbt);
    }


    public void changeMana(ItemStack stack, int changeBy) {
        CompoundNBT nbt = constructNBT(stack);
        int mana = nbt.getInt("mana");
        this.updateMana(stack, mana + changeBy);
    }

    @Override
    public void chargeMana(ItemStack stack, int mana) {
        if (this.canCharge()) {
            this.changeMana(stack, mana);
        }
    }

    protected CompoundNBT constructNBT(ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateTag();
        if (!nbt.contains("mana")) { nbt.putInt("mana", defaultMana); }
        stack.setTag(nbt);

        return nbt;
    }
}