package com.blockvader.wtimprovements.init;

import com.blockvader.wtimprovements.DecoyEntity;
import com.blockvader.wtimprovements.WTImprovements;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = WTImprovements.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEntities {
	
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, WTImprovements.MOD_ID);
	
	public static final RegistryObject<EntityType<DecoyEntity>> DECOY = ENTITY_TYPES.register("decoy", () -> EntityType.Builder.create(DecoyEntity::new, EntityClassification.MISC).size(0.6F, 1.8F).build(WTImprovements.MOD_ID+":decoy"));
	
	@SubscribeEvent
	public static void onEntityTypeRegistry(RegistryEvent.Register<EntityType<?>> event)
	{
		GlobalEntityTypeAttributes.put(DECOY.get(), MobEntity.func_233666_p_().func_233813_a_());
	}
}