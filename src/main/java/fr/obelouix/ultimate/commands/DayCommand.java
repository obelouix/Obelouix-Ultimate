package fr.obelouix.ultimate.commands;

import fr.obelouix.ultimate.audience.MessageSender;
import fr.obelouix.ultimate.i18n.I18n;
import fr.obelouix.ultimate.messages.PluginMessages;
import fr.obelouix.ultimate.permissions.IPermission;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DayCommand extends BukkitCommand {

    private static final I18n i18n = I18n.getInstance();
    private static Component message;
    private static CommandSender sender;

    public DayCommand(String name) {
        super(name);
        this.setUsage("/day [world]");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String label, @NotNull String[] args) {
        sender = commandSender;
        if (commandSender instanceof Player player && IPermission.hasPermission(commandSender, "obelouix.command.day")) {
            if (args.length == 0) {
                setDay(player.getWorld().getName());
            } else if (args.length == 1) {
                setDay(args[0]);
            } else {
                MessageSender.sendMessage(player, PluginMessages.wrongCommandUsage(this, player));
            }
            MessageSender.sendMessage(sender, message);
        } else if (commandSender instanceof ConsoleCommandSender) {
            if (args.length == 0) {
                commandSender.sendMessage(i18n.getTranslation(commandSender, "obelouix.command.day.console.too_few_arguments"));
            } else if (args.length == 1) {
                setDay(args[0]);
                MessageSender.sendMessage(sender, message);
            } else {
                commandSender.sendMessage(i18n.getTranslation(commandSender, "obelouix.command.day.console.too_many_arguments"));
            }
        }
        return false;
    }

    @NotNull
    protected static List<String> getNormalWorlds() {
        final List<String> worldList = new java.util.ArrayList<>(Collections.emptyList());
        for (final World world : Bukkit.getWorlds()) {
            // Only add overworlds as we don't care about changing the time in nether and ends dimensions
            if (world.getEnvironment() == World.Environment.NORMAL) {
                worldList.add(world.getName());
            }
        }
        return worldList;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (sender.hasPermission("obelouix.command.day") && args.length == 1) {
            return getNormalWorlds();
        }
        return super.tabComplete(sender, alias, args);
    }

    private void setDay(String world) {
        if (Bukkit.getWorld(world) != null) {
            Objects.requireNonNull(Bukkit.getWorld(world)).setTime(0);
            if (sender instanceof Player player) {
                message = PluginMessages.playerTimeMessage(player, 0);
            } else {
                message = PluginMessages.playerTimeMessage(sender, world, 0);
            }

        } else {
            message = PluginMessages.nonExistentWorldMessage(sender, world);
        }

    }
}
