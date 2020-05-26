package com.builderboy.elementis.tileentity;

import com.builderboy.elementis.mana.network.IManaNodeProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class ManaProducerNodeTileEntity extends TileEntity implements IManaNodeProvider {
    public ManaProducerNodeTileEntity(TileEntityType<?> type) {
        super(type);
    }

    @Override
    public int onUpdate(int mana) { return 0; }
}
