package com.sammy.omnis_backpacks.common.events;

import com.sammy.omnis_backpacks.BackpackMod;
import com.sammy.omnis_backpacks.HiddenHelper;
import com.sammy.omnis_backpacks.common.items.AbstractBackpackItem;
import com.sammy.omnis_backpacks.container.AbstractBackpackScreen;
import com.sammy.omnis_backpacks.init.ClientRegistries;
import com.sammy.omnis_backpacks.network.NetworkManager;
import com.sammy.omnis_backpacks.network.packets.OpenBackpackPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = BackpackMod.MODID, value = Dist.CLIENT)
public class ClientEvents
{
    @SubscribeEvent
    public static void openBackpackGui(TickEvent.ClientTickEvent event)
    {
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            Minecraft minecraft = Minecraft.getInstance();
            PlayerEntity playerEntity = minecraft.player;
            if (!(minecraft.currentScreen instanceof AbstractBackpackScreen))
            {
                if (ClientRegistries.BACKPACK_KEYBINDING.isKeyDown())
                {
                    ItemStack backpack = playerEntity.inventory.armorItemInSlot(2);
                    if (ModList.get().isLoaded("curios"))
                    {
                        ItemStack newBackpack = HiddenHelper.equippedBackpackCurio(playerEntity).stack;
                        if (!newBackpack.isEmpty())
                        {
                            backpack = newBackpack;
                        }
                    }
                    if (backpack.getItem() instanceof AbstractBackpackItem)
                    {
                        NetworkManager.INSTANCE.send(PacketDistributor.SERVER.noArg(), new OpenBackpackPacket());
                    }
                }
            }
        }
    }
}