package fr.obelouix.ultimate.hook;

import fr.obelouix.ultimate.ObelouixUltimate;
import me.lucko.spark.api.Spark;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

public class PluginHooks {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static @Nullable Plugin dynmap;
    private static @Nullable LuckPerms luckPerms;
    private static @Nullable Spark spark;
    private static @Nullable Plugin worldGuard;

    public static LuckPerms getLuckPerms() {
        return luckPerms;
    }

    public static Spark getSpark() {
        return spark;
    }

    public void setup() {
        worldGuard = plugin.getServer().getPluginManager().getPlugin("WorldGuard");
        dynmap = plugin.getServer().getPluginManager().getPlugin("dynmap");

        if (plugin.getServer().getPluginManager().getPlugin("LuckPerms") != null) {
            luckPerms = Bukkit.getServicesManager().getRegistration(LuckPerms.class).getProvider();
        }

        if (plugin.getServer().getPluginManager().getPlugin("Spark") != null) {
            spark = Bukkit.getServicesManager().getRegistration(Spark.class).getProvider();
        }
    }

    public Plugin getWorldGuard() {
        return worldGuard;
    }

    public Plugin getDynmap() {
        return dynmap;
    }
}
