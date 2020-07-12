package com.builderboy.elementis.registry;

import com.builderboy.elementis.Elementis;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Elementis.MODID);

    public static final RegistryObject<Block> TEST_BLOCK = registerBlock("test_block", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5f, 3.5f)));

    private static <B extends Block> RegistryObject<Block> registerBlock(String name, B block) {
        RegistryObject<Block> reg = BLOCKS.register(name, () -> block);
        ModItemRegistry.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().group(Elementis.GROUP)));
        return reg;
    }
}