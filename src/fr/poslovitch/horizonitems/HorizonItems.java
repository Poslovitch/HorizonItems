package fr.poslovitch.horizonitems;

import fr.poslovitch.horizonitems.items.HorizonItem;
import fr.poslovitch.horizonitems.items.ItemsManager;
import fr.poslovitch.horizonitems.util.FileLister;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HorizonItems extends JavaPlugin implements Listener {

    private static HorizonItems instance;
    private FileLister fileLister;

    @Override
    public void onEnable() {
        instance = this;
        fileLister = new FileLister(this);

        getLogger().info("§eLoading Items & Blocks.");
        ItemsManager.loadItems();
        getLogger().info("§aLoaded a total of §b" + ItemsManager.all.size() + " §aitems and blocks.");

        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static HorizonItems getInstance() {
        return instance;
    }

    public FileLister getFileLister() {
        return fileLister;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        for (HorizonItem item : ItemsManager.all) {
            e.getPlayer().getInventory().addItem(item.getItem());
        }
    }
}
