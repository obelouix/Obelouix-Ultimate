package fr.obelouix.ultimate.data;

import fr.obelouix.ultimate.ObelouixUltimate;
import org.bukkit.Bukkit;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
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

                for (Object map: mvMap.entrySet()){
                    plugin.getLogger().info(String.valueOf(map));
                    if (map.equals("==: MVWorld")){
                        plugin.getLogger().info(String.valueOf(mvMap.lowerEntry(map)));
                    }

                }

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
