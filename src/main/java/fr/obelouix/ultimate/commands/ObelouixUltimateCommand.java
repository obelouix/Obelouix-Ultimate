package fr.obelouix.ultimate.commands;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessingContext;
import cloud.commandframework.meta.CommandMeta;
import com.google.common.collect.ImmutableList;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.api.TranslationAPI;
import fr.obelouix.ultimate.audience.MessageSender;
import fr.obelouix.ultimate.commands.manager.BaseCommand;
import fr.obelouix.ultimate.commands.manager.CommandManager;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.messages.I18NMessages;
import fr.obelouix.ultimate.permissions.IPermission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class ObelouixUltimateCommand extends BaseCommand {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static final TranslationAPI translationAPI = plugin.getTranslationAPI();

    public void register() {
        CommandManager.getInstance().command(
                        CommandManager.getInstance().commandBuilder("obelouixultimate")
                                .argument(StringArgument.single("version/reload"), ArgumentDescription.of("return the version of the plugin or reload the plugin"))
                                .handler(this::execute)
                                .meta(CommandMeta.DESCRIPTION, "This command allow to get the plugin version or to reload it")
                                .build()
                )
                .setCommandSuggestionProcessor(this::suggestions);
    }

    private List<String> suggestions(@NonNull CommandPreprocessingContext<CommandSender> commandSenderCommandPreprocessingContext, @NonNull List<String> strings) {
        return ImmutableList.of("reload", "version");
    }

    protected void execute(@NonNull CommandContext<CommandSender> context) {
        final CommandSender sender = context.getSender();
        final String argument = context.get("version/reload");
        if (IPermission.hasPermission(sender, "obelouix.command.obelouixultimate")) {
            if (argument.equalsIgnoreCase("version")) {
                MessageSender.sendMessage(sender,
                        Component.text(I18NMessages.PLUGIN_VERSION.getTranslation(sender))
                                .color(NamedTextColor.GREEN)
                                .replaceText(TextReplacementConfig.builder()
                                        .matchLiteral("{0}")
                                        .replacement(Component.text(plugin.getDescription().getVersion()).color(NamedTextColor.AQUA))
                                        .build()
                                ));
            } else if (argument.equalsIgnoreCase("reload")) {
                Config.loadConfig();
                if (Config.isConfigReloaded()) {
                    MessageSender.sendMessage(sender, Component.text(translationAPI.getTranslation(sender, "obelouix.command.obelouixultimate.reload"))
                            .color(NamedTextColor.AQUA));
                    // Temporary will be modified later
                    MessageSender.broadcast(Component.text("[Obelouix Ultimate Plugin] ", NamedTextColor.GREEN)
                            .append(Component.text(" Le plugin a été rechargé, pour réactiver la barre de coordonnée (si elle ne fonctionne plus), 1 SEUL joueur doit déco reco", NamedTextColor.RED)));
                } else {
                    MessageSender.sendMessage(sender, Component.text(translationAPI.getTranslation(sender, "obelouix.command.obelouixultimate.reload.failed"))
                            .color(NamedTextColor.DARK_RED));
                }
            }
        }
    }
}
