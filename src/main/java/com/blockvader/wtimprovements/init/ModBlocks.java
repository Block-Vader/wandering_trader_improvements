package com.blockvader.wtimprovements.init;

import com.blockvader.wtimprovements.TradeStationBlock;
import com.blockvader.wtimprovements.WTImprovements;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WTImprovements.MOD_ID);
	
	public static final RegistryObject<Block> TRADE_STATION = BLOCKS.register("trade_station", () -> new TradeStationBlock(Block.Properties.of(Material.WOOD).strength(2.5F).sound(SoundType.WOOD)));

}