package com.builderboy.elementis;

import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ElementisEvents {
    public static final ElementisEvents INSTANCE = new ElementisEvents();
    private static final List<Consumer<? extends Event>> EVENTS = new ArrayList<>();

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