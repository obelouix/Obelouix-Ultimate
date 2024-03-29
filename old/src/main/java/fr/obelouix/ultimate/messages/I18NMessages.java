package fr.obelouix.ultimate.messages;

import fr.obelouix.ultimate.Main;
import org.bukkit.command.CommandSender;

import java.util.Locale;

public enum I18NMessages {
    ADVANCEMENTS_ROOT("obelouix.advancements.root.title"),
    ADVANCEMENTS_ROOT_DESC("obelouix.advancements.root.description"),
    /**
     * Indicate to a player that he's not allowed to run the command he tried to execute
     */
    COMMAND_COORDS_ENABLED("obelouix.command.coords.enabled"),
    COMMAND_COORDS_DISABLED("obelouix.command.coords.disabled"),
    COMMAND_COORDS_ALREADY_ENABLED("obelouix.command.coords.already.enabled"),
    COMMAND_COORDS_ALREADY_DISABLED("obelouix.command.coords.already.disabled"),
    COMMAND_NO_PERMISSION("no_permission"),
    /**
     * The message that show when there are not enough arguments provided to a command
     */
    COMMAND_NOT_ENOUGH_ARGS("obelouix.command.day.console.too_few_arguments"),
    /**
     * The message that tell the user behind the console that this command can only be run as a player
     */
    COMMAND_ONLY_FOR_PLAYER("obelouix.commands.not_for_console"),
    /**
     * The message that tell the command sender that he used the command badly
     */
    COMMAND_WRONG_USAGE("obelouix.wrong_command_usage"),
    DIRECTION("obelouix.direction"),
    DIRECTION_NORTH("obelouix.direction.north"),
    DIRECTION_NORTH_EAST("obelouix.direction.northeast"),
    DIRECTION_NORTH_WEST("obelouix.direction.northwest"),
    DIRECTION_SOUTH("obelouix.direction.south"),
    DIRECTION_SOUTH_EAST("obelouix.direction.southeast"),
    DIRECTION_SOUTH_WEST("obelouix.direction.southwest"),
    DIRECTION_EAST("obelouix.direction.east"),
    DIRECTION_WEST("obelouix.direction.west"),
    ENDERDRAGON_KILL_REWARD("obelouix.enderdragon.kill_reward"),
    ENDERDRAGON_KILL_BROADCAST("obelouix.enderdragon.broadcast"),
    ENDERDRAGON_KILL_BROADCAST_SINGLEPLAYER("obelouix.enderdragon.broadcast.single"),
    PING_COMMAND_SELF("obelouix.command.ping.self"),
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
    UP_TO_DATE("obelouix.update.up_to_date"),
    WORLDEDIT_WAND_TITLE("obelouix.worldedit_wand.title");

    private final String translation;

    I18NMessages(String translation) {
        this.translation = translation;
    }

/*    public String getTranslation(CommandSender sender) {
        return Main.getPlugin().getTranslationAPI().getTranslation(sender, translation);
    }*/

    public String getTranslationKey(){
        return translation;
    }

/*    public String getSystemTranslation() {
        return Main.getPlugin().getTranslationAPI().getTranslation(new Locale(System.getProperty("user.language"), System.getProperty("user.country")), translation);
    }*/
}
