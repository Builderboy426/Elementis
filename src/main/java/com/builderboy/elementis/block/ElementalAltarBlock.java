package com.builderboy.elementis.block;

import com.builderboy.elementis.Elementis;
import com.builderboy.elementis.client.ElementalAltarContainer;
import com.builderboy.elementis.client.ElementalAltarShape;
import com.builderboy.elementis.registries.ModStatsRegistry;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class ElementalAltarBlock extends Block {

    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    private static final VoxelShape[] SHAPES = createVoxelShape();

    public ElementalAltarBlock() { super(Block.Properties.create(Material.WOOD).hardnessAndResistance(4.5f, 4.0f).harvestTool(ToolType.AXE)); }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        Direction face = state.get(FACING);
        return getAltarVoxel(face);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    private VoxelShape getAltarVoxel(Direction facing) {
        return SHAPES[facing.getHorizontalIndex()];
    }

    private static VoxelShape[] createVoxelShape() {
        VoxelShape[] shapes = new VoxelShape[4];

        for (int i = 0; i < shapes.length; i++) {
            int direction = i % 4;
            Direction face = Direction.byHorizontalIndex(direction).getOpposite();

            shapes[i] = ElementalAltarShape.createVoxel(face);
        }

        return shapes;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isRemote) {
            INamedContainerProvider provider = this.getContainer(state, world, pos);
            if (provider != null) {
                player.openContainer(provider);
                player.addStat(ModStatsRegistry.INTERACT_WITH_ALTAR);
            }
        }

        return ActionResultType.SUCCESS;
    }

    @Nullable
    @Override
    public INamedContainerProvider getContainer(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedContainerProvider((id, playerInventory, worldCallable) -> {
            return new ElementalAltarContainer(id, playerInventory);
        }, new TranslationTextComponent("container." + Elementis.MODID + ".elemental_altar"));
    }
}