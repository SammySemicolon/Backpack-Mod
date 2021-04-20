package com.sammy.omnis_backpacks;

import com.sammy.omnis_backpacks.common.items.AbstractBackpackItem;
import com.sammy.omnis_backpacks.container.AbstractBackpackScreen;
import com.sammy.omnis_backpacks.init.ClientRegistries;
import com.sammy.omnis_backpacks.network.NetworkManager;
import com.sammy.omnis_backpacks.network.packets.OpenBackpackPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.network.PacketDistributor;

public class ClientHandler
{
    public static void openBackpackGui()
    {
        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity playerEntity = minecraft.player;
        if (!(minecraft.currentScreen instanceof AbstractBackpackScreen))
        {
            if (ClientRegistries.BACKPACK_KEYBINDING.isKeyDown())
            {
                ItemStack backpack = playerEntity.inventory.armorItemInSlot(2);
                if (backpack.getItem() instanceof AbstractBackpackItem)
                {
                    NetworkManager.INSTANCE.send(PacketDistributor.SERVER.noArg(), new OpenBackpackPacket());
                }
            }
        }
    }
}