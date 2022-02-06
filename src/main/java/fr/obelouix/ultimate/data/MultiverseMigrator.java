package fr.obelouix.ultimate.data;

import fr.obelouix.ultimate.ObelouixUltimate;
import org.bukkit.Bukkit;
import org.checkerframework.checker.units.qual.K;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
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

    private static boolean checkForMultiverseData(){
        return Files.exists(Path.of(Bukkit.getServer().getPluginsFolder().getPath(),
                "Multiverse-Core", "worlds.yml"));
    }


    private static void startMigration(){
        if(checkForMultiverseData()){
            try {
                final CommentedConfigurationNode root = worldLoader.load();
                final CommentedConfigurationNode mvRoot = multiverseLoader.load();

                NavigableMap<Object, CommentedConfigurationNode> mvMap = new TreeMap<>(mvRoot.node("worlds").childrenMap());
                mvMap.forEach((o, commentedConfigurationNode) ->
                {
                    // Is PVP enabled in this world ?
                    final boolean isPVPEnabled = mvRoot.node("worlds", o.toString(), "pvp").getBoolean();
                    // Get the seed of the world
                    final String seed = mvRoot.node("worlds", o.toString(), "seed").getString();
                    // Get the world type (Normal, Nether, The_End)
                    final String environment = mvRoot.node("worlds", o.toString(), "environment").getString();
                    // Get the game mode of the world
                    final String gamemode = mvRoot.node("worlds", o.toString(), "gameMode").getString();
                    // Should we keep the spawn of this world in ram ?
                    final boolean keepSpawnInMemory = mvRoot.node("worlds", o.toString(), "keepSpawnInMemory").getBoolean();
                    // Should we load the world when the server start ?
                    final boolean loadWorldAtStartup = mvRoot.node("worlds", o.toString(), "autoLoad").getBoolean();
                    try {
                        root.node("worlds", o.toString(), "pvp").set(isPVPEnabled);
                        root.node("worlds", o.toString(), "seed").set(seed);
                        root.node("worlds", o.toString(), "keepSpawnInMemory").set(keepSpawnInMemory);
                        root.node("worlds", o.toString(), "environment").set(environment);
                        root.node("worlds", o.toString(), "gamemode").set(gamemode);
                        root.node("worlds", o.toString(), "autoLoad").set(loadWorldAtStartup)
                                .commentIfAbsent("Should the server load the world at startup ?");
                    } catch (SerializationException e) {
                        e.printStackTrace();
                    }
                });

                worldLoader.save(root);

                /*mvRoot.node("worlds").act(value -> {
                    mvRoot.node("worlds").childrenMap().forEach((o, commentedConfigurationNode) ->
                            mvRoot.node("worlds").childrenMap().get(mvRoot.node("worlds").childrenMap().));
                });*/


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static boolean checkForExistingMigratedData(){
        return Files.exists(Path.of(plugin.getDataFolder() + File.pathSeparator + "worlds" + File.separator + "worlds.conf"));
    }

}
