package fr.obelouix.ultimate;

import fr.obelouix.ultimate.I18N.Translator;
import fr.obelouix.ultimate.commands.manager.ObelouixCommandManager;
import fr.obelouix.ultimate.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class ObelouixUltimate extends JavaPlugin {

    private static ObelouixUltimate plugin;
    private static Config config;

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static ObelouixUltimate getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        if (plugin == null) plugin = this;
        config = new Config();
        config.loadConfig();
        Translator.init(this);
        ObelouixCommandManager.init(this);
    }
}
