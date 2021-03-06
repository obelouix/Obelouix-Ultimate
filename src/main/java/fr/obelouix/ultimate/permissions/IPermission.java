package fr.obelouix.ultimate.permissions;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.api.MessagesAPI;
import fr.obelouix.ultimate.messages.I18NMessages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface IPermission {

    ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    static boolean hasPermission(@NotNull CommandSender sender, @NotNull String permission){
        if(!sender.hasPermission(permission)) {
            final Component message = Component.text(I18NMessages.COMMAND_NO_PERMISSION.getTranslation(sender))
                    .color(TextColor.color(183, 0, 0));
            MessagesAPI.sendMessage(sender, message);
            plugin.getComponentLogger().info("Refused command to " + sender.getName());
            return false;
        }
        return true;
    }

}
