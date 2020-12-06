package com.builderboy.elementis.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class ModOreBlock extends Block {
    public ModOreBlock(int mLvl) {
        this(mLvl, ToolType.PICKAXE);
    }

    public ModOreBlock(int mLvl, ToolType tool) {
        super(Block.Properties.create(Material.ROCK).harvestTool(tool).hardnessAndResistance(1.5F, 6.0F).harvestLevel(mLvl));
    }
}