package com.sammy.omnis_backpacks.init;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;

import static com.sammy.omnis_backpacks.BackpackModHelper.prefix;

public class ItemTags
{
    public static final ITag.INamedTag<Item> BACKPACK_BLACKLISTED = makeWrapperTag("backpack_blacklisted");

    public static ITag.INamedTag<Item> makeWrapperTag(String id)
    {
        return net.minecraft.tags.ItemTags.createOptional(prefix(id));
    }
}
