package fr.obelouix.ultimate.commands;

import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessingContext;
import cloud.commandframework.meta.CommandMeta;
import fr.obelouix.ultimate.api.MessagesAPI;
import fr.obelouix.ultimate.commands.manager.BaseCommand;
import fr.obelouix.ultimate.components.PingFormat;
import fr.obelouix.ultimate.messages.I18NMessages;
import fr.obelouix.ultimate.permissions.IPermission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PingCommand extends BaseCommand {

    @Override
    public void register() {
        COMMAND_MANAGER.command(
                COMMAND_MANAGER.commandBuilder("ping")
                        .meta(CommandMeta.DESCRIPTION, "get your ping")
                        //.argument(StringArgument.optional("target"))
                        .build()).commandSuggestionProcessor(this::suggestions);

        //SuggestionsProvider("playerSuggestionProvider", this::suggestions);
    }


    protected List<String> suggestions(@NonNull CommandPreprocessingContext<CommandSender> commandSenderCommandPreprocessingContext, @NonNull List<String> strings) {
        return null;
    }

    private List<String> suggestions(CommandContext<CommandSender> commandContext, String s) {
        if (commandContext.hasPermission("obelouix.command.ping.others")) {
            final List<String> players = new ArrayList<>();
            for (final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayer.equals(commandContext.getSender())) {
                    //don't had players that have this permission
                    if (!onlinePlayer.hasPermission("obelouix.ping.hide")) players.add(onlinePlayer.getName());
                }

            }
            return players;
        }
        return Collections.emptyList();
    }

    @Override
    public void execute(@NonNull CommandContext<CommandSender> context) {
        final String target = Objects.requireNonNull(context.getOptional("target").orElse(null)).toString();

        if (context.getSender() instanceof Player player) {
            if (IPermission.hasPermission(player, "obelouix.command.ping") && target.length() == 0) {
                MessagesAPI.sendMessage(player, Component.text(I18NMessages.PING_COMMAND_SELF.getTranslation(player), NamedTextColor.GRAY)
                        .replaceText(
                                TextReplacementConfig.builder()
                                        .matchLiteral("{0}")
                                        .replacement(PingFormat.getPingComponent(player.getPing()))
                                        .build()));
            }
        } else if (context.getSender() instanceof ConsoleCommandSender console) {

        }
    }

/*    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (IPermission.hasPermission(sender, "obelouix.command.ping")) {
            if (sender instanceof Player player) {
                MessagesAPI.sendMessage(sender, Component.text(I18NMessages.PING_COMMAND_SELF.getTranslation(player), NamedTextColor.GRAY)
                        .replaceText(
                                TextReplacementConfig.builder()
                                        .matchLiteral("{0}")
                                        .replacement(PingFormat.getPingComponent(player.getPing()))
                                        .build()));
            }
        }
        return false;
    }*/
}
