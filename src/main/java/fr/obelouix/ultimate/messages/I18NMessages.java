package fr.obelouix.ultimate.messages;

import fr.obelouix.ultimate.i18n.I18n;
import org.bukkit.command.CommandSender;

import java.util.Locale;

public enum I18NMessages {
    /**
     * Indicate to a player that he's not allowed to run the command he tried to execute
     */
    COMMAND_COORDS_ENABLED("obelouix.command.coords.enabled"),
    COMMAND_COORDS_DISABLED("obelouix.command.coords.disabled"),
    COMMAND_NO_PERMISSION("no_permission"),
    /**
     * The message that tell the user behind the console that this command can only be run as a player
     */
    COMMAND_ONLY_FOR_PLAYER("obelouix.commands.not_for_console"),
    DIRECTION("obelouix.direction"),
    DIRECTION_NORTH("obelouix.direction.north"),
    DIRECTION_NORTH_EAST("obelouix.direction.northeast"),
    DIRECTION_NORTH_WEST("obelouix.direction.northwest"),
    DIRECTION_SOUTH("obelouix.direction.south"),
    DIRECTION_SOUTH_EAST("obelouix.direction.southeast"),
    DIRECTION_SOUTH_WEST("obelouix.direction.southwest"),
    DIRECTION_EAST("obelouix.direction.east"),
    DIRECTION_WEST("obelouix.direction.west"),
    /**
     * The message that tell the plugin version
     */
    PLUGIN_VERSION("obelouix.plugin.version"),
    /**
     * The message when the plugin check if it has updates
     */
    UPDATE_CHECK("obelouix.update.check"),
    /**
     * The message when there is no update available
     */
    UP_TO_DATE("obelouix.update.up_to_date");

    private final String translation;

    I18NMessages(String translation) {
        this.translation = translation;
    }

    public String getTranslation(CommandSender sender) {
        return I18n.getInstance().getTranslation(sender, translation);
    }

    public String getSystemTranslation() {
        return I18n.getInstance().getTranslation(new Locale(System.getProperty("user.language"), System.getProperty("user.country")), translation);
    }
}
