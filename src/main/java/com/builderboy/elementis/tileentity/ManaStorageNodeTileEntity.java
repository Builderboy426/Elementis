package com.builderboy.elementis.tileentity;

import com.builderboy.elementis.mana.network.IManaNodeProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class ManaStorageNodeTileEntity extends TileEntity implements IManaNodeProvider {
    private int storedMana = 0;
    private int maxMana;

    public ManaStorageNodeTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public int onUpdate(int mana) {
        return 0;
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {

        return super.write(nbt);
    }

    @Override
    public void read(CompoundNBT nbt) {

        super.read(nbt);
    }

    public void clearStore() { this.storedMana = 0; }

    public int getStoredMana() { return this.storedMana; }
}