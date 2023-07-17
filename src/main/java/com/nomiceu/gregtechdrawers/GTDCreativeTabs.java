package com.nomiceu.gregtechdrawers;

import com.jaquadro.minecraft.storagedrawers.api.storage.EnumBasicDrawer;
import com.jaquadro.minecraft.storagedrawers.core.ModCreativeTabs;
import com.nomiceu.gregtechdrawers.type.DrawerMaterial;
import com.nomiceu.gregtechdrawers.type.Mod;
import com.nomiceu.gregtechdrawers.type.Mods;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GTDCreativeTabs {

	public static final CreativeTabs TAB = new CreativeTabs(GTDrawers.MODID) {
		
		{
			setNoTitle();
			setBackgroundImageName("item_search.png");
		}
		
		@Override
        @SideOnly(Side.CLIENT)
		public ItemStack createIcon() {
			for(Mod mod : Mods.ENABLED_MODS) {
				DrawerMaterial material = mod.getDefaultMaterial();
				if(material != null)
					return new ItemStack(material.getDrawerItem(), 1, EnumBasicDrawer.FULL1.getMetadata());
			}
			return ModCreativeTabs.tabStorageDrawers.getIcon();
		}
		
		@Override
		public void displayAllRelevantItems(NonNullList<ItemStack> items) {
			for(Mod mod : Mods.ENABLED_MODS) {
				for(DrawerMaterial material : mod) {
					material.getDrawerItem().getSubItems(this, items);
					material.getTrimItem().getSubItems(this, items);
				}
			}
		}
		
		@Override
		public boolean hasSearchBar() {
			return true;
		}
		
	};
	
}
