package fr.obelouix.ultimate.tweaks;

import org.bukkit.Location;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;

public class EnderDragonTweaks implements Listener {

    @EventHandler
    public void onEnderDragonDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof EnderDragon enderDragon) {
            final Location center = new Location(enderDragon.getWorld(), 0, 65, 0);
            final List<Player> rewardedPlayers = enderDragon.getWorld().getPlayers().stream()
                    .filter(player -> player.getLocation().distanceSquared(center) <= 200).toList();
            // 12 000 points -> 68 levels
            rewardedPlayers.forEach(player -> player.giveExp(12000, true));
        }
    }
}
