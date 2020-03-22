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

                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlockRegistry.ELEMENTIK_ORE.get().getDefaultState(), 7)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 8, 0, 45))));
            }
        }
    }
}