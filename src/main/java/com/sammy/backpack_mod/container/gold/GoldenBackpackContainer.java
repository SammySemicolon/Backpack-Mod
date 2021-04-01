package com.sammy.backpack_mod.container.gold;

import com.sammy.backpack_mod.container.AbstractBackpackContainer;
import com.sammy.backpack_mod.init.Registries;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;

public class GoldenBackpackContainer extends AbstractBackpackContainer
{
    public GoldenBackpackContainer(int windowId, PlayerInventory playerInv, ItemStack backpack)
    {
        super(Registries.GOLDEN_BACKPACK_CONTAINER.get(), windowId, playerInv, backpack, 3);
    }
    public static GoldenBackpackContainer makeContainer(int windowId, PlayerInventory inv, PacketBuffer buf)
    {
        return new GoldenBackpackContainer(windowId, inv, buf.readItemStack());
    }
}
