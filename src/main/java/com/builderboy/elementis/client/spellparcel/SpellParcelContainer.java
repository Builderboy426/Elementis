package com.builderboy.elementis.client.spellparcel;

import com.builderboy.elementis.registries.ModContainerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class SpellParcelContainer extends Container {

    public SpellParcelContainer(int id, PlayerInventory playerInv) {
        super(ModContainerRegistry.SPELL_PARCEL.get(), id);

    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}