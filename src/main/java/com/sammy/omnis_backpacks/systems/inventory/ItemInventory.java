package com.sammy.omnis_backpacks.systems.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;

import java.util.Objects;

public class ItemInventory extends SimpleInventory
{
    ItemStack stack;
    
    public ItemInventory(ItemStack stack, int slotCount, int slotSize)
    {
        super(slotCount, slotSize);
        this.stack = stack;

        if (stack.getOrCreateTag().contains("inventory"))
        {
            CompoundNBT nbt = (CompoundNBT) stack.getOrCreateTag().get("inventory");
            ListNBT tagList = nbt.getList("Items", Constants.NBT.TAG_COMPOUND);
            if (tagList.size() != slotCount)
            {
                nbt.putInt("Size", slotCount);
            }
            readData(stack.getOrCreateTag());
        }
    }
    
    @Override
    protected void onContentsChanged(int slot)
    {
        writeData(stack.getOrCreateTag());
        super.onContentsChanged(slot);
    }
}
