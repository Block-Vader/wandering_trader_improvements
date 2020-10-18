package com.blockvader.wtimprovements.init;

import com.blockvader.wtimprovements.WTImprovements;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = WTImprovements.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModSoundEvents {
	
	public static SoundEvent BLOCK_TRADE_STATION_DING;

	@SubscribeEvent
	public static void onSoundRegistry(final RegistryEvent.Register<SoundEvent> event)
	{
		BLOCK_TRADE_STATION_DING = new SoundEvent(new ResourceLocation(WTImprovements.MOD_ID, "block.trade_station.ding"));
		BLOCK_TRADE_STATION_DING.setRegistryName(WTImprovements.MOD_ID, "block_trade_station_ding");
		event.getRegistry().register(BLOCK_TRADE_STATION_DING);
	}
}
