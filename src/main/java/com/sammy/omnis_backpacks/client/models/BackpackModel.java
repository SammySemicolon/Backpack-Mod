package com.sammy.omnis_backpacks.client.models;// Made with Blockbench 3.8.3
// Exported for Minecraft version 1.15 - 1.16
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class BackpackModel extends BipedModel<LivingEntity>
{
	private final ModelRenderer wholeBackpack;

	public BackpackModel() {
		super(1);
		textureWidth = 32;
		textureHeight = 32;

		wholeBackpack = new ModelRenderer(this);

		ModelRenderer backpack = new ModelRenderer(this);
		backpack.setRotationPoint(0.0F, 0.0F, 0.0F);
		backpack.setTextureOffset(0, 0).addBox(-5.0F, -1.0F, 2.0F, 10.0F, 12.0F, 6.0F, 0.0F, false);
		backpack.setTextureOffset(0, 18).addBox(-3.0F, 4.0F, 8.0F, 6.0F, 7.0F, 2.0F, 0.0F, false);
		wholeBackpack.addChild(backpack);

		ModelRenderer noColor = new ModelRenderer(this)
		{
			@Override
			public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn)
			{
				super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, 1, 1, 1, 1);
			}
		};
		noColor.setRotationPoint(0.0F, 0.0F, 0.0F);
		noColor.setTextureOffset(0, 30).addBox(-1.0F, 5.0F, 10.01F, 2.0F, 2.0F, 0.0F, 0.0F, true);
		noColor.setTextureOffset(0, 28).addBox(-2.0F, 1.0F, 8.01F, 4.0F, 2.0F, 0.0F, 0.0F, true);
		noColor.setTextureOffset(22, 18).addBox(3.0F, 0.0F, -2.0F, 1.0F, 10.0F, 4.0F, 0.5F, false);
		noColor.setTextureOffset(22, 18).addBox(-4.0F, 0.0F, -2.0F, 1.0F, 10.0F, 4.0F, 0.5F, true);
		wholeBackpack.addChild(noColor);

	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		bipedBody = wholeBackpack;
		wholeBackpack.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, 1);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}