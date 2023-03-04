package fr.obelouix.ultimate.config;

import fr.obelouix.ultimate.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.spigotmc.SpigotConfig;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class Config {

    private static final HoconConfigurationLoader configLoader = HoconConfigurationLoader.builder()
            .path(Path.of(Main.getPlugin().getDataFolder().getPath(), "config.conf"))
            .build();


    private static CommentedConfigurationNode root;

    private static boolean configReloaded = true;
    private static boolean disableReloadCommand = false;
    private static boolean disableWitherBlockDamage = false;
    private static boolean showWitherSkullExplosionsParticles = false;
    private static boolean isAnvilInfiniteRepairEnabled = false;
    private static boolean isFastLeafDecayEnabled = false;
    private static final Set<World> coordinatesBlacklist = new HashSet<>();

    private static boolean giveEnderDragon = false;

    private static boolean reviveCoralBlock;

    public static void loadConfig() {
        try {
            if (!configReloaded) configReloaded = true;
            root = configLoader.load();
            createFile();
        } catch (ConfigurateException e) {
            Main.getPlugin().getComponentLogger().error("An error occurred while loading this configuration: " + e.getMessage());
            configReloaded = false;
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }

        Main.getPlugin().getComponentLogger().info("Loading configuration...");

        final File file = Path.of(Main.getPlugin().getDataFolder().getPath(), "config.conf").toFile();

        try {
            addMissingConfigs();
        } catch (SerializationException e) {
            e.printStackTrace();
        }

        giveEnderDragon = root.node("tweaks", "give-enderdragon-exp-to-everyone").getBoolean();

        disableReloadCommand = root.node("disable-default-reload-command").getBoolean();
        isAnvilInfiniteRepairEnabled = root.node("anvil", "infinite-repair").getBoolean();
        disableWitherBlockDamage = root.node("protection", "explosions", "wither", "disable-block-damage").getBoolean();
        showWitherSkullExplosionsParticles = root.node("protection", "explosions", "wither", "show-wither-skull-explosions-particles").getBoolean();
        isFastLeafDecayEnabled = root.node("fast-leaf-decay", "enabled").getBoolean();
        Main.getPlugin().getComponentLogger().info("Configuration loaded");

        try {
            Objects.requireNonNull(root.node("coordinates", "world", "blacklist").getList(String.class)).forEach(
                    s -> coordinatesBlacklist.add(Bukkit.getWorld(s)));
        } catch (SerializationException e) {
            e.printStackTrace();
        }

        save(root);
    }

    private static void createFile() {
        final File file = Path.of(Main.getPlugin().getDataFolder().getPath(), "config.conf").toFile();

        if (!file.exists()) {
            Main.getPlugin().getComponentLogger().info("Creating configuration file...");


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
     */
    private static void addMissingConfigs() throws SerializationException {

        CompletableFuture.runAsync(Config::addFeatureSettings);


        final CommentedConfigurationNode enderDragonExpShare = root.node("tweaks", "give-enderdragon-exp-to-everyone");

        if (enderDragonExpShare.empty()) {

            final double expMergeRadius = SpigotConfig.config.getDouble("world-settings.default.merge-radius.exp");
            if (expMergeRadius > 0.) enderDragonExpShare.set(true);
            else enderDragonExpShare.set(false);

            enderDragonExpShare.commentIfAbsent("""
                    Give Ender Dragon's experience to every player that are present in the end.
                    You can enable this if your server merge experience orbs (see spigot.yml).
                    By default it is enabled when this configuration generate if your server merge experience
                    """);
        }

        if (root.node("debug").empty()) {
            root.node("debug").set(false)
                    .commentIfAbsent("Enable this only if you want to see the debug logs of the plugin.\n"
                            + "Be aware that this will be spammy");
        }

        if (root.node("disable-default-reload-command").empty()) {
            root.node("disable-default-reload-command").set(false).commentIfAbsent("Enabling this will block the /reload command");
        }

        if (root.node("custom-server-brand").empty()) {
            root.node("custom-server-brand").set("&r" + Bukkit.getName())
                    .commentIfAbsent("""
                            Allows you to fake the server brand in the F3 menu
                            You can use colors codes like &a,&1,&2,...
                            Please don't remove " or it will fail to parse color codes
                            """);
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

        if (root.node("anvil", "infinite-repair").empty()) {
            root.node("anvil", "infinite-repair").set(Boolean.TRUE);
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


/*        if (PluginDetector.getEssentials() != null) {
            root.node("night-skipper", "essentials-hook").set(Boolean.TRUE);
        } else {
            root.node("night-skipper", "essentials-hook").set(Boolean.FALSE);
        }*/
        save(root);
    }

    private static void addStorageSettings() {
        CompletableFuture.runAsync(() ->
                {
                    try {
                        if (root.node("data", "storage-type").empty()) {
                            root.node("data", "storage-type").set("file")
                                    .commentIfAbsent("Choose between: file, h2, mysql");
                        }

                        if (root.node("data", "database", "url").empty()) {
                            root.node("data", "database", "url").set("")
                                    .commentIfAbsent("Use this only for mysql");
                        }

                        if (root.node("data", "database", "port").empty()) {
                            root.node("data", "database", "port").set(3306)
                                    .commentIfAbsent("Use this only for mysql");
                        }

                        if (root.node("data", "database", "username").empty()) {
                            root.node("data", "database", "username").set("")
                                    .commentIfAbsent("Optional for h2");
                        }

                        if (root.node("data", "database", "password").empty()) {
                            root.node("data", "database", "password").set("")
                                    .commentIfAbsent("Optional for h2");
                        }
                    } catch (SerializationException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }


    private static void addFeatureSettings() {
        CompletableFuture.runAsync(() -> {
            try {
                if (root.node("coordinates", "world", "blacklist").empty()) {
                    root.node("coordinates", "world", "blacklist").setList(String.class, new ArrayList<>())
                            .commentIfAbsent("Add here the worlds were you wish to hide the coordinates bar" +
                                    "\n Like this: blacklist=[\"world1\",\"world2\"]");
                }

                if (root.node("fast-leaf-decay", "enabled").empty()) {
                    root.node("fast-leaf-decay", "enabled").set(Boolean.TRUE);
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

            } catch (SerializationException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static boolean giveEnderDragonExp() {
        return giveEnderDragon;
    }



    public static boolean isDisableReloadCommand() {
        return disableReloadCommand;
    }

    public static boolean isConfigReloaded() {
        return configReloaded;
    }

    public static boolean isWitherBlockDamageDisabled() {
        return disableWitherBlockDamage;
    }

    public static boolean showWitherSkullExplosionsParticles() {
        return showWitherSkullExplosionsParticles;
    }

    public static CommentedConfigurationNode getRoot() {
        return root;
    }

    public static boolean isFastLeafDecayEnabled() {
        return isFastLeafDecayEnabled;
    }

    public static boolean isAnvilInfiniteRepairEnabled() {
        return isAnvilInfiniteRepairEnabled;
    }

    public static Set<World> getCoordinatesBlacklist() {
        return coordinatesBlacklist;
    }

    public static boolean canReviveCoralBlock() {
        return reviveCoralBlock;
    }
}
