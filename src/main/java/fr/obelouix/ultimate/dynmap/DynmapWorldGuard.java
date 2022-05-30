package fr.obelouix.ultimate.dynmap;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.worldguard.WorldGuard;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.dynmap.DynmapCommonAPI;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class DynmapWorldGuard {

    private final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    public DynmapWorldGuard(DynmapCommonAPI dynmapCommonAPI) {
        if (Config.isDynmapWorldGuardEnabled()) {
//            plugin.getServer().getPluginManager().registerEvents(this, plugin);
            dynmapCommonAPI.getMarkerAPI().createMarkerSet(Config.getDynmapWorldGuardLayer().toLowerCase(Locale.ROOT),
                    Config.getDynmapWorldGuardLayer(), null, true);
            runnable(dynmapCommonAPI);
        }
    }

    public void runnable(DynmapCommonAPI dynmapCommonAPI) {
        new BukkitRunnable() {
            @Override
            public void run() {

                // Remove WorldGuard area markers if their WorldGuard region no longer exist
                Bukkit.getWorlds().forEach(world -> {
                    final World world1 = BukkitAdapter.adapt(world);
                    final Map<String, ProtectedRegion> region = Objects.requireNonNull(WorldGuard.getWorldGuardPlatform().getRegionContainer().get(world1)).getRegions();
                    region.keySet().forEach(regionID -> {
                        final double[] x = {
                                Objects.requireNonNull(Objects.requireNonNull(WorldGuard.getWorldGuardPlatform().getRegionContainer().get(world1)).getRegion(regionID)).getMinimumPoint().getBlockX(),
                                Objects.requireNonNull(Objects.requireNonNull(WorldGuard.getWorldGuardPlatform().getRegionContainer().get(world1)).getRegion(regionID)).getMaximumPoint().getBlockX()
                        };
                        final double[] z = {
                                Objects.requireNonNull(Objects.requireNonNull(WorldGuard.getWorldGuardPlatform().getRegionContainer().get(world1)).getRegion(regionID)).getMinimumPoint().getBlockZ(),
                                Objects.requireNonNull(Objects.requireNonNull(WorldGuard.getWorldGuardPlatform().getRegionContainer().get(world1)).getRegion(regionID)).getMaximumPoint().getBlockZ()
                        };
                        dynmapCommonAPI.getMarkerAPI().getMarkerSet(Config.getDynmapWorldGuardLayer().toLowerCase(Locale.ROOT))
                                .getAreaMarkers().forEach(areaMarker -> {
                                    if (areaMarker.getUniqueMarkerID().startsWith("wg_") && !region.containsKey(areaMarker.getUniqueMarkerID().substring(3))) {
                                        areaMarker.deleteMarker();
                                    }
                                });
                        if (dynmapCommonAPI.getMarkerAPI().getMarkerSet(Config.getDynmapWorldGuardLayer().toLowerCase(Locale.ROOT)).findAreaMarker("wg_" + regionID) == null) {
                            dynmapCommonAPI.getMarkerAPI().getMarkerSet(Config.getDynmapWorldGuardLayer().toLowerCase(Locale.ROOT))
                                    .createAreaMarker("wg_" + regionID, regionID, false, world.getName(), x, z, false);
                        }
                    });
                });
            }
        }.runTaskTimer(plugin, 0, 6000);
    }

}
