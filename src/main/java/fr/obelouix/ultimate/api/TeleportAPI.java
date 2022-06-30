package fr.obelouix.ultimate.api;

import io.papermc.lib.PaperLib;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportAPI {
    /**
     * Teleport every player at a given location
     *
     * @param location where to teleport
     */
    public static void teleport(Location location) {
        Bukkit.getOnlinePlayers().forEach(player -> PaperLib.teleportAsync(player, location));
    }

    public static void teleport(Player player, Location location) {
        player.teleport(location);
    }

    public static void teleportTask(Player player, Location location) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.teleport(location);
            }
        }.run();
    }

}
