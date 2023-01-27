package com.blockvader.wtimprovements.init;

import com.blockvader.wtimprovements.DecoyTotemItem;
import com.blockvader.wtimprovements.WTImprovements;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = WTImprovements.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WTImprovements.MOD_ID);
	
	public static final RegistryObject<Item> TOTEM_OF_DECOY = ITEMS.register("totem_of_decoy", () -> new DecoyTotemItem(new Properties().stacksTo(16).rarity(Rarity.UNCOMMON).tab(ItemGroup.TAB_COMBAT)));
	public static final RegistryObject<Item> TRADE_STATION = ITEMS.register("trade_station", () -> new BlockItem(ModBlocks.TRADE_STATION.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));

}
