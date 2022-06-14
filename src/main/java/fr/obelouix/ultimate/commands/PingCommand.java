package fr.obelouix.ultimate.commands;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.meta.CommandMeta;
import fr.obelouix.ultimate.commands.manager.BaseCommand;
import fr.obelouix.ultimate.permissions.IPermission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PingCommand extends BaseCommand {

    @Override
    protected void register() {
        CommandBuilder("ping")
                .meta(CommandMeta.DESCRIPTION, "get your ping")
                .argument(StringArgument.optional("player"), ArgumentDescription.of("The player that you want to see the ping"))
                .build();

        SuggestionsProvider("playerSuggestionProvider", this::suggestions);
    }

    private List<String> suggestions(CommandContext<CommandSender> commandSenderCommandContext, String s) {
        if (commandSenderCommandContext.hasPermission("obelouix.command.ping.others")) {
            final List<String> players = new ArrayList<>();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                //don't had players that have this permission
                if (!onlinePlayer.hasPermission("obelouix.ping.hide")) players.add(onlinePlayer.getName());
            }
            return players;
        }
        return Collections.emptyList();
    }

    @Override
    public void execute(@NonNull CommandContext<CommandSender> context) {
        if (context.contains("player")) ;
        if (context.getSender() instanceof Player player) {
            if (IPermission.hasPermission(player, "obelouix.command.ping")) {

            }
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
