package fr.obelouix.ultimate.commands.manager;

import cloud.commandframework.brigadier.CloudBrigadierManager;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.exceptions.InvalidSyntaxException;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.minecraft.extras.AudienceProvider;
import cloud.commandframework.minecraft.extras.MinecraftExceptionHandler;
import cloud.commandframework.paper.PaperCommandManager;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.commands.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;

public class CommandManager extends PaperCommandManager<CommandSender> {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static CommandManager instance = null;
    private static final Function<Exception, Component> DEFAULT_SYNTAX_ERROR =
            e -> Component.text(String.format("/%s", ((InvalidSyntaxException) e).getCorrectSyntax()),
                    NamedTextColor.GRAY);

    static {
        try {
            instance = new CommandManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommandManager() throws Exception {
        super(
                plugin,
                AsynchronousCommandExecutionCoordinator.<CommandSender>newBuilder().build(),
                Function.identity(),
                Function.identity()
        );
        instance = this;

        if (this.queryCapability(CloudBukkitCapabilities.NATIVE_BRIGADIER)) {
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
                new AdminCommand(),
                new DayCommand(),
                new MiddayCommand()
        ).forEach(BaseCommand::register);


        new MinecraftExceptionHandler<CommandSender>()
                .withDefaultHandlers()
                .withDecorator(component -> Component.text().append((Component) DEFAULT_SYNTAX_ERROR)
                        .build())
                .apply(this, AudienceProvider.nativeAudience());

//        registerCommand(new ObelouixUltimateCommand("obelouixultimate"), plugin);
        //registerCommand(new DayCommand("day"), plugin);
        //   registerCommand(new NightCommand("night"), plugin);
        //   registerCommand(new MiddayCommand("midday"), plugin);
        registerCommand(new PluginCommand("plugins"), plugin);
        registerCommand(new MaintenanceCommand("maintenance"), plugin);

    }

    /**
     * allow registering of commands in the Bukkit CommandMap instead of using the plugin.yml
     *
     * @param command the command to register
     * @param plugin  the {@link JavaPlugin} where the command belong
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

    public static CommandManager getInstance() {
        return instance;
    }

}
