package fr.obelouix.ultimate.audience;

import fr.obelouix.ultimate.data.PlayerData;
import org.bukkit.Bukkit;

import java.util.stream.Collectors;

public class Audience {

    // Audience that see the coordinates bar
    public static final net.kyori.adventure.audience.Audience COORDINATES_AUDIENCE = net.kyori.adventure.audience.Audience.audience(PlayerData.getCoordinatesPlayerMap().keySet());
    // Audience for players that can join silently and that are allowed to see other players that can also join silently
    public static final net.kyori.adventure.audience.Audience SILENT_JOIN_AUDIENCE = net.kyori.adventure.audience.Audience.audience(Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("obelouix.silentjoin")).collect(Collectors.toSet()));

}
