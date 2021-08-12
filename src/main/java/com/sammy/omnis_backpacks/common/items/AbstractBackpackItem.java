package com.sammy.omnis_backpacks.common.items;

import com.sammy.omnis_backpacks.BackpackMod;
import com.sammy.omnis_backpacks.HiddenHelper;
import com.sammy.omnis_backpacks.client.models.BackpackModel;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;

import javax.annotation.Nullable;

import static net.minecraft.item.BlockItem.setTileEntityNBT;

public class AbstractBackpackItem extends ArmorItem implements IDyeableArmorItem, IInventory
{
    public final Block backpackBlock;
    private LazyValue<Object> model;

    public AbstractBackpackItem(Block backpackBlock, Properties builder)
    {
        super(ArmorMaterial.LEATHER, EquipmentSlotType.CHEST, builder);
        this.backpackBlock = backpackBlock;
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            this.model = DistExecutor.runForDist(() -> () -> new LazyValue<>(BackpackModel::new), () -> () -> null);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unchecked")
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A original)
    {
        return (A) model.getValue();
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
    {
        if (type != null)
        {
            return "omnis_backpacks:" + overlayTexture();
        }
        return "omnis_backpacks:textures/block/backpack_base.png";
    }

    public String overlayTexture()
    {
        return null;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt)
    {
        if (!BackpackMod.isCuriosLoaded)
        {
            return null;
        }
        return HiddenHelper.curiosBackpackProvider(stack);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        if (playerIn.isSneaking())
        {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
        return ActionResult.resultFail(playerIn.getHeldItem(handIn));
    }

    //region BlockItem copy paste
    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        ActionResultType actionresulttype = this.tryPlace(new BlockItemUseContext(context));
        return !actionresulttype.isSuccessOrConsume() && this.isFood() ? this.onItemRightClick(context.getWorld(), context.getPlayer(), context.getHand()).getType() : actionresulttype;
    }
    public ActionResultType tryPlace(BlockItemUseContext context)
    {
        if (!context.canPlace())
        {
            return ActionResultType.FAIL;
        }
        else
        {
            BlockItemUseContext blockitemusecontext = this.getBlockItemUseContext(context);
            if (blockitemusecontext == null)
            {
                return ActionResultType.FAIL;
            }
            else
            {
                BlockState blockstate = this.getStateForPlacement(blockitemusecontext);
                if (blockstate == null)
                {
                    return ActionResultType.FAIL;
                }
                else if (!this.placeBlock(blockitemusecontext, blockstate))
                {
                    return ActionResultType.FAIL;
                }
                else
                {
                    BlockPos blockpos = blockitemusecontext.getPos();
                    World world = blockitemusecontext.getWorld();
                    PlayerEntity playerentity = blockitemusecontext.getPlayer();
                    ItemStack itemstack = blockitemusecontext.getItem();
                    BlockState blockstate1 = world.getBlockState(blockpos);
                    Block block = blockstate1.getBlock();
                    if (block == blockstate.getBlock())
                    {
                        blockstate1 = this.func_219985_a(blockpos, world, itemstack, blockstate1);
                        this.onBlockPlaced(blockpos, world, playerentity, itemstack, blockstate1);
                        block.onBlockPlacedBy(world, blockpos, blockstate1, playerentity, itemstack);
                        if (playerentity instanceof ServerPlayerEntity)
                        {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerentity, blockpos, itemstack);
                        }
                    }

                    SoundType soundtype = blockstate1.getSoundType(world, blockpos, context.getPlayer());
                    world.playSound(playerentity, blockpos, blockstate1.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    if (playerentity == null || !playerentity.abilities.isCreativeMode)
                    {
                        itemstack.shrink(1);
                    }

                    return ActionResultType.func_233537_a_(world.isRemote);
                }
            }
        }
    }

    private BlockState func_219985_a(BlockPos p_219985_1_, World p_219985_2_, ItemStack p_219985_3_, BlockState p_219985_4_)
    {
        BlockState blockstate = p_219985_4_;
        CompoundNBT compoundnbt = p_219985_3_.getTag();
        if (compoundnbt != null)
        {
            CompoundNBT compoundnbt1 = compoundnbt.getCompound("BlockStateTag");
            StateContainer<Block, BlockState> statecontainer = p_219985_4_.getBlock().getStateContainer();

            for (String s : compoundnbt1.keySet())
            {
                Property<?> property = statecontainer.getProperty(s);
                if (property != null)
                {
                    String s1 = compoundnbt1.get(s).getString();
                    blockstate = func_219988_a(blockstate, property, s1);
                }
            }
        }

        if (blockstate != p_219985_4_)
        {
            p_219985_2_.setBlockState(p_219985_1_, blockstate, 2);
        }

        return blockstate;
    }

    private static <T extends Comparable<T>> BlockState func_219988_a(BlockState p_219988_0_, Property<T> p_219988_1_, String p_219988_2_)
    {
        return p_219988_1_.parseValue(p_219988_2_).map((p_219986_2_) ->
        {
            return p_219988_0_.with(p_219988_1_, p_219986_2_);
        }).orElse(p_219988_0_);
    }

    protected boolean canPlace(BlockItemUseContext p_195944_1_, BlockState p_195944_2_)
    {
        PlayerEntity playerentity = p_195944_1_.getPlayer();
        ISelectionContext iselectioncontext = playerentity == null ? ISelectionContext.dummy() : ISelectionContext.forEntity(playerentity);
        return (!this.checkPosition() || p_195944_2_.isValidPosition(p_195944_1_.getWorld(), p_195944_1_.getPos())) && p_195944_1_.getWorld().placedBlockCollides(p_195944_2_, p_195944_1_.getPos(), iselectioncontext);
    }

    protected boolean checkPosition()
    {
        return true;
    }

    protected boolean placeBlock(BlockItemUseContext context, BlockState state)
    {
        return context.getWorld().setBlockState(context.getPos(), state, 11);
    }

    @Nullable
    public BlockItemUseContext getBlockItemUseContext(BlockItemUseContext context)
    {
        return context;
    }

    protected boolean onBlockPlaced(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack, BlockState state)
    {
        return setTileEntityNBT(worldIn, player, pos, stack);
    }

    @Nullable
    protected BlockState getStateForPlacement(BlockItemUseContext context)
    {
        BlockState blockstate = backpackBlock.getStateForPlacement(context);
        return blockstate != null && this.canPlace(context, blockstate) ? blockstate : null;
    }
    //endregion

    public void openContainer(World world, PlayerEntity player, ItemStack backpack)
    {
    }

    @Override
    public int getSizeInventory()
    {
        return 0;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int index)
    {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        return null;
    }

    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {

    }

    @Override
    public void markDirty()
    {

    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player)
    {
        return false;
    }

    @Override
    public void clear()
    {

    }
}