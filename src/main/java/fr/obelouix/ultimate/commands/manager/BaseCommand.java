package fr.obelouix.ultimate.commands.manager;

import cloud.commandframework.Command;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.paper.PaperCommandManager;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.api.TranslationAPI;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.BiFunction;

public abstract class BaseCommand {

    protected static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    protected static final TranslationAPI translationAPI = ObelouixUltimate.getInstance().getTranslationAPI();
    protected static final PaperCommandManager<CommandSender> COMMAND_MANAGER = CommandManager.getInstance().getManager();


    /**
     * Initialize your command inside this method
     */
    protected abstract void register();

    /**
     * Method to tell what the command should do
     *
     * @param context context of the command
     */
    protected abstract void execute(@NonNull CommandContext<CommandSender> context);

    /**
     * Provide a command builder with an already defined handler (the {@link #execute(CommandContext)} method)
     *
     * @param command the command name
     * @return the command builder
     */
    protected cloud.commandframework.Command.@NonNull Builder<CommandSender> CommandBuilder(String command) {
        Command.@NonNull Builder<CommandSender> builder = COMMAND_MANAGER.commandBuilder(command);
        return builder.handler(this::execute);
    }

    protected void SuggestionsProvider(String name, BiFunction<cloud.commandframework.context.CommandContext<org.bukkit.command.CommandSender>, java.lang.String, java.util.List<java.lang.String>> suggestionMethod) {
        COMMAND_MANAGER.getParserRegistry().registerSuggestionProvider(name, suggestionMethod);
    }

}
