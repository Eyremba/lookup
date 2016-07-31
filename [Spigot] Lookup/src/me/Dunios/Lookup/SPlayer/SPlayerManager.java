package me.Dunios.Lookup.SPlayer;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.Dunios.Lookup.Main;

public class SPlayerManager
{
	public static ArrayList<SPlayer> players = new ArrayList<>();
	public static void init()
	{
		players.clear();
		File folder = new File(Main.getInstance().getDataFolder() + "/players");
		if(!folder.exists())
		{
			folder.mkdirs();
		}
		for(File f : folder.listFiles())
		{
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
			SPlayer sp = new SPlayer();
			sp.loadFromConfig(cfg);
		}
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(!userExist(p.getUniqueId()))
			{
				createPlayer(p);
			}
		}
		
		Bukkit.getConsoleSender().sendMessage("§8[§cLookup§8] §7Loaded §e" + players.size() + "§7 players");
	}
	
	public static SPlayer getSPlayerByName(String username)
	{
		for(SPlayer sp : players)
		{
			if(sp.getName().equalsIgnoreCase(username))
			{
				return sp;
			}
		}
		return null;
	}
	
	public static SPlayer getSPlayerByUUID(UUID uuid)
	{
		for(SPlayer sp : players)
		{
			if(sp.getUuid().equals(uuid))
			{
				return sp;
			}
		}
		return null;
	}
	
	public static boolean userExist(UUID uuid)
	{
		for(SPlayer sp : players)
		{
			if(sp.getUuid().equals(uuid))
			{
				return true;
			}
		}
		return false;
	}
	
	public static void createPlayer(Player player)
	{
		SPlayer p = new SPlayer();
		p.setName(player.getName());
		p.setUuid(player.getUniqueId());
		p.setFirstLogin(getCurrentDate());
		p.setLastLogin(getCurrentDate());
		p.setIp(player.getAddress().getAddress().getHostAddress());
		p.setOnline(true);
		p.setJoinCount(1);
		p.save();
	}
	
	
	
	public static String getCurrentDate()
	{
		Date d = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		
		return df.format(d);
	}
}
