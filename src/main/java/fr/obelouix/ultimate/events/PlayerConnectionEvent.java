package fr.obelouix.ultimate.events;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.entity.CustomVillager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.inventivetalent.glow.GlowAPI;

public class PlayerConnectionEvent implements Listener {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    public PlayerConnectionEvent() {

    }

    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent event) {
        final Player player = event.getPlayer();
/*
        if (!player.hasPermission("obelouix.maintenance.bypass")) {
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Component.text("Server in maintenance"));
        }*/
        CustomVillager.create(player.getLocation(), "", false);
        //Bukkit.getScheduler().runTaskLater(plugin, () -> GlowAPI.setGlowing(event.getPlayer(), GlowAPI.Color.DARK_RED, Bukkit.getOnlinePlayers()), 10);
    }

    /**
     * Just for testing purposes
     */
    /*@EventHandler
    public void onPlayerMove(final PlayerMoveEvent playerMoveEvent){
        Bukkit.getScheduler().runTaskLater(plugin, () -> GlowAPI.setGlowing(playerMoveEvent.getPlayer(), GlowAPI.Color.WHITE, playerMoveEvent.getPlayer()), 10);
    }*/

}
