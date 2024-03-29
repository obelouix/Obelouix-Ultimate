package fr.obelouix.ultimate.commands.manager;

import cloud.commandframework.context.CommandContext;
import cloud.commandframework.paper.PaperCommandManager;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class CommandRegistration {

    protected static final PaperCommandManager<CommandSender> COMMAND_MANAGER = ObelouixCommandManager.getManager();

    protected abstract void register();

    public abstract void execute(@NonNull CommandContext<CommandSender> context);
}
