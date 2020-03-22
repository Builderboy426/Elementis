package com.builderboy.builderscore.client;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityContainer<T extends TileEntity> extends Container {
    protected final T tileEntity;
    protected final World world;

    public TileEntityContainer(ContainerType<?> type, int id, World world, BlockPos pos) {
        super(type, id);
        this.tileEntity = (T) world.getTileEntity(pos);
        this.world = world;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return false;
    }
}