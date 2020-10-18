package com.blockvader.wtimprovements.init;

import com.blockvader.wtimprovements.WTImprovements;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = WTImprovements.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class InitPotionTypes {

	//Vanilla
	public static Potion MIGHTY_NIGHT_VISION;
	public static Potion MIGHTY_INVISIBILITY;
	public static Potion MIGHTY_LEAPING;
	public static Potion MIGHTY_FIRE_RESISTANCE;
	public static Potion MIGHTY_SWIFTNESS;
	public static Potion MIGHTY_TURTLE_MASTER;
	public static Potion MIGHTY_WATER_BREATHING;
	public static Potion MIGHTY_HEALING;
	public static Potion MIGHTY_REGENERATION;
	public static Potion MIGHTY_STRENGTH;
	public static Potion MIGHTY_SLOW_FALLING;
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
		MIGHTY_NIGHT_VISION = new Potion("mighty_night_vision", new EffectInstance(Effects.NIGHT_VISION, 24000)).setRegistryName("mighty_night_vision");
		event.getRegistry().register(MIGHTY_NIGHT_VISION);
		MIGHTY_INVISIBILITY = new Potion("mighty_invisibility", new EffectInstance(Effects.INVISIBILITY, 24000)).setRegistryName("mighty_invisibility");
		event.getRegistry().register(MIGHTY_INVISIBILITY);
		MIGHTY_LEAPING = new Potion("mighty_leaping", new EffectInstance(Effects.JUMP_BOOST, 4800, 1)).setRegistryName("mighty_leaping");
		event.getRegistry().register(MIGHTY_LEAPING);
		MIGHTY_FIRE_RESISTANCE = new Potion("mighty_fire_resistance", new EffectInstance(Effects.FIRE_RESISTANCE, 24000)).setRegistryName("mighty_fire_resistance");
		event.getRegistry().register(MIGHTY_FIRE_RESISTANCE);
		MIGHTY_SWIFTNESS = new Potion("mighty_swiftness", new EffectInstance(Effects.SPEED, 4800, 1)).setRegistryName("mighty_swiftness");
		event.getRegistry().register(MIGHTY_SWIFTNESS);
		MIGHTY_TURTLE_MASTER = new Potion("mighty_turtle_master", new EffectInstance(Effects.SLOWNESS, 1060, 5), new EffectInstance(Effects.RESISTANCE, 1060, 3)).setRegistryName("mighty_turtle_master");
		event.getRegistry().register(MIGHTY_TURTLE_MASTER);
		MIGHTY_WATER_BREATHING = new Potion("mighty_water_breathing", new EffectInstance(Effects.INVISIBILITY, 24000)).setRegistryName("mighty_water_breathing");
		event.getRegistry().register(MIGHTY_WATER_BREATHING);
		MIGHTY_HEALING = new Potion("mighty_healing", new EffectInstance(Effects.INSTANT_HEALTH, 1, 2)).setRegistryName("mighty_healing");
		event.getRegistry().register(MIGHTY_HEALING);
		MIGHTY_REGENERATION = new Potion("mighty_regeneration", new EffectInstance(Effects.REGENERATION, 1200, 1)).setRegistryName("mighty_regeneration");
		event.getRegistry().register(MIGHTY_REGENERATION);
		MIGHTY_STRENGTH = new Potion("mighty_strength", new EffectInstance(Effects.STRENGTH, 4800, 1)).setRegistryName("mighty_strength");
		event.getRegistry().register(MIGHTY_STRENGTH);
		MIGHTY_SLOW_FALLING = new Potion("mighty_slow_falling", new EffectInstance(Effects.SLOW_FALLING, 12000)).setRegistryName("mighty_slow_falling");
		event.getRegistry().register(MIGHTY_SLOW_FALLING);
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
