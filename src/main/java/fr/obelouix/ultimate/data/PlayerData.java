package fr.obelouix.ultimate.data;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.utils.FakeServerBrand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLocaleChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerData extends fr.obelouix.ultimate.data.player.Player implements Listener {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static final Map<Player, Boolean> showCoordinates = new HashMap<>();

    /**
     * This method allow to get the client locale of a player
     *
     * @return the locale of the player
     */
    static String getPlayerLocaleString(@NotNull Player player) {
        return player.locale().toString();
    }

    public static boolean isShowCoordinates(Player player) {
        return showCoordinates.get(player);
    }

    public static void setShowCoordinates(Player player, boolean show) {
        showCoordinates.replace(player, show);
        final HoconConfigurationLoader playerFile = HoconConfigurationLoader.builder()
                .path(Path.of(plugin.getDataFolder().getPath(), "data", "players", player.getName() + ".conf"))
                .build();
        try {
            final CommentedConfigurationNode root = playerFile.load();
            root.node("show-coordinates").set(show);
            playerFile.save(root);
        } catch (ConfigurateException e) {
            e.printStackTrace();
        }

    }

    @EventHandler
    public void onPlayerChangeLocale(PlayerLocaleChangeEvent event) {
        final BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                final HoconConfigurationLoader playerFile = HoconConfigurationLoader.builder()
                        .path(Path.of(plugin.getDataFolder().getPath(), "data", "players", event.getPlayer().getName() + ".conf"))
                        .build();
                final CommentedConfigurationNode root;
                try {
                    root = playerFile.load();
                    setLocale(getPlayerLocaleString(event.getPlayer()));
                    root.node("language").set(getLocale());
                    playerFile.save(root);
                } catch (ConfigurateException e) {
                    e.printStackTrace();
                }

            }
        };
        //execute the task 10 ticks ( = 500 ms) after player logged in
        bukkitRunnable.runTaskLaterAsynchronously(plugin, 10L);
    }

    //High priority because we must get the player locale before sending any translated messages
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        final BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                setLocale(getPlayerLocaleString(event.getPlayer()));
            }
        };
        //execute the task 10 ticks ( = 500 ms) after player logged in
        bukkitRunnable.runTaskLaterAsynchronously(plugin, 10L);

        FakeServerBrand.sendFakeBrand(event.getPlayer());

        if (DataStorage.isFileBasedStorage()) {
            final HoconConfigurationLoader playerFile = HoconConfigurationLoader.builder()
                    .path(Path.of(plugin.getDataFolder().getPath(), "data", "players", event.getPlayer().getName() + ".conf"))
                    .build();
            final CommentedConfigurationNode root = playerFile.load();
            root.node("uuid").set(event.getPlayer().getUniqueId());
            if (root.node("language").getString() == null || !Objects.requireNonNull(root.node("language").getString()).equalsIgnoreCase(getLocale())) {
                root.node("language").set(getLocale());
            }
            if (root.node("show-coordinates").empty()) {
                root.node("show-coordinates").set(true);
            }

            showCoordinates.putIfAbsent(event.getPlayer(), root.node("show-coordinates").getBoolean());
            playerFile.save(root);
        }

    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        showCoordinates.remove(event.getPlayer());
    }

}