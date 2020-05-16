package com.blockvader.wtimprovements.client;

import com.blockvader.wtimprovements.DecoyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
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
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//Copy of PlayerRenderer
@OnlyIn(Dist.CLIENT)
public class DecoyRenderer extends LivingRenderer<DecoyEntity, DecoyModel<DecoyEntity>>{

	@SuppressWarnings("rawtypes")
	public DecoyRenderer(EntityRendererManager renderManager)
	{
		super(renderManager, new DecoyModel<>(0.0F), 0.5F);
		this.addLayer(new BipedArmorLayer<>(this, new BipedModel(0.5F), new BipedModel(1.0F)));
		this.addLayer(new HeldItemLayer<>(this));
		this.addLayer(new HeadLayer<>(this));
		this.addLayer(new ElytraLayer<>(this));
	}
	
	public void func_225623_a_(DecoyEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_)
	{
		this.setModelVisibilities(p_225623_1_);
		super.func_225623_a_(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
	}

	private void setModelVisibilities(DecoyEntity decoy) {
		DecoyModel<DecoyEntity> decoyModel = this.getEntityModel();
		if (decoy.getFakeId() != null)
		{
			
		}
		ItemStack itemstack = decoy.getHeldItemMainhand();
		ItemStack itemstack1 = decoy.getHeldItemOffhand();
		decoyModel.setVisible(true);
		/*decoyModel.bipedHeadwear.showModel = clientPlayer.isWearing(decoyModelPart.HAT);
		decoyModel.bipedBodyWear.showModel = clientPlayer.isWearing(decoyModelPart.JACKET);
		decoyModel.bipedLeftLegwear.showModel = clientPlayer.isWearing(decoyModelPart.LEFT_PANTS_LEG);
		decoyModel.bipedRightLegwear.showModel = clientPlayer.isWearing(decoyModelPart.RIGHT_PANTS_LEG);
		decoyModel.bipedLeftArmwear.showModel = clientPlayer.isWearing(decoyModelPart.LEFT_SLEEVE);
		decoyModel.bipedRightArmwear.showModel = clientPlayer.isWearing(decoyModelPart.RIGHT_SLEEVE);*/
		decoyModel.field_228270_o_ = false;
		BipedModel.ArmPose bipedmodel$armpose = this.func_217766_a(decoy, itemstack, itemstack1, Hand.MAIN_HAND);
		BipedModel.ArmPose bipedmodel$armpose1 = this.func_217766_a(decoy, itemstack, itemstack1, Hand.OFF_HAND);
		if (decoy.getPrimaryHand() == HandSide.RIGHT) {
			decoyModel.rightArmPose = bipedmodel$armpose;
			decoyModel.leftArmPose = bipedmodel$armpose1;
		} else {
			decoyModel.rightArmPose = bipedmodel$armpose1;
			decoyModel.leftArmPose = bipedmodel$armpose;
		}
	}

	private BipedModel.ArmPose func_217766_a(DecoyEntity p_217766_1_, ItemStack p_217766_2_, ItemStack p_217766_3_, Hand p_217766_4_)
	{
		BipedModel.ArmPose bipedmodel$armpose = BipedModel.ArmPose.EMPTY;
		ItemStack itemstack = p_217766_4_ == Hand.MAIN_HAND ? p_217766_2_ : p_217766_3_;
		if (!itemstack.isEmpty())
		{
			bipedmodel$armpose = BipedModel.ArmPose.ITEM;
			boolean flag3 = p_217766_2_.getItem() == Items.CROSSBOW;
			boolean flag = CrossbowItem.isCharged(p_217766_2_);
			boolean flag1 = p_217766_3_.getItem() == Items.CROSSBOW;
			boolean flag2 = CrossbowItem.isCharged(p_217766_3_);
			if (flag3 && flag)
			{
				bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
			}

			if (flag1 && flag2 && p_217766_2_.getItem().getUseAction(p_217766_2_) == UseAction.NONE) {
	            	bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
	            }
		}

		return bipedmodel$armpose;
	}

	@Override
	public ResourceLocation getEntityTexture(DecoyEntity entity)
	{
		if (entity.getFakeId() != null)
		{
			NetworkPlayerInfo info = Minecraft.getInstance().getConnection().getPlayerInfo(entity.getFakeId());
			return info == null ? DefaultPlayerSkin.getDefaultSkin(entity.getFakeId()) : info.getLocationSkin();
		}
		return null;
	}
	
	protected void func_225620_a_(DecoyEntity p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_)
	{
		p_225620_2_.func_227862_a_(0.9375F, 0.9375F, 0.9375F);
	}

	public void func_229144_a_(MatrixStack p_229144_1_, IRenderTypeBuffer p_229144_2_, int p_229144_3_, DecoyEntity p_229144_4_)
	{
		this.func_229145_a_(p_229144_1_, p_229144_2_, p_229144_3_, p_229144_4_, (this.entityModel).bipedRightArm, (this.entityModel).bipedRightArmwear);
	}

	public void func_229146_b_(MatrixStack p_229146_1_, IRenderTypeBuffer p_229146_2_, int p_229146_3_, DecoyEntity p_229146_4_) {
		this.func_229145_a_(p_229146_1_, p_229146_2_, p_229146_3_, p_229146_4_, (this.entityModel).bipedLeftArm, (this.entityModel).bipedLeftArmwear);
	}

	private void func_229145_a_(MatrixStack p_229145_1_, IRenderTypeBuffer p_229145_2_, int p_229145_3_, DecoyEntity decoy, ModelRenderer p_229145_5_, ModelRenderer p_229145_6_) {
		DecoyModel<DecoyEntity> playermodel = this.getEntityModel();
		this.setModelVisibilities(decoy);
		playermodel.swingProgress = 0.0F;
	      playermodel.field_228270_o_ = false;
	      playermodel.swimAnimation = 0.0F;
	      playermodel.func_225597_a_(decoy, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
	      p_229145_5_.rotateAngleX = 0.0F;
	      p_229145_5_.func_228308_a_(p_229145_1_, p_229145_2_.getBuffer(RenderType.func_228634_a_(this.getEntityTexture(decoy))), p_229145_3_, OverlayTexture.field_229196_a_);
	      p_229145_6_.rotateAngleX = 0.0F;
	      p_229145_6_.func_228308_a_(p_229145_1_, p_229145_2_.getBuffer(RenderType.func_228644_e_(this.getEntityTexture(decoy))), p_229145_3_, OverlayTexture.field_229196_a_);
	   }

	   protected void func_225621_a_(DecoyEntity p_225621_1_, MatrixStack p_225621_2_, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
	      float f = p_225621_1_.getSwimAnimation(p_225621_5_);
	      if (p_225621_1_.isElytraFlying()) {
	         super.func_225621_a_(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
	         float f1 = (float)p_225621_1_.getTicksElytraFlying() + p_225621_5_;
	         float f2 = MathHelper.clamp(f1 * f1 / 100.0F, 0.0F, 1.0F);
	         if (!p_225621_1_.isSpinAttacking()) {
	            p_225621_2_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(f2 * (-90.0F - p_225621_1_.rotationPitch)));
	         }

	         Vec3d vec3d = p_225621_1_.getLook(p_225621_5_);
	         Vec3d vec3d1 = p_225621_1_.getMotion();
	         double d0 = Entity.func_213296_b(vec3d1);
	         double d1 = Entity.func_213296_b(vec3d);
	         if (d0 > 0.0D && d1 > 0.0D) {
	            double d2 = (vec3d1.x * vec3d.x + vec3d1.z * vec3d.z) / (Math.sqrt(d0) * Math.sqrt(d1));
	            double d3 = vec3d1.x * vec3d.z - vec3d1.z * vec3d.x;
	            p_225621_2_.func_227863_a_(Vector3f.field_229181_d_.func_229193_c_((float)(Math.signum(d3) * Math.acos(d2))));
	         }
	      } else if (f > 0.0F) {
	         super.func_225621_a_(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
	         float f3 = p_225621_1_.isInWater() ? -90.0F - p_225621_1_.rotationPitch : -90.0F;
	         float f4 = MathHelper.lerp(f, 0.0F, f3);
	         p_225621_2_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(f4));
	         if (p_225621_1_.func_213314_bj()) {
	            p_225621_2_.func_227861_a_(0.0D, -1.0D, (double)0.3F);
	         }
	      } else {
	         super.func_225621_a_(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
	      }

	   }

}
