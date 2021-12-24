package fr.obelouix.ultimate.commands.manager;

import cloud.commandframework.brigadier.CloudBrigadierManager;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.commands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.function.Function;

public class CommandManager extends PaperCommandManager<CommandSender> {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    public CommandManager() throws Exception {
        super(
                plugin,
                AsynchronousCommandExecutionCoordinator.<CommandSender>newBuilder().build(),
                Function.identity(),
                Function.identity()
        );

        if (this.queryCapability(CloudBukkitCapabilities.NATIVE_BRIGADIER)) {
            this.registerBrigadier();
            plugin.getLogger().info("Using Native Brigadier");
            final CloudBrigadierManager<?, ?> brigadierManager = this.brigadierManager();
            if (brigadierManager != null) {
                brigadierManager.setNativeNumberSuggestions(false);
            }
        }

        if (this.queryCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            plugin.getLogger().info("Registered Async Completions");
            this.registerAsynchronousCompletions();
        }

        new ObelouixUltimateCommand().register();

//        registerCommand(new ObelouixUltimateCommand("obelouixultimate"), plugin);
        registerCommand(new DayCommand("day"), plugin);
        registerCommand(new NightCommand("night"), plugin);
        registerCommand(new MiddayCommand("midday"), plugin);
        registerCommand(new PluginCommand("plugins"), plugin);
        registerCommand(new AdminCommand("admin"), plugin);
        registerCommand(new MaintenanceCommand("maintenance"), plugin);
    }

    /**
     * allow registering of commands in the Bukkit CommandMap instead of using the plugin.yml
     *
     * @param command the command to register
     * @param plugin  the {@link JavaPlugin} where the command belong
     * @throws ReflectiveOperationException
     */
    private void registerCommand(Command command, JavaPlugin plugin) throws ReflectiveOperationException, IOException {
        //Getting command map from CraftServer
        final Method commandMap = plugin.getServer().getClass().getMethod("getCommandMap", (Class<?>[]) null);
        //Invoking the method and getting the returned object (SimpleCommandMap)
        final Object cmdMap = commandMap.invoke(plugin.getServer(), (Object[]) null);
        //getting register method with parameters String and Command from SimpleCommandMap
        final Method register = cmdMap.getClass().getMethod("register", String.class, Command.class);
        //Registering the command provided above
        register.invoke(cmdMap, plugin.getName(), command);
        //All the exceptions thrown above are due to reflection, They will be thrown if any of the above methods
        //and objects used above change location or turn private
    }
}
