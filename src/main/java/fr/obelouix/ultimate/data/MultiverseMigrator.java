package fr.obelouix.ultimate.data;

import fr.obelouix.ultimate.ObelouixUltimate;
import org.bukkit.Bukkit;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NavigableMap;
import java.util.TreeMap;

public class MultiverseMigrator {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    private static final HoconConfigurationLoader worldLoader = HoconConfigurationLoader.builder()
            .path(Path.of(plugin.getDataFolder().getPath(), "worlds.conf"))
            .build();

    private static final YamlConfigurationLoader multiverseLoader = YamlConfigurationLoader.builder()
            .path(Path.of(Bukkit.getServer().getPluginsFolder().getPath(),
                    "Multiverse-Core", "worlds.yml"))
            .build();

    public MultiverseMigrator() {
        startMigration();
    }

    private static boolean checkForMultiverseData() {
        return Files.exists(Path.of(Bukkit.getServer().getPluginsFolder().getPath(),
                "Multiverse-Core", "worlds.yml"));
    }


    private static void startMigration() {
        if (checkForMultiverseData() && !checkForExistingMigratedData()) {
            plugin.getComponentLogger().info("Found Multiverse-Core data, starting importation of worlds...");
            try {
                final CommentedConfigurationNode root = worldLoader.load();
                final CommentedConfigurationNode mvRoot = multiverseLoader.load();

                final NavigableMap<Object, CommentedConfigurationNode> mvMap = new TreeMap<>(mvRoot.node("worlds").childrenMap());
                mvMap.keySet().forEach(o -> {
                    final boolean isPVPEnabled = mvRoot.node("worlds", o.toString(), "pvp").getBoolean();
                    final String seed = mvRoot.node("worlds", o.toString(), "seed").getString();
                    final String environment = mvRoot.node("worlds", o.toString(), "environment").getString();
                    final String gamemode = mvRoot.node("worlds", o.toString(), "gameMode").getString();
                    final boolean keepSpawnInMemory = mvRoot.node("worlds", o.toString(), "keepSpawnInMemory").getBoolean();
                    final boolean loadWorldAtStartup = mvRoot.node("worlds", o.toString(), "autoLoad").getBoolean();
                    final String portalForm = mvRoot.node("worlds", o.toString(), "portalForm").getString();
                    try {
                        root.node("worlds", o.toString(), "pvp").set(isPVPEnabled);
                        root.node("worlds", o.toString(), "seed").set(seed);
                        root.node("worlds", o.toString(), "keepSpawnInMemory").set(keepSpawnInMemory);
                        root.node("worlds", o.toString(), "environment").set(environment);
                        root.node("worlds", o.toString(), "gamemode").set(gamemode);
                        root.node("worlds", o.toString(), "autoLoad").set(loadWorldAtStartup)
                                .commentIfAbsent("Should the server load the world at startup ?");
                        root.node("worlds", o.toString(), "portalForm").set(portalForm)
                                .commentIfAbsent("""
                                        Can player builds portals ?
                                        Possible values: NONE, NETHER, END, ALL
                                        """);


                    } catch (SerializationException e) {
                        e.printStackTrace();
                    }
                });

                worldLoader.save(root);
                plugin.getComponentLogger().info("Importation successful. Moved data to worlds.conf");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static boolean checkForExistingMigratedData() {
        return Files.exists(Path.of(plugin.getDataFolder().getPath(),"/worlds.conf"));
    }

}
