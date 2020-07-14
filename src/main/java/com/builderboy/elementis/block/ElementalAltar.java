package com.builderboy.elementis.block;

import com.builderboy.elementis.utils.ShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class ElementalAltar extends BaseBlock {

    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    private static final VoxelShape[] SHAPES = createVoxelShape();

    public ElementalAltar() {
        super(Block.Properties.create(Material.WOOD).hardnessAndResistance(3.5f, 3.5f).harvestTool(ToolType.AXE));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray) {
        if (player.getHeldItemMainhand() != ItemStack.EMPTY) {
            ItemStack held = player.getHeldItemMainhand();

            //TODO: add the conversion to elemental worktable using a staff (any)
        }

        //TODO: add the elemental altar container and gui functionality

        return ActionResultType.PASS;
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

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH ? SHAPES[0] : SHAPES[1];
    }

    private static VoxelShape[] createVoxelShape() {
        VoxelShape[] shapes = new VoxelShape[2];

        shapes[0] = createVoxels(Direction.NORTH);
        shapes[1] = createVoxels(Direction.EAST);

        return shapes;
    }

    public static VoxelShape createVoxels(Direction direction) {
        VoxelShape shape;

        //Legs
        shape = VoxelShapes.combineAndSimplify(
                ShapeHelper.makeCuboidShape(1D, 0D, 1D, 4D, 12D, 5D, direction),
                ShapeHelper.makeCuboidShape(12D, 0D, 1D, 15D, 12D, 5D, direction),
                IBooleanFunction.OR
        );

        shape = VoxelShapes.combineAndSimplify(
                shape,
                ShapeHelper.makeCuboidShape(1D, 0D, 11D, 4D, 12D, 15D, direction),
                IBooleanFunction.OR
        );

        shape = VoxelShapes.combineAndSimplify(
                shape,
                ShapeHelper.makeCuboidShape(12D, 0D, 11D, 15D, 12D, 15D, direction),
                IBooleanFunction.OR
        );

        //Beams
        shape = VoxelShapes.combineAndSimplify(
                shape,
                ShapeHelper.makeCuboidShape(3.5D, 7D, 2.25D, 12.5D, 9D, 3.75D, direction),
                IBooleanFunction.OR
        );

        shape = VoxelShapes.combineAndSimplify(
                shape,
                ShapeHelper.makeCuboidShape(3.5D, 7D, 12.25D, 12.5D, 9D, 13.75D, direction),
                IBooleanFunction.OR
        );

        //Table
        shape = VoxelShapes.combineAndSimplify(
                shape,
                ShapeHelper.makeCuboidShape(1D, 12D, 1D, 15D, 16D, 15D, direction),
                IBooleanFunction.OR
        );

        return shape;
    }
}