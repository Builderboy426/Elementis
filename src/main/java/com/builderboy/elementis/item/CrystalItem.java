package com.builderboy.elementis.item;

import com.builderboy.elementis.registry.ModItemRegistry;
import com.builderboy.elementis.utils.CrystalType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CrystalItem extends BaseItem {
    private CrystalType type;

    public CrystalItem(CrystalType type) {
        super();
        this.type = type;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        World world = entity.getEntityWorld();

        //Execute on the server
        if (!world.isRemote()) {
            //Check if the given item is a crystal
            CrystalItem crystal = stack.getItem() instanceof CrystalItem ? (CrystalItem) stack.getItem() : null;

            if (crystal != null) {
                //Convert an Elementik Crystal to a Manik Crystal if in water
                if (entity.isInWater()) {
                    if (crystal.getType() == CrystalType.ELEMENTIK) {
                        ItemStack manikCrystal = new ItemStack(ModItemRegistry.MANIK_CRYSTAL.get(), stack.getCount());
                        ItemEntity manikEntity = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), manikCrystal);

                        manikEntity.setHeadRotation(entity.rotationYaw, 0);
                        manikEntity.setDefaultPickupDelay();
                        world.addEntity(manikEntity);

                        entity.setItem(ItemStack.EMPTY);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public CrystalType getType() {
        return this.type;
    }
}