package com.nomiceu.gregtechdrawers.event;

import com.jaquadro.minecraft.chameleon.Chameleon;
import com.jaquadro.minecraft.chameleon.resources.ModelRegistry;
import com.nomiceu.gregtechdrawers.GTDrawers;
import com.nomiceu.gregtechdrawers.block.BlockGTDrawers;
import com.nomiceu.gregtechdrawers.item.ItemGTTrim;
import com.nomiceu.gregtechdrawers.type.Mods;
import com.nomiceu.gregtechdrawers.block.model.GTDrawerModel;
import com.nomiceu.gregtechdrawers.render.tileentity.TileEntityGTDrawersRenderer;
import com.nomiceu.gregtechdrawers.tileentity.TileEntityGTDrawers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEvents {

	@SubscribeEvent
	public void onModelRegistryEvent(ModelRegistryEvent event) {
		for(ItemGTTrim trim : Mods.TRIM_ITEMS) {
			registerItem(trim);
		}
		
		ModelRegistry modelRegistry = Chameleon.instance.modelRegistry;
		for(BlockGTDrawers drawers : Mods.DRAWERS_BLOCKS) {
			//registerBlock(drawers);
			drawers.initDynamic();
			modelRegistry.registerModel(new GTDrawerModel.Register(drawers));
		}
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGTDrawers.class, new TileEntityGTDrawersRenderer());
	}
	
	public static void registerItem(Item item) {
		registerItem(item, 0);
	}
	
	public static void registerItem(Item item, int meta) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	public static void registerBlock(BlockGTDrawers block) {
		ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation(GTDrawers.MODID + ":basicdrawers_" + block.mod + "_" + block.material, "block=" + state.getValue(BlockGTDrawers.BLOCK) + ",facing=" + state.getValue(BlockGTDrawers.FACING));
			}
		});
	}
	
}
