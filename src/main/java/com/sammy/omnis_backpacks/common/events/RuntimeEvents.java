package com.sammy.omnis_backpacks.common.events;

import com.sammy.omnis_backpacks.BackpackMod;
import com.sammy.omnis_backpacks.BackpackModHelper;
import com.sammy.omnis_backpacks.common.blocks.BackpackBlock;
import com.sammy.omnis_backpacks.common.blocks.BackpackTileEntity;
import com.sammy.omnis_backpacks.common.items.AbstractBackpackItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BackpackMod.MODID)
public class RuntimeEvents
{
    @SubscribeEvent
    public static void rightClickBackpack(PlayerInteractEvent.RightClickBlock event)
    {
        World world = event.getWorld();
        PlayerEntity playerEntity = event.getPlayer();
        if (event.getHand().equals(Hand.MAIN_HAND))
        {
            if (playerEntity.isSneaking())
            {
                TileEntity tileEntity = world.getTileEntity(event.getPos());
                ItemStack backpack = playerEntity.inventory.armorItemInSlot(2);
                if (tileEntity instanceof BackpackTileEntity)
                {
                    BackpackTileEntity backpackTileEntity = (BackpackTileEntity) tileEntity;
                    if (backpack.isEmpty())
                    {
                        if (BackpackModHelper.areWeOnServer(world))
                        {
                            playerEntity.inventory.armorInventory.set(2, backpackTileEntity.backpack);
                            world.removeBlock(event.getPos(), true);
                        }
                        world.playSound(playerEntity, event.getPos(), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.PLAYERS, 1, 1);
                        playerEntity.swingArm(Hand.MAIN_HAND);
                    }
                }
                else if (backpack.getItem() instanceof AbstractBackpackItem)
                {
                    AbstractBackpackItem backpackItem = (AbstractBackpackItem) backpack.getItem();
                    BlockPos pos = event.getPos().offset(event.getHitVec().getFace());
                    if (world.getBlockState(pos).isAir())
                    {
                        playerEntity.inventory.armorInventory.set(2, ItemStack.EMPTY);
                        BlockState backpackState = backpackItem.backpackBlock.getDefaultState().with(BackpackBlock.HORIZONTAL_FACING, playerEntity.getHorizontalFacing());
                        world.setBlockState(pos, backpackState);
                        tileEntity = world.getTileEntity(pos);
                        if (tileEntity instanceof BackpackTileEntity)
                        {
                            BackpackTileEntity backpackTileEntity = (BackpackTileEntity) tileEntity;
                            backpackTileEntity.backpack = backpack.copy();
                            world.notifyBlockUpdate(pos, backpackState, backpackState, 3);
                        }
                        world.playSound(playerEntity, event.getPos(), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.PLAYERS, 1, 1);
                        playerEntity.swingArm(Hand.MAIN_HAND);
                    }
                }
            }
        }
    }
}