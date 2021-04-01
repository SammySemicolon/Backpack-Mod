package com.sammy.backpack_mod.common.blocks;

import com.sammy.backpack_mod.common.SimpleTileEntity;
import com.sammy.backpack_mod.init.Registries;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import java.awt.*;

public class BackpackTileEntity extends SimpleTileEntity
{
    public ItemStack backpack;
    public BackpackTileEntity()
    {
        super(Registries.BACKPACK_TE.get());
    }
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        if (backpack != null)
        {
            compound.put("backpack", backpack.serializeNBT());
        }
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        if (compound.contains("backpack"))
        {
            backpack = ItemStack.read(compound.getCompound("backpack"));
        }
        super.readData(compound);
    }
}