package com.builderboy.elementis.utils.item;

import com.builderboy.elementis.Elementis;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class InteractiveItem extends Item implements IInteractiveItem {
    public InteractiveItem() { super(new Properties().group(Elementis.ELEMENTIS).maxStackSize(1)); }

    public InteractiveItem(Item.Properties properties) { super(properties); }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack held = player.getHeldItemMainhand();

        if (!world.isRemote) { return clientRightClick(world, player, held, player.isSneaking()); }

        return serverRightClick(world, player, held, player.isSneaking());
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();

        if (!world.isRemote) { serverBlockClick(world, player, context.getPos(), context.getItem(), player.isSneaking()); }

        return serverBlockClick(world, player, context.getPos(), context.getItem(), player.isSneaking());
    }
}
