package fr.obelouix.ultimate.worldguard;

import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import fr.obelouix.ultimate.ObelouixUltimate;

public class WorldGuard {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static final com.sk89q.worldguard.WorldGuard worldGuard = com.sk89q.worldguard.WorldGuard.getInstance();

    public WorldGuard() {
    }

    public static com.sk89q.worldguard.WorldGuard getWorldGuard() {
        return worldGuard;
    }

    public static WorldGuardPlatform getWorldGuardPlatform() {
        return worldGuard.getPlatform();
    }
}
