package com.builderboy.elementis.registries;

import com.builderboy.elementis.Elementis;
import com.builderboy.elementis.item.ManaTabletItem;
import com.builderboy.elementis.item.ManaTabletItem.ManaTabletSize;
import com.builderboy.elementis.item.ManaTabletItem.ManaTabletType;
import com.builderboy.elementis.item.StaffItem;
import com.builderboy.elementis.item.StaffItem.StaffType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemRegistry {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Elementis.MODID);

    public static final RegistryObject<Item> TEST_ITEM = registerItem("test_item", 1);

    //Tools
    public static final RegistryObject<Item> GRINDING_STONE = registerItem("grinding_stone", 1);
    public static final RegistryObject<Item> GRINDING_BOWL = registerItem("grinding_bowl", 1);

    //Staffs
    public static final RegistryObject<Item> ELEMENTIK_STAFF = registerStaffItem("elementik_staff", StaffType.ELEMENTIK);
    public static final RegistryObject<Item> ALDENIK_STAFF = registerStaffItem("aldenik_staff", StaffType.ALDENIK);
    public static final RegistryObject<Item> MALDINIK_STAFF = registerStaffItem("maldinik_staff", StaffType.MALDINIK);
    public static final RegistryObject<Item> PALDINIK_STAFF = registerStaffItem("paldinik_staff", StaffType.PALDINIK);
    public static final RegistryObject<Item> TALENTIK_STAFF = registerStaffItem("talentik_staff", StaffType.TALENTIK);

    //Materials
    public static final RegistryObject<Item> MANIK_CRYSTAL = registerItem("manik_crystal");

    public static final RegistryObject<Item> ELEMENTIK_CRYSTAL = registerItem("elementik_crystal");
    public static final RegistryObject<Item> ALDENIK_CRYSTAL = registerItem("aldenik_crystal");
    public static final RegistryObject<Item> MALDINIK_CRYSTAL = registerItem("maldinik_crystal");
    public static final RegistryObject<Item> PALDINIK_CRYSTAL = registerItem("paldinik_crystal");
    public static final RegistryObject<Item> TALENTIK_CRYSTAL = registerItem("talentik_crystal");

    public static final RegistryObject<Item> ELEMENTIK_SHARD = registerItem("elementik_shard");
    public static final RegistryObject<Item> ALDENIK_SHARD = registerItem("aldenik_shard");
    public static final RegistryObject<Item> MALDINIK_SHARD = registerItem("maldinik_shard");
    public static final RegistryObject<Item> PALDINIK_SHARD = registerItem("paldinik_shard");
    public static final RegistryObject<Item> TALENTIK_SHARD = registerItem("talentik_shard");

    public static final RegistryObject<Item> ELEMENTIK_DUST = registerItem("elementik_dust");
    public static final RegistryObject<Item> ALDENIK_DUST = registerItem("aldenik_dust");
    public static final RegistryObject<Item> MALDINIK_DUST = registerItem("maldinik_dust");
    public static final RegistryObject<Item> PALDINIK_DUST = registerItem("paldinik_dust");
    public static final RegistryObject<Item> TALENTIK_DUST = registerItem("talentik_dust");

    public static final RegistryObject<Item> CRUDE_MANA_CORE = registerItem("crude_mana_core", 1);
    public static final RegistryObject<Item> SIMPLE_MANA_CORE = registerItem("simple_mana_core", 1);
    public static final RegistryObject<Item> MANA_CORE = registerItem("mana_core", 1);
    public static final RegistryObject<Item> REINFORCED_MANA_CORE = registerItem("reinforced_mana_core", 1);

    //Mana Storage
    public static final RegistryObject<Item> TINY_CRUDE_MANA_TABLET = registerManaTablet("tiny_crude_mana_tablet", ManaTabletType.CRUDE, ManaTabletSize.TINY);
    public static final RegistryObject<Item> SMALL_CRUDE_MANA_TABLET = registerManaTablet("small_crude_mana_tablet", ManaTabletType.CRUDE, ManaTabletSize.SMALL);
    public static final RegistryObject<Item> CRUDE_MANA_TABLET = registerManaTablet("crude_mana_tablet", ManaTabletType.CRUDE, ManaTabletSize.NORMAL);
    public static final RegistryObject<Item> LARGE_CRUDE_MANA_TABLET = registerManaTablet("large_crude_mana_tablet", ManaTabletType.CRUDE, ManaTabletSize.LARGE);

    public static final RegistryObject<Item> TINY_SIMPLE_MANA_TABLET = registerManaTablet("tiny_simple_mana_tablet", ManaTabletType.SIMPLE, ManaTabletSize.TINY);
    public static final RegistryObject<Item> SMALL_SIMPLE_MANA_TABLET = registerManaTablet("small_simple_mana_tablet", ManaTabletType.SIMPLE, ManaTabletSize.SMALL);
    public static final RegistryObject<Item> SIMPLE_MANA_TABLET = registerManaTablet("simple_mana_tablet", ManaTabletType.SIMPLE, ManaTabletSize.NORMAL);
    public static final RegistryObject<Item> LARGE_SIMPLE_MANA_TABLET = registerManaTablet("large_simple_mana_tablet", ManaTabletType.SIMPLE, ManaTabletSize.LARGE);

    public static final RegistryObject<Item> TINY_MANA_TABLET = registerManaTablet("tiny_mana_tablet", ManaTabletType.NORMAL, ManaTabletSize.TINY);
    public static final RegistryObject<Item> SMALL_MANA_TABLET = registerManaTablet("small_mana_tablet", ManaTabletType.NORMAL, ManaTabletSize.SMALL);
    public static final RegistryObject<Item> MANA_TABLET = registerManaTablet("mana_tablet", ManaTabletType.NORMAL, ManaTabletSize.NORMAL);
    public static final RegistryObject<Item> LARGE_MANA_TABLET = registerManaTablet("large_mana_tablet", ManaTabletType.NORMAL, ManaTabletSize.LARGE);

    public static final RegistryObject<Item> TINY_REINFORCED_MANA_TABLET = registerManaTablet("tiny_reinforced_mana_tablet", ManaTabletType.REINFORCED, ManaTabletSize.TINY);
    public static final RegistryObject<Item> SMALL_REINFORCED_MANA_TABLET = registerManaTablet("small_reinforced_mana_tablet", ManaTabletType.REINFORCED, ManaTabletSize.SMALL);
    public static final RegistryObject<Item> REINFORCED_MANA_TABLET = registerManaTablet("reinforced_mana_tablet", ManaTabletType.REINFORCED, ManaTabletSize.NORMAL);
    public static final RegistryObject<Item> LARGE_REINFORCED_MANA_TABLET = registerManaTablet("large_reinforced_mana_tablet", ManaTabletType.REINFORCED, ManaTabletSize.LARGE);

    //Item Registries
    private static RegistryObject<Item> registerItem(String name) {
        return registerItem(name, 64);
    }

    private static RegistryObject<Item> registerItem(String name, int size) {
        RegistryObject<Item> reg = ITEMS.register(name, () -> new Item(new Item.Properties().group(Elementis.GROUP).maxStackSize(size)));
        return reg;
    }

    private static RegistryObject<Item> registerStaffItem(String name, StaffType type) {
        RegistryObject<Item> reg = ITEMS.register(name, () -> new StaffItem(type));
        return reg;
    }

    private static RegistryObject<Item> registerManaTablet(String name, ManaTabletType type, ManaTabletSize size) {
        RegistryObject<Item> reg = ITEMS.register(name, () -> new ManaTabletItem(type, size));
        return reg;
    }
}