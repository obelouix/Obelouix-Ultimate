package fr.obelouix.ultimate.scheduler;

import io.papermc.paper.threadedregions.scheduler.EntityScheduler;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public abstract class FoliaScheduler implements Scheduler<ScheduledTask> {

    /**
     * Schedule a task to run asynchronously using the {@link GlobalRegionScheduler}
     *
     * @param plugin   the plugin that will be used to register the task
     * @param runnable the task to run
     */
    @Override
    public void schedule(Plugin plugin, Runnable runnable) {
        Bukkit.getServer().getGlobalRegionScheduler().execute(plugin, runnable);
    }

    /**
     * Schedule a task to run asynchronously using the {@link GlobalRegionScheduler}
     *
     * @param plugin the plugin that will be used to register the task
     * @param task   the task to run
     * @param delay  the delay in ticks before the task is run
     */
    @Override
    public void delayedSchedule(Plugin plugin, Consumer<ScheduledTask> task, int delay) {
        Bukkit.getServer().getGlobalRegionScheduler().runDelayed(plugin, task, delay);
    }


    /**
     * Schedule a task to run asynchronously using the {@link RegionScheduler}
     *
     * @param plugin   the plugin that will be used to register the task
     * @param location the location that will be used to register the task
     * @param runnable the task to run
     */
    public void regionSchedule(Plugin plugin, Location location, Runnable runnable) {
        Bukkit.getServer().getRegionScheduler().execute(plugin, location, runnable);
    }

    /**
     * Schedule a task to run asynchronously using the {@link EntityScheduler}
     *
     * @param plugin                the plugin that will be used to register the task
     * @param entity                the entity that will be used to register the task
     * @param runnable              the task to run
     * @param retiredEntityRunnable the task to run if the entity is no longer valid
     * @param delay                 the delay in ticks before the task is run
     */
    public void entitySchedule(Plugin plugin, Entity entity, Runnable runnable, Runnable retiredEntityRunnable, int delay) {

        final Entity entity1 = Bukkit.getServer().getEntity(entity.getUniqueId());
        if (entity1 == null) {
            plugin.getComponentLogger().error(
                    Component.text(
                            "An entity (UUID: %UUID%) was null when trying to schedule a task with the EntityScheduler",
                            NamedTextColor.DARK_RED
                    ).replaceText(builder -> builder.match("%UUID%").replacement(entity.getUniqueId().toString()))
            );
        } else {
            entity1.getScheduler().execute(plugin, runnable, retiredEntityRunnable, delay);
        }
    }

}
