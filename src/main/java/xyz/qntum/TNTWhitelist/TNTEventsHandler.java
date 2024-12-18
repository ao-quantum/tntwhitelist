package xyz.qntum.TNTWhitelist;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.TNTPrimeEvent;

public class TNTEventsHandler implements Listener {

    private final TNTWhitelist plugin;

    public TNTEventsHandler(TNTWhitelist plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType() != Material.TNT) return;
        if (Util.isWhiteListed(plugin, e.getPlayer())) return;

        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onTNTIgnition(TNTPrimeEvent e) {
        if (e.getCause() == TNTPrimeEvent.PrimeCause.PLAYER || e.getPrimingEntity() instanceof Player) {
            // Get the player who primed the TNT
            Player player = (Player) e.getPrimingEntity();

            if (player == null) return;

            if (Util.isWhiteListed(plugin, player)) return;

            e.setCancelled(true);
        }


        if (e.getPrimingBlock() != null) {
            // I don't know how to find who placed the block
            // So just cancel the TNT in case it was set off by a block

            e.setCancelled(true);
        }

        // If activated by redstone
        if (e.getCause() == TNTPrimeEvent.PrimeCause.REDSTONE) {
            if (plugin.getConfig().getBoolean("disableRedstoneIgnitions")) e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockIgnite(BlockIgniteEvent e) {
        // Check it's a tnt/minecart being ignited
        if (e.getBlock().getType() != Material.TNT || e.getBlock().getType() != Material.TNT_MINECART) return;

        // Check it is player who ignited it
        if (e.getPlayer() != null) return;

        if (Util.isWhiteListed(plugin, e.getPlayer())) return;

        e.setCancelled(true);
    }

}
