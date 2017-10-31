package fr.poslovitch.horizonitems.listeners;

import fr.poslovitch.horizonitems.HorizonItems;
import fr.poslovitch.horizonitems.api.menus.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MenuListener implements Listener {

    public static Map<UUID, Menu> openedMenus;

    private HorizonItems plugin;

    public MenuListener(HorizonItems plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
        openedMenus = new HashMap<>();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (openedMenus.containsKey(e.getWhoClicked().getUniqueId())) {
            Menu menu = openedMenus.get(e.getWhoClicked().getUniqueId());
            if (e.getRawSlot() < e.getInventory().getSize()) {
                Menu.MenuClickHandler handler = menu.getMenuClickHandler(e.getSlot());
                if (handler != null) {
                    e.setCancelled(!handler.onClick((Player) e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), e.isRightClick(), e.isShiftClick()));
                }
            }
            else e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        if (openedMenus.containsKey(player.getUniqueId())) {
            openedMenus.get(player.getUniqueId()).getMenuCloseHandler().onClose(player);
            openedMenus.remove(player.getUniqueId());
        }
    }
}
