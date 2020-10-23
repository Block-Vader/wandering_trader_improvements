package com.blockvader.wtimprovements.init;

import com.blockvader.wtimprovements.DecoyEntity;
import com.blockvader.wtimprovements.DecoyTotemItem;
import com.blockvader.wtimprovements.WTImprovements;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = WTImprovements.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class InitDecoy {
	
	public static EntityType<DecoyEntity> DECOY;
	
	public static Item TOTEM_OF_DECOY;
	
	@SubscribeEvent
	public static void onEntityTypeRegistry(RegistryEvent.Register<EntityType<?>> event)
	{
		DECOY = EntityType.Builder.create(DecoyEntity::new, EntityClassification.MISC).size(0.6F, 1.8F).build(WTImprovements.MOD_ID+":decoy");
		DECOY.setRegistryName("decoy");
		event.getRegistry().register(DECOY);
		GlobalEntityTypeAttributes.put(DECOY, MobEntity.func_233666_p_().func_233813_a_());
	}
	
	@SubscribeEvent
	public static void onItemRegistry(RegistryEvent.Register<Item> event)
	{
		TOTEM_OF_DECOY = new DecoyTotemItem(new Properties().maxStackSize(16).rarity(Rarity.UNCOMMON).group(ItemGroup.COMBAT)).setRegistryName("totem_of_decoy");
		event.getRegistry().register(TOTEM_OF_DECOY);
	}
}