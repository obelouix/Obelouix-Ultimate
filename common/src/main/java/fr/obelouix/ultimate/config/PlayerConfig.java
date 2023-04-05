package fr.obelouix.ultimate.config;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class PlayerConfig extends BaseConfig {

    private static JavaPlugin javaPlugin;

    public PlayerConfig(JavaPlugin plugin) {
        javaPlugin = plugin;
    }

    private static CommentedConfigurationNode rootNode;
    private static final Path basePlayerConfigPath = Path.of(javaPlugin.getDataFolder().getPath(), "data", "players");

    public static HoconConfigurationLoader getPlayerConfig(Player player) {
        return HoconConfigurationLoader.builder()
                .path(basePlayerConfigPath.resolve(player.getUniqueId() + ".conf"))
                .build();
    }

    private static void createDataFile(Player player) throws ConfigurateException {
        final File file = basePlayerConfigPath.resolve(player.getUniqueId() + ".conf").toFile();

        if (!file.exists()) {
            javaPlugin.getComponentLogger().info("Creating " + player.getName() + "('s) config file...");

            rootNode = getPlayerConfig(player).load();
            addData(player);

            if (save(rootNode, player)) {
                javaPlugin.getComponentLogger().info("File created at: " + file.getPath());
            }
        }
    }

    private static void addData(Player player) throws SerializationException {
        addStringNode(rootNode.node("last-username"), player.getName());
        addBooleanNode(rootNode.node("show-coordinates"), true);
    }

    public static boolean save(CommentedConfigurationNode node, Player player) {
        try {
            getPlayerConfig(player).save(node);
            return true;
        } catch (ConfigurateException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static class Events implements Listener {

        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent event) {
            try {
                createDataFile(event.getPlayer());

                if (rootNode == null) {
                    rootNode = getPlayerConfig(event.getPlayer()).load();
                    setTimeNode(rootNode.node("last-login"), LocalDateTime.now());
                    save(rootNode, event.getPlayer());
                }
            } catch (ConfigurateException e) {
                e.printStackTrace();
            }
        }

        @EventHandler(priority = EventPriority.HIGH)
        public void onPlayerQuit(PlayerQuitEvent event) {
            if (rootNode == null) {
                try {
                    rootNode = getPlayerConfig(event.getPlayer()).load();
                    addLocationNode(rootNode.node("last-location"), event.getPlayer().getLocation());
                    setTimeNode(rootNode.node("last-logout"), LocalDateTime.now());
                    save(rootNode, event.getPlayer());
                } catch (ConfigurateException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }

}
