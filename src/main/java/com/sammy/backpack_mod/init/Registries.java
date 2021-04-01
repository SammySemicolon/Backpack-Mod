package com.sammy.backpack_mod.init;

import com.sammy.backpack_mod.BackpackModHelper;
import com.sammy.backpack_mod.common.blocks.BackpackBlock;
import com.sammy.backpack_mod.common.blocks.BackpackTileEntity;
import com.sammy.backpack_mod.common.items.GoldenBackpackItem;
import com.sammy.backpack_mod.common.items.NetheriteBackpackItem;
import com.sammy.backpack_mod.container.gold.GoldenBackpackContainer;
import com.sammy.backpack_mod.container.netherite.NetheriteBackpackContainer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static com.sammy.backpack_mod.BackpackMod.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID)
public class Registries
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
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

    public static final RegistryObject<Block> GOLDEN_BACKPACK_BLOCK = BLOCKS.register("golden_backpack_block", () -> new BackpackBlock(AbstractBlock.Properties.from(Blocks.BROWN_WOOL)));
    public static final RegistryObject<Block> NETHERITE_BACKPACK_BLOCK = BLOCKS.register("netherite_backpack_block", () -> new BackpackBlock(AbstractBlock.Properties.from(Blocks.BROWN_WOOL)));

    public static final RegistryObject<TileEntityType<?>> BACKPACK_TE = TILE_ENTITIES.register("abstruse_block_tile_entity", () -> TileEntityType.Builder.create((Supplier<TileEntity>) BackpackTileEntity::new, GOLDEN_BACKPACK_BLOCK.get(), NETHERITE_BACKPACK_BLOCK.get()).build(null));

    public static final RegistryObject<Item> NETHERITE_BACKPACK = ITEMS.register("netherite_backpack", () -> new NetheriteBackpackItem(NETHERITE_BACKPACK_BLOCK.get(), GEAR_PROPERTIES()));
    public static final RegistryObject<Item> BACKPACK = ITEMS.register("backpack", () -> new GoldenBackpackItem(GOLDEN_BACKPACK_BLOCK.get(), GEAR_PROPERTIES()));

    public static Item.Properties GEAR_PROPERTIES()
    {
        return new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1);
    }
}
