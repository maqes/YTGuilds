package pl.kamilkime.ytguilds.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import pl.kamilkime.ytguilds.objects.User;

public class PlayerJoinListener implements Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		User.get(e.getPlayer());
	}
}