package io.github.nomiceu.block;

import com.jaquadro.minecraft.storagedrawers.StorageDrawers;
import com.jaquadro.minecraft.storagedrawers.api.storage.EnumBasicDrawer;
import com.jaquadro.minecraft.storagedrawers.block.BlockStandardDrawers;
import com.jaquadro.minecraft.storagedrawers.block.tile.TileEntityDrawers;
import io.github.nomiceu.GTDCreativeTabs;
import io.github.nomiceu.tileentity.TileEntityGTDrawers;
import io.github.nomiceu.type.DrawerMaterial;
import io.github.nomiceu.type.Mod;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGTDrawers extends BlockStandardDrawers {
	//public static final PropertyBool LOCKED = PropertyBool.create("locked");
	
	//public final String modid, material;
	public final Mod mod;
	public final DrawerMaterial material;
	private boolean isGrass = false;
	
	public BlockGTDrawers(DrawerMaterial materialIn) {
		super("basicdrawers_" + materialIn.getMod() + "_" + materialIn, StorageDrawers.MOD_ID + ".basicDrawers");
		this.material = materialIn;
		this.mod = materialIn.getMod();
		
		setCreativeTab(GTDCreativeTabs.TAB);
	}
	
	public BlockGTDrawers setMadeOfGrass() {
		setSoundType(SoundType.PLANT);
		setHardness(3F);
		isGrass = true;
		return this;
	}
	
	@SuppressWarnings("deprecation")
	public Material getMaterial(IBlockState state) {
		return isGrass? Material.GRASS : super.getMaterial(state);
	}
	
	/*public BlockUnlimitedDrawers(String modid, String material) {
		super("basicdrawers_" + modid + "_" + material, StorageDrawers.MOD_ID + ".basicDrawers");
		this.modid = modid;
		this.material = material;
	}*/
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

		return super.getActualState(state, worldIn, pos);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
	
		return super.createBlockState();
	}
	
	@Override
	public TileEntityDrawers createNewTileEntity(World world, int meta) {
		IBlockState state = getStateFromMeta(meta);
        EnumBasicDrawer type = state.getValue(BLOCK);

        return TileEntityGTDrawers.createEntity(type.getDrawerCount());
	}

}
