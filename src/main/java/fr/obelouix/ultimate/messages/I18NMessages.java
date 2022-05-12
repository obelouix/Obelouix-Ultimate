package fr.obelouix.ultimate.messages;

import fr.obelouix.ultimate.i18n.I18n;
import org.bukkit.command.CommandSender;

import java.util.Locale;

public enum I18NMessages {
    DIRECTION("obelouix.direction"),
    DIRECTION_NORTH("obelouix.direction.north"),
    DIRECTION_NORTH_EAST("obelouix.direction.northeast"),
    DIRECTION_NORTH_WEST("obelouix.direction.northwest"),
    DIRECTION_SOUTH("obelouix.direction.south"),
    DIRECTION_SOUTH_EAST("obelouix.direction.southeast"),
    DIRECTION_SOUTH_WEST("obelouix.direction.southwest"),
    DIRECTION_EAST("obelouix.direction.east"),
    DIRECTION_WEST("obelouix.direction.west"),
    UPDATE_CHECK("obelouix.update.check"),
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
