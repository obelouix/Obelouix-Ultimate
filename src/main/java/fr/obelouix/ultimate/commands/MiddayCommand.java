package fr.obelouix.ultimate.commands;

import fr.obelouix.ultimate.audience.MessageSender;
import fr.obelouix.ultimate.i18n.I18n;
import fr.obelouix.ultimate.messages.PluginMessages;
import fr.obelouix.ultimate.permissions.IPermission;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class MiddayCommand extends BukkitCommand {

    private static final I18n i18n = I18n.getInstance();
    private static Component message;
    private static CommandSender sender;

    public MiddayCommand(String name) {
        super(name);
        this.setUsage("/midday [world]");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String label, @NotNull String[] args) {
        if (commandSender instanceof Player player && IPermission.hasPermission(commandSender, "obelouix.command.midday")) {
            if (args.length == 0) {
                setMidday(player.getWorld().getName());
            } else if (args.length == 1) {
                setMidday(args[0]);
            } else {
                MessageSender.sendMessage(commandSender, PluginMessages.wrongCommandUsage(this, player));
            }
            MessageSender.sendMessage(commandSender, message);
        } else if (commandSender instanceof ConsoleCommandSender) {
            if (args.length == 0) {
                commandSender.sendMessage(i18n.getTranslation(commandSender, "obelouix.command.day.console.too_few_arguments"));
            } else if (args.length == 1) {
                setMidday(args[0]);
                MessageSender.sendMessage(commandSender, message);
            } else {
                commandSender.sendMessage(i18n.getTranslation(commandSender, "obelouix.command.day.console.too_many_arguments"));
            }
        }
        return false;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (sender.hasPermission("obelouix.command.midday") && args.length == 1) {
            return DayCommand.getNormalWorlds();
        }
        return super.tabComplete(sender, alias, args);
    }

    private void setMidday(String world) {
        if (Bukkit.getWorld(world) != null) {
            Objects.requireNonNull(Bukkit.getWorld(world)).setTime(6000);
            if (sender instanceof Player player) {
                message = PluginMessages.playerTimeMessage(player, 6000);
            } else {
                message = PluginMessages.playerTimeMessage(sender, world, 6000);
            }

        } else {
            message = PluginMessages.nonExistentWorldMessage(sender, world);
        }

    }

}
