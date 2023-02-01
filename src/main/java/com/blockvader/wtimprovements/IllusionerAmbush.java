package com.blockvader.wtimprovements;

import java.util.ArrayList;
import java.util.Map;

import com.blockvader.wtimprovements.init.ModItems;
import com.blockvader.wtimprovements.init.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.IllusionerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = WTImprovements.MOD_ID)
public class IllusionerAmbush {
	
	@SubscribeEvent
	public static void onTraderSpawn(LivingSpawnEvent.SpecialSpawn event) {
		LivingEntity entity = event.getEntityLiving();
		if (entity instanceof WanderingTraderEntity && event.getSpawnReason() == SpawnReason.EVENT) {
			if (entity.getRandom().nextInt(10) == 0) {
				entity.addTag("disguisedIllusioner");
			}
			//Moves the trader to a trade station if one is close enough
			if (event.getWorld() instanceof ServerWorld) {
				ServerWorld world = (ServerWorld) event.getWorld();
				IChunk spawnChunk = world.getChunk(event.getEntity().blockPosition());
				ArrayList<Chunk> chunks = getChunksInRadius(world, spawnChunk.getPos(), 7);
				for (Chunk chunk : chunks) {
					Map<BlockPos, TileEntity> teMap = chunk.getBlockEntities();
					for (TileEntity te: teMap.values()) {
						if (te.getType() == ModTileEntities.TRADE_STATION.get()) {
							BlockPos pos = te.getBlockPos();
							BlockState state = entity.level.getBlockState(pos);
							Direction dir = state.getValue(TradeStationBlock.FACING);
							BlockPos pos1 = pos;
							switch (dir) {
								case NORTH: pos1 = pos.north();
								case SOUTH: pos1 = pos.south();
								case EAST: pos1 = pos.east();
								case WEST:  pos1 = pos.west();
								default: break;
							}
							if (entity.randomTeleport(pos1.getX() + 0.5D, pos1.getY(), pos1.getZ() + 0.5D, false)) {
								//Trade station will play a sound and emit redstone signal
								((TradeStationBlock)state.getBlock()).activate(state, pos, entity.level);
								((WanderingTraderEntity)entity).setWanderTarget(null);
								return;
							}
						}
					}
				}
			}
		}
	}
	
	private static ArrayList<Chunk> getChunksInRadius(ServerWorld w, ChunkPos chunkPos, int radius) {
		ArrayList<Chunk> chunks = new ArrayList<>();
		for(int curZ = - radius; curZ <= radius; curZ++) {
			for(int curX = - radius; curX <= radius; curX++) {
				Chunk chunk = w.getChunk(chunkPos.x + curX, chunkPos.z + curZ);
				chunks.add(chunk);
			}
		}
		return chunks;
	}
	
	@SubscribeEvent
	public static void onTraderInteract(PlayerInteractEvent.EntityInteract event) {
		Entity entity = event.getTarget();
		if (entity.getType() == EntityType.WANDERING_TRADER && !event.getWorld().isClientSide()) {
			if (entity.getTags().contains("disguisedIllusioner")) {
				transformToIllusioner(((WanderingTraderEntity)entity), event.getPlayer());
			}
		}
	}
	
	@SubscribeEvent
	public static void onTraderAttacked(LivingAttackEvent event) {
		LivingEntity entity = event.getEntityLiving();
		if (entity instanceof WanderingTraderEntity && event.getSource().getEntity() instanceof PlayerEntity && !entity.level.isClientSide()) {
			if (entity.getTags().contains("disguisedIllusioner")) {
				transformToIllusioner(((WanderingTraderEntity)entity), ((PlayerEntity)event.getSource().getEntity()));
			}
		}
	}
	
	@SubscribeEvent
	public static void onLoottableLoad(LootTableLoadEvent event) {
		if (event.getName().equals(EntityType.ILLUSIONER.getDefaultLootTable())) {
			LootPool pool = LootPool.lootPool().add(ItemLootEntry.lootTableItem(ModItems.TOTEM_OF_DECOY.get()).apply(SetCount.setCount(new RandomValueRange(2, 3)))).name("totem_of_decoy").build();
			event.getTable().addPool(pool);
		}
	}
	
	public static void transformToIllusioner(WanderingTraderEntity trader, PlayerEntity cause) {
		IllusionerEntity illusioner = EntityType.ILLUSIONER.create(trader.level);
		illusioner.copyPosition(trader);
		illusioner.setNoAi(trader.isNoAi());
		illusioner.setPersistenceRequired();
		World world = trader.level;
		world.addFreshEntity(illusioner);
		trader.remove();
		((ServerWorld)world).sendParticles(ParticleTypes.SMOKE, illusioner.position().x, illusioner.position().y, illusioner.position().z, 50, 0.2, 1, 0.2, 0.1);
		illusioner.playSound(SoundEvents.WANDERING_TRADER_DISAPPEARED, 1.0F, illusioner.getRandom().nextFloat() * 0.2F + 0.9F);
	}
}