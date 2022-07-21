package fr.obelouix.ultimate.profiler;

import co.aikar.timings.lib.TimingManager;
import fr.obelouix.ultimate.ObelouixUltimate;

public class Timings {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static TimingManager timingManager;

    public Timings() {
        timingManager = TimingManager.of(plugin);
    }

    public TimingManager getTimingManager() {
        return timingManager;
    }
}
