package pl.kamilkime.ytguilds;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import pl.kamilkime.ytguilds.cmds.MainCmd;
import pl.kamilkime.ytguilds.data.DataManager;
import pl.kamilkime.ytguilds.data.FileManager;
import pl.kamilkime.ytguilds.data.Settings;
import pl.kamilkime.ytguilds.listeners.InventoryClickListener;
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
		Settings.getInst().load();
		getCommand("g").setExecutor(new MainCmd());
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
	}
	
	public void onDisable(){
		DataManager.save();
	}
	
	public static Main getInst(){
		if(inst == null) return new Main();
		return inst;
	}
}