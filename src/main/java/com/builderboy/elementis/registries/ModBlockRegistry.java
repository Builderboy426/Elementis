package com.builderboy.elementis.registries;

import com.builderboy.elementis.block.ModOreBlock;
import com.builderboy.elementis.Elementis;
import com.builderboy.elementis.block.ElementalAltarBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Elementis.MODID);

    public static final RegistryObject<Block> TEST_BLOCK = registerBlock("test_block", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(4.0f, 4.0f).harvestTool(ToolType.PICKAXE)));

    public static final RegistryObject<Block> ELEMENTIK_ORE = registerBlock("elementik_ore", new ModOreBlock(0));
    public static final RegistryObject<Block> MANIK_ORE = registerBlock("manik_ore", new ModOreBlock(0));

    public static final RegistryObject<Block> ELEMENTAL_ALTAR = registerBlock("elemental_altar", new ElementalAltarBlock());

    private static <B extends Block> RegistryObject<Block> registerBlock(String name, B block) {
        RegistryObject<Block> reg = BLOCKS.register(name, () -> block);
        registerBlockItem(name, block);
        return reg;
    }

    private static <B extends Block> void registerBlockItem(String name, B block) {
        ModItemRegistry.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().group(Elementis.GROUP)));
    }
}