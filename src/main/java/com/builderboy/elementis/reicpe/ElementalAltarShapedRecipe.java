package com.builderboy.elementis.reicpe;

import com.builderboy.elementis.item.ManaTabletItem;
import com.builderboy.elementis.utils.CraftingUtils;
import com.builderboy.elementis.item.inventory.ElementalAltarInventory;
import com.builderboy.elementis.registries.ModBlockRegistry;
import com.builderboy.elementis.registries.ModRecipeRegistry;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Map;

public class ElementalAltarShapedRecipe implements IRecipe<ElementalAltarInventory> {
    private final ResourceLocation id;
    private final int recipeWidth;
    private final int recipeHeight;
    private final NonNullList<Ingredient> ingredients;
    private final int manaCost;
    private final ItemStack result;

    public ElementalAltarShapedRecipe(ResourceLocation id, int recipeWidth, int recipeHeight, NonNullList<Ingredient> ingredients, int manaCost, ItemStack result) {
        this.id = id;
        this.recipeWidth = recipeWidth;
        this.recipeHeight = recipeHeight;
        this.ingredients = ingredients;
        this.manaCost = manaCost;
        this.result = result;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(ModBlockRegistry.ELEMENTAL_ALTAR.get());
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeRegistry.ELEMENTAL_ALTAR_SHAPED_SERIALIZER.get();
    }

    public ItemStack getResult() {
        return this.result;
    }

    @Override
    public boolean matches(ElementalAltarInventory inv, World worldIn) {
        for(int i = 0; i <= inv.getWidth() - this.recipeWidth; ++i) {
            for(int j = 0; j <= inv.getHeight() - this.recipeHeight; ++j) {
                if (this.checkMatch(inv, i, j, true)) {
                    return checkManaTablet(inv);
                }

                if (this.checkMatch(inv, i, j, false)) {
                    return checkManaTablet(inv);
                }
            }
        }

        return false;
    }

    private boolean checkMatch(ElementalAltarInventory inv, int offX, int offY, boolean p_77573_4_) {
        for(int i = 0; i < inv.getWidth(); ++i) {
            for(int j = 0; j < inv.getHeight(); ++j) {
                int k = i - offX;
                int l = j - offY;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.recipeWidth && l < this.recipeHeight) {
                    if (p_77573_4_) {
                        ingredient = this.ingredients.get(this.recipeWidth - k - 1 + l * this.recipeWidth);
                    } else {
                        ingredient = this.ingredients.get(k + l * this.recipeWidth);
                    }
                }

                if (!ingredient.test(inv.getStackInSlot(i + j * inv.getWidth()))) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkManaTablet(ElementalAltarInventory inv) {
        if (this.manaCost <= 0) {
            return true;
        }

        if (inv.getStackInSlot(9) != ItemStack.EMPTY) {
            ItemStack manaTabletStack = inv.getStackInSlot(9);
            ManaTabletItem manaTablet = (ManaTabletItem) manaTabletStack.getItem();
            if (manaTablet.getMana(manaTabletStack) >= this.manaCost) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ItemStack getCraftingResult(ElementalAltarInventory inv) {
        return this.result.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= this.recipeWidth && height >= this.recipeHeight;
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
        return ModRecipeRegistry.ELEMENTAL_ALTAR_SHAPED;
    }

    public int getRecipeWidth() {
        return recipeWidth;
    }

    public int getRecipeHeight() {
        return recipeHeight;
    }

    public int getManaCost() {
        return manaCost;
    }

    public static class Serializer<R extends ElementalAltarShapedRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<R> {
        private final Serializer.IFactory<R> factory;
        private static final CraftingUtils utils = new CraftingUtils(3, 3);

        public Serializer(Serializer.IFactory<R> factory) {
            this.factory = factory;
        }

        @Override
        public R read(ResourceLocation recipeId, JsonObject json) {
            Map<String, Ingredient> map = utils.deserializeKey(JSONUtils.getJsonObject(json, "key"));
            String[] pattern = utils.shrink(utils.patternFromJson(JSONUtils.getJsonArray(json, "pattern")));
            int w = pattern[0].length();
            int h = pattern.length;
            NonNullList<Ingredient> ingredients = utils.deserializeIngredients(pattern, map, w, h);
            int manaCost = JSONUtils.getInt(json, "manacost");
            ItemStack result = utils.deserializeItem(JSONUtils.getJsonObject(json, "result"));

            return factory.create(recipeId, w, h, ingredients, manaCost, result);
        }

        @Nullable
        @Override
        public R read(ResourceLocation recipeId, PacketBuffer buffer) {
            int w = buffer.readVarInt();
            int h = buffer.readVarInt();

            NonNullList<Ingredient> ingredients = NonNullList.withSize(w * h, Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                ingredients.set(i, Ingredient.read(buffer));
            }

            int manaCost = buffer.readVarInt();

            ItemStack result = buffer.readItemStack();

            return factory.create(recipeId, w, h, ingredients, manaCost, result);
        }

        @Override
        public void write(PacketBuffer buffer, R recipe) {
            buffer.writeVarInt(recipe.getRecipeWidth());
            buffer.writeVarInt(recipe.getRecipeHeight());

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(buffer);
            }

            buffer.writeVarInt(recipe.getManaCost());

            buffer.writeItemStack(recipe.getResult());
        }

        public interface IFactory<R extends ElementalAltarShapedRecipe> {
            R create(ResourceLocation id, int recipeWidth, int recipeHeight, NonNullList<Ingredient> ingredients, int manaCost, ItemStack result);
        }
    }
}