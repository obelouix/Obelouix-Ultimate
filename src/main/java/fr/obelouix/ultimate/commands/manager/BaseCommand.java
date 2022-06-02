package fr.obelouix.ultimate.commands.manager;

import cloud.commandframework.context.CommandContext;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.api.TranslationAPI;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class BaseCommand {

    protected static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    protected final TranslationAPI translationAPI = ObelouixUltimate.getInstance().getTranslationAPI();

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
