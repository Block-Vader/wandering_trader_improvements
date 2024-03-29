package com.blockvader.wtimprovements;

import java.util.Optional;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.passive.horse.TraderLlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.GameRules;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WanderingTraderVillageSpawner {
	
	private ServerWorld world;
	private Random random;
	
	@SubscribeEvent
	public void tick(WorldTickEvent event)
	{
		if (event.phase == TickEvent.Phase.END) return;
		if (event.world.getGameRules().getBoolean(GameRules.field_230128_E_) && event.world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING) && event.world instanceof ServerWorld)
		{
			this.world = (ServerWorld) event.world;
			this.random = this.world.getRandom();
			int spawnDelay = TraderSpawnSavedData.get(this.world).getSpawnDelay();
			++spawnDelay;
			TraderSpawnSavedData.get(this.world).setSpawnDelay(spawnDelay);
			if (world.getDayTime() % 24000 == 450)
			{
				int daysSinceSpawned = spawnDelay/24000;
				if (random.nextInt(8) < daysSinceSpawned)
				{
					PlayerEntity playerentity = this.world.getRandomPlayer();
					if (playerentity != null)
					{
						BlockPos blockpos = playerentity.func_233580_cy_();
						PointOfInterestManager pointofinterestmanager = this.world.getPointOfInterestManager();
						Optional<BlockPos> optional = pointofinterestmanager.find(PointOfInterestType.MEETING.getPredicate(), (p_221241_0_) -> {
							return true;
						}, blockpos, 48, PointOfInterestManager.Status.ANY);
						if (optional.isPresent()) //Only spawns trader if village meeting point is found, otherwise similar to WanderingTraderSpawner
						{
							BlockPos blockpos1 = optional.get();
							BlockPos blockpos2 = this.getSpawnPos(blockpos1, 48);
							if (blockpos2 != null && this.isSpawnPosEmpty(blockpos2))
							{
								if (this.world.func_242406_i(blockpos2).equals(Optional.of(Biomes.THE_VOID))) return;
								WanderingTraderEntity wanderingtraderentity = EntityType.WANDERING_TRADER.spawn(this.world, (CompoundNBT)null, (ITextComponent)null, (PlayerEntity)null, blockpos2, SpawnReason.EVENT, false, false);
								if (wanderingtraderentity != null)
								{
									for(int j = 0; j < 2; ++j)
									{
										this.spawnLlamas(wanderingtraderentity, 4);
									}
									//this.world.getWorldInfo().func_230394_a_(wanderingtraderentity.getUniqueID());
									wanderingtraderentity.setDespawnDelay(11600);
									wanderingtraderentity.setWanderTarget(blockpos1);
									wanderingtraderentity.setHomePosAndDistance(blockpos1, 16);
									TraderSpawnSavedData.get(this.world).setSpawnDelay(0);
								}
							}
						}
					}
				}
			}
		}
	}
	
	@Nullable
	private BlockPos getSpawnPos(BlockPos pos, int radius)
	{
		BlockPos blockpos = null;
		for(int i = 0; i < 10; ++i)
		{
			int j = pos.getX() + this.random.nextInt(radius * 2) - radius;
			int k = pos.getZ() + this.random.nextInt(radius * 2) - radius;
			int l = this.world.getHeight(Heightmap.Type.WORLD_SURFACE, j, k);
			BlockPos blockpos1 = new BlockPos(j, l, k);
			if (WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, this.world, blockpos1, EntityType.WANDERING_TRADER))
			{
				blockpos = blockpos1;
				break;
			}
		}
		return blockpos;
	}
	
	private boolean isSpawnPosEmpty(BlockPos pos)
	{
		for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos, pos.add(1, 2, 1)))
		{
			if (!this.world.getBlockState(blockpos).getCollisionShape(this.world, blockpos).isEmpty())
			{
				return false;
			}
		}
		return true;
	}
	
	private void spawnLlamas(WanderingTraderEntity trader, int radius)
	{
		BlockPos blockpos = llamaSpawnPos(new BlockPos(trader.getPositionVec()), radius);
		if (blockpos != null)
		{
			TraderLlamaEntity traderllamaentity = EntityType.TRADER_LLAMA.spawn(world, (CompoundNBT)null, (ITextComponent)null, (PlayerEntity)null, blockpos, SpawnReason.EVENT, false, false);
			if (traderllamaentity != null)
			{
				traderllamaentity.setLeashHolder(trader, true);
			}
		}
	}
	
	@Nullable
	private BlockPos llamaSpawnPos(BlockPos pos, int radius)
	{
		BlockPos blockpos = null;
		for(int i = 0; i < 10; ++i)
		{
			int j = pos.getX() + this.world.getRandom().nextInt(radius * 2) - radius;
			int k = pos.getZ() + this.world.getRandom().nextInt(radius * 2) - radius;
			int l = world.getHeight(Heightmap.Type.WORLD_SURFACE, j, k);
			BlockPos blockpos1 = new BlockPos(j, l, k);
			if (WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, this.world, blockpos1, EntityType.WANDERING_TRADER))
			{
				blockpos = blockpos1;
				break;
			}
		}
		return blockpos;
	}
}