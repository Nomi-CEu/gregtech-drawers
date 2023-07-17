package com.nomiceu.gregtechdrawers.proxy;

import com.nomiceu.gregtechdrawers.GTDrawers;
import com.nomiceu.gregtechdrawers.type.Mods;
import com.nomiceu.gregtechdrawers.event.RegistryEvents;
import com.nomiceu.gregtechdrawers.tileentity.GTDTileEntities;
import com.nomiceu.gregtechdrawers.tileentity.TileEntityGTDrawers;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		Mods.init();
		
		GameRegistry.registerTileEntity(TileEntityGTDrawers.Slot1.class, new ResourceLocation(GTDrawers.MODID, "basicdrawers.1"));
        GameRegistry.registerTileEntity(TileEntityGTDrawers.Slot2.class, new ResourceLocation(GTDrawers.MODID, "basicdrawers.2"));
        GameRegistry.registerTileEntity(TileEntityGTDrawers.Slot4.class, new ResourceLocation(GTDrawers.MODID, "basicdrawers.4"));
		
		MinecraftForge.EVENT_BUS.register(new RegistryEvents());
	}
	
	public void init(FMLInitializationEvent event) {
		GTDTileEntities.init();
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	public void serverInit(FMLServerStartingEvent event) {
		
	}
	
	
}
