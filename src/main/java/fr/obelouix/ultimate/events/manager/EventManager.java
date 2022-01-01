package fr.obelouix.ultimate.events.manager;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.coordinates.Coordinates;
import fr.obelouix.ultimate.data.PlayerData;
import fr.obelouix.ultimate.events.AnvilEvents;
import fr.obelouix.ultimate.events.ReloadDetector;
import fr.obelouix.ultimate.events.ServerListEvent;
import fr.obelouix.ultimate.events.WitherBlockDamageEvent;
import org.bukkit.event.Listener;

public class EventManager {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    public EventManager() {
        registerEvent(new PlayerData());
        registerEvent(new ServerListEvent());
/*        if(!plugin.getServer().getPluginManager().isPluginEnabled("essentialschat")){
                registerEvent(new ChatEvent());
    }*/
        registerEvent(new ReloadDetector());
//        registerEvent(new PlayerConnectionEvent());
        if (Config.isWitherBlockDamageDisabled()) {
            registerEvent(new WitherBlockDamageEvent());
        }
        registerEvent(new Coordinates());
        if (Config.isIsAnvilInfiniteRepairEnabled()) {
            registerEvent(new AnvilEvents());
        }

/*        registerEvent(new AdminInventory());
        registerEvent(new CustomGoals());*/
    }

    public void registerEvent(Listener listener){
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
