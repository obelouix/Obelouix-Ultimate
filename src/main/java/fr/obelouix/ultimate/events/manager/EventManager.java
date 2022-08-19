package fr.obelouix.ultimate.events.manager;

import fr.obelouix.ultimate.commands.FreezeCommand;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.coordinates.Coordinates;
import fr.obelouix.ultimate.data.PlayerData;
import fr.obelouix.ultimate.events.AnvilEvents;
import fr.obelouix.ultimate.events.PlayerConnectionEvent;
import fr.obelouix.ultimate.events.ReloadDetector;
import fr.obelouix.ultimate.events.WitherBlockDamageEvent;
import fr.obelouix.ultimate.events.blocks.BlocksEvent;
import fr.obelouix.ultimate.fastleafdecay.FastLeafDecay;
import fr.obelouix.ultimate.recipes.RecipeDiscoverer;

import java.util.List;

public class EventManager extends Events {

    public EventManager() {

        addEvents(List.of(new PlayerData(),
                        new ReloadDetector(),
                        new PlayerConnectionEvent(),
                        new Coordinates(),
                        new RecipeDiscoverer(),
                        new FreezeCommand(),
                        new BlocksEvent()//,
                        //new HeadDrop()
                        //new NightSkipEvent()
                )
        );

        if (Config.isWitherBlockDamageDisabled()) {
            addEvent(new WitherBlockDamageEvent());
        }

        if (Config.canInfinitelyRepair()) {
            addEvent(new AnvilEvents());
        }

        if (Config.isFastLeafDecayEnabled()) {
            addEvent(new FastLeafDecay());
        }

/*        if (Config.isDisconnectOnHighPing()) addEvent(new PingChecker());

        if (Config.isNightSkipSystemEnabled()) addEvent(new NightSkipEvent());

        if (PluginDetector.getEssentials() != null) addEvent(new WorldEditWand());*/

        registerEvents();

    }

}
