package fr.obelouix.ultimate.messages;

import fr.obelouix.ultimate.i18n.I18n;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class PluginMessages {

    private static final I18n i18n = I18n.getInstance();

    public static Component wrongCommandUsage(Command command, CommandSender commandSender){
        return Component.text(i18n.getTranslation(commandSender, "obelouix.wrong_command_usage"))
                .replaceText(TextReplacementConfig.builder()
                        .matchLiteral("{0}")
                        .replacement(Component.text(command.getUsage()))
                        .build());
    }

}
