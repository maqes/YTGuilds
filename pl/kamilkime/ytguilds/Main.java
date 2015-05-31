package pl.kamilkime.ytguilds;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import pl.kamilkime.ytguilds.data.DataManager;
import pl.kamilkime.ytguilds.data.FileManager;
import pl.kamilkime.ytguilds.listeners.PlayerJoinListener;

public class Main extends JavaPlugin{

	private static Main inst;
	
	public Main(){
		inst = this;
	}
	
	public void onEnable(){
		inst = this;
		FileManager.check();
		DataManager.load();
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
	}
	
	public void onDisable(){
		DataManager.save();
	}
	
	public static Main getInst(){
		if(inst == null) return new Main();
		return inst;
	}
}