package com.blockvader.wtimprovements.client;

import com.blockvader.wtimprovements.init.InitBlocks;
import com.blockvader.wtimprovements.init.InitDecoy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy {
	
	@SuppressWarnings("deprecation")
	public static void registerRenderers()
	{
		Minecraft.getInstance().getItemRenderer().getItemModelMesher().register(InitDecoy.TOTEM_OF_DECOY, new ModelResourceLocation(InitDecoy.TOTEM_OF_DECOY.getRegistryName(), "inventory"));
		Minecraft.getInstance().getItemRenderer().getItemModelMesher().register(Item.getItemFromBlock(InitBlocks.TRADE_STATION), new ModelResourceLocation(Item.getItemFromBlock(InitBlocks.TRADE_STATION).getRegistryName(), "inventory"));
		RenderingRegistry.registerEntityRenderingHandler(InitDecoy.DECOY, DecoyRenderer::new);
	}
}
