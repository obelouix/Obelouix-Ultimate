package fr.obelouix.ultimate.events;

import fr.obelouix.ultimate.entity.CustomVillager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerConnectionEvent implements Listener {

    public PlayerConnectionEvent() {

    }

    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent event) {
        final Player player = event.getPlayer();

        if (!player.hasPermission("obelouix.maintenance.bypass")) {
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Component.text("Server in maintenance"));
        }
        CustomVillager.create(player.getLocation(), "", false);
    }
}
