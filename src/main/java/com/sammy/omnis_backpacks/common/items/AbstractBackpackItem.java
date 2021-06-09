package com.sammy.omnis_backpacks.common.items;

import com.sammy.omnis_backpacks.HiddenHelper;
import com.sammy.omnis_backpacks.client.models.BackpackModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.LazyValue;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;

import javax.annotation.Nullable;

public class AbstractBackpackItem extends ArmorItem implements IDyeableArmorItem
{
    public final Block backpackBlock;
    private LazyValue<Object> model;

    public AbstractBackpackItem(Block backpackBlock, Properties builder)
    {
        super(ArmorMaterial.LEATHER, EquipmentSlotType.CHEST, builder);
        this.backpackBlock = backpackBlock;
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            this.model = DistExecutor.runForDist(() -> () -> new LazyValue<>(BackpackModel::new), () -> () -> null);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unchecked")
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A original)
    {
        return (A) model.getValue();
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
    {
        if (type != null)
        {
            return "omnis_backpacks:" + overlayTexture();
        }
        return "omnis_backpacks:textures/block/backpack_base.png";
    }
    public String overlayTexture()
    {
        return null;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt)
    {
        if (!ModList.get().isLoaded("curios"))
        {
            return null;
        }
        return HiddenHelper.curiosBackpackProvider(stack);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        if (playerIn.isSneaking())
        {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
        ItemStack backpack = playerIn.getHeldItem(handIn);
        openContainer(worldIn, playerIn, backpack);
        return ActionResult.resultSuccess(backpack);
    }

    public void openContainer(World world, PlayerEntity player, ItemStack backpack)
    {
    }
}