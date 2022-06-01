package fr.obelouix.ultimate.components;

import fr.obelouix.ultimate.messages.I18NMessages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;

public class Components {

    public Component worldEditWand;


    public Components(CommandSender sender) {
        worldEditWand = Component.text(I18NMessages.WORLDEDIT_WAND_TITLE.getTranslation(sender), NamedTextColor.GOLD, TextDecoration.BOLD);
    }


}
