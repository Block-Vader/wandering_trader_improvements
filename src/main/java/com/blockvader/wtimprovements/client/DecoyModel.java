package com.blockvader.wtimprovements.client;

import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.HandSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//Copy of PlayerModel
@OnlyIn(Dist.CLIENT)
public class DecoyModel<T extends LivingEntity> extends BipedModel<T> {
	private List<ModelRenderer> field_228286_w_ = Lists.newArrayList();
	public final ModelRenderer bipedLeftArmwear;
	public final ModelRenderer bipedRightArmwear;
	public final ModelRenderer bipedLeftLegwear;
	public final ModelRenderer bipedRightLegwear;
	public final ModelRenderer bipedBodyWear;

	public DecoyModel(float modelSize) {
		super(RenderType::entityTranslucent, modelSize, 0.0F, 64, 64); {
			this.leftArm = new ModelRenderer(this, 32, 48);
			this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
			this.leftArm.setPos(5.0F, 2.0F, 0.0F);
			this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
			this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize + 0.25F);
			this.bipedLeftArmwear.setPos(5.0F, 2.0F, 0.0F);
			this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
			this.bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize + 0.25F);
			this.bipedRightArmwear.setPos(-5.0F, 2.0F, 10.0F);
		}
		this.leftLeg = new ModelRenderer(this, 16, 48);
		this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
		this.leftLeg.setPos(1.9F, 12.0F, 0.0F);
		this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
		this.bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize + 0.25F);
		this.bipedLeftLegwear.setPos(1.9F, 12.0F, 0.0F);
		this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
		this.bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize + 0.25F);
		this.bipedRightLegwear.setPos(-1.9F, 12.0F, 0.0F);
		this.bipedBodyWear = new ModelRenderer(this, 16, 32);
		this.bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, modelSize + 0.25F);
		this.bipedBodyWear.setPos(0.0F, 0.0F, 0.0F);
	}
	
	protected Iterable<ModelRenderer> bodyParts() {
	      return Iterables.concat(super.bodyParts(), ImmutableList.of(this.bipedLeftLegwear, this.bipedRightLegwear, this.bipedLeftArmwear, this.bipedRightArmwear, this.bipedBodyWear));
	   }
	
	public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
	      super.setupAnim(p_225597_1_, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);
	      this.bipedLeftLegwear.copyFrom(this.bipedBodyWear);
	      this.bipedRightLegwear.copyFrom(this.rightLeg);
	      this.bipedLeftArmwear.copyFrom(this.leftArm);
	      this.bipedRightArmwear.copyFrom(this.rightArm);
	      this.bipedBodyWear.copyFrom(this.bipedBodyWear);
	   }
	
	public void setAllVisible(boolean visible) {
	      super.setAllVisible(visible);
	      this.bipedLeftArmwear.visible = visible;
	      this.bipedRightArmwear.visible = visible;
	      this.bipedLeftLegwear.visible = visible;
	      this.bipedRightLegwear.visible = visible;
	      this.bipedBodyWear.visible = visible;
	   }
	
	public void translateHand(HandSide p_225599_1_, MatrixStack p_225599_2_) {
		ModelRenderer modelrenderer = this.getArm(p_225599_1_);
		modelrenderer.translateAndRotate(p_225599_2_);
	}

	public ModelRenderer getRandomModelRenderer(Random p_228288_1_) {
		return this.field_228286_w_.get(p_228288_1_.nextInt(this.field_228286_w_.size()));
	}

	public void accept(ModelRenderer p_accept_1_) {
		if (this.field_228286_w_ == null) {
			this.field_228286_w_ = Lists.newArrayList();
		}

		this.field_228286_w_.add(p_accept_1_);
	}
}