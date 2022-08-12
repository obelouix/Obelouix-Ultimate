package fr.obelouix.ultimate.data;

import fr.obelouix.ultimate.ObelouixUltimate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerData extends fr.obelouix.ultimate.data.player.Player implements Listener {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static final Map<Player, Boolean> showCoordinates = new HashMap<>();
    private static final Map<Player, Boolean> showBlockDropAlert = new HashMap<>();

    /**
     * This method allow to get the client locale of a player
     *
     * @return the locale of the player
     */
    public static String getPlayerLocaleString(@NotNull Player player) {
        return player.locale().toString();
    }

    public static boolean isShowCoordinates(Player player) {
        return showCoordinates.get(player);
    }

    public static void setShowCoordinates(Player player, boolean show) {
        showCoordinates.replace(player, show);
        final HoconConfigurationLoader playerFile = HoconConfigurationLoader.builder()
                .path(Path.of(plugin.getDataFolder().getPath(), "data", "players", player.getUniqueId() + ".conf"))
                .build();
        try {
            final CommentedConfigurationNode root = playerFile.load();
            root.node("show-coordinates").set(show);
            playerFile.save(root);
        } catch (ConfigurateException e) {
            e.printStackTrace();
        }

    }

    public static Map<Player, Boolean> getCoordinatesPlayerMap() {
        return showCoordinates;
    }

    public static Map<Player, Boolean> getShowBlockDropAlertMap() {
        return showBlockDropAlert;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        showCoordinates.remove(event.getPlayer());
    }

    public static CommentedConfigurationNode getPlayerFile(Player player) {
        final HoconConfigurationLoader playerFile = HoconConfigurationLoader.builder()
                .path(Path.of(plugin.getDataFolder().getPath(), "data", "players", player.getUniqueId() + ".conf"))
                .build();
        try {
            return playerFile.load();
        } catch (ConfigurateException e) {
            throw new RuntimeException(e);
        }
    }

    public static void save(Player player, ConfigurationNode node) {
        final HoconConfigurationLoader playerFile = HoconConfigurationLoader.builder()
                .path(Path.of(plugin.getDataFolder().getPath(), "data", "players", player.getUniqueId() + ".conf"))
                .build();
        try {
            playerFile.save(node);
        } catch (ConfigurateException e) {
            throw new RuntimeException(e);
        }
    }

    //High priority because we must get the player locale before sending any translated messages
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException {

        if (StorageType.isUsingFiles()) {
            final HoconConfigurationLoader playerFile = HoconConfigurationLoader.builder()
                    .path(Path.of(plugin.getDataFolder().getPath(), "data", "players", event.getPlayer().getUniqueId() + ".conf"))
                    .build();
            final CommentedConfigurationNode root = playerFile.load();

            if (root.node("last-account-name").empty() || !Objects.equals(root.node("last-account-name").getString(), event.getPlayer().getName())) {
                root.node("last-account-name").set(event.getPlayer().getName());
            }
/*            if (root.node("language").getString() == null || !Objects.requireNonNull(root.node("language").getString()).equalsIgnoreCase(getLocale())) {
                root.node("language").set(getLocale());
            }*/
            if (root.node("show-coordinates").empty()) {
                root.node("show-coordinates").set(true);
            }

            if (root.node("alerts", "block-drop").empty()) {
                root.node("alerts", "block-drop").set(true);
            }

            showCoordinates.putIfAbsent(event.getPlayer(), root.node("show-coordinates").getBoolean());
            showCoordinates.putIfAbsent(event.getPlayer(), root.node("alerts", "block-drop").getBoolean());
            playerFile.save(root);
        }

    }
}