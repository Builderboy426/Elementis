package com.builderboy.elementis;

import com.builderboy.elementis.item.CrystalItem;
import com.builderboy.elementis.item.CrystalItem.CrystalType;
import com.builderboy.elementis.registry.ModItemRegistry;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;

@Mod.EventBusSubscriber
public class ElementisEvents {
    public static final ElementisEvents INSTANCE = new ElementisEvents();

    public void addEventListeners(IEventBus bus) {
        bus.addListener(this::setupClient);
        bus.addListener(this::setupCommon);
        bus.addListener(this::interModEvent);
    }

    public void interModEvent(InterModProcessEvent event) {

    }

    public void setupClient(FMLClientSetupEvent event) {
        //ModContainerRegistry.registerScreen();
    }

    public void setupCommon(FMLCommonSetupEvent event) {
        //ModWorldFeatures.addFeatures();
    }


}