package com.builderboy.elementis.registry;

import com.builderboy.elementis.Elementis;
import com.builderboy.elementis.item.CrystalItem;
import com.builderboy.elementis.item.CrystalItem.CrystalType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Elementis.MODID);

    //Crystals
    public static final RegistryObject<Item> ELEMENTIK_CRYSTAL = ITEMS.register("elementik_crystal", () -> new CrystalItem(CrystalType.ELEMENTIK));
    public static final RegistryObject<Item> ALDENIK_CRYSTAL = ITEMS.register("aldenik_crystal", () -> new CrystalItem(CrystalType.ALDENIK));
    public static final RegistryObject<Item> MALDINIK_CRYSTAL = ITEMS.register("maldinik_crystal", () -> new CrystalItem(CrystalType.MALDINIK));
    public static final RegistryObject<Item> ZANIK_CRYSTAL = ITEMS.register("zanik_crystal", () -> new CrystalItem(CrystalType.ZANIK));
    public static final RegistryObject<Item> PALDINIK_CRYSTAL = ITEMS.register("paldinik_crystal", () -> new CrystalItem(CrystalType.PALDINIK));
    public static final RegistryObject<Item> TALENTIK_CRYSTAL = ITEMS.register("talentik_crystal", () -> new CrystalItem(CrystalType.TALENTIK));

    public static final RegistryObject<Item> KALTHENIK_CRYSTAL = ITEMS.register("kalthenik_crystal", () -> new CrystalItem(CrystalType.KALTHENIK));
    public static final RegistryObject<Item> MANIK_CRYSTAL = ITEMS.register("manik_crystal", () -> new CrystalItem(CrystalType.MANIK));
}