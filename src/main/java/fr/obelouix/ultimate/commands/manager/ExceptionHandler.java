package fr.obelouix.ultimate.commands.manager;

import cloud.commandframework.exceptions.NoPermissionException;
import cloud.commandframework.exceptions.NoSuchCommandException;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.api.MessagesAPI;
import fr.obelouix.ultimate.messages.I18NMessages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

final class ExceptionHandler implements Listener {

    public ExceptionHandler() {
    }

    // Register custom Cloud's Exceptions handlers
    public void registerExceptionHandlers(final cloud.commandframework.CommandManager<CommandSender> commandManager) {
        commandManager.registerExceptionHandler(NoPermissionException.class, this::noPermission);
        commandManager.registerExceptionHandler(NoSuchCommandException.class, this::unknownCommand);

        Bukkit.getServer().getPluginManager().registerEvent(PlayerCommandPreprocessEvent.class, this, EventPriority.HIGH, this::unknownCommandDefault, ObelouixUltimate.getInstance());

        /*
        TODO: implements those exceptions
        commandManager.registerExceptionHandler(CommandExecutionException.class, this::commandExecution);
        commandManager.registerExceptionHandler(ArgumentParseException.class, this::argumentParsing);
        commandManager.registerExceptionHandler(InvalidCommandSenderException.class, this::invalidSender);
        commandManager.registerExceptionHandler(InvalidSyntaxException.class, this::invalidSyntax);
         */

    }

    // Replace the default Bukkit/Spigot "Unknown command" message
    private void unknownCommandDefault(@NotNull Listener listener, @NotNull Event event) {
        if (event instanceof PlayerCommandPreprocessEvent ev && !ev.isCancelled()) {
            final Player player = ev.getPlayer();
            final String command = ev.getMessage().split(" ")[0];
            final HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(command);
            if (topic == null) {
                MessagesAPI.sendMessage(player, Component.text(I18NMessages.UNKNOWN_COMMAND.getTranslation(player), NamedTextColor.DARK_RED)
                        .replaceText(TextReplacementConfig.builder()
                                .matchLiteral("/help")
                                .replacement(Component.text("/help", NamedTextColor.RED).clickEvent(ClickEvent.runCommand("/help"))
                                        //TODO: change this to a translated string
                                        .hoverEvent(MiniMessage.miniMessage().deserialize("<rainbow>click to run</rainbow>")))
                                .build()));
                ev.setCancelled(true);
            }
        }
    }

    //Unknown command for cloud
    private void unknownCommand(@NonNull CommandSender sender, @NonNull NoSuchCommandException exception) {
        MessagesAPI.sendMessage(sender, Component.text(I18NMessages.UNKNOWN_COMMAND.getTranslation(sender), NamedTextColor.DARK_RED)
                .replaceText(TextReplacementConfig.builder()
                        .matchLiteral("/help")
                        .replacement(Component.text("/help", NamedTextColor.RED).clickEvent(ClickEvent.runCommand("/help")))
                        .build()));
    }

    //No Permission message
    private void noPermission(@NonNull CommandSender sender, @NonNull NoPermissionException exception) {
        MessagesAPI.sendMessage(sender, Component.text(I18NMessages.COMMAND_NO_PERMISSION.getTranslation(sender), NamedTextColor.DARK_RED));
    }

}
