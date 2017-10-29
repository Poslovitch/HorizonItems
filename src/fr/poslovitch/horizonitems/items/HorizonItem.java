package fr.poslovitch.horizonitems.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class HorizonItem {

    private String id;
    private String hash;
    private String category;

    private ItemStack item;

    public HorizonItem(String id, String category, Material material, int data, String name, List<String> lore) {
        this.id = id;
        this.category = category;

        // Constructing the item
        this.item = new ItemStack(material, 1, (short) data);

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);

        StringBuilder sb = new StringBuilder();
        for (char c : id.toCharArray()) {
            sb.append("§");
            sb.append(c);
        }

        hash = sb.toString();
        lore.add(0, hash);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }

    public String getId() {
        return id;
    }

    public String getHash() {
        return hash;
    }

    public String getCategory() {
        return category;
    }

    public ItemStack getItem() {
        return item;
    }

    public String getDisplayName() {
        return item.getItemMeta().getDisplayName();
    }

    public List<String> getLore() {
        List<String> accessibleLore = item.getItemMeta().getLore();
        accessibleLore.remove(0);
        return accessibleLore;
    }
}
