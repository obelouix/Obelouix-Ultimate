package fr.obelouix.ultimate.i18n;

import fr.obelouix.ultimate.ObelouixUltimate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.Locale;

public class Translator {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static final GlobalTranslator translator = plugin.getI18n().getGlobalTranslator();

    /**
     * Translate a message to the player language
     *
     * @param key    translation key
     * @param player the {@link Player player}
     * @return a component translated to player language
     */
    public static Component translate(String key, Player player) {
        final MessageFormat message = translator.translate(key, player.locale());
        return Component.text(message.toPattern());
    }

    /**
     * Translate a message to the OS {@link Locale locale}
     *
     * @param key translation key
     * @return a component
     */
    public static Component translate(String key) {
        final String systemLocale = System.getProperty("user.language") + "_" + System.getProperty("user.country");
        final MessageFormat message = translator.translate(key, new Locale(systemLocale));
        return Component.text(message.toPattern());
    }

    public static class minimessage {

        /**
         * Translate a message to the player language.<br>This use {@link MiniMessage} formatting
         *
         * @param key    translation key
         * @param player the {@link Player player}
         * @return a component translated to player language
         */
        public static Component translate(String key, Player player) {
            final MessageFormat message = translator.translate(key, player.locale());
            return MiniMessage.miniMessage().deserialize(message.toPattern());
        }
    }


}
