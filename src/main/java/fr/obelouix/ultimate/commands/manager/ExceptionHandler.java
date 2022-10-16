package fr.obelouix.ultimate.commands.manager;

import cloud.commandframework.exceptions.NoPermissionException;
import cloud.commandframework.exceptions.NoSuchCommandException;
import fr.obelouix.ultimate.ObelouixUltimate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
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
        //commandManager.registerExceptionHandler(ArgumentParseException.class, this::argumentParsing);

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
                final String click_to_run = PlainTextComponentSerializer.plainText().serialize(Component.translatable("obelouix.click_to_run"));
                player.sendMessage(Component.translatable("obelouix.command.unknown", NamedTextColor.DARK_RED)
                        .replaceText(TextReplacementConfig.builder()
                                .matchLiteral("/help")
                                .replacement(Component.text("/help", NamedTextColor.RED).clickEvent(ClickEvent.runCommand("/help"))
                                        .hoverEvent(MiniMessage.miniMessage().deserialize("<rainbow>" + click_to_run + "</rainbow>")))
                                .build()));

                ev.setCancelled(true);
            }
        }
    }

    //Unknown command for cloud
    private void unknownCommand(@NonNull CommandSender sender, @NonNull NoSuchCommandException exception) {
        final String click_to_run = PlainTextComponentSerializer.plainText().serialize(Component.translatable("obelouix.click_to_run"));
        sender.sendMessage(Component.translatable("obelouix.command.unknown", NamedTextColor.DARK_RED)
                .replaceText(TextReplacementConfig.builder()
                        .matchLiteral("/help")
                        .replacement(Component.text("/help", NamedTextColor.RED).clickEvent(ClickEvent.runCommand("/help"))
                                .hoverEvent(MiniMessage.miniMessage().deserialize("<rainbow>" + click_to_run + "</rainbow>")))
                        .build()));
    }

    //No Permission message
    private void noPermission(@NonNull CommandSender sender, @NonNull NoPermissionException exception) {
        sender.sendMessage(Component.translatable("no_permission", NamedTextColor.DARK_RED));
    }

/*
    private void argumentParsing(@NonNull CommandSender sender, @NonNull ArgumentParseException exception) {
        final Throwable cause = exception.getCause();
        final Supplier<Component> fallback = () -> Objects.requireNonNull(ComponentMessageThrowable.getOrConvertMessage(cause));
        MessagesAPI.sendMessage(sender, fallback.get());
    }
*/

}
