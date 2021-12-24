package fr.obelouix.ultimate.audience;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class MessageSender {

    public static void sendMessage(CommandSender sender, Component message) {

        Audience audience = Audience.audience((Audience) Objects.requireNonNull(Bukkit.getPlayer(sender.getName())));
        audience.sendMessage(message);
    }

}
