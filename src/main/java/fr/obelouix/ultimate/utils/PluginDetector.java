package fr.obelouix.ultimate.utils;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.spawn.EssentialsSpawn;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.ess3.api.IEssentials;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NonNls;

public class PluginDetector {

    private static JavaPlugin dynmap;
    private static Essentials essentials;
    private static IEssentials ess;
    private static EssentialsSpawn essentialsSpawn;
    private static RegisteredServiceProvider<LuckPerms> luckPerms;
    private static WorldEditPlugin worldEdit;
    private static WorldGuardPlugin worldGuard;

    public static synchronized void init() {
        if (detectClass("org.dynmap.bukkit.DynmapPlugin"))
            dynmap = (JavaPlugin) Bukkit.getPluginManager().getPlugin("dynmap");
        if (detectClass("com.earth2me.essentials.Essentials")) {
            essentials = Essentials.getPlugin(Essentials.class);
            ess = Essentials.getPlugin(Essentials.class);
            essentialsSpawn = Essentials.getPlugin(EssentialsSpawn.class);
        }
        if (detectClass("net.luckperms.api.LuckPerms"))
            luckPerms = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (detectClass("com.sk89q.worldedit.bukkit.WorldEditPlugin")) worldEdit = WorldEditPlugin.getInstance();
        if (detectClass("com.sk89q.worldguard.WorldGuard"))
            worldGuard = WorldGuardPlugin.getPlugin(WorldGuardPlugin.class);
    }

    /**
     * Check the presence of a {@link Class class} in the classpath
     *
     * @param classPath the {@link Class class} to be checked, must not be null
     * @return {@code true} or {@code false}
     */
    public static boolean detectClass(@NonNls String classPath) {
        try {
            Class.forName(classPath);
            return true;
        } catch (ClassNotFoundException ignored) {
        }
        return false;
    }

    public static JavaPlugin getDynmap() {
        return dynmap;
    }

    public static Essentials getEssentials() {
        return essentials;
    }

    public static IEssentials getIEssentialsAPI() {
        return ess;
    }

    public static EssentialsSpawn getEssentialsSpawn() {
        return essentialsSpawn;
    }

    public static RegisteredServiceProvider<LuckPerms> getLuckPerms() {
        return luckPerms;
    }

    public static WorldEditPlugin getWorldEdit() {
        return worldEdit;
    }

    public static WorldGuardPlugin getWorldGuard() {
        return worldGuard;
    }
}
