package com.builderboy.elementis.mana.network;

import com.builderboy.elementis.tileentity.ManaConsumerNodeTileEntity;
import com.builderboy.elementis.tileentity.ManaCoreTileEntity;
import com.builderboy.elementis.tileentity.ManaProducerNodeTileEntity;
import com.builderboy.elementis.tileentity.ManaStorageNodeTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.dimension.Dimension;

import java.util.ArrayList;
import java.util.List;

public class ManaNetwork {
    private Dimension dimension;

    private ManaCoreTileEntity core;
    private List<IManaNodeProvider> nodes = new ArrayList<>();

    public ManaNetwork(ManaCoreTileEntity core) {
        this.core = core;
        this.dimension = core.getWorld().getDimension();
    }

    public void assignNodeToNetwork(IManaNodeProvider node) {
        this.nodes.add(this.nodes.size(), node);
    }

    public void updateNodes() {
        List<ManaProducerNodeTileEntity> producers = this.sortProducerNodes();
        List<ManaConsumerNodeTileEntity> consumers = this.sortConsumerNodes();
        List<ManaStorageNodeTileEntity> stores = this.sortStorageNodes();

        int producedMana = calculateProducedMana(producers);
        int storedMana = calculateStoredMana(stores);
        int totalMana = producedMana + storedMana;

        //Clear the storage nodes
        this.clearStores(stores);
    }

    //Producer Nodes
    private List<ManaProducerNodeTileEntity> sortProducerNodes() {
        List<ManaProducerNodeTileEntity> sortedNodes = new ArrayList<>();

        for (IManaNodeProvider node : this.nodes) {
            if (node instanceof ManaProducerNodeTileEntity) {
                sortedNodes.add(sortedNodes.size(), (ManaProducerNodeTileEntity) node);
            }
        }

        return sortedNodes;
    }

    private int calculateProducedMana(List<ManaProducerNodeTileEntity> producers) {
        int producedMana = 0;

        for (ManaProducerNodeTileEntity producer : producers) {
            producer.onUpdate(producedMana);
        }

        return producedMana;
    }

    //Consumer Nodes
    private List<ManaConsumerNodeTileEntity> sortConsumerNodes() {
        List<ManaConsumerNodeTileEntity> sortedNodes = new ArrayList<>();

        for (IManaNodeProvider node : this.nodes) {
            if (node instanceof ManaConsumerNodeTileEntity) {
                sortedNodes.add(sortedNodes.size(), (ManaConsumerNodeTileEntity) node);
            }
        }

        return sortedNodes;
    }

    //Storage Nodes
    private List<ManaStorageNodeTileEntity> sortStorageNodes() {
        List<ManaStorageNodeTileEntity> sortedNodes = new ArrayList<>();

        for (IManaNodeProvider node : this.nodes) {
            if (node instanceof ManaStorageNodeTileEntity) {
                sortedNodes.add(sortedNodes.size(), (ManaStorageNodeTileEntity) node);
            }
        }

        return sortedNodes;
    }

    private int calculateStoredMana(List<ManaStorageNodeTileEntity> stores) {
        int mana = 0;

        for (ManaStorageNodeTileEntity store : stores) {
            mana += store.getStoredMana();
        }

        return mana;
    }

    private void clearStores(List<ManaStorageNodeTileEntity> stores) {
        for (ManaStorageNodeTileEntity store : stores) {
            store.clearStore();
        }

        core.clearStore();
    }

    public Dimension getDimension() { return this.dimension; }

    public ManaCoreTileEntity getCore() { return this.core; }
}