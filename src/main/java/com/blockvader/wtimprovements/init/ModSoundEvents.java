package com.blockvader.wtimprovements.init;

import com.blockvader.wtimprovements.WTImprovements;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSoundEvents {
	
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WTImprovements.MOD_ID);
	
	public static final RegistryObject<SoundEvent> BLOCK_TRADE_STATION_DING = SOUND_EVENTS.register("block_trade_station_ding", () -> new SoundEvent(new ResourceLocation(WTImprovements.MOD_ID, "block.trade_station.ding")));

}