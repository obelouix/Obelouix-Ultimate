package fr.obelouix.ultimate.commands.manager;

import cloud.commandframework.context.CommandContext;
import fr.obelouix.ultimate.audience.MessageSender;
import fr.obelouix.ultimate.data.PlayerData;
import fr.obelouix.ultimate.permissions.IPermission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class CoordsCommand extends BaseCommand {
    public void register() {
        CommandManager.getInstance().command(
                CommandManager.getInstance()
                        .commandBuilder("coords")
                        .handler(this::execute)
                        .build());
    }

    protected void execute(@NonNull CommandContext<CommandSender> context) {
        final CommandSender sender = context.getSender();
        if ((sender instanceof Player player) && IPermission.hasPermission(sender, "obelouix.command.coords")) {
            boolean showCoords = PlayerData.isShowCoordinates(player);
            PlayerData.setShowCoordinates(player, !showCoords);
            showCoords = !showCoords;
            if (showCoords) {
                MessageSender.sendMessage(player, Component.text(
                        StringUtils.capitalize(i18n.getTranslation(player, "obelouix.command.coords.enabled")), NamedTextColor.GREEN));
            } else {
                MessageSender.sendMessage(player, Component.text(
                        StringUtils.capitalize(i18n.getTranslation(player, "obelouix.command.coords.disabled")), NamedTextColor.DARK_RED));
            }
        } else if (sender instanceof ConsoleCommandSender console) {
            MessageSender.sendMessage(console, Component.text("Only a player can run this command", NamedTextColor.DARK_RED));
        }
    }
}
