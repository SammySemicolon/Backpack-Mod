package com.sammy.omnis_backpacks.container.netherite;

import com.sammy.omnis_backpacks.container.AbstractBackpackContainer;
import com.sammy.omnis_backpacks.init.Registries;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class NetheriteBackpackContainer extends AbstractBackpackContainer
{
    public NetheriteBackpackContainer(int windowId, PlayerInventory playerInv, ItemStack backpack)
    {
        super(Registries.NETHERITE_BACKPACK_CONTAINER.get(), windowId, playerInv, backpack, 6);
    }
    public static NetheriteBackpackContainer makeContainer(int windowId, PlayerInventory inv, PacketBuffer buf)
    {
        return new NetheriteBackpackContainer(windowId, inv, buf.readItemStack());
    }

    @Override
    public int offset()
    {
        return 56;
    }
}
