package fr.obelouix.ultimate.profiler;

import fr.obelouix.ultimate.ObelouixUltimate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Spark {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static me.lucko.spark.api.Spark spark;

    public Spark() {
        final RegisteredServiceProvider<me.lucko.spark.api.Spark> sparkProvider = Bukkit.getServicesManager().getRegistration(me.lucko.spark.api.Spark.class);
        if (sparkProvider != null) {
            spark = sparkProvider.getProvider();
            plugin.getComponentLogger().info(Component.text("Using Spark instead of timings...", NamedTextColor.GREEN));
        }
    }

    public me.lucko.spark.api.Spark getSpark() {
        return spark;
    }
}
