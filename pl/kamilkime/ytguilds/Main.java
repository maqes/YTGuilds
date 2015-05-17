package pl.kamilkime.ytguilds;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	private static Main inst;
	
	private Main(){
		inst = this;
	}
	
	public void onEnable(){}
	
	public void onDisable(){}
	
	public static Main getInst(){
		if(inst == null) return new Main();
		return inst;
	}
}