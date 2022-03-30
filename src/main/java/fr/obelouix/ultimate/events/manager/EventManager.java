package fr.obelouix.ultimate.events.manager;

import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.coordinates.Coordinates;
import fr.obelouix.ultimate.data.PlayerData;
import fr.obelouix.ultimate.events.*;
import fr.obelouix.ultimate.fastleafdecay.FastLeafDecay;
import fr.obelouix.ultimate.recipes.RecipeDiscoverer;

import java.util.List;

public class EventManager extends Events {

    public EventManager() {

        addEvents(List.of(new PlayerData(),
                new ServerListEvent(),
                new ServerListEvent(),
                new ReloadDetector(),
                new PlayerConnectionEvent(),
                new Coordinates(),
                new RecipeDiscoverer()));

        if (Config.isWitherBlockDamageDisabled()) {
            addEvent(new WitherBlockDamageEvent());
        }

        if (Config.isAnvilInfiniteRepairEnabled()) {
            addEvent(new AnvilEvents());
        }

        if (Config.isFastLeafDecayEnabled()) {
            addEvent(new FastLeafDecay());
        }

        if (Config.isDisconnectOnHighPing()) addEvent(new PingChecker());

        if (Config.isNightSkipSystemEnabled()) addEvent(new NightSkipEvent());

        registerEvents();

    }

}
