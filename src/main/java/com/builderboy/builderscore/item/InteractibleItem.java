package com.builderboy.builderscore.item;

import com.builderboy.builderscore.utils.IItemInteractible;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class InteractibleItem extends Item implements IItemInteractible {
    public InteractibleItem(ItemGroup group) {
        super(new Item.Properties().group(group).maxStackSize(1));
    }

    public InteractibleItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack held = player.getHeldItemMainhand();

        if (world.isRemote) {
            return clientRightClick(world, player, held, player.isShiftKeyDown());
        }

        return serverRightClick(world, player, held, player.isShiftKeyDown());
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        ItemStack held = context.getItem();
        Block block = world.getBlockState(context.getPos()).getBlock();

        if (world.isRemote) {
            return clientBlockClick(world, player, held, block, player.isShiftKeyDown());
        }

        return serverBlockClick(world, player, held, block, player.isShiftKeyDown());
    }
}