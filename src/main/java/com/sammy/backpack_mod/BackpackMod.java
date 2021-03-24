package com.sammy.backpack_mod;

import com.sammy.backpack_mod.data.*;
import com.sammy.backpack_mod.init.ItemTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.sammy.backpack_mod.BackpackMod.MODID;
import static com.sammy.backpack_mod.init.BackpackModItems.ITEMS;
import static com.sammy.backpack_mod.init.Registries.*;

@Mod(MODID)
public class BackpackMod
{
    public static final String MODID = "backpack_mod";
    private static final Logger LOGGER = LogManager.getLogger();

    public BackpackMod()
    {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modBus);
        CONTAINERS.register(modBus);
        ITEMS.register(modBus);
        TILE_ENTITIES.register(modBus);
        ItemTags.init();
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
        evt.getGenerator().addProvider(new ModItemTagProvider(evt.getGenerator(), provider));
        evt.getGenerator().addProvider(new ModRecipeProvider(evt.getGenerator()));
    }
}