package com.blockvader.wtimprovements;

import com.blockvader.wtimprovements.client.ClientProxy;
import com.blockvader.wtimprovements.init.ModBlocks;
import com.blockvader.wtimprovements.init.ModEntities;
import com.blockvader.wtimprovements.init.ModItems;
import com.blockvader.wtimprovements.init.ModPotions;
import com.blockvader.wtimprovements.init.ModSoundEvents;
import com.blockvader.wtimprovements.init.ModTileEntities;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WTImprovements.MOD_ID)
public class WTImprovements
{
	public static final String MOD_ID = "wt_improvements";
	public static final String QUARK_ID = "quark";
	public static final String ATMOSPHERIC_ID = "atmospheric";
	public static final String UPGRADE_AQUATIC_ID = "upgrade_aquatic";
	public static final String BUZZIER_BEES_ID = "buzzierbees";

	public WTImprovements()
	{	
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new WanderingTraderVillageSpawner());
        
        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntities.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModPotions.POTIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModSoundEvents.SOUND_EVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModTileEntities.TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
    	ClientProxy.registerRenderers();
    }
}