package fr.obelouix.ultimate.tweaks;

import fr.obelouix.ultimate.api.MessagesAPI;
import fr.obelouix.ultimate.messages.I18NMessages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;

public class EnderDragonTweaks implements Listener {

    @EventHandler
    public void onEnderDragonDeath(EntityDeathEvent event) {
        System.out.println(event.getEntity());
        if (event.getEntity() instanceof EnderDragon enderDragon) {

            final Location center = new Location(enderDragon.getWorld(), 0, 65, 0);
            final List<Player> rewardedPlayers = enderDragon.getWorld().getPlayers().stream()
                    .filter(player -> player.getLocation().distanceSquared(center) <= 200).toList();

            rewardedPlayers.forEach(player -> {
                MessagesAPI.sendMessage(
                        player,
                        Component.text(I18NMessages.ENDERDRAGON_KILL_REWARD.getTranslation(player),
                                NamedTextColor.GREEN).replaceText(TextReplacementConfig.builder()
                                .matchLiteral("{0}")
                                .replacement(Component.text(68, NamedTextColor.AQUA))
                                .build())
                );
                player.giveExpLevels(68);
            });

            if (rewardedPlayers.size() == 1) {
                Bukkit.getOnlinePlayers().forEach(player -> MessagesAPI.broadcast(Component.text(I18NMessages.ENDERDRAGON_KILL_BROADCAST_SINGLEPLAYER.getTranslation(player))));
            }

        }
    }
}
