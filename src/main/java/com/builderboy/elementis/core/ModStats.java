package com.builderboy.elementis.core;

import com.builderboy.elementis.Elementis;
import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ModStats {

    public static ResourceLocation INTERACT_WITH_ALTAR;

    public static void registerAll(RegistryEvent.Register<StatType<?>> event) {
        if (!event.getName().equals(ForgeRegistries.STAT_TYPES.getRegistryName())) { return; }

        INTERACT_WITH_ALTAR = register("interact_with_grill", IStatFormatter.DEFAULT);
    }

    private static ResourceLocation register(String name, IStatFormatter formatter) {
        ResourceLocation id = Elementis.getLocation(name);
        Registry.register(Registry.CUSTOM_STAT, name, id);
        Stats.CUSTOM.get(id, formatter);
        return id;
    }
}