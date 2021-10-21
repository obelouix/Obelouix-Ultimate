package fr.obelouix.ultimate.events;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.data.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;

public class PlayerConnectionEvent implements Listener {

    private final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerLocale = new PlayerData().getPlayerLocale();
        plugin.getLogger().info(playerLocale);
        if (Config.isServerInMaintenance() && !player.hasPermission("obelouix.maintenance.bypass")) {
            player.kick(Component.text("Server in maintenance"), PlayerKickEvent.Cause.TIMEOUT);
        }
    }

}
