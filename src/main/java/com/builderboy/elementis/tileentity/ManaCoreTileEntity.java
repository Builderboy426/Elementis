package com.builderboy.elementis.tileentity;

import com.builderboy.elementis.mana.network.IManaNodeProvider;
import com.builderboy.elementis.mana.network.ManaNetwork;
import com.builderboy.elementis.registries.ModTileEntityRegistry;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class ManaCoreTileEntity extends TileEntity implements IManaNodeProvider {
    private ManaNetwork network;

    private int storedMana = 0;
    private int maxMana = 5000;

    public ManaCoreTileEntity() {
        super(ModTileEntityRegistry.MANA_CORE.get());
        this.network = new ManaNetwork(this);
    }

    @Override
    public int onUpdate(int mana) { return 0; }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        return super.write(nbt);
    }

    @Override
    public void read(CompoundNBT nbt) {
        network = new ManaNetwork(this);

        super.read(nbt);
    }

    public void clearStore() { this.storedMana = 0; }

    public int getStoredMana() { return this.storedMana; }
}