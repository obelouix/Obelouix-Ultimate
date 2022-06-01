package fr.obelouix.ultimate.plugins.worldedit;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class WorldRegenerator {

    private static final WorldEdit WORLDEDIT = WorldEdit.getInstance();

    public void RegenBelowZero(Player player) {

        Chunk chunk = player.getChunk();

        LocalSession session = new LocalSession();
        BlockVector3 min = BlockVector3.at(chunk.getBlock(0, -64, 0).getX(), -64, chunk.getBlock(15, -64, 15).getZ());
        BlockVector3 max = BlockVector3.at(chunk.getBlock(0, -64, 0).getX(), -64, chunk.getBlock(15, -64, 15).getZ());
        Region region = new CuboidRegion(BukkitAdapter.adapt(player.getWorld()), min, max);
        region.getWorld().regenerate(region, new BukkitWorld(player.getWorld()));
/*        for (Chunk chunk: Bukkit.getWorld(player.getWorld().getKey()).getLoadedChunks()){
            // 432000 ticks = 6 hours
            if(chunk.getInhabitedTime() <= 432000){
                LocalSession session = new LocalSession();
                BlockVector3 min = BlockVector3.at(chunk.getBlock(0, -64, 0).getX(), -64, chunk.getBlock(15, -64, 15).getZ());
                BlockVector3 max =  BlockVector3.at(chunk.getBlock(0, -64, 0).getX(), -64, chunk.getBlock(15, -64, 15).getZ());
                Region region = new CuboidRegion(BukkitAdapter.adapt(player.getWorld()),min, max);
                region.getWorld().regenerate(region, BukkitAdapter.adapt(player.getWorld()));
                break;
            }
        }*/
    }

}
