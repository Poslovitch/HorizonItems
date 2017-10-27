package fr.poslovitch.horizonitems.items;

import fr.poslovitch.horizonitems.HorizonItems;
import fr.poslovitch.horizonitems.util.FileLister;

import java.util.ArrayList;
import java.util.List;

public class ItemsManager {

    public static List<HorizonItem> all = new ArrayList<HorizonItem>();

    public static void loadItems() {
        FileLister fl = new FileLister(HorizonItems.instance);
        for(String f : fl.list("items", "hitem")) {
            HorizonItems.instance.getLogger().info(f);
        }
    }
}
