package pl.kamilkime.ytguilds.objects;

import java.util.ArrayList;
import java.util.List;

import pl.kamilkime.ytguilds.objects.utils.GuildUtils;

public class Guild {

	private String name;
	private String tag;
	private User admin;
	private List<User> members;
	private List<User> mods;
	private List<User> invited;
	private List<Guild> allies;
	private List<Guild> enemies;
	private List<Guild> allyInvs;
	private List<Guild> neutralInvs;
	private Region region;
	private Rank rank;

	private Guild(String tag){
		this.tag = tag;
		GuildUtils.addGuild(this);
	}
	
	public static Guild get(String tag){
		for(Guild g : GuildUtils.getGuils()){
			if(g.getTag().equalsIgnoreCase(tag)) return g;
		}
		return new Guild(tag);
	}
	
	public boolean hasRegion(){
		return (this.region !=null);
	}
	
	public String getName() {
		return name;
	}

	public String getTag() {
		return tag;
	}

	public User getAdmin() {
		return admin;
	}

	public List<User> getMembers() {
		if(this.members == null) this.members = new ArrayList<User>();
		return this.members;
	}

	public List<User> getMods() {
		if(this.mods == null) this.mods = new ArrayList<User>();
		return this.mods;
	}

	public List<User> getInvited() {
		if(this.invited == null) this.invited = new ArrayList<User>();
		return this.invited;
	}

	public List<Guild> getAllies() {
		if(this.allies == null) this.allies = new ArrayList<Guild>();
		return this.allies;
	}

	public List<Guild> getEnemies() {
		if(this.enemies == null) this.enemies = new ArrayList<Guild>();
		return this.enemies;
	}

	public List<Guild> getAllyInvs() {
		if(this.allyInvs == null) this.allyInvs = new ArrayList<Guild>();
		return this.allyInvs;
	}

	public List<Guild> getNeutralInvs() {
		if(this.neutralInvs == null) this.neutralInvs = new ArrayList<Guild>();
		return this.neutralInvs;
	}

	public Region getRegion() {
		return region;
	}

	public Rank getRank() {
		if(this.rank == null) this.rank = new Rank(this);
		return rank;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public void setMods(List<User> mods) {
		this.mods = mods;
	}

	public void setInvited(List<User> invited) {
		this.invited = invited;
	}

	public void setAllies(List<Guild> allies) {
		this.allies = allies;
	}

	public void setEnemies(List<Guild> enemies) {
		this.enemies = enemies;
	}

	public void setAllyInvs(List<Guild> allyInvs) {
		this.allyInvs = allyInvs;
	}

	public void setNeutralInvs(List<Guild> neutralInvs) {
		this.neutralInvs = neutralInvs;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}
	
	public void addInvite(User u){
		if(this.invited == null) this.invited = new ArrayList<User>();
		this.invited.add(u);
	}
	
	public void addMember(User u){
		if(this.members == null) this.members = new ArrayList<User>();
		this.members.add(u);
		u.setGuild(this);
		//TODO Add to region
	}
	
	public void addMod(User u){
		if(this.mods == null) this.mods = new ArrayList<User>();
		this.mods.add(u);
	}
	
	public void addAlly(Guild g){
		if(this.allies == null) this.allies = new ArrayList<Guild>();
		this.allies.add(g);
	}
	
	public void addEnemy(Guild g){
		if(this.enemies == null) this.enemies = new ArrayList<Guild>();
		this.enemies.add(g);
	}
	
	public void addAllyInv(Guild g){
		if(this.allyInvs == null) this.allyInvs = new ArrayList<Guild>();
		this.allyInvs.add(g);
	}
	
	public void addNeutralInv(Guild g){
		if(this.neutralInvs == null) this.neutralInvs = new ArrayList<Guild>();
		this.neutralInvs.add(g);
	}
	
	public void removeInvite(User u){
		this.invited.remove(u);
	}
	
	public void removeMember(User u){
		this.members.remove(u);
		u.setGuild(null);
		//TODO Remove from region
	}
	
	public void removeMod(User u){
		this.mods.remove(u);
	}
	
	public void removeAlly(Guild g){
		this.allies.remove(g);
	}
	
	public void removeEnemy(Guild g){
		this.enemies.remove(g);
	}
	
	public void removeAllyInv(Guild g){
		this.allyInvs.remove(g);
	}
	
	public void removeNeutralInv(Guild g){
		this.neutralInvs.remove(g);
	}
	
	public String toString(){
		return this.tag;
	}
}