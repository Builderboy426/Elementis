package com.builderboy.elementis.common.block;


import com.builderboy.elementis.client.voxel.ElementalAltarVoxel;
import com.builderboy.elementis.common.block.tileentity.ElementalAltarTileEntity;
import com.builderboy.elementis.core.ModStats;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class ElementalAltarBlock extends ContainerBlock {

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

            shapes[i] = ElementalAltarVoxel.createVoxel(face);
        }

        return shapes;
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isRemote) {
            INamedContainerProvider provider = this.getContainer(state, world, pos);
            if (provider != null) {
                player.openContainer(provider);
                player.addStat(ModStats.INTERACT_WITH_ALTAR);
            }
        }

        return true;
    }

    @Override
    public boolean hasTileEntity(BlockState state) { return true; }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) { return new ElementalAltarTileEntity(); }
}