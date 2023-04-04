package fr.obelouix.ultimate.commands.manager;

import cloud.commandframework.CommandTree;
import cloud.commandframework.brigadier.CloudBrigadierManager;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Function;

public class ObelouixCommandManager {

    private static final Function<CommandTree<CommandSender>, CommandExecutionCoordinator<CommandSender>> executionCoordinatorFunction =
            AsynchronousCommandExecutionCoordinator.<CommandSender>builder().build();
    private static final Function<CommandSender, CommandSender> mapperFunction = Function.identity();
    private static PaperCommandManager<CommandSender> manager;

    public ObelouixCommandManager() {
    }

    public static void init(JavaPlugin plugin) {
        try {
            manager = new PaperCommandManager<>(
                    plugin,
                    executionCoordinatorFunction,
                    mapperFunction,
                    mapperFunction
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        manager.registerBrigadier();
        manager.registerAsynchronousCompletions();

        final CloudBrigadierManager<?, ?> brigadierManager = manager.brigadierManager();
        if (brigadierManager != null) {
            brigadierManager.setNativeNumberSuggestions(false);
        }

        registerCommands();

    }

    private static void registerCommands() {
        /*List.of(
                new CoordinatesCommand(),
                new OptionsCommand()
        ).forEach(CommandRegistration::register);*/
    }

    public static PaperCommandManager<CommandSender> getManager() {
        return manager;
    }
}
