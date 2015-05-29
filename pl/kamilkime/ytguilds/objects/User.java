package pl.kamilkime.ytguilds.objects;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import pl.kamilkime.ytguilds.objects.utils.ChatType;
import pl.kamilkime.ytguilds.objects.utils.RankUtils;
import pl.kamilkime.ytguilds.objects.utils.UserUtils;

public class User {

	private String name;
	private UUID uuid;
	private Guild guild;
	private Rank rank;
	private ChatType chat;

	private User(String name){
		this.name = name;
		UserUtils.addUser(this);
		RankUtils.update(this);
	}
	
	private User(Player p){
		this.name = p.getName();
		this.uuid = p.getUniqueId();
		UserUtils.addUser(this);
		RankUtils.update(this);
	}
	
	public String getName() {
		return this.name;
	}
	
	public UUID getUUID(){
		return this.uuid;
	}

	public Guild getGuild() {
		return this.guild;
	}

	public Rank getRank() {
		if(this.rank !=null) return rank;
		this.rank = new Rank(this);
		this.rank.setKills(0);
		this.rank.setDeaths(0);
		this.rank.setRank(1000);
		return this.rank;
	}

	public ChatType getChat() {
		if(this.chat !=null) return this.chat;
		this.chat = ChatType.PUBLIC;
		return this.chat;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setUUID(UUID uuid){
		this.uuid = uuid;
	}

	public void setGuild(Guild guild) {
		this.guild = guild;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public void setChat(ChatType chat) {
		this.chat = chat;
	}
	
	public boolean isOnline(){
		return (Bukkit.getPlayer(this.name) !=null);
	}
	
	public Player getPlayer(){
		return Bukkit.getPlayer(this.name);
	}
	
	public boolean isInvited(Guild g){
		for(User u : g.getInvited()){
			if(u.equals(this)) return true;
		}
		return false;
	}
	
	public boolean isAdmin(){
		if(!hasGuild()) return false;
		return this.guild.getAdmin().equals(this) ? true : false;
	}
	
	public boolean isMod(){
		if(!hasGuild()) return false;
		return this.guild.getMods().contains(this) ? true : false;
	}
	
	public boolean hasGuild(){
		return (this.guild !=null);
	}
	
	public static User get(String name){
		for(User u : UserUtils.getUsers()){
			if(u.getName().equalsIgnoreCase(name)) return u;
		}
		return new User(name);
	}
	
	public static User get(Player p){
		for(User u : UserUtils.getUsers()){
			if(u.getName().equalsIgnoreCase(p.getName())) return u;
			if(u.getUUID().equals(p.getUniqueId())){
				//TODO Remove file
				return u;
			}
		}
		return new User(p);
	}
	
	public String toString(){
		return this.name;
	}
}