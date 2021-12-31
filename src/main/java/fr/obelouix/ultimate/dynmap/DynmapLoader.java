package fr.obelouix.ultimate.dynmap;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.config.Config;
import org.bukkit.Location;
import org.bukkit.StructureType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapCommonAPIListener;
import org.dynmap.markers.MarkerSet;

import static fr.obelouix.ultimate.dynmap.DynmapStructures.Biomes;

public class DynmapLoader implements Listener {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static boolean isDynmapPresent;
    private static DynmapCommonAPI dynmapAPI;
    private MarkerSet markerSet;

    public static boolean isIsDynmapPresent() {
        return isDynmapPresent;
    }

    public void checkForDynmap() {
        if (plugin.getClass("org.dynmap.bukkit.DynmapPlugin")) {
            isDynmapPresent = true;
            plugin.getLogger().info("Found Dynmap");
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
            DynmapCommonAPIListener.register(new DynmapCommonAPIListener() {
                @Override
                public void apiEnabled(DynmapCommonAPI dynmapCommonAPI) {
                    new DynmapStructures(dynmapCommonAPI);
                    dynmapAPI = dynmapCommonAPI;
                }
            });
        }
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {

        if (event.getWorld().canGenerateStructures()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    final Location location = new Location(event.getWorld(), event.getChunk().getX() << 4, 64, event.getChunk().getZ() << 4);
                    final World world = location.getWorld();
                    if (world != null) {
                        Biome biome = world.getBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ());
                        for (StructureType structureType : Biomes[biome.ordinal()]) {
                            boolean show = Config.getRoot().node("dynmap", "structures", structureType.getName(), "show").getBoolean();
                            if (show) {
                                Location structureLocation = location.getWorld().locateNearestStructure(location, structureType, 1, false);
                                if (structureLocation != null) {
                                    dynmapAPI.getMarkerAPI().getMarkerSet("structures").createMarker(
                                            structureType.getName() + "," + structureLocation.getBlockX() + "," + structureLocation.getBlockZ(),
                                            Config.getRoot().node("dynmap", "structures", structureType.getName(), "displayname").getString(),
                                            world.getName(),
                                            structureLocation.getBlockX(),
                                            64,
                                            structureLocation.getBlockZ(),
                                            dynmapAPI.getMarkerAPI().getMarkerIcon(structureType.getName()), true);
                                }
                            }
                        }
                    }
                }
            }.runTaskTimer(plugin, 100, 600);
        }
    }


}
