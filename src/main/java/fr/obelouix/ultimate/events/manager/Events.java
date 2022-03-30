package fr.obelouix.ultimate.events.manager;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.config.Config;
import org.bukkit.event.Listener;

import java.util.Set;

/**
 * Abstract class that allow to add events to a {@link Set} and to register all events from this Set
 */
public abstract class Events {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    protected Set<Listener> eventsSet;

    /**
     * register all events from the events set
     */
    protected void registerEvents() {
        eventsSet.forEach(listener -> {
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
            if (Config.isDebugMode())
                plugin.getLogger().info("Registered " + listener.getClass().getSimpleName() + " event");
        });
    }

}
