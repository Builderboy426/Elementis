package com.builderboy.elementis;

import com.builderboy.elementis.registries.*;
import com.builderboy.elementis.world.ModWorldFeatures;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Optional;

@Mod(Elementis.MODID)
public class Elementis {
    public static final String MODID = "elementis";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final ItemGroup GROUP = new ItemGroup(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItemRegistry.TEST_ITEM.get());
        }
    };

    public Elementis() {
        IEventBus modEventbus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventbus.addListener(this::setupClient);
        modEventbus.addListener(this::setupCommon);
        modEventbus.addListener(this::interModEvent);

        ModBlockRegistry.BLOCKS.register(modEventbus);
        ModItemRegistry.ITEMS.register(modEventbus);

        ModTileEntityRegistry.TILE_ENTITIES.register(modEventbus);
        ModContainerRegistry.CONTAINERS.register(modEventbus);

        ModRecipeRegistry.registerRecipeType();
        ModRecipeRegistry.RECIPE_SERIALIZERS.register(modEventbus);

        modEventbus.addListener(ModStatsRegistry::registerAll);
    }

    public void interModEvent(InterModProcessEvent event) {

    }

    public void setupClient(FMLClientSetupEvent event) { ModContainerRegistry.registerScreen(); }

    public void setupCommon(FMLCommonSetupEvent event) {
        ModWorldFeatures.addFeatures();
    }

    @Nonnull
    public static String getVersion() {
        Optional<? extends ModContainer> o = ModList.get().getModContainerById(MODID);
        if (o.isPresent()) {
            return o.get().getModInfo().getVersion().toString();
        }
        return "NONE";
    }

    public static boolean isDevBuild() {
        return !getVersion().equals("NONE");
    }

    public static ResourceLocation getLocation(String path) {
        return new ResourceLocation(MODID, path);
    }
}