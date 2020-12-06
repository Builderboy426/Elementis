package com.builderboy.elementis.registry;

import com.builderboy.elementis.Elementis;
import com.builderboy.elementis.block.ModOreBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Elementis.MODID);

    //public static final RegistryObject<Block> MARBLE = registerBlock("marble", new ModOreBlock(0));
    //public static final RegistryObject<Block> SLATE = registerBlock("slate", new ModOreBlock(0));

    public static final RegistryObject<Block> ELEMENTIK_ORE = registerBlock("elementik_ore", new ModOreBlock(1));

    //public static final RegistryObject<Block> ELEMENTAL_ALTAR = registerBlock("elemental_altar", new ElementalAltarBlock());

    private static <B extends Block> RegistryObject<Block> registerBlock(String name, B block) {
        RegistryObject<Block> reg = BLOCKS.register(name, () -> block);
        registerBlockItem(name, block);
        return reg;
    }

    private static <B extends Block> void registerBlockItem(String name, B block) {
        ModItemRegistry.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().group(Elementis.GROUP)));
    }
}