package com.sammy.backpack_mod.init;

import com.sammy.backpack_mod.container.AbstractBackpackScreen;
import com.sammy.backpack_mod.container.gold.GoldenBackpackScreen;
import com.sammy.backpack_mod.container.netherite.NetheriteBackpackScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static com.sammy.backpack_mod.BackpackMod.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID, value = Dist.CLIENT)
public class ClientRegistries
{
    @SubscribeEvent
    public static void registerScreenFactory(FMLClientSetupEvent event)
    {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
        {
            ScreenManager.registerFactory(Registries.GOLDEN_BACKPACK_CONTAINER.get(), GoldenBackpackScreen::new);
            ScreenManager.registerFactory(Registries.NETHERITE_BACKPACK_CONTAINER.get(), NetheriteBackpackScreen::new);
        });
    }

    @SubscribeEvent
    public static void setItemColors(ColorHandlerEvent.Item event)
    {
        ItemColors itemColors = event.getItemColors();
        itemColors.register((stack, color) ->
                color > 0 ? -1 : ((IDyeableArmorItem) stack.getItem()).getColor(stack), BackpackModItems.BACKPACK.get(), BackpackModItems.NETHERITE_BACKPACK.get());
    }
}