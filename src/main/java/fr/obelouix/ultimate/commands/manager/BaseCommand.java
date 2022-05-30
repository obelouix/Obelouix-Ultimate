package fr.obelouix.ultimate.commands.manager;

import cloud.commandframework.context.CommandContext;
import fr.obelouix.ultimate.i18n.I18n;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class BaseCommand {

    protected final I18n i18n = I18n.getInstance();

    /**
     * Method for registering the command to the framework
     */
    public abstract void register();

    /**
     * Method to tell what the command should do
     * @param context context of the command
     */
    protected abstract void execute(@NonNull CommandContext<CommandSender> context);
}
