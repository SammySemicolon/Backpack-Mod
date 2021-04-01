/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package com.sammy.backpack_mod.container;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.backpack_mod.BackpackModHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import java.awt.*;

import static net.minecraft.util.ColorHelper.PackedColor.packColor;

public abstract class AbstractBackpackScreen extends ContainerScreen<AbstractBackpackContainer>
{
	public final ResourceLocation texture;
	public final ResourceLocation overlayTexture;
	public AbstractBackpackScreen(AbstractBackpackContainer container, PlayerInventory player, ITextComponent title, String texture)
	{
		super(container, player, title);
		this.passEvents = true;
		this.texture = BackpackModHelper.prefix(texture + ".png");
		this.overlayTexture = BackpackModHelper.prefix(texture + "_overlay.png");
	}

	@Override
	public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void renderHoveredTooltip(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY)
	{
		Minecraft mc = this.minecraft;
		if (mc != null)
		{
			ClientPlayerEntity clientPlayer = mc.player;
			if (clientPlayer != null && clientPlayer.inventory.getItemStack().isEmpty())
			{
				if (this.hoveredSlot != null && this.hoveredSlot.getHasStack())
				{
					this.renderTooltip(matrixStack, this.hoveredSlot.getStack(), mouseX, mouseY);
				}
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY)
	{
		if (this.minecraft != null && this.minecraft.player != null)
		{
			ItemStack backpack = container.backpack;
			IDyeableArmorItem coloredBackpack = (IDyeableArmorItem) backpack.getItem();
			Color color = new Color(coloredBackpack.getColor(backpack));
			Color insideColor = color.darker().darker().darker();
			Color outlineColor = color.darker().darker();
			String text = title.getString();
			float x = 89 - font.getStringWidth(text) / 2f;
			float y = 6;

			font.drawString(matrixStack, text, x, y - 1, packColor(96, outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue()));
			font.drawString(matrixStack, text, x - 1, y, packColor(96, outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue()));
			font.drawString(matrixStack, text, x + 1, y, packColor(96, outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue()));
			font.drawString(matrixStack, text, x, y + 1, packColor(96, outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue()));

			font.drawString(matrixStack, text, x, y, packColor(255,  insideColor.getRed(), insideColor.getGreen(), insideColor.getBlue()));
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y)
	{
		this.minecraft.getTextureManager().bindTexture(texture);
		blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize);

		ItemStack backpack = container.backpack;
		IDyeableArmorItem coloredBackpack = (IDyeableArmorItem) backpack.getItem();
		this.minecraft.getTextureManager().bindTexture(overlayTexture);
		Color color = new Color(coloredBackpack.getColor(backpack));
		RenderSystem.color3f(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f);
		blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}