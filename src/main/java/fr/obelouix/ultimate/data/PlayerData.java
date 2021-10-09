package fr.obelouix.ultimate.data;

import fr.obelouix.ultimate.ObelouixUltimate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLocaleChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class PlayerData implements Listener {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static String playerLocale;

    /**
     * This method allow to get the client locale of a player
     *
     * @return the locale of the player
     */
    static String getPlayerLocaleString(@NotNull Player player) {
        return player.locale().toString();
    }

    //High priority because we must get the player locale before sending any translated messages
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                playerLocale = getPlayerLocaleString(event.getPlayer());
            }
        };
        //execute the task 10 ticks ( = 500 ms) after player logged in
        bukkitRunnable.runTaskLaterAsynchronously(plugin, 10L);
    }

    @EventHandler
    public void onPlayerChangeLocale(PlayerLocaleChangeEvent event) {
        final BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                playerLocale = getPlayerLocaleString(event.getPlayer());
            }
        };
        //execute the task 10 ticks ( = 500 ms) after player logged in
        bukkitRunnable.runTaskLaterAsynchronously(plugin, 10L);
    }

    public String getPlayerLocale() {
        return playerLocale;
    }


}