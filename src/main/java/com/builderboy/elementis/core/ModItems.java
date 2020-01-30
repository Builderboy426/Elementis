package com.builderboy.elementis.core;

import com.builderboy.elementis.Elementis;
import com.builderboy.elementis.common.item.StaffItem;
import com.builderboy.elementis.common.item.StaffItem.StaffType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModItems {

    public static Item TEST;

    //Catalyst
    public static Item INFUSION_CATALYST;

    //Tier 1 Materials
    public static Item ELEMENTIK_CRYTSTAL;
    public static Item ELEMENTIK_SHARD;
    public static Item ELEMENTIK_DUST;

    //Tier 2 Materials
    public static Item ALDENIK_CRYSTAL;
    public static Item ALDENIK_SHARD;
    public static Item ALDENIK_DUST;

    public static Item PYRON_RUNE;
    public static Item GEON_RUNE;

    //Tier 3 Materials
    public static Item MALDINIK_CRYSTAL;
    public static Item MALDINIK_SHARD;
    public static Item MALDINIK_DUST;

    public static Item CRYON_RUNE;
    public static Item AERON_RUNE;

    //Tools
    public static Item GRIND_STONE;
    public static Item CRYSTAL_KNIFE;
    public static Item INK_BOTTLE;
    public static Item QUILL;

    //Staffs
    public static Item ELEMENTIK_STAFF;
    public static Item ALDENIK_STAFF;
    public static Item MALDINIK_STAFF;

    static final Map<String, BlockItem> BLOCKS_TO_REGISTER = new LinkedHashMap<>();

    public static void registerAll(RegistryEvent.Register<Item> event) {
        if (!event.getName().equals(ForgeRegistries.ITEMS.getRegistryName())) { return; }
        BLOCKS_TO_REGISTER.forEach(ModItems::register);

        TEST = register("test", new Item(new Item.Properties().group(Elementis.ELEMENTIS)));

        ELEMENTIK_CRYTSTAL = register("elementik_crystal", new Item(new Item.Properties().group(Elementis.ELEMENTIS).maxStackSize(16)));
        ELEMENTIK_SHARD = register("elementik_shard", new Item(new Item.Properties().group(Elementis.ELEMENTIS).maxStackSize(32)));
        ELEMENTIK_DUST = register("elementik_dust", new Item(new Item.Properties().group(Elementis.ELEMENTIS).maxStackSize(32)));
        ELEMENTIK_STAFF = register("elementik_staff", new StaffItem(StaffType.ELEMENTIK));

        ALDENIK_CRYSTAL = register("aldenik_crystal", new Item(new Item.Properties().group(Elementis.ELEMENTIS).maxStackSize(16)));
        ALDENIK_SHARD = register("aldenik_shard", new Item(new Item.Properties().group(Elementis.ELEMENTIS).maxStackSize(32)));
        ALDENIK_DUST = register("aldenik_dust", new Item(new Item.Properties().group(Elementis.ELEMENTIS).maxStackSize(32)));
        ALDENIK_STAFF = register("aldenik_staff", new StaffItem(StaffType.ALDENIK));

        MALDINIK_CRYSTAL = register("maldinik_crystal", new Item(new Item.Properties().group(Elementis.ELEMENTIS).maxStackSize(16)));
        MALDINIK_SHARD = register("maldinik_shard", new Item(new Item.Properties().group(Elementis.ELEMENTIS).maxStackSize(32)));
        MALDINIK_DUST = register("maldinik_dust", new Item(new Item.Properties().group(Elementis.ELEMENTIS).maxStackSize(32)));
        MALDINIK_STAFF = register("maldinik_staff", new StaffItem(StaffType.MALDINIK));
    }

    public static <I extends Item> I register(String name, I item) {
        item.setRegistryName(Elementis.getLocation(name));
        ForgeRegistries.ITEMS.register(item);
        return item;
    }
}