package fr.obelouix.ultimate.commands.manager;

import cloud.commandframework.context.CommandContext;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.api.TranslationAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public abstract class BaseCommand extends Command {

    protected static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    protected static final TranslationAPI translationAPI = ObelouixUltimate.getInstance().getTranslationAPI();
    protected static final CommandManager commandManger = CommandManager.getInstance();

    protected BaseCommand(@NotNull String name) {
        super(name);
    }

    /**
     * Method to tell what the command should do
     *
     * @param context context of the command
     */
    protected abstract void execute(@NonNull CommandContext<CommandSender> context);
}
