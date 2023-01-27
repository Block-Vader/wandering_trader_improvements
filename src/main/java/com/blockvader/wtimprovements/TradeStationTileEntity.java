package com.blockvader.wtimprovements;

import com.blockvader.wtimprovements.init.ModTileEntities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class TradeStationTileEntity extends TileEntity {

	public TradeStationTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}
	
	public TradeStationTileEntity() {
		this(ModTileEntities.TRADE_STATION.get());
	}

}