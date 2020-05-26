package com.builderboy.elementis.item;

import com.builderboy.elementis.utils.IItemInteractible;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
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
            return clientRightClick(world, player, held, player.isSneaking());
        }

        return serverRightClick(world, player, held, player.isSneaking());
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        ItemStack held = context.getItem();
        BlockPos pos = context.getPos();
        Block block = world.getBlockState(pos).getBlock();

        if (world.isRemote) {
            return clientBlockClick(world, player, held, pos, block, player.isSneaking());
        }

        return serverBlockClick(world, player, held, pos, block, player.isSneaking());
    }
}