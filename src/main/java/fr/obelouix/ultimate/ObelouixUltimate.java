package fr.obelouix.ultimate;

import co.aikar.timings.lib.TimingManager;
import fr.obelouix.ultimate.commands.manager.CommandManager;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.events.manager.EventManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NonNls;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class ObelouixUltimate extends JavaPlugin {

    private static final Logger LOGGER = Logger.getLogger("Obelouix Ultimate");
    private static ObelouixUltimate instance;
    private static TimingManager timingManager;

    /**
     * Get an instance of {@link ObelouixUltimate ObelouixUltimate} main class
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

    @Override
    public void onEnable() {
        checkPaperPresence();
        instance = this;
        timingManager = TimingManager.of(this);
        CompletableFuture.runAsync(() -> {
            Config.loadConfig();
            try {
                new CommandManager();
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }

            new EventManager();
        });
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
    private boolean getClass(@NonNls String classPath) {
        try {
            Class.forName(classPath);
            return true;
        } catch (ClassNotFoundException ignored) {
        }
        return false;
    }
}
