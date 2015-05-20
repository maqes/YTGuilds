package pl.kamilkime.ytguilds.objects;

import pl.kamilkime.ytguilds.objects.utils.RankType;
import pl.kamilkime.ytguilds.objects.utils.RankUtils;

public class Rank implements Comparable<Rank>{

	private int rank;
	private int kills;
	private int deaths;
	private User user;
	private Guild guild;
	private RankType type;
	
	public Rank(Guild guild){
		type = RankType.GUILD;
		this.guild = guild;
		RankUtils.addRank(this);
	}
	
	public Rank(User user){
		type = RankType.USER;
		this.user = user;
		RankUtils.addRank(this);
	}

	public int getRank() {
		if(this.getType().equals(RankType.USER)) return this.rank;
		int toReturn = 0;
		for(User u : this.guild.getMembers()){
			toReturn += u.getRank().getRank();
		}
		return toReturn/this.getGuild().getMembers().size();
	}

	public int getKills() {
		return this.kills;
	}

	public int getDeaths() {
		return this.deaths;
	}

	public User getUser() {
		return this.user;
	}

	public Guild getGuild() {
		return this.guild;
	}

	public RankType getType() {
		return type;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setGuild(Guild guild) {
		this.guild = guild;
	}

	public void setType(RankType type) {
		this.type = type;
	}
	
	public String getOwnerID(){
		if(this.type.equals(RankType.USER)) return this.user.getName();
		return this.guild.getTag();
	}
	
	public int compareTo(Rank rank){
		int i = Integer.compare(rank.getRank(), getRank());
		if(i == 0) i = getOwnerID().compareTo(rank.getOwnerID());
		return i;
	}
	
	public String toString(){
		return Integer.toString(this.rank);
	}
}