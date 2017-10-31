package fr.poslovitch.horizonitems.api.menus;

import fr.poslovitch.horizonitems.listeners.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {

    private String title;
    private Inventory inventory;
    private List<ItemStack> items;
    private Map<Integer, MenuClickHandler> handlers;
    private MenuOpenHandler open;
    private MenuCloseHandler close;

    public Menu(String title) {
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.items = new ArrayList<>();
        this.handlers = new HashMap<>();
        this.open = (Player p) -> {};
        this.close = (Player p) -> {};
    }

    public Menu addItem(int slot, ItemStack item) {
        final int size = this.items.size();
        if (size > slot) this.items.set(slot, item);
        else {
            for (int i = 0; i < slot - size; i++) {
                this.items.add(null);
            }
            this.items.add(item);
        }
        return this;
    }

    public Menu addItem(int slot, ItemStack item, MenuClickHandler clickHandler) {
        addItem(slot, item);
        addMenuClickHandler(slot, clickHandler);
        return this;
    }

    public ItemStack getItemInSlot(int slot) {
        setup();
        return this.inventory.getItem(slot);
    }

    public Menu addMenuClickHandler(int slot, MenuClickHandler handler) {
        this.handlers.put(slot, handler);
        return this;
    }

    public Menu addMenuOpenHandler(MenuOpenHandler handler) {
        this.open = handler;
        return this;
    }

    public Menu addMenuCloseHandler(MenuCloseHandler handler) {
        this.close = handler;
        return this;
    }

    public ItemStack[] getContents() {
        setup();
        return this.inventory.getContents();
    }

    private void setup() {
        if (this.inventory != null) return;
        this.inventory = Bukkit.createInventory(null, formToLine(this.items.size()) * 9, title);
        for (int i = 0; i < this.items.size(); i++) {
            this.inventory.setItem(i, this.items.get(i));
        }
    }

    public void reset(boolean update) {
        if (update) this.inventory.clear();
        else this.inventory = Bukkit.createInventory(null, formToLine(this.items.size()) * 9, title);
        for (int i = 0; i < this.items.size(); i++) {
            this.inventory.setItem(i, this.items.get(i));
        }
    }

    public void replaceExistingItem(int slot, ItemStack item) {
        setup();
        this.inventory.setItem(slot, item);
    }

    public void open(Player... players) {
        setup();
        for (Player p: players) {
            p.openInventory(this.inventory);
            MenuListener.openedMenus.put(p.getUniqueId(), this);
            if (open != null) open.onOpen(p);
        }
    }

    public MenuClickHandler getMenuClickHandler(int slot) {
        return handlers.get(slot);
    }

    public MenuCloseHandler getMenuCloseHandler() {
        return close;
    }

    public MenuOpenHandler getMenuOpenHandler() {
        return open;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public interface MenuClickHandler {
        boolean onClick(Player p, int slot, ItemStack item, boolean rightClick, boolean shiftClick);
    }

    public interface MenuOpenHandler {
        void onOpen(Player p);
    }

    public interface MenuCloseHandler {
        void onClose(Player p);
    }

    public static int formToLine(int i) {
        int lines = 1;

        if (i > 9) lines++;
        if (i > 9*2) lines++;
        if (i > 9*3) lines++;
        if (i > 9*4) lines++;
        if (i > 9*5) lines++;
        if (i > 9*6) lines++;

        return lines;
    }
}
