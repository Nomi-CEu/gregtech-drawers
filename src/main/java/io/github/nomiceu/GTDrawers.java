package io.github.nomiceu;

import java.io.File;

import io.github.nomiceu.integration.TOP.TOPCompat;
import net.minecraftforge.fml.common.Loader;
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

@Mod(modid = GTDrawers.MODID,
		version = GTDrawers.VERSION,
		name = GTDrawers.NAME,
		acceptedMinecraftVersions = "[1.12.2,1.13)",
		dependencies = "required:forge@[14.23.5.2847,);"
				+ "required-after:gregtech@[2.6,);"
				+ "required-after:storagedrawers;")
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
		configFolder = new File(event.getModConfigurationDirectory(), "gregtechdrawers");
		if(!configFolder.isDirectory())
			configFolder.mkdirs();
		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		if (Loader.isModLoaded("theoneprobe")) {
			logger.info("Detected The One Probe. Enabling custom handlers.");
			TOPCompat.registerProviders();
		}
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
