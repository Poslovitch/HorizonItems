package fr.poslovitch.horizonitems;

import fr.poslovitch.horizonitems.items.ItemsManager;
import fr.poslovitch.horizonitems.util.FileLister;
import org.bukkit.plugin.java.JavaPlugin;

public class HorizonItems extends JavaPlugin {

    private static HorizonItems instance;
    private FileLister fileLister;

    @Override
    public void onEnable() {
        instance = this;
        fileLister = new FileLister(this);

        getLogger().info("§eLoading Items & Blocks.");
        ItemsManager.loadItems();
        getLogger().info("§aLoaded a total of §b" + ItemsManager.all.size() + " §aitems and blocks.");
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
}
