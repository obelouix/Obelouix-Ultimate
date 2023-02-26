package fr.obelouix.ultimate.commands;

import cloud.commandframework.context.CommandContext;
import fr.obelouix.ultimate.commands.manager.CommandRegistration;
import fr.obelouix.ultimate.gui.OptionsGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class OptionsCommand extends CommandRegistration {
    @Override
    protected void register() {
        COMMAND_MANAGER.command(
                COMMAND_MANAGER.commandBuilder("options")
                        .permission("obelouix.commands.options")
                        .senderType(Player.class)
                        .handler(this::execute)
                        .build()
        );
    }

    @Override
    public void execute(@NonNull CommandContext<CommandSender> context) {
        final Player player = (Player) context.getSender();
        final OptionsGUI optionsGUI = new OptionsGUI(player);
        optionsGUI.show();
    }
}
