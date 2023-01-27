package com.blockvader.wtimprovements.client;

import com.blockvader.wtimprovements.DecoyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//Copy of PlayerRenderer
@OnlyIn(Dist.CLIENT)
public class DecoyRenderer extends LivingRenderer<DecoyEntity, DecoyModel<DecoyEntity>>{


	public DecoyRenderer(EntityRendererManager renderManager)
	{
		super(renderManager, new DecoyModel<>(0.0F), 0.5F);
		this.addLayer(new BipedArmorLayer<>(this, new BipedModel<DecoyEntity>(0.5F), new BipedModel<DecoyEntity>(1.0F)));
		this.addLayer(new HeldItemLayer<>(this));
		this.addLayer(new HeadLayer<>(this));
		this.addLayer(new ElytraLayer<>(this));
	}
	
	@Override
	public void render(DecoyEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_)
	{
		this.setModelVisibilities(p_225623_1_);
		super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
	}

	private void setModelVisibilities(DecoyEntity decoy)
	{
		DecoyModel<DecoyEntity> decoyModel = this.getModel();
		decoyModel.setAllVisible(true);
		decoyModel.crouching = false;
		BipedModel.ArmPose bipedmodel$armpose = DecoyRenderer.getArmPose(decoy, Hand.MAIN_HAND);
		BipedModel.ArmPose bipedmodel$armpose1 = DecoyRenderer.getArmPose(decoy, Hand.OFF_HAND);
		if (decoy.getMainArm() == HandSide.RIGHT)
		{
			decoyModel.rightArmPose = bipedmodel$armpose;
			decoyModel.leftArmPose = bipedmodel$armpose1;
		} else
		{
			decoyModel.rightArmPose = bipedmodel$armpose1;
			decoyModel.leftArmPose = bipedmodel$armpose;
		}
	}

	private static BipedModel.ArmPose getArmPose(DecoyEntity decoy, Hand hand) {
	      ItemStack itemstack = decoy.getItemInHand(hand);
	      if (itemstack.isEmpty()) {
	         return BipedModel.ArmPose.EMPTY;
	      } else {
	         if (decoy.getUsedItemHand() == hand && decoy.getUseItemRemainingTicks() > 0) {
	            UseAction useaction = itemstack.getUseAnimation();
	            if (useaction == UseAction.BLOCK) {
	               return BipedModel.ArmPose.BLOCK;
	            }

	            if (useaction == UseAction.BOW) {
	               return BipedModel.ArmPose.BOW_AND_ARROW;
	            }

	            if (useaction == UseAction.SPEAR) {
	               return BipedModel.ArmPose.THROW_SPEAR;
	            }

	            if (useaction == UseAction.CROSSBOW && hand == decoy.getUsedItemHand()) {
	               return BipedModel.ArmPose.CROSSBOW_CHARGE;
	            }
	         } else if (!decoy.swinging && itemstack.getItem() == Items.CROSSBOW && CrossbowItem.isCharged(itemstack)) {
	            return BipedModel.ArmPose.CROSSBOW_HOLD;
	         }

	         return BipedModel.ArmPose.ITEM;
	      }
	   }

	@Override
	public ResourceLocation getTextureLocation(DecoyEntity entity)
	{
		NetworkPlayerInfo info = Minecraft.getInstance().getConnection().getPlayerInfo(entity.getFakeId());
		return info == null ? DefaultPlayerSkin.getDefaultSkin(entity.getFakeId()) : info.getSkinLocation();
	}
	
	protected void preRenderCallback(DecoyEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime)
	{
		matrixStackIn.scale(0.9375F, 0.9375F, 0.9375F);
	}

	public void renderRightArm(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, DecoyEntity entityIn)
	{
		this.renderItem(matrixStackIn, bufferIn, combinedLightIn, entityIn, (this.model).rightArm, (this.model).bipedRightArmwear);
	}

	public void renderLeftArm(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, DecoyEntity entityIn)
	{
		this.renderItem(matrixStackIn, bufferIn, combinedLightIn, entityIn, (this.model).leftArm, (this.model).bipedLeftArmwear);
	}

	private void renderItem(MatrixStack p_229145_1_, IRenderTypeBuffer p_229145_2_, int p_229145_3_, DecoyEntity decoy, ModelRenderer rendererArmIn, ModelRenderer rendererArmwearIn)
	{
		DecoyModel<DecoyEntity> playermodel = this.getModel();
		this.setModelVisibilities(decoy);
		playermodel.attackTime = 0.0F;
		playermodel.crouching = false;
		playermodel.swimAmount = 0.0F;
		playermodel.setupAnim(decoy, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		rendererArmIn.xRot = 0.0F;
		rendererArmIn.render(p_229145_1_, p_229145_2_.getBuffer(RenderType.entitySolid(this.getTextureLocation(decoy))), p_229145_3_,OverlayTexture.NO_OVERLAY);
		rendererArmwearIn.xRot = 0.0F;
		rendererArmwearIn.render(p_229145_1_, p_229145_2_.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(decoy))), p_229145_3_, OverlayTexture.NO_OVERLAY);
	}

	@Override
	protected void setupRotations(DecoyEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
		float f = entityLiving.getSwimAmount(partialTicks);
		if (entityLiving.isFallFlying()) {
			super.setupRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
			float f1 = (float)entityLiving.getFallFlyingTicks() + partialTicks;
			float f2 = MathHelper.clamp(f1 * f1 / 100.0F, 0.0F, 1.0F);
			if (!entityLiving.isAutoSpinAttack()) {
				matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(f2 * (-90.0F - entityLiving.xRot)));
			}
	
			Vector3d vector3d = entityLiving.getViewVector(partialTicks);
			Vector3d vector3d1 = entityLiving.getDeltaMovement();
			double d0 = Entity.getHorizontalDistanceSqr(vector3d1);
			double d1 = Entity.getHorizontalDistanceSqr(vector3d);
			if (d0 > 0.0D && d1 > 0.0D) {
				double d2 = (vector3d1.x * vector3d.x + vector3d1.z * vector3d.z) / Math.sqrt(d0 * d1);
				double d3 = vector3d1.x * vector3d.z - vector3d1.z * vector3d.x;
				matrixStackIn.mulPose(Vector3f.YP.rotation((float)(Math.signum(d3) * Math.acos(d2))));
			}
		}
		else if (f > 0.0F) {
			super.setupRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
			float f3 = entityLiving.isInWater() ? -90.0F - entityLiving.xRot : -90.0F;
			float f4 = MathHelper.lerp(f, 0.0F, f3);
			matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(f4));
			if (entityLiving.isVisuallySwimming()) {
				matrixStackIn.translate(0.0D, -1.0D, (double)0.3F);
			}
		}
		else {
			super.setupRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
		}
	}
}