package fr.obelouix.ultimate.config;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.utils.LuckPermsUtils;
import net.luckperms.api.model.group.Group;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

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
    private static boolean dynmapStructuresEnabled = false;
    private static String dynmapStructuresLayerName = "";
    private static boolean dynmapWorldGuardEnabled = false;
    private static String dynmapWorldGuardLayer = "";
    private static boolean isAnvilInfiniteRepairEnabled = false;
    private static boolean isFastLeafDecayEnabled = false;
    private static boolean unloadEmptyWorlds = false;
    private static boolean disconnectOnHighPing = false;
    private static int maxPing;
    private static boolean nightSkipSystemEnabled;
    private static boolean EssentialsAFKHook;
    private static final Set<World> coordinatesBlacklist = new HashSet<>();

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

        final File file = Path.of(plugin.getDataFolder().getPath(), "config.conf").toFile();

        /*if (file.exists()) {
            try {
                addMissingConfigs();
            } catch (SerializationException e) {
                e.printStackTrace();
            }
        }*/
        try {
            addMissingConfigs();
        } catch (SerializationException e) {
            e.printStackTrace();
        }
        customServerBrandName = root.node("custom-server-brand").getString();
        storageType = root.node("data-storage-type").getString();
        disableReloadCommand = root.node("disable-default-reload-command").getBoolean();
        if (LuckPermsUtils.getLuckPermsAPI() != null) {
            for (final Object group : root.node("chat", "format").childrenMap().keySet()) {
                chatFormat.put(group.toString(), root.node("chat", "format", group.toString()).getString());
            }

        }

        if (plugin.isIsDynmapPresent()) {

            dynmapStructuresEnabled = root.node("dynmap", "modules", "structures", "enabled").getBoolean();

            if (dynmapStructuresEnabled) {
                dynmapStructuresEnabled = false;
                plugin.getLogger().info("Dynmap Structures is disabled until the lag it cause is resolved");
/*                dynmapStructuresLayerName = root.node("dynmap", "modules", "structures", "layer_name").getString();

                for (final Object structure : root.node("dynmap", "structures").childrenMap().keySet()) {
                    dynmapStructureMap.put(
                            root.node("dynmap", "structures", structure, "displayname").getString(),
                            root.node("dynmap", "structures", structure, "show").getBoolean()
                    );
                }*/
            }

            if (plugin.isWorldGuardPresent()) {
                dynmapWorldGuardEnabled = root.node("dynmap", "modules", "worldguard", "enabled").getBoolean();
                dynmapWorldGuardLayer = root.node("dynmap", "modules", "worldguard", "layer_name").getString();
            }
        }

        isAnvilInfiniteRepairEnabled = root.node("anvil", "infinite-repair").getBoolean();
        disableWitherBlockDamage = root.node("protection", "explosions", "wither", "disable-block-damage").getBoolean();
        showWitherSkullExplosionsParticles = root.node("protection", "explosions", "wither", "show-wither-skull-explosions-particles").getBoolean();
        serverInMaintenance = root.node("maintenance").getBoolean();
        isFastLeafDecayEnabled = root.node("fast-leaf-decay", "enabled").getBoolean();
        plugin.getLogger().info("Configuration loaded");

        unloadEmptyWorlds = root.node("worlds", "unload_if_empty").getBoolean();

        disconnectOnHighPing = root.node("ping", "disconnect-if-too-high").getBoolean();

        if (disconnectOnHighPing) maxPing = root.node("ping", "threshold").getInt();
        EssentialsAFKHook = root.node("night-skipper", "essentials-hook").getBoolean();

        nightSkipSystemEnabled = root.node("night-skipper", "enabled").getBoolean();

        if (EssentialsAFKHook && !plugin.isEssentialsXPresent()) {
            try {
                root.node("night-skipper", "essentials-hook").set(Boolean.FALSE);
            } catch (SerializationException e) {
                e.printStackTrace();
            }
        }

        try {
            Objects.requireNonNull(root.node("coordinates", "world", "blacklist").getList(String.class)).forEach(
                    s -> coordinatesBlacklist.add(Bukkit.getWorld(s)));
        } catch (SerializationException e) {
            e.printStackTrace();
        }

        save(root);
    }

    private static void createFile() throws ConfigurateException {
        final File file = Path.of(plugin.getDataFolder().getPath(), "config.conf").toFile();

        if (!file.exists()) {
            plugin.getLogger().info("Creating configuration file...");



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



            /*root.node("night-skipper", "enabled").set(Boolean.TRUE);

            if(plugin.isEssentialsXPresent()) {
                root.node("night-skipper", "essentials-hook").set(Boolean.TRUE);
            } else {
                root.node("night-skipper", "essentials-hook").set(Boolean.FALSE);
            }*/

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

    /**
     * add Missing entries in the configuration if the file exist
     *
     */
    private static void addMissingConfigs() throws SerializationException {

        if (root.node("data-storage-type").empty()) {
            root.node("data-storage-type").set("file")
                    .commentIfAbsent("Choose between: file, H2, MYSQL, SQLite . Note that SQL database are not yet implemented");
        }
        if (root.node("disable-default-reload-command").empty()) {
            root.node("disable-default-reload-command").set(false).commentIfAbsent("Enabling this will block the /reload command");
        }
        // Get all groups and generate the config dynamically
        if (LuckPermsUtils.getLuckPermsAPI() != null && root.node("chat").empty()) {
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

        if (root.node("custom-server-brand").empty()) {
            root.node("custom-server-brand").set("&r" + Bukkit.getName())
                    .commentIfAbsent("""
                            Allows you to fake the server brand in the F3 menu
                            You can use colors codes like &a,&1,&2,...
                            Please don't remove " or it will fail to parse color codes
                            """);
        }

        if (root.node("protection", "explosions", "wither", "disable-block-damage").empty()) {
            root.node("protection", "explosions", "wither", "disable-block-damage").set(false)
                    .commentIfAbsent("Disable damages on blocks from the wither boss");
        }

        if (root.node("protection", "explosions", "wither", "show-wither-skull-explosions-particles").empty()) {
            root.node("protection", "explosions", "wither", "show-wither-skull-explosions-particles").set(false)
                    .commentIfAbsent("Show smoke at the location where wither skulls explodes\n" +
                            "only works if block damages are blocked");
        }

        if (root.node("maintenance").empty()) {
            root.node("maintenance").set(false)
                    .commentIfAbsent("This allow to put the server in maintenance mode and also\n" +
                            "memorize maintenance state if you need to restart the server");
        }

        if (root.node("ping", "disconnect-if-too-high").empty()) {
            root.node("ping", "disconnect-if-too-high").set(Boolean.FALSE)
                    .commentIfAbsent("If Enabled, it will kick player with too much ping");
        }

        if (root.node("ping", "threshold").empty()) {
            root.node("ping", "threshold").set(500).commentIfAbsent("Minimal ping before being disconnected");
        }
        if (plugin.isIsDynmapPresent()) {
            if (root.node("dynmap", "modules", "structures", "enabled").empty()) {
                root.node("dynmap", "modules", "structures", "enabled").set(Boolean.TRUE);
            }
            if (root.node("dynmap", "modules", "structures", "layer_name").empty()) {
                root.node("dynmap", "modules", "structures", "layer_name").set("Structures");
//                    .commentIfAbsent("A server restart is required to change the name, a reload will not work");
            }
            if (plugin.isWorldGuardPresent()) {
                if (root.node("dynmap", "modules", "worldguard", "enabled").empty()) {
                    root.node("dynmap", "modules", "worldguard", "enabled").set(Boolean.TRUE);
                }
                if (root.node("dynmap", "modules", "worldguard", "layer_name").empty()) {
                    root.node("dynmap", "modules", "worldguard", "layer_name").set("WorldGuard");
                }
            }
        }

        if (root.node("anvil", "infinite-repair").empty()) {
            root.node("anvil", "infinite-repair").set(Boolean.TRUE);
        }

        if (root.node("fast-leaf-decay", "enabled").empty()) {
            root.node("fast-leaf-decay", "enabled").set(Boolean.TRUE);
        }

        if (root.node("worlds", "unload_if_empty").empty()) {
            root.node("worlds", "unload_if_empty").set(Boolean.FALSE)
                    .commentIfAbsent("""
                            This allow the server to unload every world with no player in it
                            (The main world from server.properties will never be unloaded)
                            """);
        }
        if (root.node("ping", "disconnect-if-too-high").empty()) {
            root.node("ping", "disconnect-if-too-high").set(Boolean.FALSE)
                    .commentIfAbsent("If Enabled, it will kick player with too much ping");
        }

        if (root.node("ping", "threshold").empty()) {
            root.node("ping", "threshold").set(500).commentIfAbsent("Minimal ping before being disconnected");
        }

        root.node("night-skipper", "enabled").set(Boolean.TRUE);


        if (plugin.isEssentialsXPresent()) {
            root.node("night-skipper", "essentials-hook").set(Boolean.TRUE);
        } else {
            root.node("night-skipper", "essentials-hook").set(Boolean.FALSE);
        }

        if (root.node("coordinates", "world", "blacklist").empty()) {
            root.node("coordinates", "world", "blacklist").setList(String.class, new ArrayList<>())
                    .commentIfAbsent("Add here the worlds were you wish to hide the coordinates bar" +
                            "\n Like this: blacklist=[\"world1\",\"world2\"]");
        }

        save(root);
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

    public static boolean isDynmapStructuresEnabled() {
        return dynmapStructuresEnabled;
    }

    public static String getDynmapStructuresLayerName() {
        return dynmapStructuresLayerName;
    }

    public static boolean isDynmapWorldGuardEnabled() {
        return dynmapWorldGuardEnabled;
    }

    public static HashMap<String, Boolean> getDynmapStructureMap() {
        return dynmapStructureMap;
    }

    public static CommentedConfigurationNode getRoot() {
        return root;
    }

    public static String getDynmapWorldGuardLayer() {
        return dynmapWorldGuardLayer;
    }

    public static boolean isFastLeafDecayEnabled() {
        return isFastLeafDecayEnabled;
    }

    public static boolean isAnvilInfiniteRepairEnabled() {
        return isAnvilInfiniteRepairEnabled;
    }

    public static boolean shouldUnloadEmptyWorlds() {
        return unloadEmptyWorlds;
    }

    public static boolean isDisconnectOnHighPing() {
        return disconnectOnHighPing;
    }

    public static int getMaxPing() {
        return maxPing;
    }

    public static void setMaxPing(int maxPing) {
        Config.maxPing = maxPing;
    }

    public static boolean isNightSkipSystemEnabled() {
        return nightSkipSystemEnabled;
    }

    public static boolean isEssentialsAFKHook() {
        return EssentialsAFKHook;
    }

    public static Set<World> getCoordinatesBlacklist() {
        return coordinatesBlacklist;
    }
}
