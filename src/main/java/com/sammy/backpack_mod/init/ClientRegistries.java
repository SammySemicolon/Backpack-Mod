package com.sammy.backpack_mod.init;

import com.sammy.backpack_mod.container.AbstractBackpackScreen;
import com.sammy.backpack_mod.container.gold.GoldenBackpackScreen;
import com.sammy.backpack_mod.container.netherite.NetheriteBackpackScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.sammy.backpack_mod.BackpackMod.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID, value = Dist.CLIENT)
public class ClientRegistries
{
    @SubscribeEvent
    public static void registerScreenFactory(FMLClientSetupEvent event)
    {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            ScreenManager.registerFactory(Registries.GOLDEN_BACKPACK_CONTAINER.get(), GoldenBackpackScreen::new);
            ScreenManager.registerFactory(Registries.NETHERITE_BACKPACK_CONTAINER.get(), NetheriteBackpackScreen::new);
        });
    }
}