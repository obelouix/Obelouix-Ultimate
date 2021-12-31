package fr.obelouix.ultimate.commands;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessingContext;
import cloud.commandframework.meta.CommandMeta;
import com.google.common.collect.ImmutableList;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.audience.MessageSender;
import fr.obelouix.ultimate.commands.manager.CommandManager;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.i18n.I18n;
import fr.obelouix.ultimate.permissions.IPermission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class ObelouixUltimateCommand {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static final I18n i18n = I18n.getInstance();

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

    private void execute(@NonNull CommandContext<CommandSender> context) {
        final CommandSender sender = context.getSender();
        final String argument = context.get("version/reload");
        if (IPermission.hasPermission(sender, "obelouix.command.obelouixultimate")) {
            if (argument.equalsIgnoreCase("version")) {
                MessageSender.sendMessage(sender,
                        Component.text(I18n.getInstance().getTranslation(sender, "obelouix.plugin.version"))
                                .color(NamedTextColor.GREEN)
                                .replaceText(TextReplacementConfig.builder()
                                        .matchLiteral("{0}")
                                        .replacement(Component.text(plugin.getDescription().getVersion()).color(NamedTextColor.AQUA))
                                        .build()
                                ));
            } else if (argument.equalsIgnoreCase("reload")) {
                Config.loadConfig();
                if (Config.isConfigReloaded()) {
                    MessageSender.sendMessage(sender, Component.text(i18n.getTranslation(sender, "obelouix.command.obelouixultimate.reload"))
                            .color(NamedTextColor.AQUA));
                } else {
                    MessageSender.sendMessage(sender, Component.text(i18n.getTranslation(sender, "obelouix.command.obelouixultimate.reload.failed"))
                            .color(NamedTextColor.DARK_RED));
                }
            }
        }
    }
}
