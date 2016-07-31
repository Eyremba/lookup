package me.Dunios.Lookup;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Dunios.Lookup.SPlayer.SPlayerManager;
import me.Dunios.Lookup.command.LookupCommand;
import me.Dunios.Lookup.events.PlayerLoginListener;
import me.Dunios.Lookup.events.PlayerLogoutListener;

public class Main extends JavaPlugin
{
	private static Main plugin;
	
	@Override
	public void onEnable()
	{
		plugin = this;
		commands();
		events();
		utils();
	}
	
	public static Main getInstance()
	{
		return plugin;
	}

	private void commands()
	{
		getCommand("lookup").setExecutor(new LookupCommand());
	}

	private void events()
	{
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerLoginListener(), this);
		pm.registerEvents(new PlayerLogoutListener(), this);
	}

	private void utils()
	{
		SPlayerManager.init();
	}
}
