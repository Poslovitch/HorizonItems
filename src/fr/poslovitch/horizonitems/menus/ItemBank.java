package fr.poslovitch.horizonitems.menus;

import fr.poslovitch.horizonitems.api.menus.Menu;
import fr.poslovitch.horizonitems.items.HorizonItem;
import fr.poslovitch.horizonitems.items.ItemsManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ItemBank {

    private static Menu main;
    private static Map<String, Menu> menus = new HashMap<>();

    public static void generate() {
        // Generate main menu
        main = new Menu("&6HorizonItems &8- &7by Poslovitch");

        for (String key : ItemsManager.categories.keySet()) {
            String[] category = key.split("\\.");

            for (int i = 0 ; i < category.length ; i++) {
                if (i == 0) {

                }
            }
        }
    }

    public static void openMainMenu(Player p) {
        Menu menu = new Menu("&7Bank!");
        int i = 0;
        for (HorizonItem item : ItemsManager.items){
            menu.addItem(i, item.getItem());
            i++;
        }

        menu.open(p);
    }
}
