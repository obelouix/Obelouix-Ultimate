package fr.obelouix.ultimate.dynmap;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.utils.PluginDetector;
import org.bukkit.event.Listener;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapCommonAPIListener;

public class DynmapLoader implements Listener {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static DynmapCommonAPI dynmapAPI;

    public static DynmapCommonAPI getDynmapAPI() {
        return dynmapAPI;
    }

    public void init() {
        plugin.getLogger().info("Found Dynmap");
        DynmapCommonAPIListener.register(new DynmapCommonAPIListener() {
            @Override
            public void apiEnabled(DynmapCommonAPI dynmapCommonAPI) {
                if (Config.isDynmapStructuresEnabled()) {
                    plugin.getLogger().info("Dynmap Structures is disabled until a working fix is found as it cause a huge amoung of lag");
                    //new DynmapStructures(dynmapCommonAPI);
                }
                if (PluginDetector.getWorldGuard() != null && Config.isDynmapWorldGuardEnabled()) {
                    new DynmapWorldGuard(dynmapCommonAPI);
                }

                dynmapAPI = dynmapCommonAPI;
            }
        });
    }
}
