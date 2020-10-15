package com.blockvader.wtimprovements.init;

import com.blockvader.wtimprovements.TradeStationTileEntity;
import com.blockvader.wtimprovements.WTImprovements;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = WTImprovements.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class InitTileEntities {

	public static TileEntityType<TradeStationTileEntity> TRADE_STATION;

	@SubscribeEvent
	public static void onTERegistry(final RegistryEvent.Register<TileEntityType<?>> event)
	{
		TRADE_STATION = TileEntityType.Builder.create(TradeStationTileEntity::new, InitBlocks.TRADE_STATION).build(null);
		TRADE_STATION.setRegistryName(WTImprovements.MOD_ID, "trade_station");
		event.getRegistry().register(TRADE_STATION);
	}
	
}