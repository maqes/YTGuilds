package pl.kamilkime.ytguilds.data;

import java.io.File;

import pl.kamilkime.ytguilds.Main;

public class FileManager {

	private static File mainDir = Main.getInst().getDataFolder();
	private static File cfgFile = new File(mainDir, "config.yml");
	private static File users = new File(mainDir, "users");
	private static File guilds = new File(mainDir, "guilds");
	private static File regions = new File(mainDir, "regions");
	
	public static void check(){
		if(!mainDir.exists()) mainDir.mkdir();
		if(!users.exists()) users.mkdir();
		if(!guilds.exists()) guilds.mkdir();
		if(!regions.exists()) regions.mkdir();
		if(!cfgFile.exists()) Main.getInst().saveDefaultConfig();
	}
	
	public static File getUsersFolder(){
		return users;
	}
	
	public static File getGuildsFolder(){
		return guilds;
	}
	
	public static File getRegionsFolder(){
		return regions;
	}
}