package com.blockvader.wtimprovements.client;

import com.blockvader.wtimprovements.init.ModBlocks;
import com.blockvader.wtimprovements.init.ModEntities;
import com.blockvader.wtimprovements.init.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy {
	
	public static void registerRenderers()
	{
		Minecraft.getInstance().getItemRenderer().getItemModelMesher().register(ModItems.TOTEM_OF_DECOY.get(), new ModelResourceLocation(ModItems.TOTEM_OF_DECOY.getId(), "inventory"));
		Minecraft.getInstance().getItemRenderer().getItemModelMesher().register(ModItems.TRADE_STATION.get(), new ModelResourceLocation(ModBlocks.TRADE_STATION.getId(), "inventory"));
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.DECOY.get(), DecoyRenderer::new);
	}
}
