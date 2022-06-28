package fr.obelouix.ultimate.events;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.api.MessagesAPI;
import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class NightSkipEvent implements Listener {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    final List<Player> sleepingPlayers = new ArrayList<>();
    private final List<GameMode> sleepingGameModes = List.of(GameMode.SURVIVAL, GameMode.ADVENTURE);
    private final BossBar sleepingBar = BossBar.bossBar(Component.empty(), 0, BossBar.Color.RED, BossBar.Overlay.PROGRESS);


    @EventHandler
    public void onPlayerSleep(PlayerDeepSleepEvent event) {
        Player player = event.getPlayer();
        sleepingPlayers.add(player);

        if (player.isDeeplySleeping() && !Bukkit.getScheduler().isCurrentlyRunning(runTask(player.getWorld()).getTaskId())) {
            runTask(player.getWorld());
        }
    }

    @EventHandler
    public void onPlayerLeaveBed(PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        sleepingPlayers.remove(player);
        if (world.getTime() > 0 && world.getTime() < 12300) {
            MessagesAPI.hideBossBar(player, sleepingBar);
            if (Bukkit.getScheduler().isCurrentlyRunning(runTask(player.getWorld()).getTaskId()))
                runTask(player.getWorld()).cancel();
        }
    }


    public BukkitTask runTask(World world) {


        return new BukkitRunnable() {
            @Override
            public void run() {
                world.getPlayers().forEach(
                        player -> MessagesAPI.showBossBar(updateBar(sleepingBar, world))

                );
            }
        }.runTaskTimerAsynchronously(plugin, 0, 60);
    }

    private BossBar updateBar(BossBar bossBar, World world) {
        final int percentage = world.getGameRuleValue(GameRule.PLAYERS_SLEEPING_PERCENTAGE).intValue();
        final List<Player> playersInWorld = world.getPlayers().stream().filter(player -> sleepingGameModes.contains(player.getGameMode())).toList();

        BossBar.Color color =
                switch ((sleepingPlayers.size() / (playersInWorld.size() / percentage)) * 100) {
                    case 50, 60, 70 -> BossBar.Color.YELLOW;
                    case 80, 90, 100 -> BossBar.Color.GREEN;
                    default -> BossBar.Color.RED;
                };
        return bossBar
                .color(color)
                .name(Component.text(sleepingPlayers.size() + "/" + playersInWorld.size()))
                .progress((float) sleepingPlayers.size() / (float) playersInWorld.size());
    }

}
