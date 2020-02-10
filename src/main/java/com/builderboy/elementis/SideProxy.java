package com.builderboy.elementis;

import com.builderboy.elementis.core.*;
import com.builderboy.elementis.core.world.ModWorldFeatures;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class SideProxy {

    public SideProxy() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(SideProxy::commonSetup);

        modEventBus.addListener(ModBlocks::registerAll);
        modEventBus.addListener(ModItems::registerAll);

        ModTileEntities.TILE_ENTITIES.register(modEventBus);
        ModContainers.CONTAINERS.register(modEventBus);

        modEventBus.addListener(ModStats::registerAll);
        ModRecipes.registerRecipeType();
        ModRecipes.RECIPE_SERIALIZERS.register(modEventBus);

        MinecraftForge.EVENT_BUS.addListener(SideProxy::serverStarting);
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        ModWorldFeatures.addFeatures();
    }

    private static void serverStarting(FMLServerStartingEvent event) {}

    public static class Client extends SideProxy {

        Client() {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(Client::clientSetup);
        }

        private static void clientSetup(FMLClientSetupEvent event) {
            ModContainers.registerScreens();

            //Filters
            /*
            if (ModList.get().getModContainerById("filters").isPresent()) {
                //Filters.get().register(group, new ResourceLocation("machines"), new ItemStack(ModBlocks.CASING.asItem()));
                //Filters.get().register(group, new ResourceLocation("materials"), new ItemStack(ModItems.ROCK_CHUNK));
                //Filters.get().register(group, new ResourceLocation("tools"), new ItemStack(ModItems.HAMMER));
            }
             */
        }
    }

    public static class Server extends SideProxy {

        Server() { FMLJavaModLoadingContext.get().getModEventBus().addListener(Server::serverSetup); }

        private static void serverSetup(FMLDedicatedServerSetupEvent event) {}
    }
}