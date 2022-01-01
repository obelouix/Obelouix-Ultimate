package fr.obelouix.ultimate.dynmap;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.config.Config;
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
                    new DynmapStructures(dynmapCommonAPI);
                }
                if (plugin.isWorldGuardPresent() && Config.isDynmapWorldGuardEnabled()) {
                    new DynmapWorldGuard(dynmapCommonAPI);
                }

                dynmapAPI = dynmapCommonAPI;
            }
        });
    }
}
