package com.sammy.backpack_mod.common.items;

import com.sammy.backpack_mod.BackpackModHelper;
import com.sammy.backpack_mod.container.gold.GoldenBackpackContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

public class AbstractBackpackItem extends BlockItem implements IDyeableArmorItem
{
    public AbstractBackpackItem(Block blockIn, Properties builder)
    {
        super(blockIn, builder);
    }

    @Override
    protected boolean placeBlock(BlockItemUseContext context, BlockState state)
    {
        return super.placeBlock(context, state);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand)
    {
        openContainer(world, player, player.getHeldItem(hand));
        return ActionResult.resultSuccess(player.getHeldItem(hand));
    }
    public void openContainer(World world, PlayerEntity player, ItemStack backpack)
    {
    }
}
