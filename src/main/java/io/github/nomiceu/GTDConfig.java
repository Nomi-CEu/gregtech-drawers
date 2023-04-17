package io.github.nomiceu;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

@Config(modid = GTDrawers.MODID)
public class GTDConfig {
	@Name("Fix TOP Displaying Taped Drawers")
	@Comment("Fix a bug where TOP would display a taped drawer if the drawer contained items, if the [Keep Contents on Break] storage drawer config was enabled.")
	@RequiresMcRestart
	public static boolean fixTOPTaped = true;
}
