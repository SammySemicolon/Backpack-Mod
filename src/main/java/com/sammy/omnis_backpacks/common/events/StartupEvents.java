package com.sammy.omnis_backpacks.common.events;

import com.sammy.omnis_backpacks.HiddenHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class StartupEvents
{
    @SubscribeEvent
    public static void registerCurios(InterModEnqueueEvent event)
    {
        if (!ModList.get().isLoaded("curios"))
        {
            return;
        }
        HiddenHelper.addCuriosSlot();
    }
}
