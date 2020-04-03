package com.builderboy.elementis.world;

import com.builderboy.elementis.registries.ModBlockRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class ModWorldFeatures {

    public static void addFeatures() {
        /*
        ForgeRegistries.BIOMES.forEach(biome -> {
            if (WorldFeaturesUtils.isOverworld(biome)) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                        Feature.ORE.withConfiguration(new OreFeatureConfig()).withPlacement()
                );
            }
        });
        */

        for (Biome biome : ForgeRegistries.BIOMES) {
            if (WorldFeaturesUtils.isOverworld(biome)) {

                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlockRegistry.ELEMENTIK_ORE.get().getDefaultState(), 7)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(5, 15, 0, 45))));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlockRegistry.MANIK_ORE.get().getDefaultState(), 4)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(4, 10, 0, 30))));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlockRegistry.MARBLE.get().getDefaultState(), 25)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(4, 10, 0, 30))));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlockRegistry.SLATE.get().getDefaultState(), 25)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(4, 10, 0, 30))));
            }
        }
    }
}