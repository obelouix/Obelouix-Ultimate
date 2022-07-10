package fr.obelouix.ultimate.commands;

import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessingContext;
import fr.obelouix.ultimate.commands.manager.BaseCommand;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class ExecuteCommand extends BaseCommand {
    @Override
    protected void register() {

    }


    protected List<String> suggestions(@NonNull CommandPreprocessingContext<CommandSender> context, @NonNull List<String> strings) {
        return null;
    }

    @Override
    protected void execute(@NonNull CommandContext<CommandSender> context) {

    }
}
