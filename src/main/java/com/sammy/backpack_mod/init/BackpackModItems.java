package com.sammy.backpack_mod.init;

import com.sammy.backpack_mod.common.items.GoldenBackpackItem;
import com.sammy.backpack_mod.common.items.NetheriteBackpackItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sammy.backpack_mod.BackpackMod.MODID;


public class BackpackModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static Item.Properties GEAR_PROPERTIES()
    {
        return new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1);
    }

    public static final RegistryObject<Item> BACKPACK = ITEMS.register("backpack", () -> new GoldenBackpackItem(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> NETHERITE_BACKPACK = ITEMS.register("netherite_backpack", () -> new NetheriteBackpackItem(GEAR_PROPERTIES()));
}