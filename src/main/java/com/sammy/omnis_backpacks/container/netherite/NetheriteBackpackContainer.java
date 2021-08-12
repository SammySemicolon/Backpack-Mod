package com.sammy.omnis_backpacks.container.netherite;

import com.sammy.omnis_backpacks.container.AbstractBackpackContainer;
import com.sammy.omnis_backpacks.container.gold.GoldenBackpackContainer;
import com.sammy.omnis_backpacks.init.Registries;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ShulkerBoxContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import java.awt.*;

public class NetheriteBackpackContainer extends AbstractBackpackContainer
{
    public NetheriteBackpackContainer(int windowId, PlayerInventory playerInv, ItemStack backpack)
    {
        super(Registries.NETHERITE_BACKPACK_CONTAINER.get(), windowId, playerInv, backpack);
    }

    @Override
    public int offset()
    {
        return 56;
    }
}