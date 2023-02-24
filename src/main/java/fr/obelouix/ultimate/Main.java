package fr.obelouix.ultimate;

import fr.obelouix.ultimate.I18N.PluginTranslator;
import fr.obelouix.ultimate.commands.manager.ObelouixCommandManager;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.events.EventManager;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static ComponentLogger logger;
    private static Main plugin;

    @Override
    public void onEnable() {

        if (plugin == null) plugin = this;
        logger = this.getComponentLogger();

        Config.loadConfig();
        PluginTranslator.init();
        EventManager.init();
        ObelouixCommandManager.init();

    }

    public static Main getPlugin() {
        return plugin;
    }

}