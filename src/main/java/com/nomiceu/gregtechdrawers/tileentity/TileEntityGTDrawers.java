package com.nomiceu.gregtechdrawers.tileentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.jaquadro.minecraft.storagedrawers.StorageDrawers;
import com.jaquadro.minecraft.storagedrawers.api.event.DrawerPopulatedEvent;
import com.jaquadro.minecraft.storagedrawers.api.storage.EnumBasicDrawer;
import com.jaquadro.minecraft.storagedrawers.api.storage.IDrawerAttributes;
import com.jaquadro.minecraft.storagedrawers.api.storage.IDrawerGroup;
import com.jaquadro.minecraft.storagedrawers.block.BlockStandardDrawers;
import com.jaquadro.minecraft.storagedrawers.block.tile.TileEntityDrawersStandard;
import com.jaquadro.minecraft.storagedrawers.block.tile.tiledata.StandardDrawerGroup;
import com.jaquadro.minecraft.storagedrawers.config.ConfigManager;
import com.jaquadro.minecraft.storagedrawers.core.ModBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class TileEntityGTDrawers extends TileEntityDrawersStandard {
	@CapabilityInject(IDrawerAttributes.class)
	static Capability<IDrawerAttributes> DRAWER_ATTRIBUTES_CAPABILITY = null;

	/*
	 * private static final String[] GUI_IDS = new String[] { null,
	 * StorageDrawers.MOD_ID + ":basicDrawers1", StorageDrawers.MOD_ID +
	 * ":basicDrawers2", null, StorageDrawers.MOD_ID + ":basicDrawers4" };
	 */

	private int capacity = 0;

	public static class Slot1 extends TileEntityGTDrawers {
		private GroupData groupData = new GroupData(1);

		public Slot1() {
			groupData.setCapabilityProvider(this);
			injectPortableData(groupData);
		}

		@Override
		public IDrawerGroup getGroup() {
			return groupData;
		}

		@Override
		protected void onAttributeChanged() {
			groupData.syncAttributes();
		}
	}

	public static class Slot2 extends TileEntityGTDrawers {
		private GroupData groupData = new GroupData(2);

		public Slot2() {
			groupData.setCapabilityProvider(this);
			injectPortableData(groupData);
		}

		@Override
		public IDrawerGroup getGroup() {
			return groupData;
		}

		@Override
		protected void onAttributeChanged() {
			groupData.syncAttributes();
		}
	}

	public static class Slot4 extends TileEntityGTDrawers {
		private GroupData groupData = new GroupData(4);

		public Slot4() {
			groupData.setCapabilityProvider(this);
			injectPortableData(groupData);
		}

		@Override
		public IDrawerGroup getGroup() {
			return groupData;
		}

		@Override
		protected void onAttributeChanged() {
			groupData.syncAttributes();
		}
	}

	public static class Legacy extends TileEntityGTDrawers {
		private GroupData groupData = new GroupData(4);

		public Legacy() {
			groupData.setCapabilityProvider(this);
			injectPortableData(groupData);
		}

		@Override
		public IDrawerGroup getGroup() {
			return groupData;
		}

		@Override
		protected void onAttributeChanged() {
			groupData.syncAttributes();
		}

		public void replaceWithCurrent() {
			TileEntityGTDrawers replacement = createEntity(groupData.getDrawerCount());
			if(replacement != null) {
				replacement.deserializeNBT(serializeNBT());
				getWorld().setTileEntity(getPos(), replacement);
				replacement.markDirty();
			}
		}

		@Override
		public void validate() {
			super.validate();
			getWorld().scheduleBlockUpdate(getPos(), ModBlocks.basicDrawers, 1, 0);
		}

		@Override
		public NBTTagCompound writeToPortableNBT(NBTTagCompound tag) {
			return super.writeToPortableNBT(tag);
		}
	}

	public static TileEntityGTDrawers createEntity(int slotCount) {
		switch(slotCount) {
		case 1:
			return new Slot1();
		case 2:
			return new Slot2();
		case 4:
			return new Slot4();
		default:
			return null;
		}
	}

	@Override
	public IDrawerGroup getGroup() {
		return null;
	}

	@Override
	public int getDrawerCapacity() {
		if(getWorld() == null || getWorld().isRemote)
			return super.getDrawerCapacity();

		if(capacity == 0) {
			IBlockState blockState = getWorld().getBlockState(this.pos);
			if(!blockState.getPropertyKeys().contains(BlockStandardDrawers.BLOCK))
				return 1;

			EnumBasicDrawer type = blockState.getValue(BlockStandardDrawers.BLOCK);
			ConfigManager config = StorageDrawers.config;

			switch(type) {
			case FULL1:
				capacity = config.getBlockBaseStorage("fulldrawers1");
				break;
			case FULL2:
				capacity = config.getBlockBaseStorage("fulldrawers2");
				break;
			case FULL4:
				capacity = config.getBlockBaseStorage("fulldrawers4");
				break;
			case HALF2:
				capacity = config.getBlockBaseStorage("halfdrawers2");
				break;
			case HALF4:
				capacity = config.getBlockBaseStorage("halfdrawers4");
				break;
			default:
				capacity = 1;
			}

			if(capacity <= 0)
				capacity = 1;
		}

		return capacity;
	}

	private class GroupData extends StandardDrawerGroup {
		public GroupData(int slotCount) {
			super(slotCount);
		}

		@Nonnull
		@Override
		protected DrawerData createDrawer(int slot) {
			return new StandardDrawerData(this, slot);
		}

		@Override
		public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
			return capability == TileEntityGTDrawers.DRAWER_ATTRIBUTES_CAPABILITY
					|| super.hasCapability(capability, facing);

		}

		@Nullable
		@Override
		@SuppressWarnings("unchecked")
		public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
			if(capability == TileEntityGTDrawers.DRAWER_ATTRIBUTES_CAPABILITY)
				return (T) TileEntityGTDrawers.this.getDrawerAttributes();

			return super.getCapability(capability, facing);
		}
	}

	private class StandardDrawerData extends StandardDrawerGroup.DrawerData {
		private int slot;

		public StandardDrawerData(StandardDrawerGroup group, int slot) {
			super(group);
			this.slot = slot;
		}

		@Override
		protected int getStackCapacity() {
			return upgrades().getStorageMultiplier() * getEffectiveDrawerCapacity();
		}

		@Override
		protected void onItemChanged() {
			DrawerPopulatedEvent event = new DrawerPopulatedEvent(this);
			MinecraftForge.EVENT_BUS.post(event);

			if(getWorld() != null && !getWorld().isRemote) {
				markDirty();
				markBlockForUpdate();
			}
		}

		@Override
		protected void onAmountChanged() {
			if(getWorld() != null && !getWorld().isRemote) {
				syncClientCount(slot, getStoredItemCount());
				markDirty();
			}
		}
	}
}
