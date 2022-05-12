package fr.obelouix.ultimate.coordinates;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.audience.MessageSender;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.data.PlayerData;
import fr.obelouix.ultimate.messages.I18NMessages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.nio.file.Path;

public class Coordinates implements Listener {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    public @NotNull BukkitTask runTask() {
        return new BukkitRunnable() {

            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (!Config.getCoordinatesBlacklist().contains(player.getWorld()) && PlayerData.isShowCoordinates(player)) {
                        final HoconConfigurationLoader playerFile = HoconConfigurationLoader.builder()
                                .path(Path.of(plugin.getDataFolder().getPath(), "data", "players", player.getName() + ".conf"))
                                .build();
                        final CommentedConfigurationNode root;
                        try {
                            root = playerFile.load();
                            if (root.node("show-coordinates").getBoolean()) {
                                if (!root.node("language").empty()) {

                                    final Component actionBar = Component.text("X: ", NamedTextColor.DARK_RED)
                                            .append(Component.text(player.getLocation().getBlockX(), NamedTextColor.WHITE))
                                            .append(Component.text(" Y: ", NamedTextColor.GREEN))
                                            .append(Component.text(player.getLocation().getBlockY(), NamedTextColor.WHITE))
                                            .append(Component.text(" Z: ", NamedTextColor.DARK_BLUE))
                                            .append(Component.text(player.getLocation().getBlockZ(), NamedTextColor.WHITE))
                                            .append(Component.text(" " + parseTo24(player.getWorld().getTime()) + " ", NamedTextColor.GOLD))
                                            .append(Component.text(StringUtils.capitalize(I18NMessages.DIRECTION.getTranslation(player)) + ": ", NamedTextColor.AQUA))
                                            .append(Component.text(getFacing(player), NamedTextColor.WHITE));
                                    MessageSender.sendActionBar(player.getPlayer(), actionBar);
                                }
                            }
                        } catch (ConfigurateException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.runTaskTimerAsynchronously(plugin, 0, 5);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        if (!Bukkit.getOnlinePlayers().isEmpty() && !Bukkit.getScheduler().isCurrentlyRunning(runTask().getTaskId())) {
            // Start the task if it's not already running
            runTask();
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent playerQuitEvent) {
        if (Bukkit.getOnlinePlayers().isEmpty() && Bukkit.getScheduler().isCurrentlyRunning(runTask().getTaskId())) {
            // Cancel the task if it's running and there is no player connected
            runTask().cancel();
        }
    }

    private @NotNull String parseTo24(final long time) {
        long hours = time / 1000L + 6L;
        final long minutes = time % 1000L * 60L / 1000L;
        if (hours == 24L) {
            hours = 0L;
        }
        if (hours == 25L) {
            hours = 1L;
        }
        if (hours == 26L) {
            hours = 2L;
        }
        if (hours == 27L) {
            hours = 3L;
        }
        if (hours == 28L) {
            hours = 4L;
        }
        if (hours == 29L) {
            hours = 5L;
        }
        if (hours == 30L) {
            hours = 6L;
        }
        return hours + ":" + ("0" + minutes).substring(("0" + minutes).length() - 2);
    }

    private String getFacing(final Player p) {
        final double yaw = p.getLocation().getYaw();

        if (yaw >= 337.5 || yaw <= 22.5 && yaw >= 0.0 || yaw >= -22.5 && yaw <= 0.0 || yaw <= -337.5) {
            return StringUtils.capitalize(I18NMessages.DIRECTION_SOUTH.getTranslation(p));
        }
        if (yaw >= 22.5 && yaw <= 67.5 || yaw <= -292.5) {
            return StringUtils.capitalize(I18NMessages.DIRECTION_SOUTH_WEST.getTranslation(p));
        }
        if (yaw >= 67.5 && yaw <= 112.5 || yaw <= -247.5) {
            return StringUtils.capitalize(I18NMessages.DIRECTION_WEST.getTranslation(p));
        }
        if (yaw >= 112.5 && yaw <= 157.5 || yaw <= -202.5) {
            return StringUtils.capitalize(I18NMessages.DIRECTION_NORTH_WEST.getTranslation(p));
        }
        if (yaw >= 157.5 && yaw <= 202.5 || yaw <= -157.5) {
            return StringUtils.capitalize(I18NMessages.DIRECTION_NORTH.getTranslation(p));
        }
        if (yaw >= 202.5 && yaw <= 247.5 || yaw <= -112.5) {
            return StringUtils.capitalize(I18NMessages.DIRECTION_NORTH_EAST.getTranslation(p));
        }
        if (yaw >= 247.5 && yaw <= 292.5 || yaw <= -67.5) {
            return StringUtils.capitalize(I18NMessages.DIRECTION_EAST.getTranslation(p));
        }
        if (yaw >= 292.5 || yaw <= -22.5) {
            return StringUtils.capitalize(I18NMessages.DIRECTION_SOUTH_EAST.getTranslation(p));
        }
        return "error";
    }

}
