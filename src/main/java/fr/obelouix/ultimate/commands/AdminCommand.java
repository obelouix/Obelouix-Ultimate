package fr.obelouix.ultimate.commands;

import cloud.commandframework.context.CommandContext;
import fr.obelouix.ultimate.commands.manager.BaseCommand;
import fr.obelouix.ultimate.gui.AdminInventory;
import fr.obelouix.ultimate.permissions.IPermission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class AdminCommand extends BaseCommand {
    public AdminCommand() {
        super("");
    }


    protected void execute(@NonNull CommandContext<CommandSender> context) {
        final CommandSender sender = context.getSender();
        if (sender instanceof Player player && IPermission.hasPermission(sender, "obelouix.command.admin")) {
            new AdminInventory(player);
        }
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        return false;
    }
}
