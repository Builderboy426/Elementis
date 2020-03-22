package com.builderboy.elementis.registries;

import com.builderboy.elementis.Elementis;
import com.builderboy.elementis.item.StaffItem;
import com.builderboy.elementis.item.StaffItem.StaffType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemRegistry {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Elementis.MODID);

    public static final RegistryObject<Item> TEST_ITEM = registerItem("test_item", 1);

    //Staffs
    public static final RegistryObject<Item> ELEMENTIK_STAFF = registerStaffItem("elementik_staff", StaffType.ELEMENTIK);
    public static final RegistryObject<Item> ALDENIK_STAFF = registerStaffItem("aldenik_staff", StaffType.ALDENIK);
    public static final RegistryObject<Item> MALDINIK_STAFF = registerStaffItem("maldinik_staff", StaffType.MALDINIK);
    public static final RegistryObject<Item> PALDINIK_STAFF = registerStaffItem("paldinik_staff", StaffType.PALDINIK);
    public static final RegistryObject<Item> TALENTIK_STAFF = registerStaffItem("talentik_staff", StaffType.TALENTIK);

    //Materials
    public static final RegistryObject<Item> MANIK_CRYSTAL = registerItem("manik_crystal", 16);

    public static final RegistryObject<Item> ELEMENTIK_CRYSTAL = registerItem("elementik_crystal", 16);
    public static final RegistryObject<Item> ALDENIK_CRYSTAL = registerItem("aldenik_crystal", 16);
    public static final RegistryObject<Item> MALDINIK_CRYSTAL = registerItem("maldinik_crystal", 16);
    public static final RegistryObject<Item> PALDINIK_CRYSTAL = registerItem("paldinik_crystal", 16);
    public static final RegistryObject<Item> TALENTIK_CRYSTAL = registerItem("talentik_crystal", 16);

    public static final RegistryObject<Item> ELEMENTIK_SHARD = registerItem("elementik_shard", 16);
    public static final RegistryObject<Item> ALDENIK_SHARD = registerItem("aldenik_shard", 16);
    public static final RegistryObject<Item> MALDINIK_SHARD = registerItem("maldinik_shard", 16);
    public static final RegistryObject<Item> PALDINIK_SHARD = registerItem("paldinik_shard", 16);
    public static final RegistryObject<Item> TALENTIK_SHARD = registerItem("talentik_shard", 16);

    public static final RegistryObject<Item> ELEMENTIK_DUST = registerItem("elementik_dust", 16);
    public static final RegistryObject<Item> ALDENIK_DUST = registerItem("aldenik_dust", 16);
    public static final RegistryObject<Item> MALDINIK_DUST = registerItem("maldinik_dust", 16);
    public static final RegistryObject<Item> PALDINIK_DUST = registerItem("paldinik_dust", 16);
    public static final RegistryObject<Item> TALENTIK_DUST = registerItem("talentik_dust", 16);

    public static final RegistryObject<Item> MANA_CORE = registerItem("mana_core", 32);

    //Item Registries
    private static RegistryObject<Item> registerItem(String name) {
        return registerItem(name, 1);
    }

    private static RegistryObject<Item> registerItem(String name, int size) {
        RegistryObject<Item> reg = ITEMS.register(name, () -> new Item(new Item.Properties().group(Elementis.GROUP).maxStackSize(size)));
        return reg;
    }

    private static RegistryObject<Item> registerStaffItem(String name, StaffType type) {
        RegistryObject<Item> reg = ITEMS.register(name, () -> new StaffItem(type));
        return reg;
    }
}