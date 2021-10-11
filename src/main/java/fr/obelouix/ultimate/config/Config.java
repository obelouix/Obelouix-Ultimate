package fr.obelouix.ultimate.config;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.utils.LuckPermsUtils;
import net.luckperms.api.model.group.Group;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NonNls;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Config {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    private static final HoconConfigurationLoader configLoader = HoconConfigurationLoader.builder()
            .path(Path.of(plugin.getDataFolder().getPath(), "config.conf"))
            .build();
    private static final @NonNls FileConfiguration pluginConfig = plugin.getConfig();
    private static boolean disableReloadCommand = false;
    private static String storageType;
    public static Map<String, String> chatFormat = new HashMap<>();
    private static CommentedConfigurationNode root;
    private static boolean configReloaded = true;

    public static void loadConfig() {
        try {
            if (!configReloaded) configReloaded = true;
            root = configLoader.load();
            createFile();
        } catch (ConfigurateException e) {
            plugin.getLogger().severe("An error occurred while loading this configuration: " + e.getMessage());
            configReloaded = false;
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }
        storageType = root.node("data-storage-type").getString();
        disableReloadCommand = root.node("disable-default-reload-command").getBoolean();
        if (LuckPermsUtils.getLuckPermsAPI() != null) {
            for (final Object group : root.node("chat", "format").childrenMap().keySet()) {
                chatFormat.put(group.toString(), root.node("chat", "format", group.toString()).getString());
            }

        }
    }

    private static void createFile() throws ConfigurateException {
        final File file = Path.of(plugin.getDataFolder().getPath(), "config.conf").toFile();

        if (!file.exists()) {
            plugin.getLogger().info("Creating configuration file...");

            root.node("data-storage-type").set("file")
                    .commentIfAbsent("Choose between: file, H2, MYSQL, SQLite . Note that SQL database are not yet implemented");
            root.node("disable-default-reload-command").set(false).commentIfAbsent("Enabling this will block the /reload command");

            // Get all groups and generate the config dynamically
            if (LuckPermsUtils.getLuckPermsAPI() != null) {
                for (Group group : LuckPermsUtils.getGroups()) {
                    root.node("chat").act(n -> {
                        if (group.getName().equals("default")) {
                            n.node("format")
                                    .commentIfAbsent("""
                                            This allow you to control the chat formatting for every groups
                                            You case use standard color code like &4 and also hex color codes like &#808080
                                                                                        
                                            Some tags are available to allow you to fully customize the chat:
                                            - {displayname} : player name
                                            - {message} : the message of the player
                                            - {world} : the current world of the player
                                            - {prefix} : the luckperms prefix of the group
                                            - {suffix} : the luckperms suffix of the group
                                                                                        
                                            /!\\ PLEASE NOTE THAT IF YOU ADD COLOR CODES DIRECTLY IN LUCKPERMS META FOR
                                            PREFIX AND SUFFIX, THEY WILL OVERRIDE PREFIX AND SUFFIX COLORS FROM THIS CONFIG
                                            """)
                                    .node(group.getName()).set("&#808080{displayname}: {message}")
                                    .commentIfAbsent("default LuckPerms group");
                        } else {
                            n.node("format").node(group.getName()).set("&#32cd32[{world}]&r{prefix}{displayname}{suffix}: &r{message}")
                                    .commentIfAbsent(group.getName() + " group");
                        }

                    });
                }
            }

            /*root.node("commands").act(n -> {
                n.commentIfAbsent("Allow you to control which commands you want on your server");
                for (final String command : commandList) {
                    n.node(command).raw(true);
                }
            });

            root.node("tablist").act(n -> n.node("show-player-ping").raw(true));

            root.node("watchdog", "enabled").raw(true);
            root.node("watchdog", "fly").act(
                    n -> {
                        n.node("protect-creative-mode").raw(true);
                        n.node("action").set(String.class, "ban");
                        n.node("tempban").raw(false);
                    }
            );

            root.node("watchdog", "antispam").act(n -> {
                n.node("matches-before-action").set(3);
            });*/

            save(root);
        }
    }

    public static void save(CommentedConfigurationNode node) {
        try {
            configLoader.save(node);
        } catch (ConfigurateException e) {
            e.printStackTrace();
        }
    }

    public static String getStorageType() {
        return storageType;
    }

    public static boolean isDisableReloadCommand() {
        return disableReloadCommand;
    }

    public static boolean isConfigReloaded() {
        return configReloaded;
    }
}
