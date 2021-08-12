package com.sammy.omnis_backpacks;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.omnis_backpacks.client.models.BackpackModel;
import com.sammy.omnis_backpacks.common.CurioProvider;
import com.sammy.omnis_backpacks.common.blocks.BackpackBlock;
import com.sammy.omnis_backpacks.common.blocks.BackpackTileEntity;
import com.sammy.omnis_backpacks.common.items.AbstractBackpackItem;
import com.sammy.omnis_backpacks.container.AbstractBackpackScreen;
import com.sammy.omnis_backpacks.init.ClientRegistries;
import com.sammy.omnis_backpacks.network.NetworkManager;
import com.sammy.omnis_backpacks.network.packets.OpenBackpackPacket;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.ArrayList;
import java.util.Map;

public class HiddenHelper
{
    public static void addCuriosSlot()
    {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BACK.getMessageBuilder().build());
    }
    public static ICapabilityProvider curiosBackpackProvider(ItemStack stack)
    {
        return CurioProvider.createProvider(new ICurio()
        {
            @Override
            public void playRightClickEquipSound(LivingEntity livingEntity)
            {
                livingEntity.world.playSound(null, livingEntity.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            }

            @Override
            public boolean canEquip(String identifier, LivingEntity livingEntity)
            {
                if (livingEntity instanceof PlayerEntity)
                {
                    if (((PlayerEntity) livingEntity).openContainer != null)
                    {
                        return true;
                    }
                }
                return livingEntity.isSneaking();
            }

            @Override
            public boolean canRender(String identifier, int index, LivingEntity livingEntity)
            {
                return true;
            }

            public final ResourceLocation texture = BackpackModHelper.prefix("textures/block/backpack_base.png");
            public BackpackModel model;
            @Override
            public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
            {
                matrixStack.push();
                if (model == null)
                {
                    model = new BackpackModel();
                }

                model.setRotationAngles(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                model.setLivingAnimations(livingEntity, limbSwing, limbSwingAmount, partialTicks);

                int i = ((IDyeableArmorItem)stack.getItem()).getColor(stack);
                float r = (float)(i >> 16 & 255) / 255.0F;
                float g = (float)(i >> 8 & 255) / 255.0F;
                float b = (float)(i & 255) / 255.0F;
                IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(texture), false, false);
                model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, r, g, b, 1);
                ResourceLocation overlay = BackpackModHelper.prefix(((AbstractBackpackItem)stack.getItem()).overlayTexture());
                vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(overlay), false, false);
                model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                matrixStack.pop();
            }

            @Override
            public boolean canRightClickEquip()
            {
                return true;
            }
        });
    }

    public static Pair equippedBackpackCurio(LivingEntity entity)
    {
        IItemHandlerModifiable handler = CuriosApi.getCuriosHelper().getEquippedCurios(entity).resolve().get();
        for (int i = 0; i < handler.getSlots(); i++)
        {
            if (handler.getStackInSlot(i).getItem() instanceof AbstractBackpackItem)
            {
                return new Pair(handler.getStackInSlot(i), i);
            }
        }
        return new Pair(ItemStack.EMPTY, 0);
    }
    public static class Pair
    {
        public ItemStack stack;
        public int index;
        public Pair(ItemStack stack, int index)
        {
            this.stack = stack;
            this.index = index;
        }
    }
}