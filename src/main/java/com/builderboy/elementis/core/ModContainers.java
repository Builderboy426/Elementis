package com.builderboy.elementis.core;

import com.builderboy.elementis.Elementis;
import com.builderboy.elementis.client.screen.ElementalAltarScreen;
import com.builderboy.elementis.client.container.ElementalAltarContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, Elementis.MOD_ID);

    public static final RegistryObject<ContainerType<ElementalAltarContainer>> ELEMENTAL_ALTAR = CONTAINERS.register("elemental_altar", () -> new ContainerType<>(ElementalAltarContainer::new));

    @OnlyIn(Dist.CLIENT)
    public static void registerScreens() {
        ScreenManager.registerFactory(ELEMENTAL_ALTAR.get(), ElementalAltarScreen::new);
    }
}