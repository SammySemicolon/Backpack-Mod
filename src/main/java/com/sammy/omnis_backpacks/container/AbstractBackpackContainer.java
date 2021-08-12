package com.sammy.omnis_backpacks.container;

import com.sammy.omnis_backpacks.common.items.AbstractBackpackItem;
import com.sammy.omnis_backpacks.init.ItemTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

import javax.annotation.Nonnull;
import java.awt.*;

public abstract class AbstractBackpackContainer extends Container
{
    private final IInventory inventory;
    protected final Color color;

    public AbstractBackpackContainer(ContainerType<? extends AbstractBackpackContainer> containerType, int windowId, PlayerInventory playerInv, ItemStack backpack)
    {
        this(containerType, windowId, playerInv, (AbstractBackpackItem)backpack.getItem(), backpack);
    }
    public AbstractBackpackContainer(ContainerType<? extends AbstractBackpackContainer> containerType, int windowId, PlayerInventory playerInv, IInventory inventory, ItemStack backpack)
    {
        super(containerType, windowId);
        AbstractBackpackItem backpackItem = (AbstractBackpackItem) backpack.getItem();
        this.inventory = inventory;
        this.color = new Color(backpackItem.getColor(backpack));
        for (int i = 0; i < inventory.getSizeInventory()/9f; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                int index = i * 9 + j;
                addSlot(new Slot(inventory, index, 8 + j * 18, 18 + i * 18)
                {
                    @Override
                    public boolean isItemValid(ItemStack stack)
                    {
                        return !ItemTags.BACKPACK_BLACKLISTED.contains(stack.getItem());
                    }
                });
            }
        }
        int offset = offset();
        for (int l = 0; l < 3; ++l)
        {
            for (int j1 = 0; j1 < 9; ++j1)
            {
                this.addSlot(new Slot(playerInv, j1 + (l + 1) * 9, 8 + j1 * 18, offset + 84 + l * 18)
                {
                    @Override
                    public boolean canTakeStack(PlayerEntity playerIn)
                    {
                        if (playerInv.getStackInSlot(getSlotIndex()).getItem() instanceof AbstractBackpackItem)
                        {
                            return false;
                        }
                        return super.canTakeStack(playerIn);
                    }
                });
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlot(new Slot(playerInv, i1, 8 + i1 * 18, offset + 142)
            {
                @Override
                public boolean canTakeStack(PlayerEntity playerIn)
                {
                    if (playerInv.getStackInSlot(getSlotIndex()).getItem() instanceof AbstractBackpackItem)
                    {
                        return false;
                    }
                    return super.canTakeStack(playerIn);
                }
            });
        }
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn)
    {
        playerIn.world.playSound(null, playerIn.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.PLAYERS, 1, 1);
        super.onContainerClosed(playerIn);
        this.inventory.closeInventory(playerIn);
    }

    public int offset()
    {
        return 0;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return this.inventory.isUsableByPlayer(playerIn);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < this.inventory.getSizeInventory())
            {
                if (!this.mergeItemStack(itemstack1, this.inventory.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.inventory.getSizeInventory(), false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
}