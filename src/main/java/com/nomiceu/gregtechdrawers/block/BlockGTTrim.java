package com.nomiceu.gregtechdrawers.block;

import com.jaquadro.minecraft.storagedrawers.StorageDrawers;
import com.jaquadro.minecraft.storagedrawers.api.storage.INetworked;
import com.nomiceu.gregtechdrawers.type.DrawerMaterial;
import com.nomiceu.gregtechdrawers.type.Mod;
import com.nomiceu.gregtechdrawers.GTDCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockGTTrim extends Block implements INetworked {

	public final Mod mod;
	public final DrawerMaterial material;
	private boolean isGrass = false;
	
	public BlockGTTrim(DrawerMaterial material) {
		super(Material.WOOD);

		this.mod = material.getMod();
		this.material = material;

		setTranslationKey(StorageDrawers.MOD_ID + ".trim");
		setRegistryName("trim_" + mod + "_" + material);
		setHardness(5f);
		setSoundType(SoundType.WOOD);
		setCreativeTab(GTDCreativeTabs.TAB);
	}
	
	public BlockGTTrim setMadeOfGrass() {
		setSoundType(SoundType.PLANT);
		setHardness(3F);
		isGrass = true;
		return this;
	}
	
	@SuppressWarnings("deprecation")
	public Material getMaterial(IBlockState state) {
		return isGrass? Material.GRASS : super.getMaterial(state);
	}

}
