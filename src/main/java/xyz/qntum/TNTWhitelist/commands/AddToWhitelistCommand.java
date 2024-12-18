package xyz.qntum.TNTWhitelist.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.qntum.TNTWhitelist.TNTWhitelist;

public class AddToWhitelistCommand implements CommandExecutor {

    private final TNTWhitelist plugin;

    public AddToWhitelistCommand(TNTWhitelist plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /addwl <username>");
            return false;
        }

        String username = args[0];

        // Convert username to UUID
        Player player = this.plugin.getServer().getPlayer(username);

        if (player == null) {
            sender.sendMessage("Player not found.");
            return false;
        }

        this.plugin.whitelist.add(player.getUniqueId().toString());

        sender.sendMessage("Added " + player.getName() + " to the whitelist.");

        return true;
    }
}
