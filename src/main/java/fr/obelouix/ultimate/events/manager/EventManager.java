package fr.obelouix.ultimate.events.manager;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.data.PlayerData;
import fr.obelouix.ultimate.events.*;
import fr.obelouix.ultimate.gui.AdminInventory;
import org.bukkit.event.Listener;

public class EventManager {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    public EventManager() {
        registerEvent(new PlayerData());
        registerEvent(new ServerListEvent());
        registerEvent(new ChatEvent());
        registerEvent(new ReloadDetector());
        registerEvent(new PlayerConnectionEvent());
        if (Config.isWitherBlockDamageDisabled()) {
            registerEvent(new WitherBlockDamageEvent());
        }
        registerEvent(new AdminInventory());
    }

    public void registerEvent(Listener listener){
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
