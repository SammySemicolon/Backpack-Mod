package com.sammy.backpack_mod.container;

import com.sammy.backpack_mod.common.items.NetheriteBackpackItem;
import com.sammy.backpack_mod.init.Registries;
import com.sammy.backpack_mod.systems.inventory.ItemInventory;
import com.sammy.backpack_mod.systems.inventory.ItemSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

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
                        return super.isItemValid(stack);
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
            if (playerInv.currentItem != i1)
            {
                this.addSlot(new Slot(playerInv, i1, 8 + i1 * 18, offset + 142));
            }
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
        ItemStack main = player.getHeldItemMainhand();
        ItemStack off = player.getHeldItemOffhand();
        return !main.isEmpty() && main == backpack || !off.isEmpty() && off == backpack;
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