package fr.poslovitch.horizonitems.commands;

import fr.poslovitch.horizonitems.HorizonItems;
import fr.poslovitch.horizonitems.menus.ItemBank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HorizonItemsCommand implements CommandExecutor {

    private HorizonItems plugin;

    public HorizonItemsCommand(HorizonItems plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("horizonitems").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player && sender.hasPermission("horizonitems.bank")) {
            ItemBank.openMainMenu((Player) sender);
        }

        return true;
    }
}
