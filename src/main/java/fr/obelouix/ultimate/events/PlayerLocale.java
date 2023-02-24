package fr.obelouix.ultimate.events;

import fr.obelouix.ultimate.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLocaleChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerLocale implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void getLocaleOnJoin(PlayerJoinEvent event){
        final BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                Main.getPlugin().getComponentLogger().info(String.valueOf(event.getPlayer().locale()));
            }
        };

        runnable.runTaskLater(Main.getPlugin(), 60L);

    }

    @EventHandler
    public void onPlayerChangeLocale(PlayerLocaleChangeEvent event){
        final BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                Main.getPlugin().getComponentLogger().info(String.valueOf(event.getPlayer().locale()));
            }
        };

        runnable.runTaskLater(Main.getPlugin(), 60L);

    }

}
