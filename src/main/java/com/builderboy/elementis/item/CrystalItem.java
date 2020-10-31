package com.builderboy.elementis.item;

import com.builderboy.elementis.registry.ModItemRegistry;
import com.builderboy.elementis.utils.CrystalType;
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
}