package com.builderboy.elementis.core.world;

import com.builderboy.elementis.core.ModBlocks;
import com.builderboy.elementis.core.world.ore.ModOreFeature;
import com.builderboy.elementis.core.world.ore.ModOreFeatureConfig;
import com.google.common.base.Predicate;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class ModWorldFeatures {

    private static final CountRangeConfig RANGE_FULL = new CountRangeConfig(20, 0, 0, 256);

    private static final Predicate<BlockState> TARGET_NETHER = s -> s.getBlock() == Blocks.NETHERRACK;
    private static final Predicate<BlockState> TARGET_END = s -> s.getBlock() == Blocks.END_STONE;
    private static final Predicate<BlockState> TARGET_OVERWORLD = s -> s.getBlock() == Blocks.STONE;

    public static void addFeatures() {
        ForgeRegistries.BIOMES.forEach(biome -> {
            if (isBiome(biome, Biome.Category.NETHER)) {

            } else if (isBiome(biome, Biome.Category.THEEND)) {

            } else {

                addOre(biome, ModBlocks.ELEMENTIK_ORE.getDefaultState(), 10, 20, TARGET_OVERWORLD, new CountRangeConfig(6, 0, 0, 40));

            }
        });
    }

    private static boolean isBiome(Biome biome, Biome.Category category) { return biome.getCategory() == category; }

    private static void addOre(Biome biome, BlockState state, int size, int regionSize, Predicate<BlockState> target, CountRangeConfig range) {
        addOre(biome, new ModOreFeatureConfig(state, size, regionSize, target), range);
    }

    private static void addOre(Biome biome, ModOreFeatureConfig config, CountRangeConfig rangeConfig) {
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(
                ModOreFeature.INSTATNCE,
                config,
                Placement.COUNT_RANGE,
                rangeConfig
        ));
    }
}