package com.sammy.omnis_backpacks.common.items;

import com.sammy.omnis_backpacks.BackpackModHelper;
import com.sammy.omnis_backpacks.container.ender.EnderBackpackContainer;
import com.sammy.omnis_backpacks.container.gold.GoldenBackpackContainer;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class EnderBackpackItem extends AbstractBackpackItem
{
    public EnderBackpackItem(Block backpackBlock, Properties properties)
    {
        super(backpackBlock, properties);
    }

    @Override
    public String overlayTexture()
    {
        return "textures/block/ender_backpack.png";
    }

    @Override
    public void openContainer(World world, PlayerEntity player, ItemStack backpack)
    {
        if (BackpackModHelper.areWeOnServer(world))
        {
            INamedContainerProvider container =
                    new SimpleNamedContainerProvider((w, p, pl) -> new EnderBackpackContainer(w, p, backpack, player.getInventoryEnderChest()), backpack.getDisplayName());
            NetworkHooks.openGui((ServerPlayerEntity) player, container, b -> b.writeItemStack(backpack));
        }
        player.world.playSound(null, player.getPosition(), SoundEvents.BLOCK_ENDER_CHEST_OPEN, SoundCategory.PLAYERS,1,1);
    }

    @Override
    public int getColor(ItemStack stack)
    {
        CompoundNBT compoundnbt = stack.getChildTag("display");
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 2697513;
    }
}
