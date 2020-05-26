package com.builderboy.elementis.tileentity;

import com.builderboy.elementis.mana.network.IManaNodeProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class ManaConsumerNodeTileEntity extends TileEntity implements IManaNodeProvider {
    public ManaConsumerNodeTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public int onUpdate(int mana) {
        return 0;
    }
}
