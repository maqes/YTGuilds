package pl.kamilkime.ytguilds.objects.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import pl.kamilkime.ytguilds.objects.Region;

public class RegionUtils {

	private static List<Region> regions = new ArrayList<Region>();
	
	public static List<Region> getRegions(){
		return new ArrayList<Region>(regions);
	}
	
	public static void addRegion(Region r){
		if(!regions.contains(r)) regions.add(r);
	}
	
	public static void removeRegion(Region r){
		if(regions.contains(r)) regions.remove(r);
	}
	
	public static boolean isIn(Location l){
		for(Region r : getRegions()){
			if(r.isIn(l)) return true;
		}
		return false;
	}
	
	public static Region inWhich(Location l){
		for(Region r : getRegions()){
			if(r.isIn(l)) return r;
		}
		return null;
	}
	
	public static boolean isNear(Location center, int size, int between){
		for(Region r : getRegions()){
			double dist = center.distance(r.getCenter());
			if(dist < 2*size + between) return true;
		}
		return false;
	}
}