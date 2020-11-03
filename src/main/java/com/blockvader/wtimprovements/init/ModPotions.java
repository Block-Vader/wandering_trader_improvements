package com.blockvader.wtimprovements.init;

import com.blockvader.wtimprovements.WTImprovements;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = WTImprovements.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModPotions {
	
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, WTImprovements.MOD_ID);

	//Vanilla
	public static final RegistryObject<Potion> MIGHTY_NIGHT_VISION = POTIONS.register("mighty_night_vision", () -> new Potion(new EffectInstance(Effects.NIGHT_VISION, 24000)));
	public static final RegistryObject<Potion> MIGHTY_INVISIBILITY = POTIONS.register("mighty_invisibility", () -> new Potion(new EffectInstance(Effects.INVISIBILITY, 24000)));
	public static final RegistryObject<Potion> MIGHTY_LEAPING = POTIONS.register("mighty_leaping", () -> new Potion(new EffectInstance(Effects.JUMP_BOOST, 4800, 1)));
	public static final RegistryObject<Potion> MIGHTY_FIRE_RESISTANCE = POTIONS.register("mighty_fire_resistance", () -> new Potion(new EffectInstance(Effects.FIRE_RESISTANCE, 24000)));
	public static final RegistryObject<Potion> MIGHTY_SWIFTNESS = POTIONS.register("mighty_swiftness", () -> new Potion(new EffectInstance(Effects.SPEED, 4800, 1)));
	public static final RegistryObject<Potion> MIGHTY_TURTLE_MASTER = POTIONS.register("mighty_turtle_master", () -> new Potion(new EffectInstance(Effects.SLOWNESS, 1060, 5), new EffectInstance(Effects.RESISTANCE, 1060, 3)));
	public static final RegistryObject<Potion> MIGHTY_WATER_BREATHING = POTIONS.register("mighty_water_breathing", () -> new Potion(new EffectInstance(Effects.WATER_BREATHING, 24000)));
	public static final RegistryObject<Potion> MIGHTY_HEALING = POTIONS.register("mighty_healing", () -> new Potion(new EffectInstance(Effects.INSTANT_HEALTH, 1, 2)));
	public static final RegistryObject<Potion> MIGHTY_REGENERATION = POTIONS.register("mighty_regeneration", () -> new Potion(new EffectInstance(Effects.REGENERATION, 1200, 1)));
	public static final RegistryObject<Potion> MIGHTY_STRENGTH = POTIONS.register("mighty_strength", () -> new Potion(new EffectInstance(Effects.STRENGTH, 4800, 1)));
	public static final RegistryObject<Potion> MIGHTY_SLOW_FALLING = POTIONS.register("mighty_slow_falling", () -> new Potion(new EffectInstance(Effects.SLOW_FALLING, 12000)));
	//Quark
	public static Potion MIGHTY_FORTITUDE;
	public static Potion MIGHTY_DANFER_SIGHT;
	public static Potion MIGHTY_STABILITY;
	//Atmospheric
	public static Potion MIGHTY_RELIEF;
	//Buzzier bees
	public static Potion MIGHTY_LUCK;
	//Upgrade aquatic
	public static Potion MIGHTY_RESTFULNESS;
	public static Potion MIGHTY_REPELLENCE;
	public static Potion MIGHTY_VIBING;
	
	public static boolean fortitudeCreated;
	public static boolean dangerSightCreated;
	public static boolean stabilityCreated;
	public static boolean reliefCreated;
	public static boolean luckCreated;
	public static boolean restfulnessCreated;
	public static boolean repellenceCreated;
	public static boolean vibingCreated;
	
	@SubscribeEvent
	public static void onPotionTypeRegistry(RegistryEvent.Register<Potion> event)
	{
		if (ModList.get().isLoaded(WTImprovements.QUARK_ID))
		{
			ResourceLocation dangerSight = new ResourceLocation(WTImprovements.QUARK_ID, "danger_sight");
			if (ForgeRegistries.POTIONS.containsKey(dangerSight))
			{
				MIGHTY_DANFER_SIGHT = new Potion(new EffectInstance(ForgeRegistries.POTIONS.getValue(dangerSight), 24000)).setRegistryName("mighty_danger_sight");
				event.getRegistry().register(MIGHTY_DANFER_SIGHT);
				dangerSightCreated = true;
			}
			ResourceLocation resilience = new ResourceLocation(WTImprovements.QUARK_ID, "resilience");
			if (ForgeRegistries.POTIONS.containsKey(resilience))
			{
				MIGHTY_STABILITY = new Potion(new EffectInstance(ForgeRegistries.POTIONS.getValue(resilience), 4800, 1)).setRegistryName("mighty_stability");
				event.getRegistry().register(MIGHTY_STABILITY);
				stabilityCreated = true;
			}
			MIGHTY_FORTITUDE = new Potion(new EffectInstance(Effects.RESISTANCE, 4800, 1)).setRegistryName("mighty_fortitude");
			event.getRegistry().register(MIGHTY_FORTITUDE);
			fortitudeCreated = true;
		}
		if (ModList.get().isLoaded(WTImprovements.ATMOSPHERIC_ID))
		{
			ResourceLocation relief = new ResourceLocation(WTImprovements.ATMOSPHERIC_ID, "relief");
			if (ForgeRegistries.POTIONS.containsKey(relief))
			{
				MIGHTY_RELIEF = new Potion(new EffectInstance(ForgeRegistries.POTIONS.getValue(relief), 24000)).setRegistryName("mighty_relief");
				event.getRegistry().register(MIGHTY_RELIEF);
				reliefCreated = true;
			}
		}
		if (ModList.get().isLoaded(WTImprovements.UPGRADE_AQUATIC_ID))
		{
			ResourceLocation restfulness = new ResourceLocation(WTImprovements.UPGRADE_AQUATIC_ID, "restfulness");
			if (ForgeRegistries.POTIONS.containsKey(restfulness))
			{
				MIGHTY_RESTFULNESS = new Potion(new EffectInstance(ForgeRegistries.POTIONS.getValue(restfulness), 1, 2)).setRegistryName("mighty_restfulness");
				event.getRegistry().register(MIGHTY_RESTFULNESS);
				restfulnessCreated = true;
			}
			ResourceLocation repellence = new ResourceLocation(WTImprovements.UPGRADE_AQUATIC_ID, "repellence");
			if (ForgeRegistries.POTIONS.containsKey(repellence))
			{
				MIGHTY_REPELLENCE = new Potion(new EffectInstance(ForgeRegistries.POTIONS.getValue(repellence), 4800, 1)).setRegistryName("mighty_repellence");
				event.getRegistry().register(MIGHTY_REPELLENCE);
				repellenceCreated = true;
			}
			ResourceLocation vibing = new ResourceLocation(WTImprovements.UPGRADE_AQUATIC_ID, "vibing");
			if (ForgeRegistries.POTIONS.containsKey(vibing))
			{
				MIGHTY_VIBING = new Potion(new EffectInstance(ForgeRegistries.POTIONS.getValue(vibing), 4800, 1)).setRegistryName("mighty_vibing");
				event.getRegistry().register(MIGHTY_VIBING);
				vibingCreated = true;
			}
		}
		if (ModList.get().isLoaded(WTImprovements.BUZZIER_BEES_ID))
		{
			MIGHTY_LUCK = new Potion(new EffectInstance(Effects.LUCK, 9600, 1)).setRegistryName("mighty_luck");
			event.getRegistry().register(MIGHTY_LUCK);
			luckCreated = true;
		}
	}
}
