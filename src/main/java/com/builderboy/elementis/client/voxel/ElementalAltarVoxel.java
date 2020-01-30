package com.builderboy.elementis.client.voxel;

import com.builderboy.elementis.utils.render.ShapeHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class ElementalAltarVoxel {

    public static VoxelShape createVoxel(Direction direction) {
        VoxelShape shape;

        //Feet
        VoxelShape foot1 = ShapeHelper.makeCuboidShape(1D, 0D, 1D, 4D, 1D, 5D, direction);
        VoxelShape foot2 = ShapeHelper.makeCuboidShape(12D, 0D, 1D, 15D, 1D, 5D, direction);
        VoxelShape foot3 = ShapeHelper.makeCuboidShape(1D, 0D, 11D, 4D, 1D, 15D, direction);
        VoxelShape foot4 = ShapeHelper.makeCuboidShape(12D, 0D, 11D, 15D, 1D, 15D, direction);

        shape = VoxelShapes.combineAndSimplify(foot1, foot2, IBooleanFunction.OR);
        shape = VoxelShapes.combineAndSimplify(shape, foot3, IBooleanFunction.OR);
        shape = VoxelShapes.combineAndSimplify(shape, foot4, IBooleanFunction.OR);

        //Legs
        VoxelShape leg1 = ShapeHelper.makeCuboidShape(1.5D, 1D, 1.5D, 3.5D, 9D, 4.5D, direction);
        VoxelShape leg2 = ShapeHelper.makeCuboidShape(12.5D, 1D, 1.5D, 14.5D, 9D, 4.5D, direction);
        VoxelShape leg3 = ShapeHelper.makeCuboidShape(1.5D, 1D, 11.5D, 3.5D, 9D, 14.5D, direction);
        VoxelShape leg4 = ShapeHelper.makeCuboidShape(12.5D, 1D, 11.5D, 14.5D, 9D, 14.5D, direction);

        shape = VoxelShapes.combineAndSimplify(shape, leg1, IBooleanFunction.OR);
        shape = VoxelShapes.combineAndSimplify(shape, leg2, IBooleanFunction.OR);
        shape = VoxelShapes.combineAndSimplify(shape, leg3, IBooleanFunction.OR);
        shape = VoxelShapes.combineAndSimplify(shape, leg4, IBooleanFunction.OR);

        //Beams
        VoxelShape beam1 = ShapeHelper.makeCuboidShape(3.5D, 4.5D, 2.25D, 12.5D, 6D, 3.75D, direction);
        VoxelShape beam2 = ShapeHelper.makeCuboidShape(3.5D, 4.5D, 12.25D, 12.5D, 6D, 13.75D, direction);

        shape = VoxelShapes.combineAndSimplify(shape, beam1, IBooleanFunction.OR);
        shape = VoxelShapes.combineAndSimplify(shape, beam2, IBooleanFunction.OR);

        //Table
        VoxelShape table = ShapeHelper.makeCuboidShape(1D, 9D, 1D, 15D, 13D, 15D, direction);

        shape = VoxelShapes.combineAndSimplify(shape, table, IBooleanFunction.OR);

        return shape;
    }

    public static VoxelShape addInk(VoxelShape shape, Direction direction) {
        VoxelShape newShape;

        VoxelShape inkBase = ShapeHelper.makeCuboidShape(1.5D, 13D, 12D, 4D, 14.5D, 14.5D, direction);
        VoxelShape inkTop = ShapeHelper.makeCuboidShape(2D, 14.5D, 12.5D, 3.5D, 15.25D, 14D, direction);

        newShape = VoxelShapes.combineAndSimplify(shape, inkBase, IBooleanFunction.OR);
        newShape = VoxelShapes.combineAndSimplify(newShape, inkTop, IBooleanFunction.OR);

        return newShape;
    }
}