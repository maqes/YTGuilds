package pl.kamilkime.ytguilds.data;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import pl.kamilkime.ytguilds.objects.Guild;
import pl.kamilkime.ytguilds.objects.Rank;
import pl.kamilkime.ytguilds.objects.Region;
import pl.kamilkime.ytguilds.objects.User;
import pl.kamilkime.ytguilds.objects.utils.ChatType;
import pl.kamilkime.ytguilds.objects.utils.GuildUtils;
import pl.kamilkime.ytguilds.objects.utils.RegionUtils;
import pl.kamilkime.ytguilds.objects.utils.UserUtils;

public class DataManager {

	private static void sendConsole(String msg){
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
	
	private static Location fromString(String s){
		String[] ss = s.split(" ");
		return new Location(Bukkit.getWorld(ss[0]), Integer.parseInt(ss[1]), Integer.parseInt(ss[2]), Integer.parseInt(ss[3]));
	}
	
	private static String fromLocation(Location l){
		return new String(l.getWorld().getName() + " " + l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ());
	}
	
	public static void load(){
		loadGuilds();
		loadUsers();
		loadRegions();
		checkObjects();
	}
	
	public static void save(){
		saveGuilds();
		saveUsers();
		saveRegions();
	}
	
	public static void checkObjects(){
		int removed = 0;
		for(Guild g : GuildUtils.getGuilds()){
			if(g.getName() == null || g.getTag() == null || g.getAdmin() == null){
				GuildUtils.removeGuild(g);
				removed++;
			}
		}
		for(User u : UserUtils.getUsers()){
			if(u.getName() == null || u.getUUID() == null){
				UserUtils.removeUser(u);
				removed++;
			}
		}
		for(Region r : RegionUtils.getRegions()){
			if(r.getName() == null || r.getGuild() == null || r.getCenter() == null){
				RegionUtils.removeRegion(r);
				removed++;
			}
			if(r.getLowerLoc() == null || r.getUpperLoc() == null){
				r.reCalculate();
			}
		}
		sendConsole("&6&l" + removed + " &a&lInvalid objects removed!");
	}
	
	public static void loadGuilds(){
		int loaded = 0;
		for(File f : FileManager.getGuildsFolder().listFiles()){
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
			Guild g = Guild.get(yml.getString("tag"));
			g.setName(yml.getString("name"));
			g.setAdmin(User.get(yml.getString("admin")));
			g.setMembers(UserUtils.fromNames(yml.getStringList("members")));
			g.setInvited(UserUtils.fromNames(yml.getStringList("invited")));
			g.setMods(UserUtils.fromNames(yml.getStringList("mods")));
			g.setAllies(GuildUtils.fromTags(yml.getStringList("allies")));
			g.setEnemies(GuildUtils.fromTags(yml.getStringList("enemies")));
			g.setAllyInvs(GuildUtils.fromTags(yml.getStringList("allyInvs")));
			g.setNeutralInvs(GuildUtils.fromTags(yml.getStringList("neutralInvs")));
			if(yml.get("region") !=null){
				g.setRegion(Region.get(yml.getString("region")));
			}
			g.setRank(new Rank(g));
			loaded++;
		}
		sendConsole("&6&l" + loaded + " &a&lGuilds loaded!");
	}
	
	public static void loadUsers(){
		int loaded = 0;
		for(File f : FileManager.getUsersFolder().listFiles()){
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
			User u = User.get(yml.getString("name"));
			u.setUUID(UUID.fromString(yml.getString("uuid")));
			u.setChat(ChatType.valueOf(yml.getString("chatType").toUpperCase()));
			u.getRank().setRank(yml.getInt("rank"));
			u.getRank().setKills(yml.getInt("kills"));
			u.getRank().setDeaths(yml.getInt("deaths"));
			if(yml.get("guild") !=null){
				u.setGuild(Guild.get(yml.getString("guild")));
			}
			loaded++;
		}
		sendConsole("&6&l" + loaded + " &a&lUsers loaded!");
	}
	
	public static void loadRegions(){
		int loaded = 0;
		for(File f : FileManager.getRegionsFolder().listFiles()){
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
			Region r = Region.get(yml.getString("name"));
			r.setGuild(Guild.get(yml.getString("guild")));
			r.setCenter(fromString(yml.getString("center")));
			r.setSize(yml.getInt("size"));
			r.setLowerLoc(fromString(yml.getString("lowerLoc")));
			r.setUpperLoc(fromString(yml.getString("upperLoc")));
			loaded++;
		}
		sendConsole("&6&l" + loaded + " &a&lRegions loaded!");
	}
	
	public static void saveGuilds(){
		int saved = 0;
		for(Guild g : GuildUtils.getGuilds()){
			File f = new File(FileManager.getGuildsFolder(), g.getTag() + ".yml");
			if(!f.exists()){
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
			yml.set("name", g.getName());
			yml.set("tag", g.getTag());
			yml.set("admin", g.getAdmin().getName());
			yml.set("members", UserUtils.toNames(g.getMembers()));
			yml.set("invited", UserUtils.toNames(g.getInvited()));
			yml.set("mods", UserUtils.toNames(g.getMods()));
			yml.set("allies", GuildUtils.toTags(g.getAllies()));
			yml.set("enemies", GuildUtils.toTags(g.getEnemies()));
			yml.set("allyInvs", GuildUtils.toTags(g.getAllyInvs()));
			yml.set("neutralInvs", GuildUtils.toTags(g.getNeutralInvs()));
			yml.set("region", g.getRegion().getName());
			try {
				yml.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			saved++;
		}
		sendConsole("&6&l" + saved + " &a&lGuilds saved!");
	}
	
	public static void saveUsers(){
		int saved = 0;
		for(User u : UserUtils.getUsers()){
			File f = new File(FileManager.getUsersFolder(), u.getName() + ".yml");
			if(!f.exists()){
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
			yml.set("name", u.getName());
			yml.set("uuid", u.getUUID().toString());
			yml.set("rank", u.getRank().getRank());
			yml.set("kills", u.getRank().getKills());
			yml.set("deaths", u.getRank().getDeaths());
			yml.set("chatType", u.getChat().toString());
			if(u.hasGuild()){
				yml.set("guild", u.getGuild().getTag());
			}
			else{
				yml.set("guild", null);
			}
			try {
				yml.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			saved++;
		}
		sendConsole("&6&l" + saved + " &a&lUsers saved!");
	}
	
	public static void saveRegions(){
		int saved = 0;
		for(Region r : RegionUtils.getRegions()){
			File f = new File(FileManager.getRegionsFolder(), r.getName() + ".yml");
			if(!f.exists()){
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
			yml.set("name", r.getName());
			yml.set("guild", r.getGuild().getTag());
			yml.set("size", r.getSize());
			yml.set("center", fromLocation(r.getCenter()));
			yml.set("lowerLoc", fromLocation(r.getLowerLoc()));
			yml.set("upperLoc", fromLocation(r.getUpperLoc()));
			try {
				yml.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			saved++;
		}
		sendConsole("&6&l" + saved + " &a&lRegions saved!");
	}
}