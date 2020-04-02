package com.builderboy.elementis.capability;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemInventoryCapability implements ICapabilityProvider {
    private ItemStackHandler handler;
    private final LazyOptional<IItemHandler> holder = LazyOptional.of(() -> this.handler);

    public ItemInventoryCapability(int invSize) {
        this.handler = new ItemStackHandler(invSize);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, holder);
    }
}
