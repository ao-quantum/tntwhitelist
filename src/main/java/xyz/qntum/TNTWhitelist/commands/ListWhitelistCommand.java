package xyz.qntum.TNTWhitelist.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import xyz.qntum.TNTWhitelist.TNTWhitelist;

public class ListWhitelistCommand implements CommandExecutor {

    private final TNTWhitelist plugin;

    public ListWhitelistCommand(TNTWhitelist plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String whitelistStr = String.join(", ", plugin.whitelist.stream().map(uuid -> plugin.getServer().getPlayer(uuid).getName()).toArray(String[]::new));

        sender.sendMessage("Whitelist: " + whitelistStr);

        return false;
    }
}
