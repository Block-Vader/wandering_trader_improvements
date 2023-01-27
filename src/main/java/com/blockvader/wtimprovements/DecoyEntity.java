package com.blockvader.wtimprovements;

import java.util.Optional;
import java.util.UUID;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class DecoyEntity extends LivingEntity {

	private static final DataParameter<Boolean> IS_LEFTHANDED = EntityDataManager.defineId(MobEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Optional<UUID>> FAKE_ID = EntityDataManager.defineId(MobEntity.class, DataSerializers.OPTIONAL_UUID);
	private final NonNullList<ItemStack> inventoryHands = NonNullList.withSize(2, ItemStack.EMPTY);
	private final NonNullList<ItemStack> inventoryArmor = NonNullList.withSize(4, ItemStack.EMPTY);
	private int age = 0;

	public DecoyEntity(EntityType<? extends DecoyEntity> type, World worldIn) {
		super(type, worldIn);
		entityData.set(FAKE_ID, Optional.of(UUID.fromString("6ab43178-89fd-4905-97f6-0f67d9d76fd9")));
	}
	
	public void copyPlayer(PlayerEntity player) {
		this.setPrimaryHand(player.getMainArm());
		this.copyPosition(player);
		this.yHeadRot = player.yHeadRot;
		this.setFakeId(player.getUUID());
		this.setCustomName(player.getDisplayName());
		for (EquipmentSlotType type : EquipmentSlotType.values()) {
			ItemStack stack = player.getItemBySlot(type);
			if (!stack.isEmpty()) {
				this.setItemSlot(type, stack.copy());
			}
		}
	}

	@Override
	public Iterable<ItemStack> getArmorSlots() {
		return inventoryArmor;
	}
	
	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(IS_LEFTHANDED, false);
		this.entityData.define(FAKE_ID, null);
	}

	@Override
	public ItemStack getItemBySlot(EquipmentSlotType slotIn) {
		switch(slotIn.getType()) {
		case HAND:
			return this.inventoryHands.get(slotIn.getIndex());
		case ARMOR:
			return this.inventoryArmor.get(slotIn.getIndex());
		default:
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void setItemSlot(EquipmentSlotType slotIn, ItemStack stack)
	{
		switch(slotIn.getType()) {
		case HAND:
			this.inventoryHands.set(slotIn.getIndex(), stack);
			break;
		case ARMOR:
			this.inventoryArmor.set(slotIn.getIndex(), stack);
		}
	}

	@Override
	public HandSide getMainArm() {
		return this.entityData.get(IS_LEFTHANDED) ? HandSide.LEFT : HandSide.RIGHT;
	}
	
	public void setPrimaryHand(HandSide side) {
		this.entityData.set(IS_LEFTHANDED, side == HandSide.LEFT);
	}
	
	public UUID getFakeId() {
		return this.entityData.get(FAKE_ID).orElse(UUID.fromString("6ab43178-89fd-4905-97f6-0f67d9d76fd9"));
	}
	
	public void setFakeId(UUID id) {
		this.entityData.set(FAKE_ID, Optional.of(id));
	}
	
	@Override
	public void baseTick() {
		super.baseTick();
		this.age ++;
		if (this.age > 200) {
			this.remove(); //disappears after 10 seconds
		}
	}
	
	@Override
	public boolean isInvulnerableTo(DamageSource source) {
		return true; //Unkillable, can only disappear on it's own
	}
	
	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("leftHanded", this.entityData.get(IS_LEFTHANDED));
		compound.putUUID("fakeId", this.getFakeId());
		compound.putInt("age", this.age);
		ListNBT listnbt = new ListNBT();
		for(ItemStack itemstack : this.inventoryArmor) {
			CompoundNBT compoundnbt = new CompoundNBT();
			if (!itemstack.isEmpty()) {
				itemstack.save(compoundnbt);
			}

			listnbt.add(compoundnbt);
		}
		compound.put("ArmorItems", listnbt);
		ListNBT listnbt1 = new ListNBT();
		for(ItemStack itemstack1 : this.inventoryHands) {
			CompoundNBT compoundnbt1 = new CompoundNBT();
			if (!itemstack1.isEmpty()) {
				itemstack1.save(compoundnbt1);
			}
			listnbt1.add(compoundnbt1);
		}
		compound.put("HandItems", listnbt1);
	}
	
	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		this.setFakeId(compound.getUUID("fakeId"));
		this.entityData.set(IS_LEFTHANDED, compound.getBoolean("leftHanded"));
		this.age = compound.getInt("age");
		if (compound.contains("ArmorItems", 9)) {
			ListNBT listnbt = compound.getList("ArmorItems", 10);
			for(int i = 0; i < this.inventoryArmor.size(); ++i) {
				this.inventoryArmor.set(i, ItemStack.of(listnbt.getCompound(i)));
			}
		}
		if (compound.contains("HandItems", 9)) {
			ListNBT listnbt1 = compound.getList("HandItems", 10);
			for(int j = 0; j < this.inventoryHands.size(); ++j) {
				this.inventoryHands.set(j, ItemStack.of(listnbt1.getCompound(j)));
			}
		}
	}
}
