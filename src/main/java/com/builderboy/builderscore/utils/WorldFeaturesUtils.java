package com.builderboy.builderscore.utils;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;

public class WorldFeaturesUtils {

    public static boolean isOverworld(Biome biome) { return biome.getCategory() != Category.NETHER && biome.getCategory() != Category.THEEND; }

    public static boolean isNether(Biome biome) { return biome.getCategory() == Category.NETHER; }

    public static boolean isEnd(Biome biome) { return biome.getCategory() == Category.THEEND; }
}