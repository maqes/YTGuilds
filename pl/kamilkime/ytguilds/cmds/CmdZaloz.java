package pl.kamilkime.ytguilds.cmds;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import pl.kamilkime.ytguilds.data.Settings;
import pl.kamilkime.ytguilds.objects.Guild;
import pl.kamilkime.ytguilds.objects.Region;
import pl.kamilkime.ytguilds.objects.User;
import pl.kamilkime.ytguilds.objects.utils.GuildUtils;
import pl.kamilkime.ytguilds.objects.utils.RankUtils;
import pl.kamilkime.ytguilds.objects.utils.RegionUtils;

public class CmdZaloz {

	public void zaloz(CommandSender sender, Command cmd, String label, String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage("§cMusisz byc na serwerze!");
			return;
		}
		if(args.length < 3){
			sender.sendMessage("§cCorrect usage: /g zaloz <tag> <nazwa>");
			return;
		}
		if(args.length > 3){
			sender.sendMessage("§cNazwa nie moze skladac sie z kilku wyrazow!");
			return;
		}
		Player p = (Player) sender;
		User u = User.get(p);
		if(u.hasGuild()){
			p.sendMessage("§cMasz juz gildie!");
			return;
		}
		if(GuildUtils.tagExists(args[1])){
			p.sendMessage("§cGildia o takim tagu juz istnieje!");
			return;
		}
		if(GuildUtils.nameExists(args[2])){
			p.sendMessage("§cGildia o takiej nazwie juz istnieje!");
			return;
		}
		if(args[1].length() < 3){
			p.sendMessage("§cTag musi miec min. 3 znaki!");
			return;
		}
		if(args[1].length() > 4){
			p.sendMessage("§cTag moze miec max. 4 znaki!");
			return;
		}
		if(args[2].length() > 30){
			p.sendMessage("§cNazwa moze miec max. 30 znakow!");
			return;
		}
		if(!args[1].matches("[a-zA-Z]+")){
			p.sendMessage("§cTag moze zwierac tylko litery!");
			return;
		}
		if(!args[2].matches("[a-zA-Z]+")){
			p.sendMessage("§cNazwa moze zwierac tylko litery!");
			return;
		}
		Location loc = p.getLocation();
		if(RegionUtils.isIn(loc)){
			p.sendMessage("§cW tym miejscu jest juz gildia!");
			return;
		}
		if(RegionUtils.isNear(loc, 50, 75)){
			p.sendMessage("§cJestes zbyt blisko innej gildii!");
			return;
		}
		if(loc.distance(loc.getWorld().getSpawnLocation()) < 250){
			p.sendMessage("§cJestes za blisko spawna!");
			return;
		}
		if(!hasItems(p)){
			showItems(p);
			return;
		}
		takeItems(p);
		Guild g = Guild.get(args[1]);
		g.setName(args[2]);
		g.setAdmin(u);
		g.addMember(u);
		RankUtils.update(g);
		g.setRegion(new Region(g, loc, 50));
		p.getLocation().subtract(0, 1, 0).getBlock().setType(Material.BEDROCK);
		Bukkit.getServer().broadcastMessage("§6" + u.getName() + "§7 zalozyl gildie §6" + g.getName() + " §7(§6" + g.getTag() + "§7)");
	}
//===================================================================================================================================================================
	private boolean hasItems(Player p){
		Settings set = Settings.getInst();
		if(p.hasPermission("ytguilds.vip")){
			for(String s : set.vipItems){
				String[] ss = s.split(" ");
				ItemStack is = new ItemStack(Material.getMaterial(ss[0].toUpperCase()), 1, Short.parseShort(ss[2]));
				if(!p.getInventory().containsAtLeast(is, Integer.parseInt(ss[1]))) return false;
			}
		} else{
			for(String s : set.items){
				String[] ss = s.split(" ");
				ItemStack is = new ItemStack(Material.getMaterial(ss[0].toUpperCase()), 1, Short.parseShort(ss[2]));
				if(!p.getInventory().containsAtLeast(is, Integer.parseInt(ss[1]))) return false;
			}
		}
		return true;
	}
	
	private void takeItems(Player p){
		Settings set = Settings.getInst();
		if(p.hasPermission("ytguilds.vip")){
			for(String s : set.vipItems){
				String[] ss = s.split(" ");
				ItemStack is = new ItemStack(Material.getMaterial(ss[0].toUpperCase()), Integer.parseInt(ss[1]), Short.parseShort(ss[2]));
				p.getInventory().removeItem(is);
			}
		} else{
			for(String s : set.items){
				String[] ss = s.split(" ");
				ItemStack is = new ItemStack(Material.getMaterial(ss[0].toUpperCase()), Integer.parseInt(ss[1]), Short.parseShort(ss[2]));
				p.getInventory().removeItem(is);
			}
		}
	}
	
	private void showItems(Player p){
		Settings set = Settings.getInst();
		int i = 0;
		if(p.hasPermission("ytguilds.vip")){
			Inventory inv = Bukkit.createInventory(null, (set.vipItems.size() - 1) / 9 * 9 + 9, "§5§lItemy na gildie");
			for(String s : set.vipItems){
				String[] ss = s.split(" ");
				ItemStack is = new ItemStack(Material.getMaterial(ss[0].toUpperCase()), Integer.parseInt(ss[1]), Short.parseShort(ss[2]));
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(" ");
				is.setItemMeta(im);
				inv.setItem(i++, is);
			}
			p.openInventory(inv);
		} else{
			Inventory inv = Bukkit.createInventory(null, (set.items.size() - 1) / 9 * 9 + 9, "§5§lItemy na gildie");
			for(String s : set.items){
				String[] ss = s.split(" ");
				ItemStack is = new ItemStack(Material.getMaterial(ss[0].toUpperCase()), Integer.parseInt(ss[1]), Short.parseShort(ss[2]));
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(" ");
				is.setItemMeta(im);
				inv.setItem(i++, is);
			}
			p.openInventory(inv);
		}
	}
}
