package com.sammy.omnis_backpacks;

import com.sammy.omnis_backpacks.data.*;
import net.minecraft.data.BlockTagsProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.sammy.omnis_backpacks.BackpackMod.MODID;
import static com.sammy.omnis_backpacks.init.Registries.ITEMS;
import static com.sammy.omnis_backpacks.init.Registries.*;

@Mod(MODID)
public class BackpackMod
{
    public static final String MODID = "omnis_backpacks";
    private static final Logger LOGGER = LogManager.getLogger();
    public static boolean isCuriosLoaded = false;
    public BackpackMod()
    {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modBus);
        CONTAINERS.register(modBus);
        ITEMS.register(modBus);
        TILE_ENTITIES.register(modBus);
        modBus.addListener(this::gatherData);
    }

    public void gatherData(GatherDataEvent evt)
    {
        BlockTagsProvider provider = new ModBlockTagProvider(evt.getGenerator());
        evt.getGenerator().addProvider(new ModBlockStateProvider(evt.getGenerator(), evt.getExistingFileHelper()));
        evt.getGenerator().addProvider(new ModItemModelProvider(evt.getGenerator(), evt.getExistingFileHelper()));
        evt.getGenerator().addProvider(new ModLangProvider(evt.getGenerator()));
        evt.getGenerator().addProvider(provider);
        evt.getGenerator().addProvider(new ModLootTableProvider(evt.getGenerator()));
        evt.getGenerator().addProvider(new ModItemTagProvider(evt.getGenerator(), provider, evt.getExistingFileHelper()));
        evt.getGenerator().addProvider(new ModRecipeProvider(evt.getGenerator()));
    }
}