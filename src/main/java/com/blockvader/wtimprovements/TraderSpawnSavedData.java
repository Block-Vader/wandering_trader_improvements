package com.blockvader.wtimprovements;

import java.io.File;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

public class TraderSpawnSavedData extends WorldSavedData{
	
	private int spawnDelay;
	
	public TraderSpawnSavedData() {
		super(WTImprovements.MOD_ID + ":trader_spawn_data");
	}
	
	public TraderSpawnSavedData(String name) {
		super(name);
	}

	@Override
	public void read(CompoundNBT nbt)
	{
		this.spawnDelay = nbt.getInt("spawnDelay");
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		compound.putInt("spawnDelay", spawnDelay);
		return compound;
	}
	
	@Override
	public void save(File fileIn) {
		super.save(fileIn);
	}
	
	public int getSpawnDelay()
	{
		return this.spawnDelay;
	}
	
	public void setSpawnDelay(int i)
	{
		this.spawnDelay = i;
	}
	
	public static TraderSpawnSavedData get(ServerWorld world)
	{
		TraderSpawnSavedData instance = world.getServer().getWorld(World.field_234918_g_).getSavedData().get(() -> {
			return new TraderSpawnSavedData();
			}, WTImprovements.MOD_ID + ":trader_spawn_data");
		if (instance == null)
		{
			instance = new TraderSpawnSavedData();
			world.getServer().getWorld(World.field_234918_g_).getSavedData().set(instance);
		}
		instance.markDirty();
		return instance;
	}
}