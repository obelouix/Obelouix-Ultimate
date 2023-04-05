package fr.obelouix.ultimate.scheduler;

import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public interface Scheduler<T> {

    void schedule(Plugin plugin, Runnable runnable);

    void delayedSchedule(Plugin plugin, Consumer<T> runnable, int delay);

    void delayedAsyncSchedule(Plugin plugin, Consumer<T> runnable, int delay);
}
