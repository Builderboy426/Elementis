package com.builderboy.elementis.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class ModOreBlock extends Block {

    public ModOreBlock(int mLvl) {
        this(mLvl, ToolType.PICKAXE);
    }

    public ModOreBlock(int mLvl, ToolType tool) {
        super(Block.Properties.create(Material.ROCK).harvestTool(tool).hardnessAndResistance((mLvl * 1.5f) + 2.5f, (mLvl * 2) + 3.5f).harvestLevel(mLvl));
    }
}