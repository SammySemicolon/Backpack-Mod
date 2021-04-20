package com.sammy.omnis_backpacks.common.events;

import com.sammy.omnis_backpacks.BackpackMod;
import com.sammy.omnis_backpacks.ClientHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod.EventBusSubscriber(modid = BackpackMod.MODID, value = Dist.CLIENT)
public class ClientEvents
{
    @SubscribeEvent
    public static void openBackpackGui(TickEvent.ClientTickEvent event)
    {
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            ClientHandler.openBackpackGui();
        }
    }
}