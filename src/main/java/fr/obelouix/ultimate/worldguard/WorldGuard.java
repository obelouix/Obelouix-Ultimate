package fr.obelouix.ultimate.worldguard;

import fr.obelouix.ultimate.ObelouixUltimate;

public class WorldGuard {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static boolean isWorldGuardPresent = false;

    public static boolean isIsWorldGuardPresent() {
        return isWorldGuardPresent;
    }

    public void checkForWorldGuard() {
        if (plugin.getClass("com.sk89q.worldguard.WorldGuardPlugin")) {
            isWorldGuardPresent = true;
            plugin.getLogger().info("Found WorldGuard");
        }
    }
}
