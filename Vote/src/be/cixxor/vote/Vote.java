package be.cixxor.vote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import be.cixxor.vote.commands.CommandVote;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;

public class Vote extends JavaPlugin{
	
	public static Vote instance;
	
	@Override
	public void onEnable() {
		
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			setup();
			System.out.println(ChatColor.GREEN + "Website V3 > Vote Plugin Enabled");
			setupConfig();	
		} else {
			System.out.println(ChatColor.RED + "Website V3 > Vote Plugin Disabled");
			throw new RuntimeException("Could not find PlaceholderAPI ! The vote plugin cannot work without it !");
		}
	}
	
	
	public void setup() {
	instance = this;
	getCommand("wwwvote").setExecutor(new CommandVote());
	}
	
	
	public void setupConfig() {
	this.getConfig().options().copyDefaults(true);
	this.saveConfig();
	}
	
	public static Vote getInstance() {
		return instance;
	}
	
	public void getDatasOfPlayer(Player p) throws IOException {
		if (p == null) return;
		String str = this.getConfig().getString("apilink").replace("_playername_", p.getName());
		URL theLink = new URL(str);
		URLConnection yc = theLink.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine())!= null) {
			System.out.println(inputLine);
			PlaceholderAPI.setPlaceholders(p, inputLine);
		}
		in.close();
	}
	
	@Override
	public void onDisable() {
		System.out.println(ChatColor.RED + "Website V3 > Vote Plugin Disabled");
	}
	
}
