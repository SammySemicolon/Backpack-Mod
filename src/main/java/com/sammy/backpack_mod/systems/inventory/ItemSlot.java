package com.sammy.backpack_mod.systems.inventory;

import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ItemSlot extends Slot
{
    public final ItemInventory inventory;
    public ItemSlot(ItemInventory inventory, int index, int xPosition, int yPosition)
    {
        super(null, index, xPosition, yPosition);
        this.inventory = inventory;
    }

    @Override
    public ItemStack getStack()
    {
        return inventory.getStackInSlot(getSlotIndex());
    }

    @Override
    public void putStack(ItemStack stack)
    {
        inventory.setStackInSlot(getSlotIndex(), stack);
        onSlotChanged();
    }

    @Override
    public void onSlotChanged()
    {
        inventory.onContentsChanged(getSlotIndex());
    }

    @Override
    public int getSlotStackLimit()
    {
        return inventory.slotSize;
    }

    @Override
    public ItemStack decrStackSize(int amount)
    {
        return inventory.getStackInSlot(getSlotIndex()).split(amount);
    }

    @Override
    public boolean isSameInventory(Slot other)
    {
        if (other instanceof ItemSlot)
        {
            ItemSlot slot = (ItemSlot) other;
            return slot.inventory == this.inventory;
        }
        return false;
    }
}