package fr.obelouix.ultimate.audience;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class MessageSender {

    /**
     * Send a message to a command sender
     *
     * @param sender  the command sender
     * @param message a component
     */
    public static void sendMessage(CommandSender sender, Component message) {
        Audience audience = Audience.audience(Objects.requireNonNull(Bukkit.getPlayer(sender.getName())));
        audience.sendMessage(message);
    }

    /**
     * Send a message to every connected players
     *
     * @param message a component
     */
    public static void broadcast(Component message) {
        Audience audience = Audience.audience(Bukkit.getOnlinePlayers());
        audience.sendMessage(message);
    }

    /**
     * Send a message to every connected players that as the required permission
     *
     * @param permission the permission the player need to receive the message
     * @param message    a component
     */
    public static void broadcast(String permission, Component message) {
        Collection<Player> authorizedPlayers = new java.util.ArrayList<>(Collections.emptyList());
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(permission)) authorizedPlayers.add(player);
        }
        Audience audience = Audience.audience(authorizedPlayers);
        audience.sendMessage(message);
    }

}
