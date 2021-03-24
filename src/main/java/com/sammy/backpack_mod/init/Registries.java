package com.sammy.backpack_mod.init;

import com.sammy.backpack_mod.BackpackModHelper;
import com.sammy.backpack_mod.container.gold.GoldenBackpackContainer;
import com.sammy.backpack_mod.container.netherite.NetheriteBackpackContainer;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sammy.backpack_mod.BackpackMod.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID)
public class Registries
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);

    public static final RegistryObject<ContainerType<GoldenBackpackContainer>> GOLDEN_BACKPACK_CONTAINER = CONTAINERS.register(
            "golden_backpack_container", () -> IForgeContainerType.create(
                    GoldenBackpackContainer::makeContainer));
    public static final RegistryObject<ContainerType<NetheriteBackpackContainer>> NETHERITE_BACKPACK_CONTAINER =
            CONTAINERS.register(
            "netherite_backpack_container", () -> IForgeContainerType.create(
                            NetheriteBackpackContainer::makeContainer));
}
