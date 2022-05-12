package fr.obelouix.ultimate.events;

import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.utils.LuckPermsUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class ChatEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void formatChat(AsyncChatEvent event) {
        event.renderer(this::chatRender);

    }

    private @NotNull Component chatRender(@NotNull Player player, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience audience) {
        Component msg = message;

        // If the message sender doesn't have the permission to format his message, remove all formatting codes
        if (!player.hasPermission("obelouix.chat.formatting")) {
            msg = msg.replaceText(standardColor -> standardColor.match(Pattern.compile("(&)?&([\\da-fk-orA-FK-OR])")).replacement(""))
                    .replaceText(hexColor -> hexColor.match(Pattern.compile("(&)?&#([\\da-fA-F]{6})")).replacement(""));
        }

        if (LuckPermsUtils.getLuckPermsAPI() != null) {
            //LuckPerms player prefix
            final @NotNull TextComponent prefix = Component.text(LuckPermsUtils.getPrefix(player));
            //LuckPerms player suffix
            final @NotNull TextComponent suffix = Component.text(LuckPermsUtils.getSuffix(player));
            //The chat format retrieved from the config file
            final @NotNull Component chatFormat = Component.text(Config.chatFormat.get(LuckPermsUtils.getUserPrimaryGroup(player)));

            final @NotNull String serializedFormat = PlainTextComponentSerializer.plainText().serialize(chatFormat)
                    .replaceFirst("\\{world}", player.getWorld().getName())
                    .replaceFirst("\\{prefix}", PlainTextComponentSerializer.plainText().serialize(prefix))
                    .replaceFirst("\\{displayname}", player.getName())
                    .replaceFirst("\\{suffix}", PlainTextComponentSerializer.plainText().serialize(suffix))
                    .replaceFirst("\\{message}", PlainTextComponentSerializer.plainText().serialize(msg));

            return colorFormatter(PlainTextComponentSerializer.plainText().deserialize(serializedFormat));
        }
        // Return this simple chat format is LuckPerms is not installed
        return sourceDisplayName.append(Component.text(": ")).append(msg);
    }

    /**
     * Convert hex color codes and < 1.16 codes into components color codes
     *
     * @param component the component to colorize
     * @return a colorized component
     */
    public static @NotNull TextComponent colorFormatter(Component component) {
        final @NotNull String plainText = PlainTextComponentSerializer.plainText().serialize(component);
        final LegacyComponentSerializer serializer = LegacyComponentSerializer.builder()
                .hexColors()
                .character('&')
                .hexCharacter('#')
                .build();

        return serializer.deserialize(plainText);
    }
}
