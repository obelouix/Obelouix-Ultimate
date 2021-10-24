package fr.obelouix.ultimate.events;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerConnectionEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent event) {
        final Player player = event.getPlayer();

        if (!player.hasPermission("obelouix.maintenance.bypass")) {
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Component.text("Server in maintenance"));
        }
    }

}
