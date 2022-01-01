package fr.obelouix.ultimate.worldguard;

import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;

public class WorldGuard {

    private static final com.sk89q.worldguard.WorldGuard worldGuard = com.sk89q.worldguard.WorldGuard.getInstance();

    public static com.sk89q.worldguard.WorldGuard getWorldGuard() {
        return worldGuard;
    }

    public static WorldGuardPlatform getWorldGuardPlatform() {
        return worldGuard.getPlatform();
    }
}
