package com.builderboy.elementis.block;

import com.builderboy.elementis.tileentity.ManaCoreTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class ManaCoreBlock extends Block {

    public ManaCoreBlock() {
        super(Block.Properties.create(Material.ROCK).harvestLevel(2).hardnessAndResistance(4.5f, 4.0f).harvestTool(ToolType.PICKAXE));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ManaCoreTileEntity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray) {
        if (!world.isRemote) {
            ItemStack heldStack = player.getHeldItemMainhand();
            if (heldStack.getItem() instanceof ) {}
        }

        return ActionResultType.PASS;
    }
}