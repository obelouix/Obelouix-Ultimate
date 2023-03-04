package fr.obelouix.ultimate.events;

import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.config.PlayerConfig;
import fr.obelouix.ultimate.features.AnvilinfiniteRepair;
import fr.obelouix.ultimate.hud.Coordinates;

import java.util.List;

public class EventManager extends EventsRegistration{

    private static final PlayerConfig.Events playerConfigEvents = new PlayerConfig.Events();

    public static void init() {
        addEvents(List.of(
                new Coordinates(),
                playerConfigEvents
        ));

        if (Config.isAnvilInfiniteRepairEnabled()) {
            addEvent(new AnvilinfiniteRepair());
        }

        if (Config.canReviveCoralBlock())

            registerEvents();
    }

}
