package fr.obelouix.ultimate.commands;

import fr.obelouix.ultimate.i18n.I18n;
import fr.obelouix.ultimate.messages.PluginMessages;
import fr.obelouix.ultimate.permissions.IPermission;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class DayCommand extends BukkitCommand {

    private static final I18n i18n = I18n.getInstance();

    public DayCommand(String name) {
        super(name);
        this.setUsage("/day [world]");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String label, @NotNull String[] args) {
        if (commandSender instanceof Player player && IPermission.hasPermission(commandSender, "obelouix.command.day")) {
            if (args.length == 0) {
                setDay(player.getWorld());
            } else if (args.length == 1) {
                if (Bukkit.getWorld(args[0]) != null) {
                    setDay(Objects.requireNonNull(Bukkit.getWorld(args[0])));
                }
            } else {
                player.sendMessage(PluginMessages.wrongCommandUsage(this, player));
            }
        } else {
            if (args.length == 0) {
                commandSender.sendMessage(i18n.getTranslation(commandSender, "obelouix.command.day.console.too_few_arguments"));
            } else if (args.length == 1) {
                if (Bukkit.getWorld(args[0]) != null) {
                    setDay(Objects.requireNonNull(Bukkit.getWorld(args[0])));
                }
            } else {
                commandSender.sendMessage(i18n.getTranslation(commandSender, "obelouix.command.day.console.too_many_arguments"));
            }
        }
        return false;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return super.tabComplete(sender, alias, args);
    }

    private void setDay(World world) {
        Objects.requireNonNull(Bukkit.getWorld(world.getName())).setTime(0);
    }

}
