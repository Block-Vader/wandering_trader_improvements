package com.blockvader.wtimprovements;

import com.blockvader.wtimprovements.init.InitDecoy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DecoyTotemItem extends Item{

	public DecoyTotemItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		playerIn.addStat(Stats.ITEM_USED.get(this));
		if (!playerIn.abilities.isCreativeMode)
		{
			stack.shrink(1);
			playerIn.getCooldownTracker().setCooldown(this, 400);
		}
		if (!worldIn.isRemote)
		{
			DecoyEntity decoy = new DecoyEntity(InitDecoy.DECOY, worldIn);
			decoy.copyPlayer(playerIn);
			worldIn.addEntity(decoy);
			playerIn.addPotionEffect(new EffectInstance(Effects.INVISIBILITY, 200, 0, false, false));
			BlockPos pos = playerIn.func_233580_cy_();
			for (Entity entity: worldIn.getEntitiesWithinAABBExcludingEntity(decoy, new AxisAlignedBB(pos.add(-40, -40, -40), pos.add(40, 40, 40))))
			{
				if (entity instanceof MobEntity)
				{
					if (((MobEntity)entity).getAttackTarget() == playerIn || ((MobEntity)entity).getRevengeTarget() == playerIn)
					{
						((MobEntity)entity).setRevengeTarget(decoy); //Tricks nearby mobs to attacking a decoy rather than the player
						((MobEntity)entity).setAttackTarget(decoy);
					}
				}
			}
		}
		else
		{
			playerIn.playSound(SoundEvents.ENTITY_WANDERING_TRADER_DISAPPEARED, 1.0F, playerIn.getRNG().nextFloat() * 0.2F + 0.9F);
		}
		return ActionResult.resultSuccess(stack);
	}

}
