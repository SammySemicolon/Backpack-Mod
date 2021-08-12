package com.sammy.omnis_backpacks.container.gold;

import com.sammy.omnis_backpacks.container.AbstractBackpackContainer;
import com.sammy.omnis_backpacks.init.Registries;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import java.awt.*;

public class GoldenBackpackContainer extends AbstractBackpackContainer
{
    public GoldenBackpackContainer(int windowId, PlayerInventory playerInv, ItemStack backpack)
    {
        super(Registries.GOLDEN_BACKPACK_CONTAINER.get(), windowId, playerInv, backpack);
    }
}
