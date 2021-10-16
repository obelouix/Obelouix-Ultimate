package fr.obelouix.ultimate.commands;

import fr.obelouix.ultimate.gui.AdminInventory;
import fr.obelouix.ultimate.permissions.IPermission;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminCommand extends BukkitCommand {
    public AdminCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String string, @NotNull String[] strings) {
        if (commandSender instanceof Player player && IPermission.hasPermission(commandSender, "obelouix.command.admin")) {
            new AdminInventory(player);
        }
        return false;
    }
}
