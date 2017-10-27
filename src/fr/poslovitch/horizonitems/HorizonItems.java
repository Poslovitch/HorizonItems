package fr.poslovitch.horizonitems;

import fr.poslovitch.horizonitems.items.ItemsManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HorizonItems extends JavaPlugin {

    @Override
    public void onEnable() {
        ItemsManager.loadItems();
    }

    @Override
    public void onDisable() {

    }
}
