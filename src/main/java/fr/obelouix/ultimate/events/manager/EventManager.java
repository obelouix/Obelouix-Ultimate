package fr.obelouix.ultimate.events.manager;

import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.coordinates.Coordinates;
import fr.obelouix.ultimate.data.PlayerData;
import fr.obelouix.ultimate.events.*;
import fr.obelouix.ultimate.fastleafdecay.FastLeafDecay;
import fr.obelouix.ultimate.recipes.RecipeDiscoverer;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventManager extends Events {

    public EventManager() {
        eventsSet = Stream.of(
                new PlayerData(),
                new ServerListEvent(),
                new ServerListEvent(),
                new ReloadDetector(),
                new PlayerConnectionEvent(),
                new Coordinates(),
                new RecipeDiscoverer()
        ).collect(Collectors.toSet());

        if (Config.isWitherBlockDamageDisabled()) {
            eventsSet.add(new WitherBlockDamageEvent());
        }

        if (Config.isAnvilInfiniteRepairEnabled()) {
            eventsSet.add(new AnvilEvents());
        }

        if (Config.isFastLeafDecayEnabled()) {
            eventsSet.add(new FastLeafDecay());
        }

        if (Config.isDisconnectOnHighPing()) eventsSet.add(new PingChecker());

        if (Config.isNightSkipSystemEnabled()) eventsSet.add(new NightSkipEvent());

        registerEvents();

    }

}
