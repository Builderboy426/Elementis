package com.builderboy.elementis.registry;

import com.builderboy.elementis.Elementis;
import com.builderboy.elementis.block.BaseBlock;
import com.builderboy.elementis.block.ElementalAltar;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Elementis.MODID);

    public static final RegistryObject<Block> TEST_BLOCK = registerBlock("test_block", new BaseBlock());

    public static final RegistryObject<Block> ELEMENTAL_ALTAR = registerBlock("elemental_altar", new ElementalAltar());

    private static <B extends Block> RegistryObject<Block> registerBlock(String name, B block) {
        RegistryObject<Block> reg = BLOCKS.register(name, () -> block);
        ModItemRegistry.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().group(Elementis.GROUP)));
        return reg;
    }
}