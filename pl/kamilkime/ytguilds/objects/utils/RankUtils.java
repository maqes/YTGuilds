package pl.kamilkime.ytguilds.objects.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.kamilkime.ytguilds.objects.Guild;
import pl.kamilkime.ytguilds.objects.Rank;
import pl.kamilkime.ytguilds.objects.User;

public class RankUtils {

	private static List<Rank> users = new ArrayList<Rank>();
	private static List<Rank> guilds = new ArrayList<Rank>();
	
	public static List<Rank> getUserRanks(){
		return new ArrayList<Rank>(users);
	}
	
	public static List<Rank> getGuildRanks(){
		return new ArrayList<Rank>(guilds);
	}
	
	public static void updateAll(){
		Collections.sort(users);
		Collections.sort(guilds);
	}
	
	public static void update(User u){
		if(!users.contains(u.getRank())) users.add(u.getRank());
		Collections.sort(users);
		if(u.hasGuild()) update(u.getGuild());
	}
	
	public static void update(Guild g){
		if(!guilds.contains(g.getRank())) guilds.add(g.getRank());
		Collections.sort(guilds);
	}
	
	public static void addRank(Rank r){
		if(r.getType().equals(RankType.USER)){
			users.add(r);
		}
		else{
			guilds.add(r);
		}
		updateAll();
	}
	
	public static void removeRank(Rank r){
		if(r.getType().equals(RankType.USER)){
			users.remove(r);
		}
		else{
			guilds.remove(r);
		}
		updateAll();
	}
	
	public static Rank getByTag(String tag){
		for(Rank r : guilds){
			if(r.getGuild().getTag().equalsIgnoreCase(tag)) return r;
		}
		return null;
	}
	
	public static int getPosition(User u){
		return users.indexOf(u.getRank()) + 1;
	}
	
	public static int getPosition(Guild g){
		return guilds.indexOf(g.getRank()) + 1;
	}
	
	public static User getUserByPosition(int pos){
		return users.get(pos-1).getUser();
	}
	
	public static Guild getGuildByPosition(int pos){
		return guilds.get(pos-1).getGuild();
	}
}