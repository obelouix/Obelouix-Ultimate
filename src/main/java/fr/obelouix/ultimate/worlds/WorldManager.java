package fr.obelouix.ultimate.worlds;

import fr.obelouix.ultimate.ObelouixUltimate;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.nio.file.Path;
import java.util.*;

public class WorldManager {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    private static final HoconConfigurationLoader worldLoader = HoconConfigurationLoader.builder()
            .path(Path.of(plugin.getDataFolder().getPath(), "worlds.conf"))
            .build();

    public static void loadWorlds() {
        try {
            final CommentedConfigurationNode root = worldLoader.load();
            final Map<Object, CommentedConfigurationNode> WorldList = new TreeMap<>(root.node("worlds").childrenMap());
            plugin.getLogger().info("Found worlds: "
                    + WorldList.keySet().toString().substring(1, WorldList.keySet().toString().length() - 1)
                    + " in worlds.conf");
            WorldList.keySet().forEach(world -> {
                if ((Objects.equals(Bukkit.getWorlds().get(1).getName(), world) && Bukkit.getWorlds().get(1).getName().contains("nether"))
                        || (Objects.equals(Bukkit.getWorlds().get(2).getName(), world) && Bukkit.getWorlds().get(2).getName().contains("the_end"))) {
                    if (!root.node("worlds", world, "autoLoad").getBoolean()) {
                        plugin.getLogger().info("Unloaded world '" + world + "' because autoLoad is set to 'false' for this default world");
                        Bukkit.getWorlds().remove(Bukkit.getWorld(world.toString()));
                    }
                } else if (!Objects.equals(Bukkit.getWorlds().get(0).getName(), world)) {
                    if (root.node("worlds", world, "autoLoad").getBoolean()) {
                        if (Bukkit.getWorld(world.toString()) == null) {
                            final WorldCreator wc = new WorldCreator(world.toString());
                            final String environment = root.node("worlds", world, "environment").getString();

                            switch (Objects.requireNonNull(environment).toUpperCase()) {
                                case "NORMAL" -> wc.environment(World.Environment.NORMAL);
                                case "NETHER" -> wc.environment(World.Environment.NETHER);
                                case "THE_END" -> wc.environment(World.Environment.THE_END);
                                default -> wc.environment(World.Environment.CUSTOM);
                            }
                            wc.seed(Long.parseLong(Objects.requireNonNull(root.node("worlds", world, "seed").getString())));
                            Bukkit.createWorld(wc);
                        } else {
                            Bukkit.getWorlds().add(Bukkit.getWorld(world.toString()));
                        }
                    }
                }
            });
        } catch (ConfigurateException e) {
            e.printStackTrace();
        }
    }

}
