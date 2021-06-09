package com.sammy.omnis_backpacks.init;

import com.sammy.omnis_backpacks.common.blocks.BackpackTileEntity;
import com.sammy.omnis_backpacks.container.ender.EnderBackpackScreen;
import com.sammy.omnis_backpacks.container.gold.GoldenBackpackScreen;
import com.sammy.omnis_backpacks.container.netherite.NetheriteBackpackScreen;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

import static com.sammy.omnis_backpacks.BackpackMod.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID, value = Dist.CLIENT)
public class ClientRegistries
{
    public static final KeyBinding BACKPACK_KEYBINDING = new KeyBinding("key.omnis_backpack", GLFW.GLFW_KEY_B, "key.categories.misc");
    @SubscribeEvent
    public static void registerKeybind(FMLClientSetupEvent event)
    {
        ClientRegistry.registerKeyBinding(BACKPACK_KEYBINDING);
    }

    @SubscribeEvent
    public static void registerScreenFactory(FMLClientSetupEvent event)
    {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
        {
            ScreenManager.registerFactory(Registries.GOLDEN_BACKPACK_CONTAINER.get(), GoldenBackpackScreen::new);
            ScreenManager.registerFactory(Registries.ENDER_BACKPACK_CONTAINER.get(), EnderBackpackScreen::new);
            ScreenManager.registerFactory(Registries.NETHERITE_BACKPACK_CONTAINER.get(), NetheriteBackpackScreen::new);
        });
        setCutout(Registries.GOLDEN_BACKPACK_BLOCK);
        setCutout(Registries.NETHERITE_BACKPACK_BLOCK);
        setCutout(Registries.ENDER_BACKPACK_BLOCK);

    }

    @SubscribeEvent
    public static void setBlockColors(ColorHandlerEvent.Block event)
    {
        BlockColors blockColors = event.getBlockColors();

        blockColors.register((state, reader, pos, color) -> {

            TileEntity tileEntity = reader.getTileEntity(pos);
            if (tileEntity instanceof BackpackTileEntity)
            {
                BackpackTileEntity backpackTileEntity = (BackpackTileEntity) tileEntity;
                if (backpackTileEntity.backpack != null)
                {
                    ItemStack stack = backpackTileEntity.backpack;
                    if (stack.getItem() instanceof IDyeableArmorItem)
                    {
                        return color > 0 ? -1 : ((IDyeableArmorItem) stack.getItem()).getColor(stack);
                    }
                }
            }
            return -1;
        }, Registries.GOLDEN_BACKPACK_BLOCK.get(), Registries.ENDER_BACKPACK_BLOCK.get(), Registries.NETHERITE_BACKPACK_BLOCK.get());
    }
    @SubscribeEvent
    public static void setItemColors(ColorHandlerEvent.Item event)
    {
        ItemColors itemColors = event.getItemColors();
        itemColors.register((stack, color) ->
                color > 0 ? -1 : ((IDyeableArmorItem) stack.getItem()).getColor(stack),
                Registries.BACKPACK.get(), Registries.ENDER_BACKPACK.get(), Registries.NETHERITE_BACKPACK.get());
    }
    public static void setCutout(RegistryObject<Block> b)
    {
        RenderTypeLookup.setRenderLayer(b.get(), RenderType.getCutout());
    }
}