package com.sammy.backpack_mod.common.items;

import com.sammy.backpack_mod.BackpackModHelper;
import com.sammy.backpack_mod.container.AbstractBackpackContainer;
import com.sammy.backpack_mod.container.gold.GoldenBackpackContainer;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

public class GoldenBackpackItem extends AbstractBackpackItem
{
    public GoldenBackpackItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    public void openContainer(World world, PlayerEntity player, ItemStack backpack)
    {

        if (BackpackModHelper.areWeOnServer(world))
        {
            INamedContainerProvider container =
                    new SimpleNamedContainerProvider((w, p, pl) -> new GoldenBackpackContainer(w, p, backpack), backpack.getDisplayName());
            NetworkHooks.openGui((ServerPlayerEntity) player, container, b -> b.writeItemStack(backpack));
        }
    }
}
