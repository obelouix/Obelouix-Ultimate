package fr.obelouix.ultimate.events;

import fr.obelouix.ultimate.ObelouixUltimate;
import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.NavigableMap;
import java.util.Set;

public class NightSkipEvent implements Listener {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static final HashMap<World, Integer> onlinePlayersPerWorlds = new HashMap<>();
    private static NavigableMap<Player, World> firstPlayerWhoSleptPerWorld;
    private static NavigableMap<World, Integer> sleepingPlayerCount;

    private static Set<World> worldstoSkipNight;

    @EventHandler
    public void onPlayerSleep(PlayerDeepSleepEvent event) {
       /* for (World world : Bukkit.getWorlds()) {
            if (world.getEnvironment().equals(World.Environment.NORMAL)) {
                onlinePlayersPerWorlds.putIfAbsent(world, world.getPlayerCount());
                if (plugin.isEssentialsXPresent() && Config.isEssentialsAFKHook()) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (plugin.getEssentials().getUser(player).isAfk()) {
                            onlinePlayersPerWorlds.replace(event.getPlayer().getWorld(), event.getPlayer().getWorld().getPlayerCount() - 1);
                        }
                    }
                }
            }

        }*/


        if (event.getPlayer().isDeeplySleeping()) {
            if (!firstPlayerWhoSleptPerWorld.containsKey(event.getPlayer())) {
                firstPlayerWhoSleptPerWorld.put(event.getPlayer(), event.getPlayer().getWorld());
            }

            if (!sleepingPlayerCount.containsKey(event.getPlayer().getWorld())) {
                sleepingPlayerCount.put(event.getPlayer().getWorld(), 1);
            } else {
                if (!firstPlayerWhoSleptPerWorld.containsKey(event.getPlayer())) {
                    int temp = sleepingPlayerCount.get(event.getPlayer().getWorld()) + 1;
                    sleepingPlayerCount.remove(event.getPlayer().getWorld());
                    sleepingPlayerCount.put(event.getPlayer().getWorld(), temp);
                }
            }

        }


    }

}
