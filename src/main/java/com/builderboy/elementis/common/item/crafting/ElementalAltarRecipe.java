package com.builderboy.elementis.common.item.crafting;

import com.builderboy.elementis.client.container.ElementalAltarContainer;
import com.builderboy.elementis.common.item.StaffItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ElementalAltarRecipe {

    public static final List<ElementalAltarRecipe> RECIPES = new ArrayList<>();

    private static final int craftingWidth = 3, craftingHeight = 3;
    private ItemStack[] ingredients;
    private int manaCost;
    private ItemStack result;
    private int recipeWidth, recipeHeight;

    public ElementalAltarRecipe(ItemStack[] ingredients, int manaCost, ItemStack result) {
        //Exception Checks
        if (ingredients.length > 9) throw new IllegalArgumentException("The list of ingredients must contain 9 items!");
        if (manaCost < 0) throw new IllegalArgumentException("Mana cost can not be less than 0!");
        if (result == ItemStack.EMPTY) throw new IllegalArgumentException("The resulting item cannot be null!");

        //Initialize
        this.ingredients = ingredients;
        this.manaCost = manaCost;
        this.result = result;
        this.recipeWidth = getRecipeWidth(ingredients);
        this.recipeHeight = getRecipeHeight(ingredients);
    }

    public static void add(ElementalAltarRecipe... recipes) {
        List<ElementalAltarRecipe> recipeList = new ArrayList<>();
        for (ElementalAltarRecipe recipe : recipes) {
            recipeList.add(recipe);
        }

        RECIPES.addAll(recipeList);
    }

    public static ItemStack[] createIngredientsList(ItemStack... items) {
        if (items.length > 9) throw new IllegalArgumentException("The list of ingredients must contain 9 items!");

        ItemStack[] arr = new ItemStack[9];

        for (int i = 0; i < items.length; i++) {
            arr[i] = items[i];
        }

        return arr;
    }

    public boolean match(ElementalAltarContainer container) {
        boolean isGrid;
        boolean isStaff = false;
        ItemStack[] currentIngredients = new ItemStack[9];

        //Getting items from the container
        for (int x = 0; x < craftingWidth; x++) {
            for (int y = 0; y < craftingWidth; y++) {
                int slotId = getSlotId(x, y);
                currentIngredients[slotId] = container.getSlot(slotId).getStack();
            }
        }

        //Check crafting grid
        isGrid = checkShaped(currentIngredients, this.ingredients);

        //Check staff if needed
        if (this.manaCost == 0) { isStaff = true; }
        else if (this.manaCost > 0) {
            StaffItem staff = container.getStaff();
            if (staff != null) {
                int heldMana = staff.getMana(new ItemStack(staff));
                if (heldMana >= this.manaCost) { isStaff = true; }
            }
        }

        return isGrid && isStaff;
    }

    private boolean checkShaped(ItemStack[] input, ItemStack[] ingredients) {
        boolean matches = false;

        //If the recipe takes up a 3x3 grid
        if (this.recipeWidth == 3 && this.recipeHeight == 3) {
            boolean[] arr = new boolean[9];
            boolean matched = true;

            for (int b = 0; b < arr.length; b++) {
                arr[b] = false;
            }

            for (int x = 0; x < craftingWidth; x++) {
                for (int y = 0; y < craftingHeight; y++) {
                    ItemStack in = input[getSlotId(x, y)];
                    ItemStack re = ingredients[getSlotId(x, y)];
                    if (in == re) {
                        arr[getSlotId(x, y)] = true;
                    }
                }
            }

            for (boolean check : arr) {
                if (!check) {
                    matched = false;
                    break;
                }
            }
            if (matched) { matches = true; }
        }

        //If the recipe does not take up the 3x3 grid
        if (!matches) {
            boolean[] arr = new boolean[this.recipeWidth * this.recipeHeight];
            boolean matched = true;

            int xOff = 0;
            int yOff = 0;

            ItemStack start = getStartingStack(ingredients);

            //Get offset
            for (int i = 0; i < 3 - this.recipeWidth; i++) {
                for (int j = 0; j < 3 - this.recipeHeight; j++) {
                    if (input[getSlotId(i, j)] == start) {
                        xOff = i;
                        yOff = j;
                    }
                }
            }

            //Check From Offset
            for (int x = xOff; x < craftingWidth; x++) {
                for (int y = yOff; y < craftingHeight; ) {
                    ItemStack in = input[getSlotId(x, y)];
                    ItemStack re = ingredients[getSlotId(x, y)];
                    if (in == re) { insertBoolean(arr, true); }
                }
            }

            for (int i = 0; i < arr.length; i++) {
                if (!arr[i]) {
                    matched = false;
                    break;
                }
            }

            if (matched) { matches = true; }
        }

        return matches;
    }

    public ItemStack getResult() {
        return this.result;
    }

    private int getRecipeWidth(ItemStack[] ingredients) {
        int width = 0;
        boolean[] matches = new boolean[3];

        for (int x = 0; x < craftingWidth; x++) {
            for (int y = 0; y < craftingHeight; y++) {
                int index = getSlotId(x, y);
                if (ingredients[index] != ItemStack.EMPTY) {
                    if (!matches[x]) { matches[x] = true; }
                }
            }
        }

        for (boolean check : matches) {
            if (check) { ++width; }
        }

        return width;
    }

    private int getRecipeHeight(ItemStack[] ingredients) {
        int height = 0;
        boolean[] matches = new boolean[3];

        for (int x = 0; x < craftingWidth; x++) {
            for (int y = 0; y < craftingHeight; y++) {
                int index = getSlotId(x, y);
                if (ingredients[index] != ItemStack.EMPTY) {
                    if (!matches[y]) { matches[y] = true; }
                }
            }
        }

        for (boolean check : matches) {
            if (check) { ++height; }
        }

        return height;
    }

    private int getIngredientsCount(ItemStack[] ingredients) {
        int count = 0;

        for (int i = 0; i < ingredients.length; i++) {
            if (ingredients[i] != ItemStack.EMPTY) { count++; }
        }

        return count;
    }

    private ItemStack[] removeEmptyStacks(ItemStack[] stackList) {
        ItemStack[] list = new ItemStack[getIngredientsCount(stackList)];

        for (int i = 0; i < list.length; i++) {
            if (stackList[i] != ItemStack.EMPTY) {
                list = insertStack(list, stackList[i]);
            }
        }

        return list;
    }

    private ItemStack[] insertStack(ItemStack[] arr, ItemStack stack) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == ItemStack.EMPTY) {
                arr[i] = stack;
                break;
            }
        }

        return arr;
    }

    private boolean[] insertBoolean(boolean[] arr, boolean val) {
        for (int b = 0; b < arr.length; b++) {
            if (!arr[b]) { arr[b] = val; }
        }

        return arr;
    }

    private ItemStack getStartingStack(ItemStack[] items) {
        ItemStack stack = ItemStack.EMPTY;

        for (int x = 0; x < craftingWidth; x++) {
            for (int y = 0; y < craftingHeight; y++) {
                if (stack != null) { stack = items[getSlotId(x, y)]; }
            }
        }

        return stack;
    }

    private int getSlotId(int x, int y) { return x + (3 * y); }

    public int getManaCost() { return this.manaCost; }
}