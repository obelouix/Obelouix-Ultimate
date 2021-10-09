package fr.obelouix.ultimate.config;

import fr.obelouix.ultimate.ObelouixUltimate;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NonNls;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.nio.file.Path;

public class Config {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    private static final YamlConfigurationLoader configLoader = YamlConfigurationLoader.builder()
            .path(Path.of(plugin.getDataFolder().getPath(), "config.yml"))
            .build();
    private static final @NonNls FileConfiguration pluginConfig = plugin.getConfig();
    private static CommentedConfigurationNode root;

    public static void loadConfig(){
        try {
            root = configLoader.load();
            createFile();
        } catch (ConfigurateException e) {
            plugin.getLogger().severe("An error occurred while loading this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }
    }
    private static void createFile() throws ConfigurateException {
        final File file = Path.of(plugin.getDataFolder().getPath(), "config.yml").toFile();

        if (!file.exists()) {
            plugin.getLogger().info("Creating configuration file...");
            /*root.node("commands").act(n -> {
                n.commentIfAbsent("Allow you to control which commands you want on your server");
                for (final String command : commandList) {
                    n.node(command).raw(true);
                }
            });

            root.node("tablist").act(n -> n.node("show-player-ping").raw(true));

            // Get all groups and generate the config dynamically
            if (plugin.getLuckPermsAPI() != null) {
                for (Group group : LuckPermsGroups.getGroups()) {
                    root.node("chat").act(n -> {
                        if (group.getName().equals("default")) {
                            n.node("format").node(group.getName()).set("&#808080{displayname}: {message}");
                        } else {
                            n.node("format").node(group.getName()).set("&#32cd32[{world}]&r{prefix}{displayname}{suffix}: &r{message}");
                        }

                    });
                }
            }

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
}
