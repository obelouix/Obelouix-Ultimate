package fr.obelouix.ultimate.api;

import io.papermc.lib.PaperLib;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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
        PaperLib.teleportAsync(player, location);
    }

}
