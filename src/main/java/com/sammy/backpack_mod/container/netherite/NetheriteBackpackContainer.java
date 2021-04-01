package com.sammy.backpack_mod.container.netherite;

import com.sammy.backpack_mod.container.AbstractBackpackContainer;
import com.sammy.backpack_mod.init.Registries;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;

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
