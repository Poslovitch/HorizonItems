package fr.poslovitch.horizonitems;

import fr.poslovitch.horizonitems.commands.HorizonItemsCommand;
import fr.poslovitch.horizonitems.items.HorizonItem;
import fr.poslovitch.horizonitems.items.ItemsManager;
import fr.poslovitch.horizonitems.listeners.MenuListener;
import fr.poslovitch.horizonitems.menus.ItemBank;
import fr.poslovitch.horizonitems.util.FileLister;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HorizonItems extends JavaPlugin{

    private static HorizonItems instance;
    private FileLister fileLister;

    @Override
    public void onEnable() {
        instance = this;
        fileLister = new FileLister(this);

        getLogger().info("§eLoading Items & Blocks...");
        ItemsManager.loadItems();
        getLogger().info("§aLoaded a total of §b" + ItemsManager.items.size() + " §aitems and blocks.");
        getLogger().info("§eConfigurating Categories...");
        ItemsManager.setupCategories();
        getLogger().info("§aSuccessfully configurated §b" + ItemsManager.categories.keySet().size() + " §acategories.");
        getLogger().info("§eGenerating Menus...");
        ItemBank.generate();
        getLogger().info("§aSuccessfully generated menus.");

        new MenuListener(this);
        new HorizonItemsCommand(this);
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
