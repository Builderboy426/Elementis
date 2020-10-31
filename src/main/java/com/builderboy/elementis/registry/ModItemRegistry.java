package com.builderboy.elementis.registry;

import com.builderboy.elementis.Elementis;
import com.builderboy.elementis.item.BaseItem;
import com.builderboy.elementis.item.CrystalItem;
import com.builderboy.elementis.utils.CrystalType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Elementis.MODID);

    public static final RegistryObject<Item> ELEMENTIK_CRYSTAL = ITEMS.register("elementik_crystal", () -> new CrystalItem(CrystalType.ELEMENTIK));

    public static final RegistryObject<Item> MANIK_CRYSTAL = ITEMS.register("manik_crystal", BaseItem::new);
}