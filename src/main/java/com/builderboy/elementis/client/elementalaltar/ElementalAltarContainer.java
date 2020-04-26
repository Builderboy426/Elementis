package com.builderboy.elementis.client.elementalaltar;

import com.builderboy.elementis.Elementis;
import com.builderboy.elementis.client.ManaTabletSlot;
import com.builderboy.elementis.item.ManaTabletItem;
import com.builderboy.elementis.item.inventory.ElementalAltarInventory;
import com.builderboy.elementis.registries.ModContainerRegistry;
import com.builderboy.elementis.registries.ModRecipeRegistry;
import com.builderboy.elementis.reicpe.ElementalAltarShapedRecipe;
import com.builderboy.elementis.reicpe.ElementalAltarShapelessRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Optional;

public class ElementalAltarContainer extends Container implements IRecipeHelperPopulator, IRecipeHolder {
    private final ElementalAltarInventory craftInventory;
    private final CraftResultInventory resultInventory;
    private Slot resultSlot;
    private final PlayerEntity player;
    private final World world;
    private int manaCost = 0;

    public ElementalAltarContainer(int id, PlayerInventory playerInventory) {
        super(ModContainerRegistry.ELEMENTAL_ALTAR.get(), id);
        this.player = playerInventory.player;
        this.world = playerInventory.player.world;

        this.resultInventory = new CraftResultInventory();
        this.craftInventory = new ElementalAltarInventory(this);
        this.craftInventory.openInventory(this.player);

        //Container (0 - 10)
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(this.craftInventory, j * 3 + i, 8 + j * 18, 17 + i * 18));
            }
        }

        this.addSlot(new ManaTabletSlot(this.craftInventory, 9, 170, 52));
        resultSlot = this.addSlot(new CraftingResultSlot(playerInventory.player, this.craftInventory, this.resultInventory, 10, 102, 34));

        //Player Inventory (11 - 47)
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, (j * 18) - 14, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, (k * 18) - 14, 142));
        }

        onCraftMatrixChanged(craftInventory);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            stack = slotStack.copy();

            if (index == 10) {
                IWorldPosCallable.of(world, player.getPosition()).consume((world, pos) -> {
                    slotStack.getItem().onCreated(slotStack, world, player);

                    ItemStack tabletStack = ElementalAltarContainer.this.craftInventory.getStackInSlot(9);
                    ManaTabletItem tablet = (ManaTabletItem) tabletStack.getItem();
                    tablet.changeMana(tabletStack, -ElementalAltarContainer.this.manaCost);

                    this.manaCost = 0;
                });

                if (!this.mergeItemStack(slotStack, 11, 47, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(slotStack, stack);
            } else if (index < 10) {
                if (this.mergeItemStack(slotStack, 11, 47, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(slotStack, stack);
            } else {
                if (isManaTablet(slotStack)) {
                    if (!this.mergeItemStack(slotStack, 9, 10, false)) {
                        return ItemStack.EMPTY;
                    } else if (!this.mergeItemStack(slotStack, 0, 8, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (!this.mergeItemStack(slotStack, 0, 8, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (slotStack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (slotStack.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack slotStack2 = slot.onTake(player, slotStack);
            if (index == 10) {
                player.dropItem(slotStack2, false);
            }
        }

        return stack;
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventory) {
        IWorldPosCallable.of(world, player.getPosition()).consume((world, pos) -> {
            if (!world.isRemote) {

                ServerPlayerEntity playerMP = (ServerPlayerEntity) player;
                ItemStack stack = ItemStack.EMPTY;
                Optional<ElementalAltarShapedRecipe> shapedRecipe = world.getServer().getRecipeManager().getRecipe(ModRecipeRegistry.ELEMENTAL_ALTAR_SHAPED, craftInventory, world);
                Optional<ElementalAltarShapelessRecipe> shapelessRecipe = world.getServer().getRecipeManager().getRecipe(ModRecipeRegistry.ELEMENTAL_ALTAR_SHAPELESS, craftInventory, world);

                //Shaped Recipe
                if (shapedRecipe.isPresent()) {

                    Elementis.LOGGER.info("Recipe is Present");

                    ElementalAltarShapedRecipe recipe = shapedRecipe.get();
                    int manaCost = recipe.getManaCost();

                    if (manaCost == 0) { //If the cost is 0

                        stack = recipe.getCraftingResult(craftInventory);

                    } else if (manaCost > 0) { //If the cost id greater than 0

                        if (ElementalAltarContainer.this.hasManaTablet()) {

                            int mana = ElementalAltarContainer.this.getManaTablet().getMana(ElementalAltarContainer.this.getManaTabletStack());

                            if (mana >= manaCost) {

                                stack = recipe.getCraftingResult(craftInventory);
                            }
                        }
                    }
                }

                //Shapeless Recipe
                if (shapelessRecipe.isPresent()) {
                    Elementis.LOGGER.info("Recipe is Present");

                    ElementalAltarShapelessRecipe recipe = shapelessRecipe.get();
                    int manaCost = recipe.getManaCost();

                    if (manaCost == 0) { //If the cost is 0

                        stack = recipe.getCraftingResult(craftInventory);

                    } else if (manaCost > 0) { //If the cost id greater than 0

                        if (ElementalAltarContainer.this.hasManaTablet()) {

                            int mana = ElementalAltarContainer.this.getManaTablet().getMana(ElementalAltarContainer.this.getManaTabletStack());

                            if (mana >= manaCost) {

                                stack = recipe.getCraftingResult(craftInventory);
                            }
                        }
                    }

                }

                resultInventory.setInventorySlotContents(resultSlot.slotNumber, stack);
                playerMP.connection.sendPacket(new SSetSlotPacket(windowId, resultSlot.slotNumber, stack));
            }
        });
    }

    @Override
    public boolean canDragIntoSlot(Slot slot) {
        if (slot.slotNumber == 9 || slot.slotNumber == 10) { return false; }

        return true;
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return true;
    }

    @Override
    public void onContainerClosed(PlayerEntity player) {
        super.onContainerClosed(player);

        IWorldPosCallable.of(world, player.getPosition()).consume((world, pos) -> {
            this.clearContainer(player, world, craftInventory);
        });
    }

    public ManaTabletItem getManaTablet() { return hasManaTablet() ? (ManaTabletItem)this.getManaTabletStack().getItem() : (ManaTabletItem)ItemStack.EMPTY.getItem(); }
    public ItemStack getManaTabletStack() { return hasManaTablet() ? this.craftInventory.getStackInSlot(9) : ItemStack.EMPTY; }

    public int getManaTabletMana() {
        return hasManaTablet() ? this.getManaTablet().getMana(this.getManaTabletStack()) : 0;
    }
    //public StaffItem getStaff() { return hasManaTablet() ? (StaffItem)craftInventory.getStackInSlot(9).getItem() : (StaffItem)ItemStack.EMPTY.getItem(); }

    @OnlyIn(Dist.CLIENT)
    public int getManaScaled() {
        ItemStack stack = this.craftInventory.getStackInSlot(9);

        if (isManaTablet(stack)) {
            ManaTabletItem manaTablet = (ManaTabletItem) stack.getItem();
            int cm = manaTablet.getMana(stack);
            int mm = manaTablet.getMaxMana();

            return (cm * 60) / mm;
        }

        return 0;
    }

    private boolean isManaTablet(ItemStack stack) { return stack.getItem() instanceof ManaTabletItem; }
    private boolean hasManaTablet() { return !craftInventory.getStackInSlot(9).isEmpty(); }

    //private boolean isStaff(ItemStack stack) { return stack.getItem() instanceof StaffItem; }
    //private boolean hasStaff() { return !craftInventory.getStackInSlot(9).isEmpty(); }

    @Override
    public void fillStackedContents(RecipeItemHelper helper) {

    }

    @Override
    public void setRecipeUsed(@Nullable IRecipe<?> recipe) {}

    @Nullable
    @Override
    public IRecipe<?> getRecipeUsed() {
        return null;
    }
}