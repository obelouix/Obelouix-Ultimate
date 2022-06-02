package fr.obelouix.ultimate.tweaks;

import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.events.manager.Events;
import org.spigotmc.SpigotConfig;

public class TweaksManager extends Events {

    public TweaksManager() {

        final double expMergeRadius = SpigotConfig.config.getDouble("world-settings.default.merge-radius.exp");
        if (expMergeRadius > 0 && Config.giveEnderDragonExp()) {
            addEvent(new EnderDragonTweaks());
        }

        registerEvents();

    }
}
