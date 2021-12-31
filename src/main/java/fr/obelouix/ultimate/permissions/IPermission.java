package fr.obelouix.ultimate.permissions;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.audience.MessageSender;
import fr.obelouix.ultimate.i18n.I18n;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface IPermission {

    ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    static boolean hasPermission(@NotNull CommandSender sender, @NotNull String permission){
        if(!sender.hasPermission(permission)){
            final Component message = Component.text(I18n.getInstance().getTranslation(sender, "no_permission")).color(TextColor.color(183, 0, 0));
            MessageSender.sendMessage(sender, message);
            plugin.getLogger().info("Refused command to " + sender.getName());
            return false;
        }
        return true;
    }

}
