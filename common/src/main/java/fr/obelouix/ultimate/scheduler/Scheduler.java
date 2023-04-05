package fr.obelouix.ultimate.scheduler;

import org.bukkit.plugin.Plugin;

public interface Scheduler {

    void schedule(Plugin plugin, Runnable runnable);

}
