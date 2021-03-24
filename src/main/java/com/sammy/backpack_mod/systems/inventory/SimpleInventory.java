package com.sammy.backpack_mod.systems.inventory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;

public class SimpleInventory extends ItemStackHandler
{
    public int slotCount;
    public int slotSize;
    public Predicate<ItemStack> inputPredicate;
    public Predicate<ItemStack> outputPredicate;
    
    public SimpleInventory(int slotCount, int slotSize, Predicate<ItemStack> inputPredicate, Predicate<ItemStack> outputPredicate)
    {
        this(slotCount, slotSize, inputPredicate);
        this.outputPredicate = outputPredicate;
    }
    
    public SimpleInventory(int slotCount, int slotSize, Predicate<ItemStack> inputPredicate)
    {
        this(slotCount, slotSize);
        this.inputPredicate = inputPredicate;
    }
    
    public SimpleInventory(int slotCount, int slotSize)
    {
        super(slotCount);
        this.slotCount = slotCount;
        this.slotSize = slotSize;
    }
    public int firstEmptyItem()
    {
        for (int i = 0; i < slotCount; i++)
        {
            if (getStackInSlot(i).isEmpty())
            {
                return i;
            }
        }
        return -1;
    }
    public int nonEmptyItems()
    {
        int itemCount = 0;
        for (int i = 0; i < slotCount; i++)
        {
            ItemStack item = getStackInSlot(i);
            if (!item.isEmpty())
            {
                itemCount++;
            }
            else
            {
                break;
            }
        }
        return itemCount;
    }
    public void clearItems()
    {
        for (int i = 0; i < slotCount; i++)
        {
            setStackInSlot(i, ItemStack.EMPTY);
        }
    }
    public ArrayList<Item> items()
    {
        ArrayList<Item> items = new ArrayList<>();
        for (int i = 0; i < slotCount; i++)
        {
            items.add(getStackInSlot(i).getItem());
        }
        return items;
    }
    public ArrayList<ItemStack> stacks()
    {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < slotCount; i++)
        {
            stacks.add(getStackInSlot(i));
        }
        return stacks;
    }
    public ArrayList<ItemStack> nonEmptyStacks()
    {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < slotCount; i++)
        {
            if (!getStackInSlot(i).isEmpty())
            {
                stacks.add(getStackInSlot(i));
            }
        }
        return stacks;
    }
    @Override
    public int getSlots()
    {
        return slotCount;
    }
    
    @Override
    public int getSlotLimit(int slot)
    {
        return slotSize;
    }
    
    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack)
    {
        if (inputPredicate != null)
        {
            if (!inputPredicate.test(stack))
            {
                return false;
            }
        }
        return super.isItemValid(slot, stack);
    }
    
    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        if (outputPredicate != null)
        {
            if (!outputPredicate.test(super.extractItem(slot, amount, true)))
            {
                return ItemStack.EMPTY;
            }
        }
        return super.extractItem(slot, amount, simulate);
    }
    public void readData(CompoundNBT compound)
    {
        readData(compound,"inventory");
    }
    
    public CompoundNBT writeData(CompoundNBT compound)
    {
        writeData(compound, "inventory");
        return compound;
    }
    public void readData(CompoundNBT compound, String name)
    {
        deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get(name)));
    }
    
    public CompoundNBT writeData(CompoundNBT compound, String name)
    {
        compound.put(name, serializeNBT());
        return compound;
    }
}