package com.builderboy.elementis.registry;

import com.builderboy.elementis.Elementis;
import com.builderboy.elementis.item.BaseItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Elementis.MODID);

    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item", BaseItem::new);

    //Crystals
    public static final RegistryObject<Item> ELEMENTIK_CRYSTAL = ITEMS.register("elementik_crystal", BaseItem::new);
    public static final RegistryObject<Item> ALDENIK_CRYSTAL = ITEMS.register("aldenik_crystal", BaseItem::new);
    public static final RegistryObject<Item> MALDINIK_CRYSTAL = ITEMS.register("maldinik_crystal", BaseItem::new);
    public static final RegistryObject<Item> ZANIC_CRYSTAL = ITEMS.register("zanik_crystal", BaseItem::new);
    public static final RegistryObject<Item> PALDINIK_CRYSTAL = ITEMS.register("paldinik_crystal", BaseItem::new);
    public static final RegistryObject<Item> TALENTIK_CRYSTAL = ITEMS.register("talentik_crystal", BaseItem::new);

    public static final RegistryObject<Item> MANIK_CRYSTAL = ITEMS.register("manik_crystal", BaseItem::new);
    public static final RegistryObject<Item> KALTHENIK_CRYSTAL = ITEMS.register("kalthenik_crystal", BaseItem::new);
}