package com.blockvader.wtimprovements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.blockvader.wtimprovements.init.InitPotionTypes;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CoralBlock;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = WTImprovements.MOD_ID)
public class TradeAdjustments {
	
	@SubscribeEvent
	public static void onTradeSetup(WandererTradesEvent event)
	{
		List<ITrade> genericTrades = event.getGenericTrades();
		List<ITrade> rareTrades = event.getRareTrades();
		//Didn't find a better way to remove specific trades
		genericTrades.remove(2); //glowstone
		genericTrades.remove(3); //fern
		genericTrades.remove(4); //pumpkin
		genericTrades.remove(6); //dandelion
		genericTrades.remove(6); //poppy
		genericTrades.remove(16); //wheat seeds
		for (int i = 0; i < 16; i++)
		{
			genericTrades.remove(25); //all dyes
		}
		genericTrades.remove(30); //vines
		genericTrades.remove(30); //brown mushroom
		genericTrades.remove(30); //red mushroom
		genericTrades.remove(31); //sand
		rareTrades.remove(4); //gunpowder
		
		genericTrades.add(new ItemsForEmeraldsTrade(Items.SUNFLOWER, 1, 1, 12, 1));
		genericTrades.add(new ItemsForEmeraldsTrade(Items.LILAC, 1, 1, 12, 1));
		genericTrades.add(new ItemsForEmeraldsTrade(Items.ROSE_BUSH, 1, 1, 12, 1));
		genericTrades.add(new ItemsForEmeraldsTrade(Items.PEONY, 1, 1, 12, 1));
		genericTrades.add(new ItemsForEmeraldsTrade(Items.COCOA_BEANS, 3, 1, 12, 1));
		genericTrades.add(new ItemsForEmeraldsTrade(Items.BAMBOO, 3, 1, 12, 1));
		genericTrades.add(new ItemsForEmeraldsTrade(Items.SWEET_BERRIES, 3, 1, 12, 1));
		genericTrades.add(new ItemsForEmeraldsTrade(Items.CARROT, 1, 1, 12, 1));
		genericTrades.add(new ItemsForEmeraldsTrade(Items.POTATO, 1, 1, 12, 1));
		genericTrades.add(new ItemsForEmeraldsTrade(Items.WITHER_ROSE, 4, 1, 6, 1));

		rareTrades.add(new ItemsForEmeraldsTrade(Blocks.MYCELIUM, 3, 3, 6, 1));
		rareTrades.add(new ItemsForEmeraldsTrade(Blocks.SPONGE, 10, 1, 4, 2));
		rareTrades.add(new ItemsForEmeraldsTrade(Items.ENCHANTED_GOLDEN_APPLE, 64, 1, 1, 5));
		rareTrades.add(new TreasureEnchantmentsTrade(1, 3));
		rareTrades.add(new ItemsForEmeraldsTrade(Items.TURTLE_EGG, 6, 1, 4, 1));
		rareTrades.add(new BeeNestTrade(3, 10, 1, 2));
	}
	
	static class BeeNestTrade implements VillagerTrades.ITrade
	{
		private final int beeCount;
		private final int price;
		private final int maxUses;
		private final int givenEXP;
		private final float priceMultiplier;
		
		public BeeNestTrade(int beeCount, int price, int maxUses, int givenEXP) {
			this(beeCount, price, maxUses, givenEXP, 0.05F);
		}
		
		public BeeNestTrade(int beeCount, int price, int maxUses, int givenEXP, float priceMultiplier) {
			this.beeCount = Math.min(beeCount, 3);
			this.price = price;
			this.maxUses = maxUses;
			this.givenEXP = givenEXP;
			this.priceMultiplier = priceMultiplier;
		}
		
		private ItemStack createBeeNest(World worldIn)
		{
			ItemStack stack = new ItemStack(Blocks.field_226905_ma_);
			CompoundNBT compound = new CompoundNBT();
			ListNBT bees = new ListNBT();
			for (int i = 0; i < this.beeCount; i++)
			{
				BeeEntity bee = EntityType.field_226289_e_.create(worldIn);
				CompoundNBT entityData = new CompoundNBT();
				bee.writeUnlessPassenger(entityData);
				CompoundNBT compound1 = new CompoundNBT();
				compound1.put("EntityData", entityData);
				compound1.putInt("MinOccupationTicks", 200 + bee.getRNG().nextInt(400));
				bees.add(compound1);
			}
			compound.put("Bees", bees);
            stack.setTagInfo("BlockEntityTag", compound);
			return stack;
		}

		@Override
		public MerchantOffer getOffer(Entity entity, Random p_221182_2_)
		{
			return new MerchantOffer(new ItemStack(Items.EMERALD, this.price), createBeeNest(entity.getEntityWorld()), this.maxUses, this.givenEXP, this.priceMultiplier);
		}
		
	}
	
	static class MightyPotionsTrade implements VillagerTrades.ITrade
	{
		private final int tier;
		private final int price;
		private final int maxUses;
		private final int givenEXP;
		private final float priceMultiplier;
		
		public MightyPotionsTrade(int tier, int price, int maxUses, int givenEXP) {
			this(tier, price, maxUses, givenEXP, 0.05F);
		}
		
		public MightyPotionsTrade(int tier, int price, int maxUses, int givenEXP, float priceMultiplier) {
			this.tier = tier;
			this.price = price;
			this.maxUses = maxUses;
			this.givenEXP = givenEXP;
			this.priceMultiplier = priceMultiplier;
		}
		
		private ItemStack createMightyPotion(Random rand)
		{
			List<Potion> mightyPotions = new ArrayList<Potion>();
			if (tier == 0)
			{
				mightyPotions.add(InitPotionTypes.MIGHTY_FIRE_RESISTANCE);
				mightyPotions.add(InitPotionTypes.MIGHTY_INVISIBILITY);
				mightyPotions.add(InitPotionTypes.MIGHTY_NIGHT_VISION);
				mightyPotions.add(InitPotionTypes.MIGHTY_SLOW_FALLING);
				mightyPotions.add(InitPotionTypes.MIGHTY_WATER_BREATHING);
				if (InitPotionTypes.dangerSightCreated) mightyPotions.add(InitPotionTypes.MIGHTY_DANFER_SIGHT);
			}
			else {
				mightyPotions.add(InitPotionTypes.MIGHTY_HEALING);
				mightyPotions.add(InitPotionTypes.MIGHTY_LEAPING);
				mightyPotions.add(InitPotionTypes.MIGHTY_REGENERATION);
				mightyPotions.add(InitPotionTypes.MIGHTY_STRENGTH);
				mightyPotions.add(InitPotionTypes.MIGHTY_SWIFTNESS);
				mightyPotions.add(InitPotionTypes.MIGHTY_TURTLE_MASTER);
				if (InitPotionTypes.fortitudeCreated) mightyPotions.add(InitPotionTypes.MIGHTY_FORTITUDE);
				if (InitPotionTypes.stabilityCreated) mightyPotions.add(InitPotionTypes.MIGHTY_STABILITY);
				if (InitPotionTypes.reliefCreated) mightyPotions.add(InitPotionTypes.MIGHTY_RELIEF);
				if (InitPotionTypes.repellenceCreated) mightyPotions.add(InitPotionTypes.MIGHTY_REPELLENCE);
				if (InitPotionTypes.vibingCreated) mightyPotions.add(InitPotionTypes.MIGHTY_VIBING);
				if (InitPotionTypes.restfulnessCreated) mightyPotions.add(InitPotionTypes.MIGHTY_RESTFULNESS);
				if (InitPotionTypes.luckCreated) mightyPotions.add(InitPotionTypes.MIGHTY_LUCK);
			}
			ItemStack stack = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), mightyPotions.get(rand.nextInt(mightyPotions.size())));
			
			return stack;
		}

		@Override
		public MerchantOffer getOffer(Entity p_221182_1_, Random rand)
		{
			return new MerchantOffer(new ItemStack(Items.EMERALD, this.price), createMightyPotion(rand), this.maxUses, this.givenEXP, this.priceMultiplier);
		}
	}
	
	static class ItemsForEmeraldsTrade implements VillagerTrades.ITrade
	{
		private final ItemStack offer;
		private final int price;
		private final int count;
		private final int maxUses;
		private final int givenEXP;
		private final float priceMultiplier;
		
		public ItemsForEmeraldsTrade(Block offer, int price, int count, int maxUses, int givenEXP)
		{
			this(new ItemStack(offer), price, count, maxUses, givenEXP);
		}
		
		public ItemsForEmeraldsTrade(Item offer, int price, int count, int maxUses) {
			this(new ItemStack(offer), price, count, 12, maxUses);
		}
		
		public ItemsForEmeraldsTrade(Item offer, int price, int count, int maxUses, int givenEXP) {
			this(new ItemStack(offer), price, count, maxUses, givenEXP);
		}
		
		public ItemsForEmeraldsTrade(ItemStack offer, int price, int count, int maxUses, int givenEXP) {
			this(offer, price, count, maxUses, givenEXP, 0.05F);
		}
		
		public ItemsForEmeraldsTrade(ItemStack offer, int price, int count, int maxUses, int givenEXP, float priceMultiplier)
		{
			this.offer = offer;
			this.price = price;
			this.count = count;
			this.maxUses = maxUses;
			this.givenEXP = givenEXP;
			this.priceMultiplier = priceMultiplier;
		}
		
		public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
			return new MerchantOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(this.offer.getItem(), this.count), this.maxUses, this.givenEXP, this.priceMultiplier);
		}
	}
	
	static class TreasureEnchantmentsTrade implements VillagerTrades.ITrade
	{
		private int price;
		private final int maxUses;
		private final int givenEXP;
		private final float priceMultiplier;
		
		public TreasureEnchantmentsTrade(int maxUses, int givenEXP) {
			this(maxUses, givenEXP, 0.05F);
		}
		
		public TreasureEnchantmentsTrade(int maxUses, int givenEXP, float priceMultiplier) {
			this.maxUses = maxUses;
			this.givenEXP = givenEXP;
			this.priceMultiplier = priceMultiplier;
		}
		
		private ItemStack createTreasureBook(Random rand)
		{
			List<Enchantment> treasureEnchs = new ArrayList<Enchantment>();
			for (Enchantment ench: ForgeRegistries.ENCHANTMENTS.getValues())
			{
				if (ench.isTreasureEnchantment() && !ench.isCurse())
				{
					treasureEnchs.add(ench);
				}
			}
			Enchantment treasure = treasureEnchs.get(rand.nextInt(treasureEnchs.size()));
			ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
			stack.addEnchantment(treasure, treasure.getMaxLevel());
			this.price = Math.min(15 * treasure.getMaxLevel(), 64);
			return stack;
		}

		@Override
		public MerchantOffer getOffer(Entity p_221182_1_, Random rand)
		{
			ItemStack book = this.createTreasureBook(rand);
			return new MerchantOffer(new ItemStack(Items.EMERALD, this.price), book, this.maxUses, this.givenEXP, this.priceMultiplier);
		}
	}
	
	//Reduces chance of too many items of one type being sold
	@SubscribeEvent
	public static void onTraderSpawn(LivingSpawnEvent.SpecialSpawn event)
	{
		LivingEntity entity = event.getEntityLiving();
		if (entity instanceof WanderingTraderEntity)
		{
			boolean sellsFlower = false;
			boolean sellsSapling = false;
			boolean sellsSeed = false;
			boolean sellsCoral = false;
			WanderingTraderEntity trader = (WanderingTraderEntity) event.getEntityLiving();
			Random rand = trader.getRNG();
			for (int i = 0; i < 5; i++)
			{
				ItemStack stack = trader.getOffers().get(i).func_222200_d();
				if (Block.getBlockFromItem(stack.getItem()) instanceof FlowerBlock)
				{
					if (sellsFlower)
					{
						if (rand.nextBoolean()) trader.getOffers().set(i, new MerchantOffer(new ItemStack(Items.EMERALD, 12), getMightyPotion(rand), 2, 2, 0.05F));
					} else
					{
						sellsFlower = true;
					}
				}
				if (Block.getBlockFromItem(stack.getItem()) instanceof CoralBlock)
				{
					if (sellsCoral)
					{
						if (rand.nextBoolean()) trader.getOffers().set(i, new MerchantOffer(new ItemStack(Items.EMERALD, 12), getMightyPotion(rand), 2, 2, 0.05F));
					} else
					{
						sellsCoral = true;
					}
				}
				if (Block.getBlockFromItem(stack.getItem()) instanceof SaplingBlock)
				{
					if (sellsSapling)
					{
						if (rand.nextBoolean()) trader.getOffers().set(i, new MerchantOffer(new ItemStack(Items.EMERALD, 12), getMightyPotion(rand), 2, 2, 0.05F));
					} else
					{
						sellsSapling = true;
					}
				}
				if (Block.getBlockFromItem(stack.getItem()) instanceof CropsBlock)
				{
					if (sellsSeed)
					{
						if (rand.nextBoolean()) trader.getOffers().set(i, new MerchantOffer(new ItemStack(Items.EMERALD, 12), getMightyPotion(rand), 2, 2, 0.05F));
					} else
					{
						sellsSeed = true;
					}
				}
			}
		}
	}
	
	private static ItemStack getMightyPotion(Random rand)
	{
		List<Potion> mightyPotions = new ArrayList<Potion>();
		mightyPotions.add(InitPotionTypes.MIGHTY_FIRE_RESISTANCE);
		mightyPotions.add(InitPotionTypes.MIGHTY_INVISIBILITY);
		mightyPotions.add(InitPotionTypes.MIGHTY_NIGHT_VISION);
		mightyPotions.add(InitPotionTypes.MIGHTY_SLOW_FALLING);
		mightyPotions.add(InitPotionTypes.MIGHTY_WATER_BREATHING);
		if (InitPotionTypes.dangerSightCreated) mightyPotions.add(InitPotionTypes.MIGHTY_DANFER_SIGHT);
		mightyPotions.add(InitPotionTypes.MIGHTY_HEALING);
		mightyPotions.add(InitPotionTypes.MIGHTY_LEAPING);
		mightyPotions.add(InitPotionTypes.MIGHTY_REGENERATION);
		mightyPotions.add(InitPotionTypes.MIGHTY_STRENGTH);
		mightyPotions.add(InitPotionTypes.MIGHTY_SWIFTNESS);
		mightyPotions.add(InitPotionTypes.MIGHTY_TURTLE_MASTER);
		if (InitPotionTypes.fortitudeCreated) mightyPotions.add(InitPotionTypes.MIGHTY_FORTITUDE);
		if (InitPotionTypes.stabilityCreated) mightyPotions.add(InitPotionTypes.MIGHTY_STABILITY);
		if (InitPotionTypes.reliefCreated) mightyPotions.add(InitPotionTypes.MIGHTY_RELIEF);
		if (InitPotionTypes.repellenceCreated) mightyPotions.add(InitPotionTypes.MIGHTY_REPELLENCE);
		if (InitPotionTypes.vibingCreated) mightyPotions.add(InitPotionTypes.MIGHTY_VIBING);
		if (InitPotionTypes.restfulnessCreated) mightyPotions.add(InitPotionTypes.MIGHTY_RESTFULNESS);
		if (InitPotionTypes.luckCreated) mightyPotions.add(InitPotionTypes.MIGHTY_LUCK);
		ItemStack stack = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), mightyPotions.get(rand.nextInt(mightyPotions.size())));
		
		return stack;
	}
}
