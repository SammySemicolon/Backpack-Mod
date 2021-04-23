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

public class EnderBackpackContainer extends AbstractBackpackContainer
{
    public EnderBackpackContainer(int windowId, PlayerInventory playerInv, ItemStack backpack, EnderChestInventory enderInv)
    {
        super(Registries.ENDER_BACKPACK_CONTAINER.get(), windowId, playerInv, backpack, enderInv);
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn)
    {
        playerIn.world.playSound(null, playerIn.getPosition(), SoundEvents.BLOCK_ENDER_CHEST_CLOSE, SoundCategory.PLAYERS,1,1);
        super.onContainerClosed(playerIn);
    }

    public static EnderBackpackContainer makeContainer(int windowId, PlayerInventory inv, PacketBuffer buf)
    {
        return new EnderBackpackContainer(windowId, inv, buf.readItemStack(), inv.player.getInventoryEnderChest());
    }
}
