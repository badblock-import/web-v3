package be.cixxor.vote;

import org.bukkit.plugin.java.JavaPlugin;

import be.cixxor.vote.commands.CommandVote;
import net.md_5.bungee.api.ChatColor;

public class Vote extends JavaPlugin{
	
	@Override
	public void onEnable() {
		setupConfig();
		setup();
		System.out.println(ChatColor.GREEN + "Website V3 > Vote Plugin Enabled");
	}
	
	public void setup() {
	getCommand("wwwvote").setExecutor(new CommandVote());
	}
	
	public void setupConfig() {
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
	}
	
	@Override
	public void onDisable() {
		System.out.println(ChatColor.RED + "Website V3 > Vote Plugin Disabled");
	}

}
