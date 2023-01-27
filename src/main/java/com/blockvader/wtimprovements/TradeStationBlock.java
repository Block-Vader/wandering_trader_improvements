package com.blockvader.wtimprovements;

import java.util.Random;

import com.blockvader.wtimprovements.init.ModTileEntities;
import com.blockvader.wtimprovements.init.ModSoundEvents;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TradeStationBlock extends Block {
	
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	protected static final VoxelShape LOWER_PART = Block.box(0.0F, 0.0F, 0.0F, 16.0F, 12.0F, 16.0F);
	protected static final VoxelShape UPPER_PART_NORTH = Block.box(0.0F, 12.0F, 8.0F, 16.0F, 16.0F, 16.0F);
	protected static final VoxelShape UPPER_PART_SOUTH = Block.box(0.0F, 12.0F, 0.0F, 16.0F, 16.0F, 8.0F);
	protected static final VoxelShape UPPER_PART_EAST = Block.box(0.0F, 12.0F, 0.0F, 8.0F, 16.0F, 16.0F);
	protected static final VoxelShape UPPER_PART_WEST = Block.box(8.0F, 12.0F, 0.0F, 16.0F, 16.0F, 16.0F);
	
	public TradeStationBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, Boolean.valueOf(false)));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.getValue(FACING))
		{
		case EAST: return VoxelShapes.or(LOWER_PART, UPPER_PART_EAST);
		case WEST: return VoxelShapes.or(LOWER_PART, UPPER_PART_WEST);
		case SOUTH: return VoxelShapes.or(LOWER_PART, UPPER_PART_SOUTH);
		default: return VoxelShapes.or(LOWER_PART, UPPER_PART_NORTH);
		}
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
		builder.add(POWERED);
	}
	
	@Override
	public int getSignal(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
		return blockState.getValue(POWERED) == false ? 0 : 16;
	}
	
	@Override
	public boolean isSignalSource(BlockState state) {
		return true;
	}
	
	public void activate(BlockState state, BlockPos pos, World world) {
		world.playSound(null, pos, ModSoundEvents.BLOCK_TRADE_STATION_DING.get(), SoundCategory.BLOCKS, 1.0f, 1.0f);
		world.setBlock(pos, state.setValue(POWERED, Boolean.valueOf(true)), 3);
		world.updateNeighborsAt(pos, this);
		world.getBlockTicks().scheduleTick(pos, this, 20);
	}
	
	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
		world.setBlock(pos, state.setValue(POWERED, Boolean.valueOf(false)), 3);
		world.updateNeighborsAt(pos, this);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return ModTileEntities.TRADE_STATION.get().create();
	}

}