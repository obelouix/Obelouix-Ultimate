package fr.obelouix.ultimate.coordinates;

import fr.obelouix.ultimate.audience.MessageSender;
import fr.obelouix.ultimate.i18n.I18n;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Locale;

public class Coordinates implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent playerMoveEvent) {
        Player player = playerMoveEvent.getPlayer();
        Component actionBar = Component.text("X: ", NamedTextColor.DARK_RED)
                .append(Component.text(player.getLocation().getBlockX(), NamedTextColor.WHITE))
                .append(Component.text(" Y: ", NamedTextColor.GREEN))
                .append(Component.text(player.getLocation().getBlockY(), NamedTextColor.WHITE))
                .append(Component.text(" Z: ", NamedTextColor.DARK_BLUE))
                .append(Component.text(player.getLocation().getBlockZ(), NamedTextColor.WHITE))
                .append(Component.text(" " + parse24(player.getWorld().getTime()) + " ", NamedTextColor.GOLD))
                .append(Component.text(" Direction: ", NamedTextColor.AQUA))
                .append(Component.text(getFacing(player), NamedTextColor.WHITE));
        MessageSender.sendActionBar(playerMoveEvent.getPlayer(), actionBar);
    }

    private String parse24(final long time) {
        long hours = time / 1000L + 6L;
        final long minutes = time % 1000L * 60L / 1000L;
        if (hours == 24L) {
            hours = 0L;
        }
        if (hours == 25L) {
            hours = 1L;
        }
        if (hours == 26L) {
            hours = 2L;
        }
        if (hours == 27L) {
            hours = 3L;
        }
        if (hours == 28L) {
            hours = 4L;
        }
        if (hours == 29L) {
            hours = 5L;
        }
        if (hours == 30L) {
            hours = 6L;
        }
        String minutes_ = "0" + minutes;
        return hours + ":" + minutes_.substring(minutes_.length() - 2);
    }

    private String getFacing(final Player p) {
        final double yaw = p.getLocation().getYaw();

        if (yaw >= 337.5 || (yaw <= 22.5 && yaw >= 0.0) || (yaw >= -22.5 && yaw <= 0.0) || (yaw <= -337.5 && yaw <= 0.0)) {
            return I18n.getInstance().getTranslation(p, "obelouix.direction.south").toUpperCase(Locale.ROOT);
        }
        if ((yaw >= 22.5 && yaw <= 67.5) || (yaw <= -292.5 && yaw >= -337.5)) {
            return I18n.getInstance().getTranslation(p, "obelouix.direction.southwest").toUpperCase(Locale.ROOT);
        }
        if ((yaw >= 67.5 && yaw <= 112.5) || (yaw <= -247.5 && yaw >= -292.5)) {
            return I18n.getInstance().getTranslation(p, "obelouix.direction.west").toUpperCase(Locale.ROOT);
        }
        if ((yaw >= 112.5 && yaw <= 157.5) || (yaw <= -202.5 && yaw >= -247.5)) {
            return I18n.getInstance().getTranslation(p, "obelouix.direction.northwest").toUpperCase(Locale.ROOT);
        }
        if ((yaw >= 157.5 && yaw <= 202.5) || (yaw <= -157.5 && yaw >= -202.5)) {
            return I18n.getInstance().getTranslation(p, "obelouix.direction.north").toUpperCase(Locale.ROOT);
        }
        if ((yaw >= 202.5 && yaw <= 247.5) || (yaw <= -112.5 && yaw >= -157.5)) {
            return I18n.getInstance().getTranslation(p, "obelouix.direction.northeast").toUpperCase(Locale.ROOT);
        }
        if ((yaw >= 247.5 && yaw <= 292.5) || (yaw <= -67.5 && yaw >= -112.5)) {
            return I18n.getInstance().getTranslation(p, "obelouix.direction.east").toUpperCase(Locale.ROOT);
        }
        if ((yaw >= 292.5 && yaw <= 337.5) || (yaw <= -22.5 && yaw >= -67.5)) {
            return I18n.getInstance().getTranslation(p, "obelouix.direction.southeast").toUpperCase(Locale.ROOT);
        }
        return "Error!";
    }

}
