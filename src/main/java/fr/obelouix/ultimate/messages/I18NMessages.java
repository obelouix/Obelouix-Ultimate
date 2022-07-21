package fr.obelouix.ultimate.messages;

import fr.obelouix.ultimate.ObelouixUltimate;
import org.bukkit.command.CommandSender;

import java.util.Locale;

public enum I18NMessages {
    ADVANCEMENTS_ROOT("obelouix.advancements.root.title"),
    ADVANCEMENTS_ROOT_DESC("obelouix.advancements.root.description"),
    /**
     * Indicate to a player that he's not allowed to run the command he tried to execute
     */
    CLICK_TO_RUN("obelouix.click_to_run"),
    COMMAND_COORDS_ENABLED("obelouix.command.coords.enabled"),
    COMMAND_COORDS_DISABLED("obelouix.command.coords.disabled"),
    COMMAND_FREEZE_SENDER_TARGET_FROZEN("obelouix.command.freeze.sender.target_frozen"),
    COMMAND_FREEZE_SENDER_TARGET_UNFROZEN("obelouix.command.freeze.sender.target_unfrozen"),
    COMMAND_FREEZE_TARGET_IS_SENDER("obelouix.command.freeze.target_is_sender"),
    COMMAND_FREEZE_TARGET_FROZEN("obelouix.command.freeze.target.frozen"),
    COMMAND_FREEZE_TARGET_UNFROZEN("obelouix.command.freeze.target.unfrozen"),
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
    COMMAND_TIME_NOT_ALLOWED_TO_CHANGE_THIS_WORLD("obelouix.command.time.not_allowed_for_this_world"),
    COMMAND_WORLD_REQUIRED("obelouix.command.world_required"),
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
    PROTECTION_FREEZE("obelouix.protection.freeze"),
    TELEPORTING("obelouix.teleporting"),
    TELEPORTING_EVERYONE_SPAWN("obelouix.teleporting_everyone_spawn"),
    TELEPORTING_TARGET_SPAWN("obelouix.teleporting_target_spawn"),
    TELEPORTED_TO_SPAWN("obelouix.teleported_to_spawn"),
    UNKNOWN_COMMAND("obelouix.command.unknown"),
    /**
     * The message when the plugin check if it has updates
     */
    UPDATE_CHECK("obelouix.update.check"),
    /**
     * The message when there is no update available
     */
    UP_TO_DATE("obelouix.update.up_to_date"),
    WORLDEDIT_WAND_TITLE("obelouix.worldedit_wand.title");

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private final String translation;

    I18NMessages(String translation) {
        this.translation = translation;
    }

    public String getTranslation(CommandSender sender) {
        return plugin.getTranslationAPI().getTranslation(sender, translation);
    }

    public String getSystemTranslation() {
        return plugin.getTranslationAPI().getTranslation(new Locale(System.getProperty("user.language"), System.getProperty("user.country")), translation);
    }
}
