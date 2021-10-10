package fr.obelouix.ultimate.events.manager;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.data.PlayerData;
import fr.obelouix.ultimate.events.ChatEvent;
import fr.obelouix.ultimate.events.ReloadDetector;
import org.bukkit.event.Listener;

public class EventManager {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    public EventManager() {
        registerEvent(new PlayerData());
        registerEvent(new ChatEvent());
        registerEvent(new ReloadDetector());
    }

    public void registerEvent(Listener listener){
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
