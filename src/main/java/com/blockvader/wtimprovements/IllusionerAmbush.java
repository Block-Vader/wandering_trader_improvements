package com.blockvader.wtimprovements;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.IllusionerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = WTImprovements.MOD_ID)
public class IllusionerAmbush {
	
	@SubscribeEvent
	public static void onTraderSpawn(LivingSpawnEvent.SpecialSpawn event)
	{
		LivingEntity entity = event.getEntityLiving();
		if (entity instanceof WanderingTraderEntity && event.getSpawnReason() == SpawnReason.EVENT)
		{
			if (entity.getRNG().nextInt(10) == 0)
			{
				entity.addTag("disguisedIllusioner");
			}
		}
	}
	
	@SubscribeEvent
	public static void onTraderInteract(PlayerInteractEvent.EntityInteract event)
	{
		Entity entity = event.getTarget();
		if (entity.getType() == EntityType.WANDERING_TRADER && !event.getWorld().isRemote())
		{
			if (entity.getTags().contains("disguisedIllusioner"))
			{
				transformToIllusioner(((WanderingTraderEntity)entity), event.getPlayer());
			}
		}
	}
	
	@SubscribeEvent
	public static void onTraderAttacked(LivingAttackEvent event)
	{
		LivingEntity entity = event.getEntityLiving();
		if (entity instanceof WanderingTraderEntity && event.getSource().getTrueSource() instanceof PlayerEntity && !entity.getEntityWorld().isRemote())
		{
			if (entity.getTags().contains("disguisedIllusioner"))
			{
				transformToIllusioner(((WanderingTraderEntity)entity), ((PlayerEntity)event.getSource().getTrueSource()));
			}
		}
	}
	
	@SubscribeEvent
	public static void onLoottableLoad(LootTableLoadEvent event)
	{
		if (event.getName().toString().equals("minecraft:entities/illusioner"))
		{
			LootTable table = event.getLootTableManager().getLootTableFromLocation(new ResourceLocation(WTImprovements.MOD_ID + ":injects/illusioner"));
			LootPool pool = table.getPool(WTImprovements.MOD_ID + ":totem_of_decoy");
			event.getTable().addPool(pool);
		}
	}
	
	public static void transformToIllusioner(WanderingTraderEntity trader, PlayerEntity cause)
	{
		IllusionerEntity illusioner = EntityType.ILLUSIONER.create(trader.getEntityWorld());
		illusioner.copyLocationAndAnglesFrom(trader);
		illusioner.setNoAI(trader.isAIDisabled());
		illusioner.enablePersistence();
		World world = trader.getWorld();
		world.addEntity(illusioner);
		trader.remove();
		((ServerWorld)world).spawnParticle(ParticleTypes.SMOKE, illusioner.getPositionVector().x, illusioner.getPositionVector().y, illusioner.getPositionVector().z, 50, 0.2, 1, 0.2, 0.1);
		illusioner.playSound(SoundEvents.ENTITY_WANDERING_TRADER_DISAPPEARED, 1.0F, illusioner.getRNG().nextFloat() * 0.2F + 0.9F);
	}
}
