package com.builderboy.elementis.utils;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;

public interface IItemInteractible {

    default ActionResult<ItemStack> clientRightClick(World world, PlayerEntity player, ItemStack held, boolean isSneaking) { return new ActionResult<>(ActionResultType.PASS, held); }
    default ActionResult<ItemStack> serverRightClick(World world, PlayerEntity player, ItemStack held, boolean isSneaking) { return new ActionResult<>(ActionResultType.PASS, held); }

    default ActionResultType clientBlockClick(World world, PlayerEntity player, ItemStack held, Block blockClicked, boolean isSneaking) { return ActionResultType.PASS; }
    default ActionResultType serverBlockClick(World world, PlayerEntity player, ItemStack held, Block blockClicked, boolean isSneaking) { return ActionResultType.PASS; }
}