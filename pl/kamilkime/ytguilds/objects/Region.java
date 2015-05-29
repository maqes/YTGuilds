package pl.kamilkime.ytguilds.objects;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import pl.kamilkime.ytguilds.objects.utils.RegionUtils;

public class Region {

	private String name;
	private Guild guild;
	private Location center;
	private Location lowerLoc;
	private Location upperLoc;
	private int size;
	
	private Region(String name){
		this.name = name;
		RegionUtils.addRegion(this);
	}
	
	public Region(Guild guild, Location center, int size){
		this.guild = guild;
		this.name = guild.getTag();
		this.center = center;
		this.size = size;
		RegionUtils.addRegion(this);
		reCalculate();
	}
	
	public static Region get(String name){
		for(Region r : RegionUtils.getRegions()){
			if(r.getName().equalsIgnoreCase(name)) return r;
		}
		return new Region(name);
	}
	
	public void reCalculate(){
		Vector low = new Vector(center.getBlockX() - this.size, 0, center.getBlockZ() - this.size);
		Vector up = new Vector(center.getBlockX() + this.size, 256, center.getBlockZ() + this.size);
		this.lowerLoc = low.toLocation(center.getWorld());
		this.upperLoc = up.toLocation(center.getWorld());
	}

	public String getName() {
		return this.name;
	}

	public Guild getGuild() {
		return this.guild;
	}

	public Location getCenter() {
		return this.center;
	}

	public Location getLowerLoc() {
		return this.lowerLoc;
	}

	public Location getUpperLoc() {
		return this.upperLoc;
	}

	public int getSize() {
		return this.size;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGuild(Guild guild) {
		this.guild = guild;
	}

	public void setCenter(Location center) {
		this.center = center;
		reCalculate();
	}

	public void setLowerLoc(Location lowerLoc) {
		this.lowerLoc = lowerLoc;
	}

	public void setUpperLoc(Location upperLoc) {
		this.upperLoc = upperLoc;
	}

	public void setSize(int size) {
		this.size = size;
		reCalculate();
	}
	
	public void delete(){
		RegionUtils.removeRegion(this);
		this.guild.setRegion(null);
		this.guild = null;
		this.center = null;
		this.lowerLoc = null;
		this.upperLoc = null;
		//TODO remove file
	}
	
	public boolean isIn(Location l){
		reCalculate();
		if(this.lowerLoc == null || this.upperLoc == null || l == null) return false;
		if((l.getBlockX() > getLowerLoc().getBlockX()) && (l.getBlockX() < getUpperLoc().getBlockX()) &&
			(l.getBlockY() > getLowerLoc().getBlockY()) && (l.getBlockY() < getUpperLoc().getBlockY()) &&
			(l.getBlockZ() > getLowerLoc().getBlockZ()) && (l.getBlockZ() < getUpperLoc().getBlockZ())
		){
			return true;
		}
		return false;
	}
}