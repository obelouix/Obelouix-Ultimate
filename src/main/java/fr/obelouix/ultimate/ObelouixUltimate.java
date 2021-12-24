package fr.obelouix.ultimate;

import co.aikar.timings.lib.TimingManager;
import fr.obelouix.ultimate.commands.manager.CommandManager;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.data.DataStorage;
import fr.obelouix.ultimate.entity.EntityRegistry;
import fr.obelouix.ultimate.events.manager.EventManager;
import fr.obelouix.ultimate.utils.LuckPermsUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NonNls;

import java.util.logging.Logger;

public class ObelouixUltimate extends JavaPlugin {

    private static final Logger LOGGER = Logger.getLogger("Obelouix Ultimate");
    private static ObelouixUltimate instance;
    private static TimingManager timingManager;

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
        if (!getClass("com.destroystokyo.paper.PaperConfig")) {
            LOGGER.severe(
                    """
                            **************************************************************\s
                                                        
                              This plugin require Paper or one of its fork (e.g. Purpur)
                              No support will be provided on non paper based servers
                                                        
                            **************************************************************
                            """);

            LOGGER.info("Shutting down the server...");
            // Shutdown the server to force the user to change server software
            getServer().shutdown();
        }
    }

    /**
     * Check the presence of a {@link Class class} in the classpath
     *
     * @param classPath the {@link Class class} to be checked, must not be null
     * @return {@code true} or {@code false}
     */
    public boolean getClass(@NonNls String classPath) {
        try {
            Class.forName(classPath);
            return true;
        } catch (ClassNotFoundException ignored) {
        }
        return false;
    }

    private void checkOfflineMode() {
        if (!this.getServer().getOnlineMode()) {
            LOGGER.warning("""
                                        
                    ****************************************************************
                                        
                      THIS SERVER IS RUNNING IN OFFLINE MODE. IF YOU RUN INTO ANY
                      PROBLEMS DON'T ASK FOR SUPPORT. SUPPORT WILL BE ONLY GIVEN
                      TO SERVERS RUNNING IN ONLINE MODE
                                        
                    ****************************************************************
                    """);
        }
    }

    @Override
    public void onEnable() {
        checkPaperPresence();
        checkOfflineMode();
        instance = this;
        timingManager = TimingManager.of(this);
/*        CompletableFuture.runAsync(() -> {
            Config.loadConfig();
            DataStorage.setupStorage();
            try {
                new CommandManager();
            } catch (ReflectiveOperationException e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
            new EventManager();
        });*/
        Config.loadConfig();
        try {
            new CommandManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //new EntityRegistry();
        new EventManager();
        new EntityRegistry();
        DataStorage.setupStorage();
        LuckPermsUtils.checkForLuckPerms();
    }
}
