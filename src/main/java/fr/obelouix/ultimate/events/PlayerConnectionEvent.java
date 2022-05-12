package fr.obelouix.ultimate.events;

import fr.obelouix.ultimate.ObelouixUltimate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerConnectionEvent implements Listener {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    public PlayerConnectionEvent() {

    }

    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent event) {
        final Player player = event.getPlayer();
    }

}
