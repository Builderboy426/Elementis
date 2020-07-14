package com.builderboy.elementis.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BaseBlock extends Block {

    public BaseBlock() {
        super(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5f, 3.5f));
    }

    public BaseBlock(Properties properties) {
        super(properties);
    }
}