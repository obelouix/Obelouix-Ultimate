package fr.obelouix.ultimate.events;

import fr.obelouix.ultimate.i18n.I18n;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ReloadDetector implements Listener {

    private static final I18n i18n = I18n.getInstance();

    @EventHandler
    public void commandPreProcess(PlayerCommandPreprocessEvent event){
        final Player player = event.getPlayer();
        if(event.getMessage().equalsIgnoreCase("/reload") || event.getMessage().equalsIgnoreCase("/reload confirm") ||
                event.getMessage().equalsIgnoreCase("/rl") || event.getMessage().equalsIgnoreCase("/rl confirm")) {
            if(player.hasPermission("bukkit.command.reload")) {
                final Component message = Component.text(i18n.getTranslation(player, "obelouix.reload_cmd_is_bad"))
                        .color(NamedTextColor.DARK_RED)
                        .replaceText(TextReplacementConfig.builder()
                                .matchLiteral("{0}")
                                .replacement(Component.text("https://madelinemiller.dev/blog/problem-with-reload/", Style.style(TextDecoration.UNDERLINED))
                                        .color(NamedTextColor.WHITE)
                                        .clickEvent(ClickEvent.openUrl("https://madelinemiller.dev/blog/problem-with-reload/")))
                                .build());
                player.sendMessage(message);
            }
        }
    }

}
