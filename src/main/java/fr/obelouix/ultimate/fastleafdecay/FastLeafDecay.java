package fr.obelouix.ultimate.fastleafdecay;

import fr.obelouix.ultimate.ObelouixUltimate;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastLeafDecay implements Listener {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static final List<BlockFace> faces = Arrays.asList(BlockFace.UP, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.DOWN);
    private final List<Block> decayList = new ArrayList<>();


    @EventHandler(ignoreCancelled = true)
    public void onLeafDecay(LeavesDecayEvent leavesDecayEvent) {
        removeBlock(leavesDecayEvent.getBlock());
    }

    private void removeBlock(Block block) {
        Collections.shuffle(faces);
        faces.forEach(blockFace -> {
            final Leaves leaves = (Leaves) block.getBlockData();
            if (!leaves.isPersistent()) {
                plugin.getServer().getScheduler().runTask(plugin, () -> decay(block.getRelative(blockFace)));
            }
            this.decayList.add(block.getRelative(blockFace));
        });
    }

    private void decay(final Block block) {
        if (!this.decayList.remove(block)) {
            return;
        }
        if (!Tag.LEAVES.isTagged(block.getType())) {
            return;
        }
        final Leaves leaves = (Leaves) block.getBlockData();
        if (leaves.isPersistent()) {
            return;
        }
        if (leaves.getDistance() < 7) {
            return;
        }
        final LeavesDecayEvent event = new LeavesDecayEvent(block);
        plugin.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        block.getWorld().playSound(block.getLocation(), Sound.BLOCK_AZALEA_LEAVES_BREAK, SoundCategory.BLOCKS, 1f, 1.2f);
        block.breakNaturally();
    }
}
