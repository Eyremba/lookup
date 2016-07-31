package me.Dunios.Lookup.SPlayer;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.Dunios.Lookup.Main;

public class SPlayer
{
	private String name;
	private UUID uuid;
	private String ip;
	
	private boolean online;
	private int joinCount;
	
	private String firstLogin;
	private String lastLogin;
	
	public SPlayer()
	{
		SPlayerManager.players.add(this);
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public UUID getUuid()
	{
		return uuid;
	}
	public void setUuid(UUID uuid)
	{
		this.uuid = uuid;
	}
	public String getIp()
	{
		return ip;
	}
	public void setIp(String ip)
	{
		this.ip = ip;
	}
	public boolean isOnline()
	{
		return online;
	}
	public void setOnline(boolean online)
	{
		this.online = online;
	}
	public int getJoinCount()
	{
		return joinCount;
	}
	public void setJoinCount(int joinCount)
	{
		this.joinCount = joinCount;
	}
	public String getFirstLogin()
	{
		return firstLogin;
	}
	public void setFirstLogin(String firstLogin)
	{
		this.firstLogin = firstLogin;
	}
	public String getLastLogin()
	{
		return lastLogin;
	}
	public void setLastLogin(String lastLogin)
	{
		this.lastLogin = lastLogin;
	}
	
	public void save()
	{
		File f = new File(Main.getInstance().getDataFolder() + "/players", getUuid().toString() + ".yml");
		if(!f.exists())
		{
			try
			{
				f.createNewFile();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		cfg.set("player.name", getName());
		cfg.set("player.uuid", getUuid().toString());
		cfg.set("player.ip", getIp());
		cfg.set("player.joincount", getJoinCount());
		cfg.set("player.firstlogin", getFirstLogin());
		cfg.set("player.lastlogin", getLastLogin());
		
		try
		{
			cfg.save(f);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public void loadFromConfig(FileConfiguration cfg)
	{
		setName(cfg.getString("player.name"));
		setUuid(UUID.fromString(cfg.getString("player.uuid")));
		setIp(cfg.getString("player.ip"));
		setJoinCount(cfg.getInt("player.joincount"));
		setLastLogin(cfg.getString("player.lastlogin"));
		setFirstLogin(cfg.getString("player.firstlogin"));
	}
}
