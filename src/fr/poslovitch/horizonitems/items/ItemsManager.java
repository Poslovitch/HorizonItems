package fr.poslovitch.horizonitems.items;

import fr.poslovitch.horizonitems.HorizonItems;
import fr.poslovitch.horizonitems.util.FileLister;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ItemsManager {

    public static List<HorizonItem> all = new ArrayList<HorizonItem>();

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

            // Now we can parse its data!
            System.out.println(item);

            
        }
    }
}
