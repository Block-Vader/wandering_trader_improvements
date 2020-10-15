package com.blockvader.wtimprovements.init;

import com.blockvader.wtimprovements.TradeStationBlock;
import com.blockvader.wtimprovements.WTImprovements;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = WTImprovements.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class InitBlocks {

	public static Block TRADE_STATION;
	
	@SubscribeEvent
	public static void onBlocksRegistry(final RegistryEvent.Register<Block> event)
	{
		TRADE_STATION = new TradeStationBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.5F).sound(SoundType.WOOD)).setRegistryName("trade_station");
		event.getRegistry().register(TRADE_STATION);
	}
	
	@SubscribeEvent
	public static void onItemRegistry(final RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(new BlockItem(TRADE_STATION, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName("trade_station"));
	}
	
}
