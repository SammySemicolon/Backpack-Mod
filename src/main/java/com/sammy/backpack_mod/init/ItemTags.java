package com.sammy.backpack_mod.init;

import com.sammy.backpack_mod.BackpackModHelper;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;

public class ItemTags
{
    public static ITag.INamedTag<Item> BACKPACK_BLACKLISTED;

    public static ITag.INamedTag<Item> makeWrapperTag(String id)
    {
        return net.minecraft.tags.ItemTags.createOptional(BackpackModHelper.prefix(id));
    }

    public static void init()
    {
        BACKPACK_BLACKLISTED = makeWrapperTag("backpack_blacklisted");
    }
}
