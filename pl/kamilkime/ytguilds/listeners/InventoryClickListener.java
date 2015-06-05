package pl.kamilkime.ytguilds.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e){
		if(e.getInventory() == null) return;
		if(e.getCurrentItem() == null) return;
		if(e.getCurrentItem().getType().equals(Material.AIR)) return;
		if(!e.getCurrentItem().hasItemMeta()) return;
		if(e.getInventory().getTitle() !=null && e.getInventory().getTitle().equals("§5§lItemy na gildie")){
			e.setCancelled(true);
			e.getWhoClicked().openInventory(e.getInventory());
		}
	}
}