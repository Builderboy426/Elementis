package com.builderboy.elementis.registries;

import com.builderboy.elementis.Elementis;
import com.builderboy.elementis.reicpe.ElementalAltarShapedRecipe;
import com.builderboy.elementis.reicpe.ElementalAltarShapelessRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeRegistry {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, Elementis.MODID);

    public static IRecipeType<ElementalAltarShapedRecipe> ELEMENTAL_ALTAR_SHAPED;
    public static final RegistryObject<ElementalAltarShapedRecipe.Serializer<ElementalAltarShapedRecipe>> ELEMENTAL_ALTAR_SHAPED_SERIALIZER = RECIPE_SERIALIZERS.register("elemental_altar_shaped", () -> new ElementalAltarShapedRecipe.Serializer<>(ElementalAltarShapedRecipe::new));

    public static IRecipeType<ElementalAltarShapelessRecipe> ELEMENTAL_ALTAR_SHAPELESS;
    public static final RegistryObject<ElementalAltarShapelessRecipe.Serializer<ElementalAltarShapelessRecipe>> ELEMENTAL_ALTAR_SHAPELESS_SERIALIZER = RECIPE_SERIALIZERS.register("elemental_altar_shaped", () -> new ElementalAltarShapelessRecipe.Serializer<>(ElementalAltarShapelessRecipe::new));

    public static void registerRecipeType() {
        //ELEMENTAL_ALTAR = register("elemental_altar");
        ELEMENTAL_ALTAR_SHAPED = register("elemental_altar_shaped");
        ELEMENTAL_ALTAR_SHAPELESS = register("elemental_altar_shapeless");

    }

    public static <R extends IRecipe<?>> IRecipeType<R> register(String name) {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(name), new IRecipeType<R>() {
            @Override
            public String toString() {
                return name;
            }
        });
    }
}