package com.builderboy.elementis;

import com.builderboy.elementis.registry.ModItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

@Mod(Elementis.MODID)
public class Elementis {
    public static final String MODID = "elementis";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final ItemGroup GROUP = new ItemGroup(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItemRegistry.ELEMENTIK_CRYSTAL.get());
        }
    };

    public Elementis() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        //Adds all the events to the forge event listener
        ElementisEvents.INSTANCE.addEventListeners(eventBus);

        //Register Items and Blocks
        ModItemRegistry.ITEMS.register(eventBus);
    }

    //Get the version of the mod
    public static String getVersion() {
        Optional<? extends ModContainer> o = ModList.get().getModContainerById(MODID);
        if (o.isPresent()) {
            return o.get().getModInfo().getVersion().toString();
        }
        return "NONE";
    }

    //Is the mod a development version
    public static boolean isDevBuild() {
        return !getVersion().equals("NONE");
    }

    //Get the mod's resource location
    public static ResourceLocation getLocation(String path) {
        return new ResourceLocation(MODID, path);
    }
}