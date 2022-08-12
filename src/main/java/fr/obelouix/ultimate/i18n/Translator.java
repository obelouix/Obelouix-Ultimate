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

    public static Component translate(String key, Player player) {
        final MessageFormat message = translator.translate(key, player.locale());
        return Component.text(message.toPattern());
    }

    public static Component translate(String key) {
        final String systemLocale = System.getProperty("user.language") + "_" + System.getProperty("user.country");
        final MessageFormat message = translator.translate(key, new Locale(systemLocale));
        return Component.text(message.toPattern());
    }

    public static class minimessage {
        public static Component translate(String key, Player player) {
            final MessageFormat message = translator.translate(key, player.locale());
            return MiniMessage.miniMessage().deserialize(message.toPattern());
        }
    }


}
