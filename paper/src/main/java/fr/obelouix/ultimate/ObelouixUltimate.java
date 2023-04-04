package fr.obelouix.ultimate;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ObelouixUltimate extends JavaPlugin {

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        // Just in case someone change folia-supported to true in the paper-plugin.yml...
        if (Folia.isFolia()) {
            getComponentLogger().error(
                    Component.text("Your server is running Folia, " +
                                    "please use the Paper version of ObelouixUltimate or some features will not work",
                            NamedTextColor.DARK_RED));
        }
    }
}
