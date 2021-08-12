package com.sammy.omnis_backpacks.common.blocks;

import com.sammy.omnis_backpacks.common.SimpleTileEntity;
import com.sammy.omnis_backpacks.init.Registries;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;

public class BackpackTileEntity extends SimpleTileEntity
{
    public ItemStack backpack;
    public BackpackTileEntity()
    {
        super(Registries.BACKPACK_TE.get());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet)
    {
        world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 8);
        super.onDataPacket(net, packet);
    }

    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        if (backpack != null)
        {
            compound.put("backpack", backpack.serializeNBT());
        }
        return compound;
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        if (compound.contains("backpack"))
        {
            backpack = ItemStack.read(compound.getCompound("backpack"));
        }
    }
}