package fr.obelouix.ultimate.config;

import fr.obelouix.ultimate.utils.PluginDetector;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class DynmapConfig extends BaseConfig {

    private final CommentedConfigurationNode dynmapNode = Config.getRoot().node("dynmap");

    @Override
    protected void addToConfig() throws SerializationException {

        if (PluginDetector.getDynmap() != null) {
            if (dynmapNode.node("modules", "structures", "enabled").empty()) {
                dynmapNode.node("modules", "structures", "enabled").set(true);
            }
            if (dynmapNode.node("modules", "structures", "layer_name").empty()) {
                dynmapNode.node("modules", "structures", "layer_name").set("Structures");
            }

            if (PluginDetector.getWorldGuard() != null) {
                if (dynmapNode.node("modules", "worldguard", "enabled").empty()) {
                    dynmapNode.node("modules", "worldguard", "enabled").set(true);
                }
                if (dynmapNode.node("modules", "worldguard", "layer_name").empty()) {
                    dynmapNode.node("modules", "worldguard", "layer_name").set("WorldGuard");
                }
            }
        }
    }

    @Override
    protected void readConfig() {
        if (PluginDetector.getDynmap() != null) {

            Config.dynmapStructuresEnabled = dynmapNode.node("modules", "structures", "enabled").getBoolean();

            if (Config.dynmapStructuresEnabled) {
                Config.dynmapStructuresEnabled = false;
                Config.plugin.getComponentLogger().info("Dynmap Structures is disabled until the lag it cause is resolved");
            }

            if (PluginDetector.getWorldGuard() != null) {
                Config.dynmapWorldGuardEnabled = dynmapNode.node("modules", "worldguard", "enabled").getBoolean();
                Config.dynmapWorldGuardLayer = dynmapNode.node("modules", "worldguard", "layer_name").getString();
            }
        }
    }
}
