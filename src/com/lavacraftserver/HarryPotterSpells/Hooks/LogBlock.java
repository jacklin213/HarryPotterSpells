package com.lavacraftserver.HarryPotterSpells.Hooks;

public class LogBlock {
	/* TODO move to addon
	HarryPotterSpells plugin;
	public LogBlock(HarryPotterSpells instance){
		plugin=instance;
	}
	public Consumer LogBlockConsumer;
	public de.diddiz.LogBlock.LogBlock LogBlockPlugin;
	
	public void setupLogBlock() {
		if(plugin.getConfig().getBoolean("LogBlockEnabled")) {
			Plugin lbplugin = Bukkit.getServer().getPluginManager().getPlugin("LogBlock");
			if(lbplugin != null) {
				LogBlockConsumer = ((de.diddiz.LogBlock.LogBlock)lbplugin).getConsumer();
				LogBlockPlugin = (de.diddiz.LogBlock.LogBlock)lbplugin;
			} else {
				plugin.PM.log("Could not hook into LogBlock. LogBlock features have been disabled.", Level.WARNING);
				plugin.getConfig().set("LogBlockEnabled", false);
			}
		}
	}

	public void logSpell(Player p, String spell) {
		if(LogBlockConsumer != null) {
			LogBlockConsumer.queueChat(p.getName(), "SPELL CAST: " + spell);
		}
		
	}

	*/
	

}
