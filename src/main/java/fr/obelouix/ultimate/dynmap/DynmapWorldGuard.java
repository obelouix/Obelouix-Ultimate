package fr.obelouix.ultimate.dynmap;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.config.Config;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.dynmap.DynmapCommonAPI;

import java.util.Locale;

public class DynmapWorldGuard implements Listener {

    private final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    public DynmapWorldGuard(DynmapCommonAPI dynmapCommonAPI) {
        if (Config.isDynmapWorldGuardEnabled()) {
//            plugin.getServer().getPluginManager().registerEvents(this, plugin);
            dynmapCommonAPI.getMarkerAPI().createMarkerSet(Config.getDynmapWorldGuardLayer().toLowerCase(Locale.ROOT),
                    Config.getDynmapWorldGuardLayer(), null, true);
            runnable();
        }
    }

    public void runnable() {
        new BukkitRunnable() {
            @Override
            public void run() {

            }
        }.runTaskTimer(plugin, 0, 1200);
    }

}
