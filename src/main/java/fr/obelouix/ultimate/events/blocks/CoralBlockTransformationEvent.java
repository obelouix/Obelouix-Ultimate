package fr.obelouix.ultimate.events.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import java.util.stream.Stream;

public class CoralBlockTransformationEvent implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void reviveDeadCoral(ProjectileHitEvent event) {
        final Projectile projectile = event.getEntity();
        final Block hitBlock = event.getHitBlock();

        if (projectile instanceof ThrownPotion potion) {
            final PotionMeta potionMeta = potion.getPotionMeta();
            if (potionMeta.getBasePotionData().getType() == PotionType.REGEN) {
                if (hitBlock == null) event.setCancelled(true);
                else transform(hitBlock);
            }
        }

    }

    private void transform(Block block) {

        Stream.of(
                BlockFace.NORTH_WEST,
                BlockFace.NORTH,
                BlockFace.NORTH_EAST,
                BlockFace.WEST,
                BlockFace.SELF,
                BlockFace.EAST,
                BlockFace.SOUTH_WEST,
                BlockFace.SOUTH,
                BlockFace.SOUTH_EAST
        ).map(block::getRelative).forEach(blockFace -> {
            switch (blockFace.getType()) {
                case DEAD_BRAIN_CORAL_BLOCK -> blockFace.setType(Material.BRAIN_CORAL_BLOCK);
                case DEAD_BUBBLE_CORAL_BLOCK -> blockFace.setType(Material.BUBBLE_CORAL_BLOCK);
                case DEAD_FIRE_CORAL_BLOCK -> blockFace.setType(Material.FIRE_CORAL_BLOCK);
                case DEAD_HORN_CORAL_BLOCK -> blockFace.setType(Material.HORN_CORAL_BLOCK);
                case DEAD_TUBE_CORAL_BLOCK -> blockFace.setType(Material.TUBE_CORAL_BLOCK);
                default -> {
                }
            }
        });
    }

}
