package com.blockvader.wtimprovements.client;

import com.blockvader.wtimprovements.init.InitDecoy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy {
	
	public static void registerRenderers()
	{
		Minecraft.getInstance().getItemRenderer().getItemModelMesher().register(InitDecoy.TOTEM_OF_DECOY, new ModelResourceLocation(InitDecoy.TOTEM_OF_DECOY.getRegistryName(), "inventory"));
		RenderingRegistry.registerEntityRenderingHandler(InitDecoy.DECOY, DecoyRenderer::new);
	}

}
