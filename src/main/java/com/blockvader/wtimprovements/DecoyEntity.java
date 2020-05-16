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

	private static final DataParameter<Boolean> IS_LEFTHANDED = EntityDataManager.createKey(MobEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Optional<UUID>> FAKE_ID = EntityDataManager.createKey(MobEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
	private final NonNullList<ItemStack> inventoryHands = NonNullList.withSize(2, ItemStack.EMPTY);
	private final NonNullList<ItemStack> inventoryArmor = NonNullList.withSize(4, ItemStack.EMPTY);
	private int age = 0;

	public DecoyEntity(EntityType<? extends DecoyEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	public void copyPlayer(PlayerEntity player)
	{
		this.setPrimaryHand(player.getPrimaryHand());
		this.copyLocationAndAnglesFrom(player);
		this.rotationYawHead = player.rotationYawHead;
		this.setFakeId(player.getUniqueID());
		this.setCustomName(player.getDisplayName());
		for (EquipmentSlotType type : EquipmentSlotType.values())
		{
			ItemStack stack = player.getItemStackFromSlot(type);
			if (!stack.isEmpty())
			{
				this.setItemStackToSlot(type, stack.copy());
			}
		}
	}

	@Override
	public Iterable<ItemStack> getArmorInventoryList()
	{
		return inventoryArmor;
	}
	
	@Override
	protected void registerData()
	{
		super.registerData();
		this.dataManager.register(IS_LEFTHANDED, false);
		this.dataManager.register(FAKE_ID, null);
	}

	@Override
	public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn)
	{
		switch(slotIn.getSlotType())
		{
		case HAND:
			return this.inventoryHands.get(slotIn.getIndex());
		case ARMOR:
			return this.inventoryArmor.get(slotIn.getIndex());
		default:
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack)
	{
		switch(slotIn.getSlotType())
		{
		case HAND:
			this.inventoryHands.set(slotIn.getIndex(), stack);
			break;
		case ARMOR:
			this.inventoryArmor.set(slotIn.getIndex(), stack);
		}
	}

	@Override
	public HandSide getPrimaryHand()
	{
		return this.dataManager.get(IS_LEFTHANDED) ? HandSide.LEFT : HandSide.RIGHT;
	}
	
	public void setPrimaryHand(HandSide side)
	{
		this.dataManager.set(IS_LEFTHANDED, side == HandSide.LEFT);
	}
	
	public UUID getFakeId()
	{
		return this.dataManager.get(FAKE_ID).orElse(null);
	}
	
	public void setFakeId(UUID id)
	{
		this.dataManager.set(FAKE_ID, Optional.of(id));
	}
	
	@Override
	public void baseTick()
	{
		super.baseTick();
		this.age ++;
		if (this.age > 200)
		{
			this.remove(); //disappears after 10 seconds
		}
	}
	
	@Override
	public boolean isInvulnerableTo(DamageSource source)
	{
		return true; //Unkillable, can only disappear on it's own
	}
	
	@Override
	public void writeAdditional(CompoundNBT compound)
	{
		super.writeAdditional(compound);
		compound.putBoolean("leftHanded", this.dataManager.get(IS_LEFTHANDED));
		compound.putUniqueId("fakeId", this.getFakeId());
		compound.putInt("age", this.age);
		ListNBT listnbt = new ListNBT();

		for(ItemStack itemstack : this.inventoryArmor)
		{
			CompoundNBT compoundnbt = new CompoundNBT();
			if (!itemstack.isEmpty())
			{
				itemstack.write(compoundnbt);
			}

			listnbt.add(compoundnbt);
		}
		compound.put("ArmorItems", listnbt);
		ListNBT listnbt1 = new ListNBT();

		for(ItemStack itemstack1 : this.inventoryHands)
		{
			CompoundNBT compoundnbt1 = new CompoundNBT();
			if (!itemstack1.isEmpty())
			{
				itemstack1.write(compoundnbt1);
			}
			listnbt1.add(compoundnbt1);
		}
		compound.put("HandItems", listnbt1);
	}
	
	@Override
	public void readAdditional(CompoundNBT compound)
	{
		super.readAdditional(compound);
		this.setFakeId(compound.getUniqueId("fakeId"));
		this.dataManager.set(IS_LEFTHANDED, compound.getBoolean("leftHanded"));
		this.age = compound.getInt("age");
		if (compound.contains("ArmorItems", 9))
		{
			ListNBT listnbt = compound.getList("ArmorItems", 10);

			for(int i = 0; i < this.inventoryArmor.size(); ++i)
			{
				this.inventoryArmor.set(i, ItemStack.read(listnbt.getCompound(i)));
			}
		}

		if (compound.contains("HandItems", 9)) {
			ListNBT listnbt1 = compound.getList("HandItems", 10);

			for(int j = 0; j < this.inventoryHands.size(); ++j)
			{
				this.inventoryHands.set(j, ItemStack.read(listnbt1.getCompound(j)));
			}
		}
	}

}
