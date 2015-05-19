package pl.kamilkime.ytguilds.objects.utils;

import java.util.ArrayList;
import java.util.List;

import pl.kamilkime.ytguilds.objects.Guild;
import pl.kamilkime.ytguilds.objects.User;

public class GuildUtils {

	private static List<Guild> guilds = new ArrayList<Guild>();
	
	public static List<Guild> getGuils(){
		return new ArrayList<Guild>(guilds);
	}
	
	public static void addGuild(Guild u){
		if(!guilds.contains(u)) guilds.add(u);
	}
	
	public static void removeGuild(Guild u){
		if(guilds.contains(u)) guilds.remove(u);
	}
	
	//TODO toTags(), fromTags()
	
	public static Guild getPlayerGuild(String name){
		for(Guild g : guilds){
			for(User u : g.getMembers()){
				if(u.getName().equalsIgnoreCase(name)){
					return g;
				}
			}
		}
		return null;
	}
	
	public static Guild byName(String name){
		for(Guild g : guilds){
			if(g.getName().equalsIgnoreCase(name)) return g;
		}
		return null;
	}
	
	public static boolean tagExists(String tag){
		for(Guild g : guilds){
			if(g.getTag().equalsIgnoreCase(tag)) return true;
		}
		return false;
	}
	
	public static boolean nameExists(String name){
		for(Guild g : guilds){
			if(g.getName().equalsIgnoreCase(name)) return true;
		}
		return false;
	}
}