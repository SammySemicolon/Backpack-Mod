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
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

public abstract class AbstractBackpackScreen extends ContainerScreen<AbstractBackpackContainer>
{
	public final ResourceLocation texture;
	public AbstractBackpackScreen(AbstractBackpackContainer container, PlayerInventory player, ITextComponent title, ResourceLocation texture)
	{
		super(container, player, title);
		this.passEvents = true;
		this.texture = texture;
	}

	@Override
	public void init()
	{
		super.init();
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
			this.font.func_243248_b(matrixStack, this.title, 97.0F, 6.0F, 4210752);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y)
	{
		this.minecraft.getTextureManager().bindTexture(texture);
		blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}