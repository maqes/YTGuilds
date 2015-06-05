package pl.kamilkime.ytguilds.data;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import pl.kamilkime.ytguilds.Main;

public class Settings {

	private static Settings inst;
	private FileConfiguration cfg = Main.getInst().getConfig();
	public List<String> items;
	public List<String> vipItems;
	
	private Settings(){
		inst = this;
	}
	
	public void load(){
		this.items = cfg.getStringList("items");
		this.vipItems = cfg.getStringList("vipItems");
	}
	
	public static Settings getInst(){
		if(inst == null) return new Settings();
		return inst;
	}
}