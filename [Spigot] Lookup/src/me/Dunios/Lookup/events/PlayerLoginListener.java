package me.Dunios.Lookup.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.Dunios.Lookup.SPlayer.SPlayer;
import me.Dunios.Lookup.SPlayer.SPlayerManager;

public class PlayerLoginListener implements Listener
{
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		if(!SPlayerManager.userExist(e.getPlayer().getUniqueId()))
		{
			SPlayerManager.createPlayer(e.getPlayer());
		}
		
		SPlayer sp = SPlayerManager.getSPlayerByName(e.getPlayer().getName());
		sp.setOnline(true);
		sp.setLastLogin(SPlayerManager.getCurrentDate());
		sp.setJoinCount(sp.getJoinCount() + 1);
		sp.save();
	}
}
