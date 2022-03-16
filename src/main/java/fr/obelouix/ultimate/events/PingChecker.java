package fr.obelouix.ultimate.events;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.audience.MessageSender;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.i18n.I18n;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class PingChecker implements Listener {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static final I18n i18n = I18n.getInstance();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void checkOnPlayerJoin(PlayerJoinEvent event) {
        checkPing(event.getPlayer());
    }

    private BukkitTask checkPing(final Player player) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                if (player.getPing() >= Config.getMaxPing()) {
                    final Component message = Component.text(i18n.getTranslation(player, "obelouix.message.ping.toohigh"))
                            .replaceText(builder ->
                                    builder.match("(%s")
                                            .replacement(
                                                    Component.text("(" + player.getPing() + " ms)", NamedTextColor.DARK_RED)
                                            ));

                    MessageSender.sendKickMessage(player, message, PlayerKickEvent.Cause.TIMEOUT);
                }
            }
        }.runTaskTimer(plugin, 20, 6000);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void stopTaskOnPlayerDisconnect(PlayerQuitEvent event) {
        if (!checkPing(event.getPlayer()).isCancelled()) {
            Bukkit.getServer().getScheduler().cancelTask(checkPing(event.getPlayer()).getTaskId());
        }
    }

}
