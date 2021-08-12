package com.sammy.omnis_backpacks.container.ender;

import com.sammy.omnis_backpacks.container.AbstractBackpackContainer;
import com.sammy.omnis_backpacks.init.Registries;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

import java.awt.*;

public class EnderBackpackContainer extends AbstractBackpackContainer
{
    public EnderBackpackContainer(int windowId, PlayerInventory playerInv, EnderChestInventory enderInv, ItemStack backpack)
    {
        super(Registries.ENDER_BACKPACK_CONTAINER.get(), windowId, playerInv, enderInv, backpack);
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn)
    {
        playerIn.world.playSound(null, playerIn.getPosition(), SoundEvents.BLOCK_ENDER_CHEST_CLOSE, SoundCategory.PLAYERS, 1, 1);
        super.onContainerClosed(playerIn);
    }
}