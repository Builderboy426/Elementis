package com.builderboy.elementis.registries;

import com.builderboy.elementis.Elementis;
import com.google.common.collect.Sets;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityRegistry {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Elementis.MODID);

    //public static final RegistryObject<TileEntityType<ElementalAltarTileEntity>> ELEMENTAL_ALTAR = TILE_ENTITIES.register("elemental_altar", () -> new TileEntityType<>(ElementalAltarTileEntity::new, Sets.newHashSet(ModBlockRegistry.ELEMENTAL_ALTAR.get()), null));
}