package com.builderboy.elementis.common.block;

import com.builderboy.elementis.client.voxel.ElementalAltarVoxel;
import com.builderboy.elementis.utils.render.ShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class ElementalAltarBlock extends Block {

    private static final BooleanProperty INK = BooleanProperty.create("ink");
    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    private static final VoxelShape[] SHAPES = createVoxelShape();

    public ElementalAltarBlock() { super(Block.Properties.create(Material.WOOD).hardnessAndResistance(4.5f, 4.0f).harvestTool(ToolType.AXE)); }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        Direction face = state.get(FACING);
        boolean hasInk = state.get(INK);
        return getAltarVoxel(face, hasInk);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite()).with(INK, true);
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
        builder.add(FACING, INK);
    }

    private VoxelShape getAltarVoxel(Direction facing, boolean hasInk) {
        if (facing != Direction.UP && facing != Direction.DOWN) {
            if (hasInk) { return SHAPES[4 + facing.getHorizontalIndex()]; }
            else { return SHAPES[facing.getHorizontalIndex()]; }
        }

        return SHAPES[0];
    }

    private static VoxelShape[] createVoxelShape() {
        VoxelShape[] shapes = new VoxelShape[8];

        for (int i = 0; i < shapes.length; i++) {
            int direction = i % 4;
            Direction face = Direction.byHorizontalIndex(direction).getOpposite();

            if (i <= 4) { shapes[i] = ElementalAltarVoxel.createVoxel(face); }
            else if (i > 4) { shapes[i] = ElementalAltarVoxel.addInk(ElementalAltarVoxel.createVoxel(face), face); }
        }

        return shapes;
    }
}