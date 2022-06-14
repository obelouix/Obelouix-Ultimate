package fr.obelouix.ultimate.commands;

import cloud.commandframework.context.CommandContext;
import fr.obelouix.ultimate.api.MessagesAPI;
import fr.obelouix.ultimate.commands.manager.BaseCommand;
import fr.obelouix.ultimate.data.PlayerData;
import fr.obelouix.ultimate.messages.I18NMessages;
import fr.obelouix.ultimate.permissions.IPermission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class CoordsCommand extends BaseCommand {

    @Override
    protected void register() {

    }

    protected void execute(@NonNull CommandContext<CommandSender> context) {
        final CommandSender sender = context.getSender();
        if ((sender instanceof Player player) && IPermission.hasPermission(sender, "obelouix.command.coords")) {
            boolean showCoords = PlayerData.isShowCoordinates(player);
            PlayerData.setShowCoordinates(player, !showCoords);
            showCoords = !showCoords;
            if (showCoords) {
                MessagesAPI.sendMessage(player, Component.text(
                        StringUtils.capitalize(I18NMessages.COMMAND_COORDS_ENABLED.getTranslation(player)), NamedTextColor.GREEN));
            } else {
                MessagesAPI.sendMessage(player, Component.text(
                        StringUtils.capitalize(I18NMessages.COMMAND_COORDS_DISABLED.getTranslation(player)), NamedTextColor.DARK_RED));
            }
        } else if (sender instanceof ConsoleCommandSender console) {
            MessagesAPI.sendMessage(console, Component.text(I18NMessages.COMMAND_ONLY_FOR_PLAYER.getSystemTranslation(), NamedTextColor.DARK_RED));
        }
    }

}
