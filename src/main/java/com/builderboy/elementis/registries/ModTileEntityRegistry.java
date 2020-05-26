package com.builderboy.elementis.registries;

import com.builderboy.elementis.Elementis;
import com.builderboy.elementis.tileentity.ManaCoreTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityRegistry {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Elementis.MODID);

    public static final RegistryObject<TileEntityType<ManaCoreTileEntity>> MANA_CORE = TILE_ENTITIES.register("mana_core", () -> new TileEntityType<>(ManaCoreTileEntity::new, ModBlockRegistry.MANA_CORE, null));
}