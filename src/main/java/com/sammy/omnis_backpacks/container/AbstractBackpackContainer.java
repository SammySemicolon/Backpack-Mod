package com.sammy.omnis_backpacks.container;

import com.sammy.omnis_backpacks.init.ItemTags;
import com.sammy.omnis_backpacks.systems.inventory.ItemInventory;
import com.sammy.omnis_backpacks.systems.inventory.ItemSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

import javax.annotation.Nonnull;

public abstract class AbstractBackpackContainer extends Container
{
    public ItemStack backpack;
    public final int rowCount;

    public AbstractBackpackContainer(ContainerType<? extends AbstractBackpackContainer> containerType, int windowId, PlayerInventory playerInv, ItemStack backpack, int rowCount)
    {
        super(containerType, windowId);

        this.backpack = backpack;
        this.rowCount = rowCount;
        ItemInventory inventory = create(backpack);
        int offset = offset();
        for (int i = 0; i < rowCount; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                int index = i * 9 + j;
                addSlot(new ItemSlot(inventory, index, 8 + j * 18, 18 + i * 18)
                {
                    @Override
                    public boolean isItemValid(ItemStack stack)
                    {
                        return !ItemTags.BACKPACK_BLACKLISTED.contains(stack.getItem());
                    }
                });
            }
        }

        for (int l = 0; l < 3; ++l)
        {
            for (int j1 = 0; j1 < 9; ++j1)
            {
                this.addSlot(new Slot(playerInv, j1 + (l + 1) * 9, 8 + j1 * 18, offset + 84 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlot(new Slot(playerInv, i1, 8 + i1 * 18, offset + 142));
        }
    }
    @Override
    public void onContainerClosed(PlayerEntity playerIn)
    {
        playerIn.world.playSound(null, playerIn.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.PLAYERS,1,1);
        super.onContainerClosed(playerIn);
    }
    public AbstractBackpackContainer(ContainerType<? extends AbstractBackpackContainer> containerType, int windowId, PlayerInventory playerInv, ItemStack backpack, EnderChestInventory enderChestInventory)
    {
        super(containerType, windowId);

        this.backpack = backpack;
        this.rowCount = 3;
        int offset = offset();
        for (int i = 0; i < rowCount; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                int index = i * 9 + j;
                addSlot(new Slot(enderChestInventory, index, 8 + j * 18, 18 + i * 18));
            }
        }

        for (int l = 0; l < 3; ++l)
        {
            for (int j1 = 0; j1 < 9; ++j1)
            {
                this.addSlot(new Slot(playerInv, j1 + (l + 1) * 9, 8 + j1 * 18, offset + 84 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlot(new Slot(playerInv, i1, 8 + i1 * 18, offset + 142));
        }
    }
    public int offset()
    {
        return 0;
    }
    public ItemInventory create(ItemStack stack)
    {
        return new ItemInventory(stack, rowCount*9, 64);
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity player)
    {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < rowCount * 9)
            {
                if (!this.mergeItemStack(itemstack1, rowCount * 9, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, rowCount * 9, false))
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