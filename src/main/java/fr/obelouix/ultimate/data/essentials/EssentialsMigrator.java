package fr.obelouix.ultimate.data.essentials;

import fr.obelouix.ultimate.ObelouixUltimate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class EssentialsMigrator {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static final Path EssentialsPluginFolder = Path.of(Bukkit.getPluginsFolder().getPath(), "Essentials");

    public static void start() {
        CompletableFuture.supplyAsync(() -> {
            if (Files.exists(EssentialsPluginFolder)) {
                plugin.getComponentLogger().info(Component.text("Starting migration of Essentials plugin data...", NamedTextColor.YELLOW));
                return true;
            }
            return false;
        }).thenAcceptAsync(folderExist -> {
            if (folderExist) {
                try {
                    migrateSpawnData();
                    migrateUserData();
                    FileUtils.copyDirectory(EssentialsPluginFolder.toFile(), Path.of(plugin.getDataFolder().getPath(), "migrated_data", "Essentials").toFile());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private static void migrateSpawnData() {

        final YamlConfigurationLoader oldSpawn = YamlConfigurationLoader.builder()
                .path(EssentialsPluginFolder.resolve("spawn.yml"))
                .build();

        final HoconConfigurationLoader migratedSpawn = HoconConfigurationLoader.builder()
                .path(Path.of(plugin.getDataFolder().getPath(), "spawn.conf"))
                .build();

        try {
            plugin.getComponentLogger().info(Component.text("migrating essentials spawn data", NamedTextColor.YELLOW));
            final CommentedConfigurationNode oldSpawnNode = oldSpawn.load();
            final CommentedConfigurationNode migratedSpawnNode = migratedSpawn.load();

            oldSpawnNode.childrenMap().forEach((key, value) -> {
                try {
                    migratedSpawnNode.node(key).set(value);
                    migratedSpawn.save(migratedSpawnNode);
                    plugin.getComponentLogger().info(Component.text("migrated essentials spawn data", NamedTextColor.GREEN));

                } catch (IOException e) {
                    plugin.getComponentLogger().info(Component.text("failed to migrate essentials spawn data", NamedTextColor.DARK_RED));
                }
            });


        } catch (ConfigurateException e) {
            throw new RuntimeException(e);
        }

    }

    private static void migrateUserData() {
        try {
            final File playerFiles = new File(EssentialsPluginFolder.resolve("userdata").toUri());
            //only take yaml files
            final FilenameFilter filter = (dir, name) -> name.endsWith(".yml");
            final List<File> files = Arrays.stream(Objects.requireNonNull(playerFiles.listFiles(filter))).toList();

            Arrays.stream(Objects.requireNonNull(playerFiles.listFiles(filter))).forEach(file -> {

                final YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                        .path(file.toPath())
                        .build();

                final HoconConfigurationLoader migratedPlayerData = HoconConfigurationLoader.builder()
                        .path(Path.of(plugin.getDataFolder().getPath(), "data", "players", file.getName().substring(0, file.getName().lastIndexOf(".")) + ".conf"))
                        .build();


                try {
                    final ConfigurationNode node = loader.load();
                    final ConfigurationNode migratedData = migratedPlayerData.load();

                    node.childrenMap().forEach((key, value) -> {
                        try {
                            migratedData.node(key).set(value);
                            migratedPlayerData.save(migratedData);
                            //System.out.println("migrated " +  ((files.indexOf(file) +1) / queueSize)*100 + "% of player data");
                        } catch (ConfigurateException e) {
                            throw new RuntimeException(e);
                        }
                    });

                } catch (ConfigurateException e) {
                    throw new RuntimeException(e);
                }

            });

        } catch (Exception e) {

        }
    }


}
