package fr.obelouix.ultimate.events;

import fr.obelouix.ultimate.entity.CustomVillager;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CustomGoals implements Listener {

    @EventHandler
    public void addGoal(EntityTargetEvent event) {
        org.bukkit.entity.@NotNull EntityType entityType = event.getEntityType();
        if (event.getEntityType() == EntityType.VILLAGER && Objects.requireNonNull(event.getTarget()).getType() == EntityType.PLAYER) {
            new CustomVillager(net.minecraft.world.entity.EntityType.VILLAGER, ((CraftWorld) event.getEntity().getWorld()).getHandle());
        }
    }

}
