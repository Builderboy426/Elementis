package com.builderboy.elementis.core.world.ore;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ModOreFeatureConfig implements IFeatureConfig {

    public final BlockState state;
    public final int size;
    public final int regionSize;
    public final Predicate<BlockState> target;

    public ModOreFeatureConfig(BlockState state, int size, int regionSize, Predicate<BlockState> target) {
        this.state = state;
        this.size = size;
        this.regionSize = regionSize;
        this.target = target;
    }

    @Override
    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(
                ops.createString("state"), BlockState.serialize(ops, state).getValue(),
                ops.createString("size"), ops.createInt(size),
                ops.createString("regionSize"), ops.createInt(regionSize)
        )));
    }

    public static ModOreFeatureConfig deserialize(Dynamic<?> dynamic) {
        BlockState state = dynamic.get("state").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
        int size = dynamic.get("size").asInt(0);
        int regionSize = dynamic.get("regionSize").asInt(0);
        return new ModOreFeatureConfig(state, size, regionSize, s -> s != null && s.getBlock() == Blocks.STONE);
    }
}