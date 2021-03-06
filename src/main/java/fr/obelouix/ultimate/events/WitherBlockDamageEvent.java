package fr.obelouix.ultimate.events;

import fr.obelouix.ultimate.config.Config;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class WitherBlockDamageEvent implements Listener {

    @EventHandler
    public void onWitherExplosion(EntityExplodeEvent event) {
        final EntityType entityType = event.getEntityType();
        final Entity entity = event.getEntity();

        if (entityType == EntityType.WITHER) {
            event.blockList().removeAll(event.blockList());
        }

        if (entity instanceof WitherSkull) {
            if (Config.showWitherSkullExplosionsParticles()) {
                final World world = event.getLocation().getWorld();
                final Location location = event.getLocation();
                world.playEffect(location, Effect.SMOKE, 50);
            }
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onWitherDestroyBlocks(EntityChangeBlockEvent event) {
        final EntityType type = event.getEntity().getType();
        if (type == EntityType.WITHER)
            event.setCancelled(true);
    }
}
