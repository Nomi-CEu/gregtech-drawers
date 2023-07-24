package com.nomiceu.gregtechdrawers;

import org.apache.logging.log4j.Logger;

import com.nomiceu.gregtechdrawers.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = GTDrawers.MODID,
		version = Tags.VERSION,
		name = GTDrawers.NAME,
		acceptedMinecraftVersions = "[1.12.2,1.13)",
		dependencies = "required:forge@[14.23.5.2847,);"
				+ "required-after:gregtech@[2.6,);"
				+ "required-after:storagedrawers;")
public class GTDrawers {
	public static final String MODID = "gregtechdrawers";

	public static final String NAME = "Gregtech Drawers";
	
	@Instance(GTDrawers.MODID)
	public static GTDrawers instance;
	
	@SidedProxy(clientSide = "com.nomiceu.gregtechdrawers.proxy.ClientProxy", serverSide = "com.nomiceu.gregtechdrawers.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static Logger logger;
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
	
	@EventHandler
	public void serverInit(FMLServerStartingEvent event) {
		proxy.serverInit(event);
	}
}
