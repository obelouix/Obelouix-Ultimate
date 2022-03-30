package fr.obelouix.ultimate.events.manager;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.config.Config;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract class that allow to add events to a {@link Set} and to register all events from this Set
 */
public abstract class Events {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private final Set<Listener> eventsSet = new HashSet<>();

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

    /**
     * add a list of event into the set that will register all of this
     *
     * @param eventList a list of events
     */
    public void addEvents(Collection<Listener> eventList) {
        eventsSet.addAll(eventList);
    }

    /**
     * add an event into the set that will register it
     *
     * @param listener an event
     */
    public void addEvent(Listener listener) {
        eventsSet.add(listener);
    }


}
