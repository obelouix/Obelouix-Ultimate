package fr.obelouix.ultimate.config;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.dynmap.DynmapLoader;
import fr.obelouix.ultimate.utils.LuckPermsUtils;
import net.luckperms.api.model.group.Group;
import org.bukkit.Bukkit;
import org.bukkit.StructureType;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Config {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    private static final HoconConfigurationLoader configLoader = HoconConfigurationLoader.builder()
            .path(Path.of(plugin.getDataFolder().getPath(), "config.conf"))
            .build();
    private static final HashMap<String, Boolean> dynmapStructureMap = new HashMap<>();
    public static Map<String, String> chatFormat = new HashMap<>();
    private static String customServerBrandName;
    private static boolean disableReloadCommand = false;
    private static String storageType;
    private static CommentedConfigurationNode root;
    private static boolean configReloaded = true;
    private static boolean disableWitherBlockDamage = false;
    private static boolean showWitherSkullExplosionsParticles = false;
    private static boolean serverInMaintenance = false;

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

        plugin.getLogger().info("Loading configuration...");

        customServerBrandName = root.node("custom-server-brand").getString();
        storageType = root.node("data-storage-type").getString();
        disableReloadCommand = root.node("disable-default-reload-command").getBoolean();
        if (LuckPermsUtils.getLuckPermsAPI() != null) {
            for (final Object group : root.node("chat", "format").childrenMap().keySet()) {
                chatFormat.put(group.toString(), root.node("chat", "format", group.toString()).getString());
            }

        }

        if (DynmapLoader.isIsDynmapPresent()) {
            for (final Object structure : root.node("dynmap", "structures").childrenMap().keySet()) {
                dynmapStructureMap.put(
                        root.node("dynmap", "structures", structure, "displayname").getString(),
                        root.node("dynmap", "structures", structure, "show").getBoolean()
                );
            }
        }

        disableWitherBlockDamage = root.node("protection", "explosions", "wither", "disable-block-damage").getBoolean();
        showWitherSkullExplosionsParticles = root.node("protection", "explosions", "wither", "show-wither-skull-explosions-particles").getBoolean();
        serverInMaintenance = root.node("maintenance").getBoolean();
        plugin.getLogger().info("Configuration loaded");

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
                for (final Group group : LuckPermsUtils.getGroups()) {
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

            if (DynmapLoader.isIsDynmapPresent()) {
                for (String structureType : StructureType.getStructureTypes().keySet()) {
                    root.node("dynmap", "structures").act(n -> {
                        n.node(structureType, "show").set(Boolean.TRUE);
                        n.node(structureType, "displayname").set(structureType.replaceAll("_", " "));
                    }).commentIfAbsent("Set the structures to show on dynmap and set their names");
                }
            }

            root.node("custom-server-brand").set("&r" + Bukkit.getName())
                    .commentIfAbsent("""
                            Allows you to fake the server brand in the F3 menu
                            You can use colors codes like &a,&1,&2,...
                            Please don't remove " or it will fail to parse color codes
                            """);

            root.node("protection", "explosions", "wither", "disable-block-damage").set(false)
                    .commentIfAbsent("Disable damages on blocks from the wither boss");

            root.node("protection", "explosions", "wither", "show-wither-skull-explosions-particles").set(false)
                    .commentIfAbsent("Show smoke at the location where wither skulls explodes\n" +
                            "only works if block damages are blocked");

            root.node("maintenance").set(false)
                    .commentIfAbsent("This allow to put the server in maintenance mode and also\n" +
                            "memorize maintenance state if you need to restart the server");

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

    public static String getCustomServerBrandName() {
        return customServerBrandName;
    }

    public static boolean isWitherBlockDamageDisabled() {
        return disableWitherBlockDamage;
    }

    public static boolean showWitherSkullExplosionsParticles() {
        return showWitherSkullExplosionsParticles;
    }

    public static boolean isServerInMaintenance() {
        return serverInMaintenance;
    }

    public static void setServerInMaintenance(boolean serverInMaintenance) {
        Config.serverInMaintenance = serverInMaintenance;
        try {
            root.node("maintenance").set(serverInMaintenance);
            save(Config.getRoot());
        } catch (SerializationException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Boolean> getDynmapStructureMap() {
        return dynmapStructureMap;
    }

    public static CommentedConfigurationNode getRoot() {
        return root;
    }

}
