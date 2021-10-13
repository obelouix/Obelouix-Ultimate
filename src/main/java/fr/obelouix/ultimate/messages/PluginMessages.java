package fr.obelouix.ultimate.messages;

import fr.obelouix.ultimate.i18n.I18n;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class PluginMessages {

    private static final I18n i18n = I18n.getInstance();
    private static final DecimalFormat format = new DecimalFormat("00");
    private static long worldHour;
    private static long worldMinute;

    /**
     * Create a component that will give the correct use of a given command
     *
     * @param command       - the command that will be displayed in the message
     * @param commandSender - the player/console who sended the command
     * @return a formatted message with the correct use of the given command
     */
    public static Component wrongCommandUsage(Command command, CommandSender commandSender) {
        return Component.text(i18n.getTranslation(commandSender, "obelouix.wrong_command_usage"))
                .color(NamedTextColor.DARK_RED)
                .replaceText(TextReplacementConfig.builder()
                        .matchLiteral("{0}")
                        .replacement(Component.text(command.getUsage()).color(NamedTextColor.RED))
                        .build());
    }

    /**
     * Send a message returning the time of the world based on the player world
     *
     * @param sender - the player
     * @param time   - time of the world in ticks
     * @return a formatted component with world's time
     */
    public static Component playerTimeMessage(CommandSender sender, int time) {
        final Player player = (Player) sender;
        calculateTime(time);

        return Component.text(i18n.getTranslation(player, "obelouix.command.time.set"))
                .color(NamedTextColor.GOLD)
                .replaceText(TextReplacementConfig.builder()
                        .matchLiteral("{0}")
                        .replacement(Component.text(player.getWorld().getName())
                                .color(NamedTextColor.RED))
                        .build())
                .replaceText(TextReplacementConfig.builder()
                        .matchLiteral("{1}")
                        .replacement(Component.text(format.format(worldHour) + "h" + format.format(worldMinute))
                                .color(NamedTextColor.RED))
                        .build());
    }

    /**
     * Send a message returning the time of the world based on the world name given in argument
     *
     * @param sender - console
     * @param world  - the world name
     * @param time   - time of the world in ticks
     * @return a formatted component with world's time
     */
    public static Component playerTimeMessage(CommandSender sender, String world, int time) {
        calculateTime(time);

        return Component.text(i18n.getTranslation(sender, "obelouix.command.time.set"))
                .color(NamedTextColor.GOLD)
                .replaceText(TextReplacementConfig.builder()
                        .matchLiteral("{0}")
                        .replacement(Component.text(world)
                                .color(NamedTextColor.RED))
                        .build())
                .replaceText(TextReplacementConfig.builder()
                        .matchLiteral("{1}")
                        .replacement(Component.text(format.format(worldHour) + "h" + format.format(worldMinute))
                                .color(NamedTextColor.RED))
                        .build());
    }

    /**
     * Allows calculating real time of the world in human format
     *
     * @param time - time of the world in ticks
     */
    private static void calculateTime(long time) {
        worldHour = time / 1000 + 6;
        worldMinute = (time % 1000) * 60 / 1000;

        if (worldHour > 23) {
            worldHour = worldHour - 24;
        }
    }

    public static Component nonExistentWorldMessage(CommandSender sender, String world) {
        return Component.text(i18n.getTranslation(sender, "obelouix.command.day.world_non_existent"))
                .color(NamedTextColor.DARK_RED)
                .replaceText(TextReplacementConfig.builder()
                        .matchLiteral("{0}")
                        .replacement(Component.text(world).color(NamedTextColor.RED))
                        .build());
    }
}
