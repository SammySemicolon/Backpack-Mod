package com.sammy.backpack_mod.common.items;

import com.sammy.backpack_mod.BackpackModHelper;
import com.sammy.backpack_mod.container.netherite.NetheriteBackpackContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

public class NetheriteBackpackItem extends Item implements IDyeableArmorItem
{
    public NetheriteBackpackItem(Properties properties)
    {
        super(properties);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand)
    {
        if (BackpackModHelper.areWeOnServer(world))
        {
            ItemStack stack = player.getHeldItem(hand);
            INamedContainerProvider container =
                    new SimpleNamedContainerProvider((w, p, pl) -> new NetheriteBackpackContainer(
                    w,
                    p,
                    stack), stack.getDisplayName());
            NetworkHooks.openGui((ServerPlayerEntity) player, container, b -> b.writeBoolean(hand == Hand.MAIN_HAND));
        }
        return ActionResult.resultSuccess(player.getHeldItem(hand));
    }
}