package com.blockvader.wtimprovements;

import java.io.File;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.storage.WorldSavedData;

public class TraderSpawnSavedData extends WorldSavedData {
	
	private int spawnDelay;
	
	public TraderSpawnSavedData() {
		super(WTImprovements.MOD_ID + ":trader_spawn_data");
	}
	
	public TraderSpawnSavedData(String name) {
		super(name);
	}

	@Override
	public void load(CompoundNBT nbt)
	{
		this.spawnDelay = nbt.getInt("spawnDelay");
	}

	@Override
	public CompoundNBT save(CompoundNBT compound)
	{
		compound.putInt("spawnDelay", spawnDelay);
		return compound;
	}
	
	@Override
	public void save(File fileIn) {
		super.save(fileIn);
	}
	
	public int getSpawnDelay() {
		return this.spawnDelay;
	}
	
	public void setSpawnDelay(int i) {
		this.spawnDelay = i;
	}
}