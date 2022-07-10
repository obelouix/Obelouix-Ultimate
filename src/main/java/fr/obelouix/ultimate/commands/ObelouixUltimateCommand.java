package fr.obelouix.ultimate.commands;

import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessingContext;
import com.google.common.collect.ImmutableList;
import fr.obelouix.ultimate.api.MessagesAPI;
import fr.obelouix.ultimate.commands.manager.BaseCommand;
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

    protected List<String> suggestions(@NonNull CommandPreprocessingContext<CommandSender> commandSenderCommandPreprocessingContext, @NonNull List<String> strings) {
        return ImmutableList.of("reload", "version");
    }

    @Override
    protected void register() {

    }

    protected void execute(@NonNull CommandContext<CommandSender> context) {
        final CommandSender sender = context.getSender();
        final String argument = context.get("version/reload");
        if (IPermission.hasPermission(sender, "obelouix.command.obelouixultimate")) {
            if (argument.equalsIgnoreCase("version")) {
                MessagesAPI.sendMessage(sender,
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
                    MessagesAPI.sendMessage(sender, Component.text(translationAPI.getTranslation(sender, "obelouix.command.obelouixultimate.reload"))
                            .color(NamedTextColor.AQUA));
                    // Temporary will be modified later
                    MessagesAPI.broadcast(Component.text("[Obelouix Ultimate Plugin] ", NamedTextColor.GREEN)
                            .append(Component.text(" Le plugin a été rechargé, pour réactiver la barre de coordonnée (si elle ne fonctionne plus), 1 SEUL joueur doit déco reco", NamedTextColor.RED)));
                } else {
                    MessagesAPI.sendMessage(sender, Component.text(translationAPI.getTranslation(sender, "obelouix.command.obelouixultimate.reload.failed"))
                            .color(NamedTextColor.DARK_RED));
                }
            }
        }
    }
}
