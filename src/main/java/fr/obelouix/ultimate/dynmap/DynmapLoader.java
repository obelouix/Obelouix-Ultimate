package fr.obelouix.ultimate.dynmap;

import fr.obelouix.ultimate.ObelouixUltimate;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapCommonAPIListener;

public class DynmapLoader {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static boolean isDynmapPresent;

    public static void checkForDynmap() {
        if (plugin.getClass("org.dynmap.bukkit.DynmapPlugin")) {
            isDynmapPresent = true;
            plugin.getLogger().info("Found Dynmap");
            DynmapCommonAPIListener.register(new DynmapCommonAPIListener() {
                @Override
                public void apiEnabled(DynmapCommonAPI dynmapCommonAPI) {
                    new DynmapStructures(dynmapCommonAPI);
                }
            });
        }
    }

    public static boolean isIsDynmapPresent() {
        return isDynmapPresent;
    }
}
