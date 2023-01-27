package com.blockvader.wtimprovements;

import com.blockvader.wtimprovements.init.ModEntities;

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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class DecoyTotemItem extends Item {

	public DecoyTotemItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getItemInHand(handIn);
		playerIn.awardStat(Stats.ITEM_USED.get(this));
		if (!playerIn.isCreative()) {
			stack.shrink(1);
			playerIn.getCooldowns().addCooldown(this, 400);
		}
		if (!worldIn.isClientSide) {
			DecoyEntity decoy = new DecoyEntity(ModEntities.DECOY.get(), worldIn);
			decoy.copyPlayer(playerIn);
			worldIn.addFreshEntity(decoy);
			playerIn.addEffect(new EffectInstance(Effects.INVISIBILITY, 200, 0, false, false));
			Vector3d pos = playerIn.position();
			for (Entity entity: worldIn.getEntities(decoy, new AxisAlignedBB(pos.add(-40, -40, -40), pos.add(40, 40, 40)))) {
				if (entity instanceof MobEntity) {
					if (((MobEntity)entity).getTarget() == playerIn) {
						((MobEntity)entity).setTarget(decoy); //Tricks nearby mobs to attacking a decoy rather than the player
					}
				}
			}
		} else {
			playerIn.playSound(SoundEvents.WANDERING_TRADER_DISAPPEARED, 1.0F, random.nextFloat() * 0.2F + 0.9F);
		}
		return ActionResult.success(stack);
	}

}
