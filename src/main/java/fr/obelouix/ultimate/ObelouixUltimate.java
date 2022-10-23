package fr.obelouix.ultimate;

import co.aikar.timings.lib.TimingManager;
import fr.obelouix.ultimate.api.TranslationAPI;
import fr.obelouix.ultimate.commands.manager.CommandManager;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.data.StorageType;
import fr.obelouix.ultimate.events.manager.EventManager;
import fr.obelouix.ultimate.hook.PluginHooks;
import fr.obelouix.ultimate.i18n.I18N;
import fr.obelouix.ultimate.profiler.Spark;
import fr.obelouix.ultimate.profiler.Timings;
import fr.obelouix.ultimate.recipes.CustomCraftingTableRecipes;
import fr.obelouix.ultimate.recipes.CustomFurnaceRecipes;
import fr.obelouix.ultimate.tweaks.TweaksManager;
import fr.obelouix.ultimate.utils.PluginDetector;
import fr.obelouix.ultimate.utils.ServerType;
import fr.obelouix.ultimate.utils.Updater;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ObelouixUltimate extends JavaPlugin {

    private static ObelouixUltimate instance;
    private static TranslationAPI translationAPI;
    private static TimingManager timingManager;
    private static Spark spark;
    private static Timings timings;
    private final I18N i18n = new I18N();
    private PluginHooks hooks;

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

    @Override
    public void onEnable() {

        if (!ServerType.isPaperServer()) {
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

        checkOfflineMode();
        instance = this;
        i18n.init();
        PluginDetector.init();

        hooks = new PluginHooks();
        hooks.setup();

        // Use Spark instead of Timings if available
        if (PluginHooks.getSpark() == null) {
            timings = new Timings();
        }


        //LuckPermsUtils.checkForLuckPerms();
/*        if (PluginDetector.getWorldGuard() != null) {
            this.getComponentLogger().info("Found WorldGuard");
//            new WorldGuard();
        }
        if (PluginDetector.getDynmap() != null) {
            new DynmapLoader().init();
        }*/
        Config.loadConfig();

        try {
            StorageType.setup();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        CommandManager.getInstance();
        new EventManager();
        new TweaksManager();
        /*new Blocks();
        List.of(new CompressedCobblestone(), new Cobblestone()).forEach(RecipeRegistry::addRecipe);*/
        new CustomFurnaceRecipes();
        new CustomCraftingTableRecipes();
        new Updater();

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


    public TranslationAPI getTranslationAPI() {
        return translationAPI;
    }

    public I18N getI18n() {
        return i18n;
    }

    public PluginHooks getHooks() {
        return hooks;
    }
}
