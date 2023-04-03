package fr.obelouix.ultimate.commands;

import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessingContext;
import cloud.commandframework.meta.CommandMeta;
import com.google.common.collect.ImmutableList;
import fr.obelouix.ultimate.api.MessagesAPI;
import fr.obelouix.ultimate.commands.manager.CommandRegistration;
import fr.obelouix.ultimate.config.PlayerConfig;
import fr.obelouix.ultimate.messages.I18NMessages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;

import java.util.List;

public class CoordinatesCommand extends CommandRegistration {

    private boolean stateChanged;

    @Override
    public void register() {
        COMMAND_MANAGER.command(
                COMMAND_MANAGER.commandBuilder("coordinates", "coords")
                        .meta(CommandMeta.DESCRIPTION, "enable/disable coordinates HUD")
                        .permission("obelouix.commands.coordinates")
                        .argument(StringArgument.of("state"))
                        .handler(this::execute)
                        .senderType(Player.class)
                        .build()
        ).commandSuggestionProcessor(this::suggestions);
    }

    private List<String> suggestions(@NonNull CommandPreprocessingContext<CommandSender> commandSenderCommandPreprocessingContext, @NonNull List<String> strings) {
        return ImmutableList.of("on", "off");
    }


    @Override
    public void execute(@NonNull CommandContext<CommandSender> context) {
        Player sender = (Player) context.getSender();
        if(context.contains("state")) {
            String arg = context.get("state");
            if(arg.equals("on")) {
                updateCoordinateConfigState(sender, true);
                if(stateChanged){
                    MessagesAPI.sendMessage(sender,
                            GlobalTranslator.render(Component.translatable(I18NMessages.COMMAND_COORDS_ENABLED.getTranslationKey(),
                                    NamedTextColor.GREEN), sender.locale())
                    );
                }

            }
            else {
                updateCoordinateConfigState(sender, false);
                if(stateChanged) {
                    MessagesAPI.sendMessage(sender,
                            GlobalTranslator.render(Component.translatable(I18NMessages.COMMAND_COORDS_DISABLED.getTranslationKey(),
                                    NamedTextColor.GREEN), sender.locale())
                    );
                }
            }
        }
        stateChanged = false;
    }

    private void updateCoordinateConfigState(Player player,boolean state){
        try {
            final CommentedConfigurationNode root = PlayerConfig.getPlayerConfig(player).load();
            final boolean coordsConfigState = PlayerConfig.getBooleanNode(root.node("show-coordinates"));

            if(state == coordsConfigState) {
                if(coordsConfigState) {
                    MessagesAPI.sendMessage(player, GlobalTranslator.render(Component.translatable(I18NMessages.COMMAND_COORDS_ALREADY_ENABLED.getTranslationKey(),
                            NamedTextColor.DARK_RED), player.locale()));
                } else {
                    MessagesAPI.sendMessage(player, GlobalTranslator.render(Component.translatable(I18NMessages.COMMAND_COORDS_ALREADY_DISABLED.getTranslationKey(),
                            NamedTextColor.DARK_RED), player.locale()));
                }
                return;
            }

            PlayerConfig.setBooleanNode(root.node("show-coordinates"), state);
            PlayerConfig.save(root, player);
            stateChanged = true;
        } catch (ConfigurateException e) {
            throw new RuntimeException(e);
        }
    }

}
