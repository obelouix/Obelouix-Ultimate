package fr.obelouix.ultimate.config;

import fr.obelouix.ultimate.ObelouixUltimate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.World;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Config {

    protected static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    private static final HoconConfigurationLoader configLoader = HoconConfigurationLoader.builder()
            .path(Path.of(plugin.getDataFolder().getPath(), "config.conf"))
            .build();
    protected static final Set<World> coordinatesBlacklist = new HashSet<>();
    protected static boolean reviveCoralBlock;
    protected static boolean debugMode;
    protected static boolean disableReloadCommand;
    protected static String customServerBrand;
    protected static String storageType;
    protected static boolean witherBlockDamage;
    private static CommentedConfigurationNode paperRoot;
    protected static boolean showWitherSkullExplosionsParticles = false;
    protected static boolean dynmapStructuresEnabled = false;
    protected static boolean dynmapWorldGuardEnabled = false;

    private static final HashMap<String, Boolean> dynmapStructureMap = new HashMap<>();
    public static final Map<String, String> chatFormat = new HashMap<>();
    protected static String dynmapWorldGuardLayer = "";
    protected static boolean enableCoordinates;
    private static boolean configReloaded = true;
    protected static boolean infiniteAnvilRepair;
    protected static boolean fastLeafDecay;
    protected static boolean shareEnderDragonExperience;
    protected static String databaseUsername;
    protected static String databasePassword;
    protected static String databaseUrl;
    protected static int databasePort;
    private static CommentedConfigurationNode root;
    private static MainConfig mainConfig;
    private static FeatureConfig featureConfig;
    private static ProtectionConfig protectionConfig;
    private static LuckPermsConfig luckPermsConfig;
    private static DynmapConfig dynmapConfig;
    private static List<BaseConfig> configCategories;

    public static void loadConfig() {
        try {
            if (!configReloaded) configReloaded = true;
            root = configLoader.load();
            mainConfig = new MainConfig();
            featureConfig = new FeatureConfig();
            protectionConfig = new ProtectionConfig();
            luckPermsConfig = new LuckPermsConfig();
            dynmapConfig = new DynmapConfig();
            createFile();
        } catch (ConfigurateException e) {
            plugin.getComponentLogger().error("An error occurred while loading this configuration: " + e.getMessage());
            configReloaded = false;
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }

        configCategories = List.of(mainConfig, featureConfig, protectionConfig, luckPermsConfig, dynmapConfig);

        plugin.getComponentLogger().info(Component.text("Loading configuration...", NamedTextColor.GOLD));

        configCategories.forEach(baseConfig -> {
            try {
                baseConfig.addToConfig();
            } catch (SerializationException e) {
                throw new RuntimeException(e);
            }
        });

        configCategories.forEach(BaseConfig::readConfig);
        plugin.getComponentLogger().info(Component.text("Configuration loaded", NamedTextColor.GREEN));

        save(root);
    }

    private static void createFile() {
        final File file = Path.of(plugin.getDataFolder().getPath(), "config.conf").toFile();

        if (!file.exists()) {
            plugin.getComponentLogger().info(Component.text("Creating configuration file...", NamedTextColor.GOLD));

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

    public static void reload() {
        CompletableFuture.runAsync(() -> configCategories.forEach(baseConfig -> {
                    try {
                        //add missing entries when reloading (e.g. if user deleted some)
                        baseConfig.addToConfig();
                        save(root);
                    } catch (SerializationException e) {
                        throw new RuntimeException(e);
                    }
                })
        ).thenRun(() -> {
            configCategories.forEach(BaseConfig::readConfig);
            plugin.getComponentLogger().info(Component.text("Reloaded configuration", NamedTextColor.GREEN));
        });
    }


    /**
     * add Missing entries in the configuration if the file exist
     */

    public static boolean giveEnderDragonExp() {
        return shareEnderDragonExperience;
    }

    public static boolean isDebugMode() {
        return debugMode;
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

    public static String getCustomServerBrand() {
        return customServerBrand;
    }

    public static boolean isWitherBlockDamageDisabled() {
        return witherBlockDamage;
    }

    public static boolean showWitherSkullExplosionsParticles() {
        return showWitherSkullExplosionsParticles;
    }


    public static boolean isDynmapStructuresEnabled() {
        return dynmapStructuresEnabled;
    }

    public static String getDynmapStructuresLayerName() {
        String dynmapStructuresLayerName = "";
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
        return fastLeafDecay;
    }

    public static boolean canInfinitelyRepair() {
        return infiniteAnvilRepair;
    }

    public static Set<World> getCoordinatesBlacklist() {
        return coordinatesBlacklist;
    }

    public static String getDatabaseUsername() {
        return databaseUsername;
    }

    public static String getDatabasePassword() {
        return databasePassword;
    }

    public static String getDatabaseUrl() {
        return databaseUrl;
    }

    public static int getDatabasePort() {
        return databasePort;
    }

    /**
     * Return if the plugin should enable the sharing events for enderdragon's experience
     *
     * @return sharedEnderDragonExperience
     */
    public static boolean ShareEnderDragonExperience() {
        return Config.shareEnderDragonExperience;
    }

    public static boolean canReviveCoralBlock() {
        return reviveCoralBlock;
    }
}
