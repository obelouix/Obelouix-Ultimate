package fr.obelouix.ultimate;

import fr.obelouix.ultimate.commands.manager.ObelouixCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ObelouixUltimate extends JavaPlugin {

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        ObelouixCommandManager.init(this);

    }
}
