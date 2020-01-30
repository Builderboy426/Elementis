package com.builderboy.elementis.core;

import com.builderboy.elementis.Elementis;
import com.builderboy.elementis.common.block.ElementalAltarBlock;
import com.builderboy.elementis.common.block.ModOreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class ModBlocks {

    public static Block TEST_BLOCK;

    public static Block ELEMENTIK_ORE;

    public static Block ELEMENTAL_ALTAR;
    public static Block ALCHEMICAL_TRANSFUSER;

    public static void registerAll(RegistryEvent.Register<Block> event) {
        if (!event.getName().equals(ForgeRegistries.BLOCKS.getRegistryName())) { return; }

        TEST_BLOCK = register("test_block", new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));

        ELEMENTIK_ORE = register("elementik_ore", new ModOreBlock(3));

        ELEMENTAL_ALTAR = register("elemental_altar", new ElementalAltarBlock());
        //ALCHEMICAL_TRANSFUSER = register("alchemical_transfuser", new Block(Block.Properties.create(Material.WOOD).hardnessAndResistance(4.0f, 4.5f).harvestTool(ToolType.AXE)));
    }

    public static <B extends Block> B register(String name, B block) { return register(name, block, Elementis.ELEMENTIS); }

    public static <B extends Block> B register(String name, B block, ItemGroup group) {
        BlockItem item = new BlockItem(block, new Item.Properties().group(group));
        return register(name, block, item);
    }

    public static <B extends Block> B register(String name, B block, @Nullable BlockItem item) {
        block.setRegistryName(Elementis.getLocation(name));
        ForgeRegistries.BLOCKS.register(block);
        if (item != null) { ModItems.BLOCKS_TO_REGISTER.put(name, item); }
        return block;
    }
}