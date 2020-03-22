package com.builderboy.elementis.registries;

import com.builderboy.elementis.Elementis;
import com.builderboy.elementis.client.elementalaltar.ElementalAltarContainer;
import com.builderboy.elementis.client.elementalaltar.ElementalAltarScreen;
import com.builderboy.elementis.client.spellparcel.SpellParcelContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerRegistry {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, Elementis.MODID);

    public static final RegistryObject<ContainerType<ElementalAltarContainer>> ELEMENTAL_ALTAR = CONTAINERS.register("elemental_altar", () -> new ContainerType<>(ElementalAltarContainer::new));
    public static final RegistryObject<ContainerType<SpellParcelContainer>> SPELL_PARCEL = CONTAINERS.register("spell_parcel", () -> new ContainerType<>(SpellParcelContainer::new));

    @OnlyIn(Dist.CLIENT)
    public static void registerScreen() {
        Elementis.LOGGER.info("Registering Screens!");
        ScreenManager.registerFactory(ELEMENTAL_ALTAR.get(), ElementalAltarScreen::new);
    }
}