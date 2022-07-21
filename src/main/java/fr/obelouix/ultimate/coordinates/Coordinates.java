package fr.obelouix.ultimate.coordinates;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.api.MessagesAPI;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.i18n.TranslationKey;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
                    if (!Config.getCoordinatesBlacklist().contains(player.getWorld())) {
                        final HoconConfigurationLoader playerFile = HoconConfigurationLoader.builder()
                                .path(Path.of(plugin.getDataFolder().getPath(), "data", "players", player.getName() + ".conf"))
                                .build();
                        final CommentedConfigurationNode root;
                        try {
                            root = playerFile.load();
                            if (root.node("show-coordinates").getBoolean()) {

                                final Component actionBar = Component.text("X: ", NamedTextColor.DARK_RED)
                                        .append(Component.text(player.getLocation().getBlockX(), NamedTextColor.WHITE))
                                        .append(Component.text(" Y: ", NamedTextColor.GREEN))
                                        .append(Component.text(player.getLocation().getBlockY(), NamedTextColor.WHITE))
                                        .append(Component.text(" Z: ", NamedTextColor.DARK_BLUE))
                                        .append(Component.text(player.getLocation().getBlockZ(), NamedTextColor.WHITE))
                                        .append(Component.text(" " + parseTo24(player.getWorld().getTime()) + " ", NamedTextColor.GOLD))
                                        .append(showDirection(player));
                                MessagesAPI.sendActionBar(player.getPlayer(), actionBar);
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

    private Component showDirection(Player player) {
        return TranslationKey.DIRECTION.toCapitalizedComponent(NamedTextColor.AQUA).append(Component.text(": "))
                .append(getFacing(player));
    }

    private Component getFacing(final Player player) {

        Component direction = Component.empty();
        double rotation = (player.getLocation().getYaw() - 180) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 22.5) {
            direction = direction.append(TranslationKey.NORTH.toComponent());
        } else if (22.5 <= rotation && rotation < 67.5) {
            direction = direction.append(TranslationKey.NORTH_EAST.toComponent());
        } else if (67.5 <= rotation && rotation < 112.5) {
            direction = direction.append(TranslationKey.EAST.toComponent());
        } else if (112.5 <= rotation && rotation < 157.5) {
            direction = direction.append(TranslationKey.SOUTH_EAST.toComponent());
        } else if (157.5 <= rotation && rotation < 202.5) {
            direction = direction.append(TranslationKey.SOUTH.toComponent());
        } else if (202.5 <= rotation && rotation < 247.5) {
            direction = direction.append(TranslationKey.SOUTH_WEST.toComponent());
        } else if (247.5 <= rotation && rotation < 292.5) {
            direction = direction.append(TranslationKey.WEST.toComponent());
        } else if (292.5 <= rotation && rotation < 337.5) {
            direction = direction.append(TranslationKey.NORTH_WEST.toComponent());
        } else if (337.5 <= rotation && rotation < 360.0) {
            direction = direction.append(TranslationKey.NORTH.toComponent());
        } else {
            return Component.text("error", NamedTextColor.DARK_RED);
        }

        return direction.color(NamedTextColor.WHITE);
    }

}
