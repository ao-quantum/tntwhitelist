package xyz.qntum.TNTWhitelist;

import org.bukkit.entity.Player;

public class Util {

    public static boolean isWhiteListed(TNTWhitelist plugin, String playerUUID) {
        return plugin.whitelist.contains(playerUUID);
    }

    public static boolean isWhiteListed(TNTWhitelist plugin, Player player) {
        return isWhiteListed(plugin, player.getUniqueId().toString());
    }

}
