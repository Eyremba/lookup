package me.Dunios.Lookup.command;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.Dunios.Lookup.Main;
import me.Dunios.Lookup.SPlayer.SPlayer;
import me.Dunios.Lookup.SPlayer.SPlayerManager;

public class LookupCommand implements CommandExecutor
{

	private String PREFIX = "§cLookup §8» §7";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(sender.hasPermission("lookup.use"))
		{
			if(args.length == 0)
			{
				sender.sendMessage(PREFIX + "§cWrong usage: §6/lookup <username>");
			}
			else if(args.length == 1)
			{
				sender.sendMessage(PREFIX + "Loading data...");
				new BukkitRunnable()
				{
					
					@Override
					public void run()
					{
						SPlayer sp = SPlayerManager.getSPlayerByName(args[0]);
						if(sp == null)
						{
							sender.sendMessage(PREFIX + "§cNo data for §6" + args[0]);
							return;
						}
						sender.sendMessage(PREFIX + "§8§m-----------------------------------");
						sender.sendMessage(PREFIX + "Name§8: §6" + sp.getName());
						sender.sendMessage(PREFIX + "UUID§8: §6" + sp.getUuid());
						sender.sendMessage(PREFIX + "IP§8: §6" + sp.getIp() + " §7(" + getCountry(sp.getIp()) + ")");
						ArrayList<SPlayer> list = SPlayerManager.getSPlayersByIP(sp.getIp());
						if(list.size() > 1)
						{
							sender.sendMessage(PREFIX + "Owned accounts");
							for(SPlayer s : list)
							{
								sender.sendMessage(PREFIX + "  §8-§6" + s.getName());
							}
						}
						sender.sendMessage(PREFIX + "Online§8: §6" + sp.isOnline());
						sender.sendMessage(PREFIX + "First login§8: §6" + sp.getFirstLogin());
						sender.sendMessage(PREFIX + "Last login§8: §6" + sp.getLastLogin());
						sender.sendMessage(PREFIX + "Join count§8: §6" + sp.getJoinCount());
						Player p = Bukkit.getPlayer(sp.getUuid());
						if(p != null)
						{
							sender.sendMessage(PREFIX + "§8§m------------§a§l  Live data §r§8§m-------------");
							sender.sendMessage(PREFIX + "Gamemode§8: §6" + p.getGameMode().toString().toLowerCase());
							sender.sendMessage(PREFIX + "Location§8: §6(" + p.getLocation().getBlockX() + ", " + p.getLocation().getBlockY() + ", " + p.getLocation().getBlockZ() + ") " + p.getLocation().getWorld().getName());
							sender.sendMessage(PREFIX + "Health§8: §6" + p.getHealth());
							sender.sendMessage(PREFIX + "Hunger§8: §6" + p.getFoodLevel()); 
							sender.sendMessage(PREFIX + "Ping§8: §6" + ((CraftPlayer) p).getHandle().ping + "ms"); 
						}
						sender.sendMessage(PREFIX + "§8§m-----------------------------------");
					}
				}.runTaskAsynchronously(Main.getInstance());
			}
		}
		return false;
	}
	
	
	public String getCountry(String ip)
	{
		try
		{
			URL url = new URL("http://ipinfo.io/" + ip + "/country");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			return in.readLine();
		}
		catch(Exception ex)
		{
			return "undefined";
		}
	}
}
