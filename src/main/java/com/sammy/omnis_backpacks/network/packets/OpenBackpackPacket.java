package com.sammy.omnis_backpacks.network.packets;

import com.sammy.omnis_backpacks.common.items.AbstractBackpackItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenBackpackPacket
{
    public OpenBackpackPacket()
    {
    }
    public static OpenBackpackPacket decode(PacketBuffer buf)
    {
        return new OpenBackpackPacket();
    }
    
    public void encode(PacketBuffer buf)
    {
    }
    
    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> {
            PlayerEntity playerEntity = context.get().getSender();
            ItemStack backpack = playerEntity.inventory.armorInventory.get(2);
            if (backpack.getItem() instanceof AbstractBackpackItem)
            {
                AbstractBackpackItem backpackItem = (AbstractBackpackItem) backpack.getItem();
                backpackItem.openContainer(playerEntity.world, playerEntity, backpack);
            }
        });
        context.get().setPacketHandled(true);
    }
}