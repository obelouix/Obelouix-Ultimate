package fr.obelouix.ultimate.config;

import fr.obelouix.ultimate.AbstractPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
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
            .path(Path.of(AbstractPlugin.getPlugin().getDataFolder().getPath(), "config.conf"))
            .build();
    private static final Set<World> coordinatesBlacklist = new HashSet<>();
    private static CommentedConfigurationNode root;
    private static boolean configReloaded = true;
    private static boolean disableReloadCommand = false;
    private static boolean disableWitherBlockDamage = false;
    private static boolean showWitherSkullExplosionsParticles = false;
    private static boolean isAnvilInfiniteRepairEnabled = false;
    private static boolean isFastLeafDecayEnabled = false;
    private static boolean giveEnderDragon = false;

    private static boolean reviveCoralBlock;
    private static boolean protectTamedAnimals;

    private static void createFile() {
        final File file = Path.of(AbstractPlugin.getPlugin().getDataFolder().getPath(), "config.conf").toFile();

        if (!file.exists()) {
            AbstractPlugin.getPlugin().getComponentLogger().info("Creating configuration file...");


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


/*        final CommentedConfigurationNode enderDragonExpShare = root.node("tweaks", "give-enderdragon-exp-to-everyone");

        if (enderDragonExpShare.empty()) {

            final double expMergeRadius = SpigotConfig.config.getDouble("world-settings.default.merge-radius.exp");
            if (expMergeRadius > 0.) enderDragonExpShare.set(true);
            else enderDragonExpShare.set(false);

            enderDragonExpShare.commentIfAbsent("""
                    Give Ender Dragon's experience to every player that are present in the end.
                    You can enable this if your server merge experience orbs (see spigot.yml).
                    By default it is enabled when this configuration generate if your server merge experience
                    """);
        }*/

        save(root);
    }

    private static void addFeatureSettings() {
        CompletableFuture.runAsync(() -> {

            final CommentedConfigurationNode featuresNode = root.node("features");

            try {

                if (featuresNode.node("blocks", "revive-coral-with-potion").empty()) {
                    featuresNode.node("blocks", "revive-coral-with-potion").set(true)
                            .commentIfAbsent("Allows to revive dead coral blocks using instant health splash potions");
                }

                if (featuresNode.node("entity", "tamed", "block-damage").empty()) {
                    featuresNode.node("entity", "tamed", "block-damage").set(true)
                            .commentIfAbsent("Protect tamed entities from any damage source");
                }

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

    public static boolean shouldProtectTamedAnimals() {
        return protectTamedAnimals;
    }

    public void loadConfig() {
        try {
            if (!configReloaded) configReloaded = true;
            root = configLoader.load();
            createFile();
        } catch (ConfigurateException e) {
            AbstractPlugin.getPlugin().getComponentLogger().error("An error occurred while loading this configuration: " + e.getMessage());
            configReloaded = false;
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }

        AbstractPlugin.getPlugin().getComponentLogger().info("Loading configuration...");

        final File file = Path.of(AbstractPlugin.getPlugin().getDataFolder().getPath(), "config.conf").toFile();

        try {
            addMissingConfigs();
        } catch (SerializationException e) {
            e.printStackTrace();
        }

        disableReloadCommand = root.node("disable-default-reload-command").getBoolean();
        disableWitherBlockDamage = root.node("protection", "explosions", "wither", "disable-block-damage").getBoolean();
        giveEnderDragon = root.node("tweaks", "give-enderdragon-exp-to-everyone").getBoolean();
        isAnvilInfiniteRepairEnabled = root.node("anvil", "infinite-repair").getBoolean();
        isFastLeafDecayEnabled = root.node("fast-leaf-decay", "enabled").getBoolean();
        protectTamedAnimals = root.node("features", "entity", "tamed", "block-damage").getBoolean();
        reviveCoralBlock = root.node("features", "blocks", "revive-coral-with-potion").getBoolean();
        showWitherSkullExplosionsParticles = root.node("protection", "explosions", "wither", "show-wither-skull-explosions-particles").getBoolean();
        AbstractPlugin.getPlugin().getComponentLogger().info("Configuration loaded");

        try {
            Objects.requireNonNull(root.node("coordinates", "world", "blacklist").getList(String.class)).forEach(
                    s -> coordinatesBlacklist.add(Bukkit.getWorld(s)));
        } catch (SerializationException e) {
            e.printStackTrace();
        }

        save(root);
    }
}
