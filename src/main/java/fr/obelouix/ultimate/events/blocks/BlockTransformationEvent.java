package fr.obelouix.ultimate.events.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

public class BlockTransformationEvent implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void reviveDeadCoral(ProjectileHitEvent event) {
        final Projectile projectile = event.getEntity();

        if (projectile instanceof ThrownPotion potion) {
            final PotionMeta potionMeta = potion.getPotionMeta();
            if (potionMeta.getBasePotionData().getType() != PotionType.REGEN) event.setCancelled(true);
            if (event.getHitBlock() == null) event.setCancelled(true);

            final Block hitBlock = event.getHitBlock();
            switch (hitBlock.getType()) {
                case DEAD_BRAIN_CORAL_BLOCK -> hitBlock.setType(Material.BRAIN_CORAL_BLOCK);
                case DEAD_BUBBLE_CORAL_BLOCK -> hitBlock.setType(Material.BUBBLE_CORAL_BLOCK);
                case DEAD_FIRE_CORAL_BLOCK -> hitBlock.setType(Material.FIRE_CORAL_BLOCK);
                case DEAD_HORN_CORAL_BLOCK -> hitBlock.setType(Material.HORN_CORAL_BLOCK);
                case DEAD_TUBE_CORAL_BLOCK -> hitBlock.setType(Material.TUBE_CORAL_BLOCK);
                default -> event.setCancelled(true);
            }
        } else event.setCancelled(true);

    }

}
