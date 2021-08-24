package com.sammy.omnis_backpacks.common.items;

import com.sammy.omnis_backpacks.BackpackModHelper;
import com.sammy.omnis_backpacks.container.netherite.NetheriteBackpackContainer;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.ShulkerBoxContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.awt.*;

public class NetheriteBackpackItem extends AbstractBackpackItem
{
    public NetheriteBackpackItem(Block backpackBlock, Properties properties)
    {
        super(backpackBlock, properties);
    }

    @Override
    public String overlayTexture()
    {
        return "textures/block/netherite_backpack.png";
    }
    @Override
    public void openContainer(World world, PlayerEntity player, ItemStack backpack)
    {
        if (BackpackModHelper.areWeOnServer(world))
        {
            INamedContainerProvider container =
                    new SimpleNamedContainerProvider((w, p, pl) -> new NetheriteBackpackContainer(w, p, backpack), backpack.getDisplayName());
            NetworkHooks.openGui((ServerPlayerEntity) player, container, b -> b.writeItemStack(backpack));
        }
        player.world.playSound(null, player.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.PLAYERS,1,1);
    }

    @Override
    public int inventorySize()
    {
        return 54;
    }
}