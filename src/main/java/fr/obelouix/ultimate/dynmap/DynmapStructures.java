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
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;

import java.util.*;

import static org.bukkit.StructureType.*;
import static org.bukkit.block.Biome.*;

public class DynmapStructures implements Listener {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static final StructureType[][] Biomes = new StructureType[Biome.values().length][];
    private static final Map<StructureType, String> labels = new HashMap<>();
    private static final List<String> enabledStructures = new ArrayList<>();
    private static MarkerAPI markerAPI;
    private static MarkerSet markerSet;

    public DynmapStructures() {

    }

    public DynmapStructures(DynmapCommonAPI dynmapCommonAPI) {
        markerAPI = dynmapCommonAPI.getMarkerAPI();
        setupBiomeStructures();
        for (String structureType : StructureType.getStructureTypes().keySet()) {
            labels.put(StructureType.getStructureTypes().get(structureType), structureType);
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
                    }
                }
            }.runTaskAsynchronously(plugin);
        }
    }

    private void setupBiomeStructures() {
        Biomes[OCEAN.ordinal()] = new StructureType[]{BURIED_TREASURE, MINESHAFT, OCEAN_RUIN, SHIPWRECK, STRONGHOLD, RUINED_PORTAL};
        Biomes[PLAINS.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, VILLAGE, PILLAGER_OUTPOST, RUINED_PORTAL};
        Biomes[DESERT.ordinal()] = new StructureType[]{DESERT_PYRAMID, MINESHAFT, STRONGHOLD, VILLAGE, PILLAGER_OUTPOST, RUINED_PORTAL};
        Biomes[WINDSWEPT_HILLS.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, RUINED_PORTAL};
        Biomes[FOREST.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, RUINED_PORTAL};
        Biomes[TAIGA.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, VILLAGE, PILLAGER_OUTPOST, RUINED_PORTAL};
        Biomes[SWAMP.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, SWAMP_HUT, RUINED_PORTAL};
        Biomes[RIVER.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, RUINED_PORTAL};
        Biomes[NETHER_WASTES.ordinal()] = new StructureType[]{NETHER_FORTRESS, BASTION_REMNANT};
        Biomes[THE_END.ordinal()] = new StructureType[]{END_CITY};
        Biomes[FROZEN_OCEAN.ordinal()] = new StructureType[]{BURIED_TREASURE, MINESHAFT, OCEAN_RUIN, SHIPWRECK, STRONGHOLD, RUINED_PORTAL};
        Biomes[FROZEN_RIVER.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, RUINED_PORTAL};
        Biomes[SNOWY_PLAINS.ordinal()] = new StructureType[]{IGLOO, MINESHAFT, STRONGHOLD, VILLAGE, PILLAGER_OUTPOST, RUINED_PORTAL};
        Biomes[MUSHROOM_FIELDS.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, RUINED_PORTAL};
        Biomes[BEACH.ordinal()] = new StructureType[]{BURIED_TREASURE, MINESHAFT, OCEAN_RUIN, SHIPWRECK, STRONGHOLD, RUINED_PORTAL};
        Biomes[JUNGLE.ordinal()] = new StructureType[]{JUNGLE_PYRAMID, MINESHAFT, STRONGHOLD, RUINED_PORTAL};
        Biomes[SPARSE_JUNGLE.ordinal()] = new StructureType[]{JUNGLE_PYRAMID, MINESHAFT, STRONGHOLD, RUINED_PORTAL};
        Biomes[DEEP_OCEAN.ordinal()] = new StructureType[]{BURIED_TREASURE, MINESHAFT, OCEAN_MONUMENT, OCEAN_RUIN, SHIPWRECK, STRONGHOLD};
        Biomes[STONY_SHORE.ordinal()] = new StructureType[]{BURIED_TREASURE, MINESHAFT, OCEAN_RUIN, SHIPWRECK, STRONGHOLD, RUINED_PORTAL};
        Biomes[SNOWY_BEACH.ordinal()] = new StructureType[]{BURIED_TREASURE, MINESHAFT, OCEAN_RUIN, SHIPWRECK, STRONGHOLD, RUINED_PORTAL};
        Biomes[BIRCH_FOREST.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, RUINED_PORTAL};
        Biomes[DARK_FOREST.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, WOODLAND_MANSION, RUINED_PORTAL};
        Biomes[SNOWY_TAIGA.ordinal()] = new StructureType[]{IGLOO, MINESHAFT, STRONGHOLD, VILLAGE, RUINED_PORTAL};
        Biomes[OLD_GROWTH_PINE_TAIGA.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, RUINED_PORTAL};
        Biomes[WINDSWEPT_FOREST.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, RUINED_PORTAL};
        Biomes[SAVANNA.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, VILLAGE, PILLAGER_OUTPOST, RUINED_PORTAL};
        Biomes[SAVANNA_PLATEAU.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, VILLAGE, PILLAGER_OUTPOST, RUINED_PORTAL};
        Biomes[BADLANDS.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, RUINED_PORTAL};
        Biomes[WOODED_BADLANDS.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, RUINED_PORTAL};
        Biomes[SMALL_END_ISLANDS.ordinal()] = new StructureType[]{END_CITY};
        Biomes[END_MIDLANDS.ordinal()] = new StructureType[]{END_CITY};
        Biomes[END_HIGHLANDS.ordinal()] = new StructureType[]{END_CITY};
        Biomes[END_BARRENS.ordinal()] = new StructureType[]{END_CITY};
        Biomes[WARM_OCEAN.ordinal()] = new StructureType[]{BURIED_TREASURE, MINESHAFT, OCEAN_RUIN, SHIPWRECK, STRONGHOLD, RUINED_PORTAL};
        Biomes[LUKEWARM_OCEAN.ordinal()] = new StructureType[]{BURIED_TREASURE, MINESHAFT, OCEAN_RUIN, SHIPWRECK, STRONGHOLD, RUINED_PORTAL};
        Biomes[COLD_OCEAN.ordinal()] = new StructureType[]{BURIED_TREASURE, MINESHAFT, OCEAN_RUIN, SHIPWRECK, STRONGHOLD, RUINED_PORTAL};
        Biomes[DEEP_LUKEWARM_OCEAN.ordinal()] = new StructureType[]{BURIED_TREASURE, MINESHAFT, OCEAN_MONUMENT, OCEAN_RUIN, SHIPWRECK, STRONGHOLD, RUINED_PORTAL};
        Biomes[DEEP_COLD_OCEAN.ordinal()] = new StructureType[]{BURIED_TREASURE, MINESHAFT, OCEAN_MONUMENT, OCEAN_RUIN, SHIPWRECK, STRONGHOLD, RUINED_PORTAL};
        Biomes[DEEP_FROZEN_OCEAN.ordinal()] = new StructureType[]{BURIED_TREASURE, MINESHAFT, OCEAN_MONUMENT, OCEAN_RUIN, SHIPWRECK, STRONGHOLD, RUINED_PORTAL};
        Biomes[THE_VOID.ordinal()] = new StructureType[]{};
        Biomes[SUNFLOWER_PLAINS.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, VILLAGE, RUINED_PORTAL};
        Biomes[WINDSWEPT_GRAVELLY_HILLS.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, RUINED_PORTAL};
        Biomes[FLOWER_FOREST.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, RUINED_PORTAL};
        Biomes[ICE_SPIKES.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, VILLAGE, RUINED_PORTAL};
        Biomes[OLD_GROWTH_BIRCH_FOREST.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, RUINED_PORTAL};
        Biomes[OLD_GROWTH_SPRUCE_TAIGA.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, RUINED_PORTAL};
        Biomes[WINDSWEPT_SAVANNA.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, VILLAGE, PILLAGER_OUTPOST, RUINED_PORTAL};
        Biomes[ERODED_BADLANDS.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, RUINED_PORTAL};
        Biomes[BAMBOO_JUNGLE.ordinal()] = new StructureType[]{JUNGLE_PYRAMID, MINESHAFT, STRONGHOLD, RUINED_PORTAL};
        Biomes[SOUL_SAND_VALLEY.ordinal()] = new StructureType[]{NETHER_FORTRESS, BASTION_REMNANT, NETHER_FOSSIL};
        Biomes[CRIMSON_FOREST.ordinal()] = new StructureType[]{NETHER_FORTRESS, BASTION_REMNANT};
        Biomes[WARPED_FOREST.ordinal()] = new StructureType[]{NETHER_FORTRESS, BASTION_REMNANT};
        Biomes[BASALT_DELTAS.ordinal()] = new StructureType[]{NETHER_FORTRESS};
        Biomes[DRIPSTONE_CAVES.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, VILLAGE};
        Biomes[LUSH_CAVES.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, VILLAGE};
        Biomes[MEADOW.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, VILLAGE, PILLAGER_OUTPOST, RUINED_PORTAL};
        Biomes[GROVE.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, PILLAGER_OUTPOST, RUINED_PORTAL};
        Biomes[SNOWY_SLOPES.ordinal()] = new StructureType[]{IGLOO, MINESHAFT, STRONGHOLD, PILLAGER_OUTPOST, RUINED_PORTAL};
        Biomes[FROZEN_PEAKS.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, PILLAGER_OUTPOST, RUINED_PORTAL};
        Biomes[JAGGED_PEAKS.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, PILLAGER_OUTPOST, RUINED_PORTAL};
        Biomes[STONY_PEAKS.ordinal()] = new StructureType[]{MINESHAFT, STRONGHOLD, PILLAGER_OUTPOST, RUINED_PORTAL};

        Config.getDynmapStructureMap().forEach((s, stringBooleanHashMap) -> {
            if (stringBooleanHashMap.get(s.replaceAll("_", " "))) {
                enabledStructures.add(s);
            }
        });

        plugin.getLogger().info(Arrays.toString(enabledStructures.toArray()));

        if (!enabledStructures.isEmpty()) {
            markerSet = markerAPI.createMarkerSet("structures", "Structures", null, true);
            markerSet.setHideByDefault(false);
        }

    }

}
