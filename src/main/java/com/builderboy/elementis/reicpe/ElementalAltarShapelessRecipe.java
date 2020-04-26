package com.builderboy.elementis.reicpe;

import com.builderboy.elementis.item.ManaTabletItem;
import com.builderboy.elementis.item.inventory.ElementalAltarInventory;
import com.builderboy.elementis.registries.ModBlockRegistry;
import com.builderboy.elementis.registries.ModRecipeRegistry;
import com.builderboy.elementis.utils.CraftingUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ElementalAltarShapelessRecipe implements IRecipe<ElementalAltarInventory> {
    private final ResourceLocation id;
    private final NonNullList<Ingredient> ingredients;
    private final int manaCost;
    private final ItemStack result;
    private final boolean isSimple;

    public ElementalAltarShapelessRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, int manaCost, ItemStack result) {
        this.id = id;
        this.ingredients = ingredients;
        this.manaCost = manaCost;
        this.result = result;
        this.isSimple = ingredients.stream().allMatch(Ingredient::isSimple);
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(ModBlockRegistry.ELEMENTAL_ALTAR.get());
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeRegistry.ELEMENTAL_ALTAR_SHAPELESS_SERIALIZER.get();
    }

    public ItemStack getResult() {
        return this.result;
    }

    @Override
    public boolean matches(ElementalAltarInventory inv, World worldIn) {
        RecipeItemHelper helper = new RecipeItemHelper();
        List<ItemStack> inputs = new ArrayList<>();
        int i = 0;

        for (int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack stack = inv.getStackInSlot(j);
            if (!stack.isEmpty()) {
                ++i;

                if (isSimple) { helper.func_221264_a(stack, 1); }
                else { inputs.add(stack); }
            }
        }

        return i == this.ingredients.size() && (isSimple ? helper.canCraft(this, null) : RecipeMatcher.findMatches(inputs, this.ingredients) != null);
    }

    @Override
    public ItemStack getCraftingResult(ElementalAltarInventory inv) {
        return this.result.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= this.ingredients.size();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.result;
    }

    @Override
    public String getGroup() {
        return "";
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipeRegistry.ELEMENTAL_ALTAR_SHAPELESS;
    }

    public int getManaCost() {
        return manaCost;
    }

    public static class Serializer<R extends ElementalAltarShapelessRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<R> {
        private final Serializer.IFactory<R> factory;
        private static final CraftingUtils utils = new CraftingUtils(3, 3);

        public Serializer(Serializer.IFactory<R> factory) {
            this.factory = factory;
        }

        @Override
        public R read(ResourceLocation recipeId, JsonObject json) {
            NonNullList<Ingredient> ingredients = readIngredients(JSONUtils.getJsonArray(json, "ingredients"));
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            } else if (ingredients.size() > 9) {
                throw new JsonParseException("Too many ingredients for shapeless recipe the max is 9");
            }
            int manaCost = JSONUtils.getInt(json, "manacost");
            ItemStack result = utils.deserializeItem(JSONUtils.getJsonObject(json, "result"));

            return factory.create(recipeId, ingredients, manaCost, result);
        }

        private static NonNullList<Ingredient> readIngredients(JsonArray jsonArray) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();

            for(int i = 0; i < jsonArray.size(); ++i) {
                Ingredient ingredient = Ingredient.deserialize(jsonArray.get(i));
                if (!ingredient.hasNoMatchingItems()) {
                    nonnulllist.add(ingredient);
                }
            }

            return nonnulllist;
        }

        @Nullable
        @Override
        public R read(ResourceLocation recipeId, PacketBuffer buffer) {
            int i = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(i, Ingredient.EMPTY);

            for (int j = 0; j < ingredients.size(); ++j) {
                ingredients.set(j, Ingredient.read(buffer));
            }

            int manaCost = buffer.readVarInt();

            ItemStack result = buffer.readItemStack();

            return factory.create(recipeId, ingredients, manaCost, result);
        }

        @Override
        public void write(PacketBuffer buffer, R recipe) {
            buffer.writeVarInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(buffer);
            }

            buffer.writeVarInt(recipe.getManaCost());
            buffer.writeItemStack(recipe.getResult());
        }

        public interface IFactory<R extends ElementalAltarShapelessRecipe> {
            R create(ResourceLocation id, NonNullList<Ingredient> ingredients, int manaCost, ItemStack result);
        }
    }
}