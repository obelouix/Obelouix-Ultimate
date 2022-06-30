package fr.obelouix.ultimate;

import co.aikar.timings.lib.TimingManager;
import fr.obelouix.ultimate.api.TranslationAPI;
import fr.obelouix.ultimate.commands.manager.CommandManager;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.data.MultiverseMigrator;
import fr.obelouix.ultimate.data.StorageType;
import fr.obelouix.ultimate.dynmap.DynmapLoader;
import fr.obelouix.ultimate.events.manager.EventManager;
import fr.obelouix.ultimate.recipes.CustomCraftingTableRecipes;
import fr.obelouix.ultimate.recipes.CustomFurnaceRecipes;
import fr.obelouix.ultimate.tweaks.TweaksManager;
import fr.obelouix.ultimate.utils.LuckPermsUtils;
import fr.obelouix.ultimate.utils.PluginDetector;
import fr.obelouix.ultimate.utils.Updater;
import fr.obelouix.ultimate.worlds.WorldManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ObelouixUltimate extends JavaPlugin {

    private static ObelouixUltimate instance;
    private static TranslationAPI translationAPI;
    private static TimingManager timingManager;

    private CommandManager commandManager;

    /**
     * Get an instance of {@link ObelouixUltimate ObelouixUltimate} main class
     *
     * @return {@link ObelouixUltimate ObelouixUltimate}
     */
    public static ObelouixUltimate getInstance() {
        return instance;
    }

    /**
     * Get an instance of Aikar's {@link TimingManager TimingManager}
     *
     * @return {@link TimingManager TimingManager}
     */
    public static TimingManager getTimingManager() {
        return timingManager;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    /**
     * Check if the server is a paper based server
     * if not, the server will be shutdown
     */
    private void checkPaperPresence() {
        if (!PluginDetector.detectClass("com.destroystokyo.paper.PaperConfig")) {
            this.getLogger().severe(
                    """
                            **************************************************************\s
                                                        
                              This plugin require Paper or one of its fork (e.g. Purpur)
                              No support will be provided on non paper based servers
                                                        
                            **************************************************************
                            """);

            this.getComponentLogger().info("Shutting down the server...");
            // Shutdown the server to force the user to change server software
            getServer().shutdown();
        }
    }

 /*   private boolean isServerUtilsPresent() {
        return getClass("net.frankheijden.serverutils.bukkit.ServerUtils");
    }*/

    private void checkOfflineMode() {
        if (!this.getServer().getOnlineMode()) {
            this.getComponentLogger().warn(Component.text("""
                                        
                    ****************************************************************
                                        
                      THIS SERVER IS RUNNING IN OFFLINE MODE. IF YOU RUN INTO ANY
                      PROBLEMS DON'T ASK FOR SUPPORT. SUPPORT WILL BE ONLY GIVEN
                      TO SERVERS RUNNING IN ONLINE MODE
                                        
                    ****************************************************************
                    """, NamedTextColor.DARK_RED));
        }
    }

    @Override
    public void onEnable() {
        checkPaperPresence();
        checkOfflineMode();
        instance = this;
        translationAPI = new TranslationAPI();
        translationAPI.setResourceBundleBaseName("lang_");
        PluginDetector.init();
        timingManager = TimingManager.of(this);

        new MultiverseMigrator();
        WorldManager.loadWorlds();
        if (Config.shouldUnloadEmptyWorlds()) {
            Bukkit.getServer().getScheduler().runTaskTimer(this, WorldManager.unloadEmptyWorlds(), 300, 1200);
        }

        if (Config.isDisconnectOnHighPing() && Config.getMaxPing() < 200) {
            this.getComponentLogger().warn("""
                    Minimum ping for kicking a player is too low (must be 200 or higher)
                    Using 200ms as value until you change it in the config file
                    """);

            Config.setMaxPing(200);
        }

        LuckPermsUtils.checkForLuckPerms();
        if (PluginDetector.getWorldGuard() != null) {
            this.getComponentLogger().info("Found WorldGuard");
//            new WorldGuard();
        }
        if (PluginDetector.getDynmap() != null) {
            new DynmapLoader().init();
        }
        Config.loadConfig();

        try {
            StorageType.setup();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        CommandManager.getInstance();
        new EventManager();
        new TweaksManager();
        new CustomFurnaceRecipes();
        new CustomCraftingTableRecipes();
        new Updater();
    }

    public TranslationAPI getTranslationAPI() {
        return translationAPI;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

}
