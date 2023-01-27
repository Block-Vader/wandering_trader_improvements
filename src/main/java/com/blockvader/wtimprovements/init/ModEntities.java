package com.blockvader.wtimprovements.init;

import com.blockvader.wtimprovements.DecoyEntity;
import com.blockvader.wtimprovements.WTImprovements;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = WTImprovements.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEntities {
	
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, WTImprovements.MOD_ID);
	
	public static final RegistryObject<EntityType<DecoyEntity>> DECOY = ENTITY_TYPES.register("decoy", () -> EntityType.Builder.of(DecoyEntity::new, EntityClassification.MISC).sized(0.6F, 1.8F).build(WTImprovements.MOD_ID+":decoy"));
	
	@SubscribeEvent
	public static void onEntityTypeRegistry(EntityAttributeCreationEvent event) {
		event.put(DECOY.get(), MobEntity.createMobAttributes().build());
	}
}