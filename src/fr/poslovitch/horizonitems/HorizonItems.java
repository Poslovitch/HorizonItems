package fr.poslovitch.horizonitems;

import fr.poslovitch.horizonitems.items.ItemsManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HorizonItems extends JavaPlugin {

    public static HorizonItems instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("§eLoading Items & Blocks.");
        ItemsManager.loadItems();
        getLogger().info("§aLoaded a total of §b" + ItemsManager.all.size() + " §aitems and blocks.");
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
