package fr.obelouix.ultimate.commands;

import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import fr.obelouix.ultimate.commands.manager.BaseCommand;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

public class TimeCommand extends BaseCommand {
    @Override
    protected void register() {
        COMMAND_MANAGER.command(
                COMMAND_MANAGER.commandBuilder("time")
                        .argument(StringArgument.of("input"))
                        .handler(this::execute)
                        .build()
        );
    }

    @Override
    protected void execute(@NonNull CommandContext<CommandSender> context) {

    }
}
