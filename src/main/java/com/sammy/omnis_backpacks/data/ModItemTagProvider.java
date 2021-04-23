package com.sammy.omnis_backpacks.data;

import com.sammy.omnis_backpacks.BackpackMod;
import com.sammy.omnis_backpacks.BackpackModHelper;
import com.sammy.omnis_backpacks.init.Registries;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.ArrayList;

import static com.sammy.omnis_backpacks.init.ItemTags.BACKPACK_BLACKLISTED;


public class ModItemTagProvider extends ItemTagsProvider
{
    public ModItemTagProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper)
    {
        super(dataGenerator, blockTagProvider, BackpackMod.MODID, existingFileHelper);
    }

    @Override
    public String getName()
    {
        return "Item Tags";
    }

    @Override
    protected void registerTags()
    {
        getOrCreateBuilder(BACKPACK_BLACKLISTED).add(Registries.BACKPACK.get(), Registries.NETHERITE_BACKPACK.get(),
                        Items.SHULKER_BOX, Items.BLACK_SHULKER_BOX, Items.BLUE_SHULKER_BOX, Items.BROWN_SHULKER_BOX, Items.CYAN_SHULKER_BOX, Items.GRAY_SHULKER_BOX, Items.GREEN_SHULKER_BOX, Items.LIGHT_BLUE_SHULKER_BOX, Items.LIGHT_GRAY_SHULKER_BOX, Items.LIME_SHULKER_BOX, Items.MAGENTA_SHULKER_BOX, Items.ORANGE_SHULKER_BOX, Items.PINK_SHULKER_BOX, Items.PURPLE_SHULKER_BOX, Items.RED_SHULKER_BOX, Items.WHITE_SHULKER_BOX, Items.YELLOW_SHULKER_BOX);
    }
}