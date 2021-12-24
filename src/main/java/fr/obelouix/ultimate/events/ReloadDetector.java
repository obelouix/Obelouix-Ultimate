package fr.obelouix.ultimate.events;

import com.google.common.collect.ImmutableList;
import fr.obelouix.ultimate.audience.MessageSender;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.i18n.I18n;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.List;

public class ReloadDetector implements Listener {

    private static final I18n i18n = I18n.getInstance();
    private final List<String> commands = ImmutableList.of("reload", "reload confirm", "rl", "rl confirm");

    @EventHandler
    public void playerCommandPreProcess(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        for (final String match : commands) {
            if (event.getMessage().equalsIgnoreCase("/" + match) && player.hasPermission("bukkit.command.reload")) {
                if(Config.isDisableReloadCommand()){
                    MessageSender.sendMessage(player, commandDisabledMessage(player));
                    event.setCancelled(true);
                } else {
                    MessageSender.sendMessage(player, warnMessage(player));
                }

                break;
            }
        }
    }

    @EventHandler
    public void consoleCommandPreProcess(ServerCommandEvent event) {
        for (final String match : commands) {
            if (event.getCommand().equalsIgnoreCase(match)) {
                if(Config.isDisableReloadCommand()){
                    MessageSender.sendMessage(event.getSender(), commandDisabledMessage(event.getSender()));
                    event.setCancelled(true);
                } else{
                    MessageSender.sendMessage(event.getSender(), warnMessage(event.getSender()));
                }
                break;
            }
        }
    }

    private Component warnMessage(CommandSender sender) {
        return Component.text(i18n.getTranslation(sender, "obelouix.reload_cmd_is_bad"))
                .color(NamedTextColor.DARK_RED)
                .replaceText(TextReplacementConfig.builder()
                        .matchLiteral("{0}")
                        .replacement(Component.text("https://madelinemiller.dev/blog/problem-with-reload/", Style.style(TextDecoration.UNDERLINED))
                                .color(NamedTextColor.WHITE)
                                .clickEvent(ClickEvent.openUrl("https://madelinemiller.dev/blog/problem-with-reload/")))
                        .build());
    }

    private Component commandDisabledMessage(CommandSender sender){
        return Component.text(i18n.getTranslation(sender, "obelouix.command.bukkit.reload.disabled"))
                .color(NamedTextColor.GREEN)
                .replaceText(TextReplacementConfig.builder()
                        .matchLiteral("{0}")
                        .replacement(Component.text("https://madelinemiller.dev/blog/problem-with-reload/", Style.style(TextDecoration.UNDERLINED))
                                .color(NamedTextColor.WHITE)
                                .clickEvent(ClickEvent.openUrl("https://madelinemiller.dev/blog/problem-with-reload/")))
                        .build());
    }

}
