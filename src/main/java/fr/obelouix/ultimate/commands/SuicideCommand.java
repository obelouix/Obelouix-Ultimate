package fr.obelouix.ultimate.commands;

import cloud.commandframework.context.CommandContext;
import fr.obelouix.ultimate.commands.manager.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class SuicideCommand extends BaseCommand {
    @Override
    protected void register() {
        COMMAND_MANAGER.command(BuildCommand("suicide")
                .permission("obelouix.command.suicide")
                .senderType(Player.class)
                .build());
    }

    @Override
    protected void execute(@NonNull CommandContext<CommandSender> context) {

        if (context.getSender() instanceof Player player) {
            // We have to run this synchronously (the command is async)
            Bukkit.getScheduler().runTask(plugin, () -> player.setHealth(0));
        }

    }
}
