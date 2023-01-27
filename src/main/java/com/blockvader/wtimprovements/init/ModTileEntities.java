package com.blockvader.wtimprovements.init;

import com.blockvader.wtimprovements.TradeStationTileEntity;
import com.blockvader.wtimprovements.WTImprovements;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = WTImprovements.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModTileEntities {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, WTImprovements.MOD_ID);
	
	public static final RegistryObject<TileEntityType<TradeStationTileEntity>> TRADE_STATION = TILE_ENTITY_TYPES.register("trade_station", () -> TileEntityType.Builder.of(TradeStationTileEntity::new, ModBlocks.TRADE_STATION.get()).build(null));
	
}