package fr.obelouix.ultimate.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class PaperScheduler implements Scheduler<BukkitTask> {

    /**
     * Schedule a task to run synchronously using the {@link Bukkit#getScheduler() Bukkit Scheduler}
     *
     * @param plugin   the plugin that will be used to register the task
     * @param runnable the task to run
     */
    @Override
    public void schedule(Plugin plugin, Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin, runnable);
    }

    /**
     * Schedule a task to run synchronously using the {@link Bukkit#getScheduler() Bukkit Scheduler}
     *
     * @param plugin   the plugin that will be used to register the task
     * @param runnable the task to run
     * @param delay    the delay in ticks before the task is run
     */
    @Override
    public void delayedSchedule(Plugin plugin, Consumer<BukkitTask> runnable, int delay) {
        Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
    }

    /**
     * Schedule a task to run asynchronously using the {@link Bukkit#getScheduler() Bukkit Scheduler}
     *
     * @param plugin   the plugin that will be used to register the task
     * @param runnable the task to run
     * @param delay    the delay in ticks before the task is run
     */
    @Override
    public void delayedAsyncSchedule(Plugin plugin, Consumer<BukkitTask> runnable, int delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
    }

    /**
     * Schedule a task to run asynchronously using the {@link Bukkit#getScheduler() Bukkit Scheduler}
     *
     * @param plugin   the plugin that will be used to register the task
     * @param runnable the task to run
     */
    public void scheduleAsync(Plugin plugin, Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }

}
