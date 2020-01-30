package com.builderboy.elementis;

import com.builderboy.elementis.core.ModBlocks;
import com.builderboy.elementis.core.ModItems;
import com.builderboy.elementis.core.world.ModWorldFeatures;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class SideProxy {

    public SideProxy() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::commonSetup);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModBlocks::registerAll);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModItems::registerAll);

        //FMLJavaModLoadingContext.get().getModEventBus().addListener(ModTileEntities::registerAll);

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