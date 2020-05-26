package com.builderboy.elementis.item;

import com.builderboy.elementis.Elementis;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ManikLinkerItem extends InteractibleItem {
    public ManikLinkerItem() {
        super(Elementis.GROUP);
    }

    @Override
    public ActionResultType serverBlockClick(World world, PlayerEntity player, ItemStack held, BlockPos pos, Block blockClicked, boolean isSneaking) {
        
        return ActionResultType.PASS;
    }
}