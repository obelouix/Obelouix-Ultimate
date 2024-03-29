package fr.obelouix.ultimate;

import fr.obelouix.ultimate.I18N.Translator;
import fr.obelouix.ultimate.commands.manager.ObelouixCommandManager;
import fr.obelouix.ultimate.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractPlugin extends JavaPlugin {


    protected static AbstractPlugin plugin;
    private static Config config;

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        config = new Config();
        config.loadConfig();
        Translator.init(this);
        ObelouixCommandManager.init(this);
    }

    public static AbstractPlugin getPlugin() {
        return plugin;
    }

    public static Config getPluginConfig() {
        return config;
    }

}
