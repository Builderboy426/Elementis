package com.builderboy.elementis.client.elementalaltar;

import com.builderboy.elementis.Elementis;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ElementalAltarScreen extends ContainerScreen<ElementalAltarContainer> {
    public static final ResourceLocation GUI_TEXTURE = Elementis.getLocation("textures/gui/container/elemental_altar.png");

    public ElementalAltarScreen(ElementalAltarContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
        this.xSize = 214;
        this.ySize = 166;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTick) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTick);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String title = this.title.getFormattedText();
        this.font.drawString(title, (float)(this.xSize / 2 - this.font.getStringWidth(title) / 2) - 22, 4.0f, 0xffffffff);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(i - 22, j, 0, 0, 214, this.ySize);

        int k = this.container.getManaScaled();
        this.blit(i + 157, j + (67 - k), 252, 60 - k, 4, k);
    }
}