package com.builderboy.elementis.item;

import com.builderboy.elementis.registry.ModItemRegistry;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
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
        Item item = stack.getItem();

        //If the world is executing on server side
        if (!world.isRemote) {
            if (entity.isInWater()) {
                //Check it the item is a CrystalItem
                if (item instanceof CrystalItem) {
                    CrystalItem crystal = (CrystalItem) item;
                    CrystalType type = crystal.getType();

                    if (type == CrystalType.ELEMENTIK) {
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

    public enum CrystalType {
        ELEMENTIK,
        ALDENIK,
        MALDINIK,
        PALDINIK,
        TALENTIK,
        ZANIK,
        MANIK,
        KALTHENIK
    }
}