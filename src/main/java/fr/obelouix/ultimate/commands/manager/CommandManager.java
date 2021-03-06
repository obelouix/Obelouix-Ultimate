package fr.obelouix.ultimate.commands.manager;

import cloud.commandframework.CommandTree;
import cloud.commandframework.brigadier.CloudBrigadierManager;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import com.avaje.ebeaninternal.server.deploy.parse.AnnotationParser;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.commands.TimeCommand;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;

public class CommandManager {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static CommandManager instance = null;

    static {
        try {
            instance = new CommandManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AnnotationParser annotationParser;
    private PaperCommandManager<CommandSender> manager;
    private Audience audience;

    public CommandManager() {
        instance = this;

        try {

            final Function<CommandTree<CommandSender>, CommandExecutionCoordinator<CommandSender>> executionCoordinatorFunction =
                    AsynchronousCommandExecutionCoordinator.<CommandSender>newBuilder().build();
            final Function<CommandSender, CommandSender> mapperFunction = Function.identity();

            this.manager = new PaperCommandManager<>(
                    plugin,
                    executionCoordinatorFunction,
                    mapperFunction,
                    mapperFunction
            );

            this.audience = Audience.audience(plugin.getServer());

            // No need to check as we don't support old versions that don't have theses

            // Register Brigadier mappings
            this.manager.registerBrigadier();
            plugin.getComponentLogger().info(Component.text("Registered Brigadier mappings", NamedTextColor.GREEN));
            // Register asynchronous completions
            this.manager.registerAsynchronousCompletions();
            plugin.getComponentLogger().info(Component.text("Registered Async completions", NamedTextColor.GREEN));


            final CloudBrigadierManager<?, ?> brigadierManager = this.manager.brigadierManager();
            if (brigadierManager != null) {
                brigadierManager.setNativeNumberSuggestions(false);
            }


        } catch (Exception e) {
            plugin.getComponentLogger().error(Component.text("Failed to initialize the command manager", NamedTextColor.DARK_RED));
            return;
        }

/*        if (this.queryCapability(CloudBukkitCapabilities.NATIVE_BRIGADIER)) {
            this.registerBrigadier();
            //plugin.getLogger().info("Using Native Brigadier");
            final CloudBrigadierManager<?, ?> brigadierManager = this.brigadierManager();
            if (brigadierManager != null) {
                brigadierManager.setNativeNumberSuggestions(false);
            }
        }

        if (this.queryCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            //plugin.getLogger().info("Registered Async Completions");
            this.registerAsynchronousCompletions();
        }
        List.of(
                new ObelouixUltimateCommand(),
                new CoordsCommand(),
                new AdminCommand()*//*,

                new DayCommand(),
                new MiddayCommand(),
                new NightCommand(),
                new MapImageCommand()*//*
        ).forEach(BaseCommand::register);*/

        //new PingCommand().register();

        List.of(
                new TimeCommand()
                //new PingCommand()
        ).forEach(BaseCommand::register);


/*        new MinecraftExceptionHandler<CommandSender>()
                .withDefaultHandlers()
                .withDecorator(component -> Component.text().append((Component) DEFAULT_SYNTAX_ERROR)
                        .build())
                .apply(this, AudienceProvider.nativeAudience());

        registerCommand(new PluginCommand("plugins"));
        registerCommand(new MaintenanceCommand("maintenance"));*/

    }

    public static CommandManager getInstance() {
        return instance;
    }

    /**
     * allow registering of commands in the Bukkit CommandMap instead of using the plugin.yml
     *
     * @param command the command to register
     */
    private void registerCommand(Command command) {
        //Getting command map from CraftServer
        final Method commandMap;
        try {
            commandMap = plugin.getServer().getClass().getMethod("getCommandMap", (Class<?>[]) null);
            //Invoking the method and getting the returned object (SimpleCommandMap)
            final Object cmdMap = commandMap.invoke(plugin.getServer(), (Object[]) null);
            //getting register method with parameters String and Command from SimpleCommandMap
            final Method register = cmdMap.getClass().getMethod("register", String.class, Command.class);
            //Registering the command provided above
            register.invoke(cmdMap, plugin.getName(), command);
            //All the exceptions thrown above are due to reflection, They will be thrown if any of the above methods
            //and objects used above change location or turn private
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    public PaperCommandManager<CommandSender> getManager() {
        return manager;
    }
}
