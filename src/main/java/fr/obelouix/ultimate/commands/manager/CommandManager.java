package fr.obelouix.ultimate.commands.manager;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.commands.DayCommand;
import fr.obelouix.ultimate.commands.NightCommand;
import fr.obelouix.ultimate.commands.ObelouixUltimateCommand;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;

public class CommandManager {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    public CommandManager() throws ReflectiveOperationException {
        registerCommand(new ObelouixUltimateCommand("obelouixultimate"), plugin);
        registerCommand(new DayCommand("day"), plugin);
        registerCommand(new NightCommand("night"), plugin);
    }

    /**
     * allow registering of commands in the Bukkit CommandMap instead of using the plugin.yml
     *
     * @param command the command to register
     * @param plugin  the {@link org.bukkit.plugin.java.JavaPlugin} where the command belong
     * @throws ReflectiveOperationException
     */
    private void registerCommand(Command command, JavaPlugin plugin) throws ReflectiveOperationException {
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
