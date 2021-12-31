package fr.obelouix.ultimate.dynmap;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.worldguard.WorldGuard;
import org.bukkit.event.Listener;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapCommonAPIListener;
import org.dynmap.markers.MarkerSet;

public class DynmapLoader implements Listener {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static boolean isDynmapPresent;
    private static DynmapCommonAPI dynmapAPI;
    private MarkerSet markerSet;

    public static boolean isIsDynmapPresent() {
        return isDynmapPresent;
    }

    public static DynmapCommonAPI getDynmapAPI() {
        return dynmapAPI;
    }

    public void checkForDynmap() {
        if (plugin.getClass("org.dynmap.bukkit.DynmapPlugin")) {
            isDynmapPresent = true;
            plugin.getLogger().info("Found Dynmap");
            DynmapCommonAPIListener.register(new DynmapCommonAPIListener() {
                @Override
                public void apiEnabled(DynmapCommonAPI dynmapCommonAPI) {
                    if (Config.isDynmapStructuresEnabled()) {
                        new DynmapStructures(dynmapCommonAPI);
                    }
                    if (WorldGuard.isIsWorldGuardPresent() && Config.isDynmapWorldGuardEnabled()) {
                        new DynmapWorldGuard(dynmapCommonAPI);
                    }

                    dynmapAPI = dynmapCommonAPI;
                }
            });
        }
    }
}
