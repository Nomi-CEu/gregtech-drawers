package io.github.nomiceu;

import java.io.File;

import org.apache.logging.log4j.Logger;

import io.github.nomiceu.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = GTDrawers.MODID, version = GTDrawers.VERSION, name = GTDrawers.NAME,
		useMetadata = true)
public class GTDrawers {
	public static final String MODID = "gregtechdrawers";
	public static final String VERSION = "${version}";

	public static final String NAME = "Gregtech Drawers";
	
	@Instance(GTDrawers.MODID)
	public static GTDrawers instance;
	
	@SidedProxy(clientSide = "io.github.nomiceu.proxy.ClientProxy", serverSide = "io.github.nomiceu.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static Logger logger;
	
	public static File configFolder; 
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		configFolder = new File(event.getModConfigurationDirectory(), "storagedrawerskappa");
		if(!configFolder.isDirectory())
			configFolder.mkdirs();
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
