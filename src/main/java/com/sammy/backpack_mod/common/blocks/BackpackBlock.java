package com.sammy.backpack_mod.common.blocks;

import com.sammy.backpack_mod.BackpackModHelper;
import com.sammy.backpack_mod.common.items.AbstractBackpackItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.stream.Stream;

public class BackpackBlock extends HorizontalBlock
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final VoxelShape NORTH = Stream.of(
            Block.makeCuboidShape(6, 7, 10, 10, 10, 11),
            Block.makeCuboidShape(5, 0, 10, 11, 7, 12),
            Block.makeCuboidShape(3, 0, 4, 13, 12, 10)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape EAST = Stream.of(
            Block.makeCuboidShape(5, 7, 6, 6, 10, 10),
            Block.makeCuboidShape(4, 0, 5, 6, 7, 11),
            Block.makeCuboidShape(6, 0, 3, 12, 12, 13)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape SOUTH = Stream.of(
            Block.makeCuboidShape(6, 7, 5, 10, 10, 6),
            Block.makeCuboidShape(5, 0, 4, 11, 7, 6),
            Block.makeCuboidShape(3, 0, 6, 13, 12, 12)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape WEST = Stream.of(
            Block.makeCuboidShape(10, 7, 6, 11, 10, 10),
            Block.makeCuboidShape(10, 0, 5, 12, 7, 11),
            Block.makeCuboidShape(4, 0, 3, 10, 12, 13)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public BackpackBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, false));

    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        switch (state.get(HORIZONTAL_FACING))
        {
            case NORTH:
            {
                return NORTH;
            }
            case EAST:
            {
                return EAST;
            }
            case SOUTH:
            {
                return SOUTH;
            }
            case WEST:
            {
                return WEST;
            }
        }
        return super.getShape(state, worldIn, pos, context);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (stateIn.get(WATERLOGGED))
        {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid)
    {
        if (world.getTileEntity(pos) instanceof BackpackTileEntity)
        {
            BackpackTileEntity tileEntity = (BackpackTileEntity) world.getTileEntity(pos);
            world.addEntity(new ItemEntity(world, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, tileEntity.backpack));
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_FACING, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new BackpackTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (handIn.equals(Hand.OFF_HAND) || BackpackModHelper.areWeOnClient(worldIn))
        {
            return ActionResultType.SUCCESS;
        }
        if (worldIn.getTileEntity(pos) instanceof BackpackTileEntity)
        {
            BackpackTileEntity tileEntity = (BackpackTileEntity) worldIn.getTileEntity(pos);
            AbstractBackpackItem backpackItem = (AbstractBackpackItem) tileEntity.backpack.getItem();
            backpackItem.openContainer(worldIn, player, tileEntity.backpack);
            return ActionResultType.SUCCESS;
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
    {
        if (world.getTileEntity(pos) instanceof BackpackTileEntity)
        {
            return ((BackpackTileEntity) world.getTileEntity(pos)).backpack;
        }
        return super.getPickBlock(state, target, world, pos, player);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (worldIn.getTileEntity(pos) instanceof BackpackTileEntity)
        {
            BackpackTileEntity tileEntity = (BackpackTileEntity) worldIn.getTileEntity(pos);
            tileEntity.backpack = stack.copy();
            worldIn.notifyBlockUpdate(pos, state, state, 3);
        }
    }
}