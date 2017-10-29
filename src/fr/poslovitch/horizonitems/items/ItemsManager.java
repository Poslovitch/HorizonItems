package fr.poslovitch.horizonitems.items;

import fr.poslovitch.horizonitems.HorizonItems;
import fr.poslovitch.horizonitems.util.FileLister;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ItemsManager {

    public static List<HorizonItem> all = new ArrayList<>();

    public static void loadItems() {
        for(String item : HorizonItems.getInstance().getFileLister().list("items", "hitem")) {
            File itemFile = new File(HorizonItems.getInstance().getDataFolder() + "/items", item + ".hitem");

            // Firstly, check if this item comes from the JAR.
            // Then we can copy it into the correct folder
            if (!itemFile.exists()) {
                if (HorizonItems.getInstance().getResource("items/" + item + ".hitem") != null) {
                    HorizonItems.getInstance().saveResource("items/" + item + ".hitem", true);
                    itemFile = new File(HorizonItems.getInstance().getDataFolder() + "/items", item + ".hitem");
                }
            }

            System.out.println(item);

            // Now we can parse its data!
            String[][] itemData = ItemParser.parse(itemFile);

            // Meta
            String id = itemData[0][0];
            String category = itemData[0][1];

            // Item
            String[] type = itemData[1][0].split(":");
            Material material = Material.valueOf(type[0]);
            int data = (type.length >= 2) ? Integer.valueOf(type[1]) : 0;

            String name = ChatColor.translateAlternateColorCodes('&', itemData[1][1]);

            all.add(new HorizonItem(id, category, material, data, name, new ArrayList<>()));
        }
    }
}
