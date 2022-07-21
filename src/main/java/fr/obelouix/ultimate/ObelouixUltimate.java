package fr.obelouix.ultimate;

import co.aikar.timings.lib.TimingManager;
import fr.obelouix.ultimate.api.TranslationAPI;
import fr.obelouix.ultimate.commands.manager.CommandManager;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.data.MultiverseMigrator;
import fr.obelouix.ultimate.data.StorageType;
import fr.obelouix.ultimate.dynmap.DynmapLoader;
import fr.obelouix.ultimate.events.manager.EventManager;
import fr.obelouix.ultimate.i18n.I18N;
import fr.obelouix.ultimate.profiler.Spark;
import fr.obelouix.ultimate.profiler.Timings;
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
    private static Spark spark;
    private static Timings timings;
    private final I18N i18n = new I18N();

    /**
     * Get an instance of {@link ObelouixUltimate ObelouixUltimate} main class
     *
     * @return {@link ObelouixUltimate ObelouixUltimate}
     */
    public static ObelouixUltimate getInstance() {
        return instance;
    }

    /**
     * Get an instance of Spark API
     *
     * @return {@link me.lucko.spark.api.Spark Spark}
     */
    public static Spark getSpark() {
        return spark;
    }

    /**
     * Get an instance of Aikar's {@link TimingManager TimingManager}
     *
     * @return {@link TimingManager TimingManager}
     */
    public static Timings getTimings() {
        return timings;
    }

    @Override
    public void onDisable() {
        i18n.unregister();
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
        i18n.init();
        getComponentLogger().info(Component.translatable("obelouix.teleporting"));
        PluginDetector.init();

        // Use Spark instead of Timings if available
        spark = new Spark();
        if (spark.getSpark() == null) {
            timings = new Timings();
        }

        /* EssentialsMigrator.start();*/

        new MultiverseMigrator();
        WorldManager.loadWorlds();
        if (Config.shouldUnloadEmptyWorlds()) {
            Bukkit.getServer().getScheduler().runTaskTimer(this, WorldManager.unloadEmptyWorlds(), 300, 1200);
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
}
