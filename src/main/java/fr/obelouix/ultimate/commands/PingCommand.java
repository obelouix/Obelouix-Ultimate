package fr.obelouix.ultimate.commands;

import fr.obelouix.ultimate.api.MessagesAPI;
import fr.obelouix.ultimate.components.PingFormat;
import fr.obelouix.ultimate.messages.I18NMessages;
import fr.obelouix.ultimate.permissions.IPermission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PingCommand extends Command {
    public PingCommand(@NotNull String name) {
        super(name);
    }

    @Override
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
    }
}
